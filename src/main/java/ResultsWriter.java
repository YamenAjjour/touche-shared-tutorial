import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.logging.Logger;

public class ResultsWriter {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    static void  writeResults(SearchResult[] results, String path)
    {

        try {
            FileWriter fw = new FileWriter(path);

            for (int i = 0; i < results.length; i++) {
                for(int j =0;j<results[i].argumentIds.length;j++)
                {
                    LOGGER.info(String.format("Writing results for Topic %s",results[i].topicId));
                    fw.write(results[i].topicId+" Q0 "+results[i].argumentIds[j]+" "+results[i].scores[j]+" tutorial \n");
                }

            }

            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
