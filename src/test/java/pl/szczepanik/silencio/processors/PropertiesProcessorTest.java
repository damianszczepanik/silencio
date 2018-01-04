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
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.mocks.PropertyVisitorHolder;
import pl.szczepanik.silencio.mocks.WriterCrashOnWrite;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PropertiesProcessorTest extends GenericTest {

    @Test
    public void shouldReturnPassedFormat() {
         
        // given
        PropertiesProcessor processor = new PropertiesProcessor();

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
        Reader referenceInput = ResourceLoader.loadPropertiesAsReader("suv.properties");
        refProps.load(referenceInput);

        // when
        PropertiesProcessor processor = new PropertiesProcessor();
        processor.realLoad(input);
        Properties properties = Whitebox.getInternalState(processor, "properties");

        // then
        assertThat(properties).isEqualTo(refProps);

        IOUtils.closeQuietly(referenceInput);
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
    public void shouldInvokeProcessByRealProcess() {

        // given
        PropertiesProcessor processor = new PropertiesProcessor();
        PropertyVisitorHolder visitorMock = new PropertyVisitorHolder();
        Whitebox.setInternalState(processor, "visitor", visitorMock);

        Properties properties = new Properties();
        Whitebox.setInternalState(processor, "properties", properties);

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
    public void shouldWritePropertiesIntoWriter() throws IOException {

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        output = new StringWriter();

        PropertiesProcessor processor = new PropertiesProcessor();
        Properties properties = new Properties();
        properties.load(input);
        Whitebox.setInternalState(processor, "properties", properties);

        // when
        processor.realWrite(output);

        // then
        Properties refProps = new Properties();
        refProps.load(new StringReader(output.toString()));
        assertThat(properties).isEqualTo(refProps);
    }

}