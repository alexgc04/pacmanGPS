package pacman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static final String DISALLOW_DOCTYPE_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String BUILD_PLUGINS_XPATH = "/*[local-name()='project']/*[local-name()='build']/*[local-name()='plugins']";
    private static final String JAR_PLUGIN_FILTER = "/*[local-name()='plugin'][*[local-name()='artifactId' and text()='maven-jar-plugin']]";
    private static final String MAIN_CLASS_PATH = "/*[local-name()='configuration']/*[local-name()='archive']/*[local-name()='manifest']/*[local-name()='mainClass']";
    private static final String MAIN_CLASS_XPATH = BUILD_PLUGINS_XPATH + JAR_PLUGIN_FILTER + MAIN_CLASS_PATH;

    @Test
    void jarPluginIncludesMainClass() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature(DISALLOW_DOCTYPE_FEATURE, true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(locatePom());

        XPath xPath = XPathFactory.newInstance().newXPath();
        Node mainClassNode = (Node) xPath.evaluate(MAIN_CLASS_XPATH, document, XPathConstants.NODE);

        assertNotNull(mainClassNode);
        assertEquals("pacman.Game", mainClassNode.getTextContent());
    }

    private File locatePom() {
        Path current = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        while (current != null) {
            Path candidate = current.resolve("pom.xml");
            File candidateFile = candidate.toFile();
            if (candidateFile.isFile()) {
                return candidateFile;
            }
            current = current.getParent();
        }
        throw new IllegalStateException("Unable to locate pom.xml from working directory");
    }
}
