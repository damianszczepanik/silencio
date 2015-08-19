package pl.szczepanik.silencio.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.szczepanik.silencio.StubGenerator;

public class GeneratorFactoryTest {

    // unchanged generator used for most methods
    private static final Generator uniqueType = new StubGenerator(SupportedTypes.XML, "thisIsUniqueGeneratorType");

    private static GeneratorFactory factory;

    @Test
    public void shouldFilterGeneratorByType() {

        // when
        Generator xmlType = new StubGenerator(SupportedTypes.XML, "test");

        // when
        factory.register(xmlType);

        // then
        Set<Generator> generators = factory.findByType(SupportedTypes.XML);
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
        Generator xmlType = new StubGenerator(SupportedTypes.JSON, null);

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
        Set<Generator> generators = factory.findByType(uniqueType.getType());
        assertThat(generators).isEmpty();
    }

    @Test
    public void shouldReturnAllRegisteredGenerators() {

        // given
        Generator g1 = new StubGenerator(SupportedTypes.XML, "uno");
        Generator g2 = new StubGenerator(SupportedTypes.JSON, "one");
        Generator g3 = new StubGenerator(SupportedTypes.XML, "ein");

        // when
        factory.register(g1);
        factory.register(g2);
        factory.register(g3);

        // then
        Set<Generator> generators = factory.findByType(SupportedTypes.XML);
        assertThat(generators).hasSize(2);
        assertThat(generators).contains(g1, g3);
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
