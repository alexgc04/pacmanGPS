package pacman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class BuildConfigurationTest {

    @Test
    void jarPluginIncludesMainClass() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("pom.xml"));

        String mainClassXPath =
            "/*[local-name()='project']/*[local-name()='build']/*[local-name()='plugins']"
                + "/*[local-name()='plugin'][*[local-name()='artifactId' and text()='maven-jar-plugin']]"
                + "/*[local-name()='configuration']/*[local-name()='archive']/*[local-name()='manifest']/*[local-name()='mainClass']";

        XPath xPath = XPathFactory.newInstance().newXPath();
        Node mainClassNode = (Node) xPath.evaluate(mainClassXPath, document, XPathConstants.NODE);

        assertNotNull(mainClassNode);
        assertEquals("pacman.Game", mainClassNode.getTextContent());
    }
}
