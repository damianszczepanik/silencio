package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import pl.szczepanik.silencio.core.ExecutionConfig;
import pl.szczepanik.silencio.mocks.ConverterVisitor;
import pl.szczepanik.silencio.stubs.StubExecutionConfig;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public class PropertiesVisitorTest {

    private Reader input;

    @Test
    public void shouldVisitAllJsonNodes() throws IOException {

        final int nodesCounter = 14;

        // given
        input = ResourceLoader.loadPropertiesAsReader("suv.properties");
        Properties properties = new Properties();
        properties.load(input);

        PropertiesVisitor visitor = new PropertiesVisitor();
        ConverterVisitor visitCounter = new ConverterVisitor();
        ExecutionConfig[] executionConfigs = StubExecutionConfig.asList(visitCounter);
        visitor.setExecutionConfigs(executionConfigs);

        // when
        visitor.process(properties);

        // then
        assertThat(visitCounter.getVisitCounter()).isEqualTo(nodesCounter);
    }

    @After
    public void closeStreams() {
        IOUtils.closeQuietly(input);
    }
}
