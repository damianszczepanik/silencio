package pl.szczepanik.silencio.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.szczepanik.silencio.StubGenerator;

/**
 * Default factory implementation that holds generators.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class GeneratorFactoryTest {

    // unchanged generator used for most methods
    private static final Generator uniqueType = new StubGenerator(Format.XML, "thisIsUniqueGeneratorType");

    private static final Generator XML_1 = new StubGenerator(Format.XML, "uno");
    private static final Generator JSON_1 = new StubGenerator(Format.JSON, "one");
    private static final Generator XML_2 = new StubGenerator(Format.XML, "ein");

    private static GeneratorFactory factory;

    @Test
    public void shouldFilterGeneratorByType() {

        // when
        Generator xmlType = new StubGenerator(Format.XML, "test");

        // when
        factory.register(xmlType);

        // then
        Set<Generator> generators = factory.findByFormat(Format.XML);
        assertThat(generators).hasSize(1);
        assertThat(generators).contains(xmlType);
    }

    @Test
    public void shouldFilterOutGeneratorByName() {

        // when
        factory.register(uniqueType);

        // then
        Generator generator = factory.findByName(uniqueType.getName());
        assertThat(generator).isNotNull();
    }

    @Test
    public void shouldNotAllowToRegisterGeneratorTwice() {

        // when
        factory.register(uniqueType);

        // then
        try {
            factory.register(uniqueType);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Generator with name 'thisIsUniqueGeneratorType' has been already registered!");
        }
    }

    @Test
    public void shouldNotAllowToRegisterGeneratorWithoutType() {

        // when
        Generator xmlType = new StubGenerator(null, "test");
    
        // then
        try {
            factory.register(xmlType);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Type of the generator must not be empty!");
        }
    }

    @Test
    public void shouldNotAllowToRegisterGeneratorWithoutName() {

        // when
        Generator xmlType = new StubGenerator(Format.JSON, null);

        // then
        try {
            factory.register(xmlType);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Name of the generator must not be empty!");
        }
    }

    @Test
    public void shouldUnregisterGenerator() {

        // given
        factory.register(uniqueType);

        // when
        factory.unregister(uniqueType);

        // then
        Set<Generator> generators = factory.findByFormat(uniqueType.getFormat());
        assertThat(generators).isEmpty();
    }

    @Test
    public void shouldReturnAllRegisteredGenerators() {

        // when
        factory.register(XML_1);
        factory.register(JSON_1);
        factory.register(XML_2);

        // then
        Set<Generator> generators = factory.findByFormat(Format.XML);
        assertThat(generators).hasSize(2);
        assertThat(generators).contains(XML_1, XML_2);
    }

    @Test
    public void shouldReturnAllRegisteredFormats() {


        // when
        factory.register(XML_1);
        factory.register(JSON_1);
        factory.register(XML_2);

        // then
        Set<Format> generators = factory.getRegisteredFormats();
        assertThat(generators).hasSize(2);
        assertThat(generators).contains(Format.JSON, Format.XML);
    }

    @BeforeClass
    public static void beforeClass() {
        factory = DefaultFactory.getInstance();
    }

    @Before
    public void before() {
        // unregister supported generators before each test
        factory.unregisterAll();
    }
}
