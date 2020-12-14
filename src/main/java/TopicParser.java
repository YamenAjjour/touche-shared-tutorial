import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public class TopicParser {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static List<Topic> loadTopics(String topicPath)
    {
        ArrayList<Topic> topics= new ArrayList<Topic>();

        try {
            File topicsFile = new File(topicPath+"/topics-task-1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(topicsFile);
            NodeList topicsNodes =  doc.getElementsByTagName("topic");

            for(int i = 0; i<topicsNodes.getLength();i++)
            {
                Element topicElement = (Element) topicsNodes.item(i);
                Topic topic = new Topic();
                topic.text= topicElement.getElementsByTagName("title").item(0).getTextContent();
                String topicId= topicElement.getElementsByTagName("number").item(0).getTextContent();
                LOGGER.info(String.format("reading topic %s",topic.text));
                topic.topicId=topicId;
		LOGGER.info(String.format("reading topic id%s",topic.topicId));
                topics.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topics;
    }
}
