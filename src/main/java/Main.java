import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Handler;

import org.apache.lucene.search.IndexSearcher;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;



public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public  static  void main (String[] args) throws Exception {
        String inputPath= args[1];
        String outputPath =args[2];
        LOGGER.info(String.format("input path is %s",inputPath));
        LOGGER.info(String.format("output path is %s",outputPath));
        if (inputPath == null || inputPath.length()==0)

            throw  new Exception(String.format("Input path is missing %s",inputPath));
        if(outputPath == null || outputPath.length()==0)
            throw  new Exception("Output path is missing");

        List<Topic> topics = TopicParser.loadTopics(inputPath);
        try {
            Handler fileHandler  = new FileHandler("./search-engine.log");
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //LOGGER.info("loading dataset");
        List<Argument> arguments = DatasetParser.loadDataset(inputPath);
        //LOGGER.info("indexing arguments");
        if(!Files.exists(Paths.get("/mnt/disk1/search-engine-sample/index"))) {
            SearchEngineSample.indexArguments(arguments);
        }
        IndexSearcher searcher = SearchEngineSample.loadSearcher();
        SearchResult[] results = new SearchResult[40];
        int i = 0;
        for (Topic topic: topics)
        {

            try {

                SearchResult result = SearchEngineSample.search(searcher,topic,50);
                results[i]=result;
                i++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ResultsWriter.writeResults(results,outputPath+"results.csv");
    }


}
