package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.mocks.ProcessableCounter;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesVisitorTest {

    private Reader input;

    @Test
    public void shouldVisitAllJsonNodes() throws IOException {

        final int nodeCounter = 14;

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        Properties properties = new Properties();
        properties.load(input);
        ProcessableCounter processableVisitor = new ProcessableCounter();

        // when
        PropertiesVisitor visitor = new PropertiesVisitor(processableVisitor);
        visitor.process(properties);

        // then
        assertThat(processableVisitor.getVisitCounter()).isEqualTo(nodeCounter);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
    }
}
