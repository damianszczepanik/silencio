package pl.szczepanik.silencio.processors.visitors;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.core.Value;

/**
 * Iterates over XML nodes and calls {@link #processValue(Key, Object)} for each basic node.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class XMLVisitor extends AbstractVisitor {

    static final String EXCEPTION_MESSAGE_NODE_TYPE_UNSUPPORTED = "Node with type %d, name '%s' and value '%s' is not supported";

    /**
     * Process passed JSON map and iterates over each node.
     * 
     * @param rootElement
     *            XML root element to iterate over
     */
    public void processXML(Element rootElement) {
        processAttributes(rootElement.getAttributes());
        processNodes(rootElement.getChildNodes());
    }

    private void processNodes(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            processNode(node);

            processAttributes(node.getAttributes());
            processNodes(node.getChildNodes());
        }
    }

    private void processAttributes(NamedNodeMap attributes) {
        int attributeLength = attributes == null ? 0 : attributes.getLength();
        if (attributeLength != 0) {
            for (int i = 0; i < attributeLength; i++) {
                processNode(attributes.item(i));
            }
        }
    }

    /**
     * Process the node with its attributes but without its children.
     * 
     * @param node
     *            node to process
     */
    private void processNode(Node node) {
        node.normalize();

        short type = node.getNodeType();
        switch (type) {
        case Node.ELEMENT_NODE:
        case Node.ATTRIBUTE_NODE:
            convertNodeIfNeeded(node, node.getNodeName());
            break;
        case Node.TEXT_NODE:
            convertNodeIfNeeded(node, node.getParentNode().getNodeName());
            break;

        default:
            // looks like new type of node is present so there must be done additional
            // switch-case with support or ignore such situation
            // if you get this, raise the issue
            throw new ProcessorException(String.format(EXCEPTION_MESSAGE_NODE_TYPE_UNSUPPORTED,
                    type, node.getNodeName(), node.getNodeValue()));
        }
    }

    private void convertNodeIfNeeded(Node node, String key) {
        if (shouldConvert(node)) {
            final String pureValue = StringUtils.trim(node.getNodeValue());
            Value newValue = processValue(new Key(key), pureValue);
            node.setNodeValue(newValue.getValue().toString());
        }
    }

    private boolean shouldConvert(Node node) {
        return StringUtils.isNotBlank(StringUtils.trim(node.getNodeValue()));
    }
}
