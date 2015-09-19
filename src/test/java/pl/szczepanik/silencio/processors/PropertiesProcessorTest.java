package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.mocks.PropertyVisitorHolder;
import pl.szczepanik.silencio.mocks.WriterCrashOnWrite;
import pl.szczepanik.silencio.utils.ReflectionUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesProcessorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Writer output;
    private Reader input;

    @Test
    public void shouldReturnProperFormat() {
         
        // given
        PropertiesProcessor processor = new PropertiesProcessor ();
        
        // when
        Format format = processor.getFormat();
        
        // then
        assertThat(format).isEqualTo(Format.PROPERTIES);
    }

    @Test
    public void shouldLoadPropertiesFileOnRealLoad() throws IOException {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");

        Properties refProps = new Properties();
        // input once read cannot be read again to need to have two streams to the same file
        Reader refInput = ResourceLoader.loadPropertiesAsReader("suv.properties");
        refProps.load(refInput);

        // when
        PropertiesProcessor processor = new PropertiesProcessor();
        processor.realLoad(input);
        Properties properties = (Properties) ReflectionUtility.getField(processor, "properties");

        // then
        assertThat(properties).isEqualTo(refProps);

        IOUtils.closeQuietly(refInput);
    }

    @Test
    public void shouldFailWhenLoadingInvalidPropertiesFile() {

        // given
        input = ResourceLoader.loadPropertiesAsReader("corrupted.properties");
        PropertiesProcessor processor = new PropertiesProcessor();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(containsString("Malformed \\uxxxx encoding."));
        processor.realLoad(input);
    }

    @Test
    public void shouldCallProcessByRealProcess() {

        // given
        PropertiesProcessor processor = new PropertiesProcessor();
        PropertyVisitorHolder visitorMock = new PropertyVisitorHolder();
        ReflectionUtility.setField(processor, "visitor", visitorMock);

        Properties properties = new Properties();
        ReflectionUtility.setField(processor, "properties", properties);

        // when
        processor.realProcess();
        
        // then
        assertThat(visitorMock.getProperties()).isEqualTo(properties);
    }

    @Test
    public void shouldFailOnRealWrite() {

        // given
        final String errorMessage = "Don't write into this writer any more!";
        PropertiesProcessor processor = new PropertiesProcessor();
        Writer writer = new WriterCrashOnWrite(errorMessage);

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        processor.realWrite(writer);
    }

    @Test
    public void shouldWritePropertiesInitoWRiter() throws IOException {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        output = new StringWriter();

        PropertiesProcessor processor = new PropertiesProcessor();
        Properties properties = new Properties();
        properties.load(input);
        ReflectionUtility.setField(processor, "properties", properties);

        // when
        processor.realWrite(output);

        // then
        Properties refProps = new Properties();
        refProps.load(new StringReader(output.toString()));
        assertThat(properties).isEqualTo(refProps);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
    }

}