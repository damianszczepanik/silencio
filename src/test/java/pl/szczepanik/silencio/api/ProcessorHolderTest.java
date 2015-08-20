package pl.szczepanik.silencio.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.szczepanik.silencio.StubProcessor;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class ProcessorHolderTest {

    // unchanged processor used for most methods
    private static final Processor uniqueType = new StubProcessor(Format.XML, "thisIsUniqueProcessorType");

    private static final Processor XML_1 = new StubProcessor(Format.XML, "uno");
    private static final Processor JSON_1 = new StubProcessor(Format.JSON, "one");
    private static final Processor XML_2 = new StubProcessor(Format.XML, "ein");

    private static ProcessorHolder factory;

    @Test
    public void shouldFilterProcessorByType() {

        // when
        Processor xmlType = new StubProcessor(Format.XML, "test");

        // when
        factory.register(xmlType);

        // then
        Set<Processor> processors = factory.findByFormat(Format.XML);
        assertThat(processors).hasSize(1);
        assertThat(processors).contains(xmlType);
    }

    @Test
    public void shouldFilterOutProcessorByName() {

        // when
        factory.register(uniqueType);

        // then
        Processor processor = factory.findByName(uniqueType.getName());
        assertThat(processor).isNotNull();
    }

    @Test
    public void shouldNotAllowToRegisterProcessorTwice() {

        // when
        factory.register(uniqueType);

        // then
        try {
            factory.register(uniqueType);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Processor with name 'thisIsUniqueProcessorType' has been already registered!");
        }
    }

    @Test
    public void shouldNotAllowToRegisterProcessorWithoutType() {

        // when
        Processor xmlType = new StubProcessor(null, "test");
    
        // then
        try {
            factory.register(xmlType);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Type of the processor must not be empty!");
        }
    }

    @Test
    public void shouldNotAllowToRegisterProcessorWithoutName() {

        // when
        Processor xmlType = new StubProcessor(Format.JSON, null);

        // then
        try {
            factory.register(xmlType);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Name of the processor must not be empty!");
        }
    }

    @Test
    public void shouldUnregisterProcessor() {

        // given
        factory.register(uniqueType);

        // when
        factory.unregister(uniqueType);

        // then
        Set<Processor> processors = factory.findByFormat(uniqueType.getFormat());
        assertThat(processors).isEmpty();
    }

    @Test
    public void shouldReturnAllRegisteredProcessors() {

        // when
        factory.register(XML_1);
        factory.register(JSON_1);
        factory.register(XML_2);

        // then
        Set<Processor> processors = factory.findByFormat(Format.XML);
        assertThat(processors).hasSize(2);
        assertThat(processors).contains(XML_1, XML_2);
    }

    @Test
    public void shouldReturnAllRegisteredFormats() {


        // when
        factory.register(XML_1);
        factory.register(JSON_1);
        factory.register(XML_2);

        // then
        Set<Format> processors = factory.getRegisteredFormats();
        assertThat(processors).hasSize(2);
        assertThat(processors).contains(Format.JSON, Format.XML);
    }

    @BeforeClass
    public static void beforeClass() {
        factory = DefaultFactory.getInstance();
    }

    @Before
    public void before() {
        // unregister supported processors before each test
        factory.unregisterAll();
    }
}
