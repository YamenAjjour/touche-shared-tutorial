import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

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
            InputStream inputStream = new FileInputStream(topicPath+"topics.csv");
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer);
            String text = writer.toString();
            CSVParser parser = CSVParser.parse(text, CSVFormat.RFC4180.withHeader("Topic ID","Biased","Annotator Stance","Thesis","Description","Query").withSkipHeaderRecord(true));
            List<CSVRecord> list = parser.getRecords();
            for (CSVRecord record : list)
            {

                String topicId= record.get("Topic ID");
                Topic topic = new Topic();
                topic.topicId=topicId;
                topic.text=record.get("Query");
                LOGGER.info(String.format("reading topic %s",topic.text));
                topics.add(topic);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return topics;
    }
}
