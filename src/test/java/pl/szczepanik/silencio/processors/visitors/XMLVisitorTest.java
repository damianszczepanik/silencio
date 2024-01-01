package pl.szczepanik.silencio.processors.visitors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.ConverterVisitor;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class XMLVisitorTest extends GenericTest {

    @Test
    void shouldReportExceptionOnUnsupportedModel() throws Exception {

        // when
        final String key = "myKey";
        final Object value = new Object();
        JSONVisitor parserr = new JSONVisitor();

        // then
        assertThatThrownBy(() -> Whitebox.invokeMethod(parserr, "processComplex", key, value))
                .isInstanceOf(ProcessorException.class)
                .hasMessage("Unknown type of the key: " + value.getClass().getName());
    }

    @Test
    void shouldVisitAllXMLNodes() throws ParserConfigurationException, SAXException, IOException {

        final int nodeCounter = 14;

        // given
        input = ResourceLoader.loadAsReader("suv.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(input));

        ConverterVisitor visitCounter = new ConverterVisitor();
        Execution execution = new Execution(new PositiveDecision(), visitCounter);
        XMLVisitor visitor = new XMLVisitor();
        visitor.setConfiguration(new Configuration(execution));

        // when
        visitor.processXML(document.getDocumentElement());

        // then
        assertThat(visitCounter.getVisitCounter()).isEqualTo(nodeCounter);
    }

    @Test
    void shouldFailOnInvalidNodeType() throws Exception {

        // given
        XMLVisitor visitor = new XMLVisitor();
        final short invalidNodeType = -1;
        final String nodeName = "myName";
        final String nodeValue = "yourValue";

        // when
        Node node = mock(Node.class);
        when(node.getNodeType()).thenReturn(invalidNodeType);
        when(node.getNodeName()).thenReturn(nodeName);
        when(node.getNodeValue()).thenReturn(nodeValue);

        // then
        assertThatThrownBy(() -> Whitebox.invokeMethod(visitor, "processNode", node))
                .isInstanceOf(ProcessorException.class)
                .hasMessage((String.format(XMLVisitor.EXCEPTION_MESSAGE_NODE_TYPE_UNSUPPORTED, invalidNodeType,
                        nodeName, nodeValue)));
    }
}
