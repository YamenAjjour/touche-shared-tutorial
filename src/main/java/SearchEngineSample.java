import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;

public class SearchEngineSample {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static IndexWriter createIndex()
    {
        LOGGER.info("Creating Index");
        IndexSearcher searcher;
        IndexWriter writter = null;
        try {
            Directory index =  FSDirectory.open(Paths.get("/mnt/disk1/search-engine-sample/index"));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            writter = new IndexWriter(index, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writter;
    }
    public static void indexArguments(List<Argument> arguments)
    {
        IndexWriter writter = createIndex();
        int counter = 0;
        float all=arguments.size();
        for (Argument argument : arguments)
        {
            counter = counter +1;
            if (counter%100==0)
                LOGGER.info(String.format("Indexed %f of the arguments current id is %s ", counter/all,argument.id));
            try {
                Document document = new Document();
                document.add(new TextField("conclusion", argument.conclusion, Field.Store.YES));
                document.add(new TextField("premise", argument.premise, Field.Store.YES));
                document.add(new TextField("stance", argument.stance, Field.Store.YES));
                document.add(new TextField("id", argument.id, Field.Store.YES));

                writter.addDocument(document);
                writter.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writter.close();

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
        LOGGER.info("Finished indexing");
    }

    public static IndexSearcher loadSearcher()
    {

        IndexSearcher searcher = null;
        try {
            Directory index =  FSDirectory.open(Paths.get("/mnt/disk1/search-engine-sample/index"));
            IndexReader reader=  DirectoryReader.open(index);
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searcher;
    }
    public static SearchResult search(IndexSearcher searcher, Topic topic,int n) {
       try {
           SearchResult searchResult = new SearchResult();
           String [] argumentIds = new String[n];
           Float [] scores= new Float[n];
           String[] fields = {"conclusion", "premise"};
           BooleanClause.Occur[] flags = {
                   BooleanClause.Occur.SHOULD,
                   BooleanClause.Occur.SHOULD

           };
           BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
           for (String token : topic.text.toString().split(" ")) {
               booleanQueryBuilder.add(
                       MultiFieldQueryParser.parse(token, fields, flags, new StandardAnalyzer()),
                       BooleanClause.Occur.SHOULD
               );

           }


           TopDocs hits = searcher.search(booleanQueryBuilder.build(),n);
           for(int i=0;i<hits.scoreDocs.length;++i) {
               int docId = hits.scoreDocs[i].doc;
               Document d = searcher.doc(docId);
               argumentIds[i]=d.get("id");
               scores[i]=hits.scoreDocs[i].score;
           }
           searchResult.topicId=topic.topicId;
           searchResult.argumentIds=argumentIds;
           searchResult.scores=scores;
           return searchResult;
       }
       catch (Exception e)
       {

       }
       return null;
    }
    public static Query parseQuery(String query)
    {
        Query parsedQuery=null;

        try {
            QueryParser queryParser = new QueryParser(query, new StandardAnalyzer());
            parsedQuery = queryParser.parse(query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedQuery;
    }


}
