package pacman;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
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
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("pom.xml"));

        XPath xPath = XPathFactory.newInstance().newXPath();
        Node mainClassNode = (Node) xPath.evaluate(
            "/*[local-name()='project']/*[local-name()='build']/*[local-name()='plugins']"
                + "/*[local-name()='plugin'][*[local-name()='artifactId' and text()='maven-jar-plugin']]"
                + "/*[local-name()='configuration']/*[local-name()='archive']/*[local-name()='manifest']/*[local-name()='mainClass']",
            document,
            XPathConstants.NODE);

        assertEquals("pacman.Game", mainClassNode.getTextContent());
    }
}
