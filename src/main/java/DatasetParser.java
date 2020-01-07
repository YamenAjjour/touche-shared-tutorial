import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import org.apache.commons.io.IOUtils;
public class DatasetParser {

    public static List<Argument>  loadDataset(String inputPath)
    {
        ArrayList<Argument> arguments = new ArrayList<Argument>();
        try {
            InputStream is = new FileInputStream(inputPath+"args-me.json");
            String jsonTxt = IOUtils.toString(is, "UTF-8");

            JSONObject jsonObj = new JSONObject(jsonTxt);
            JSONArray jsonArguments = jsonObj.getJSONArray("arguments");
            for (int i=0;i<jsonArguments.length();i++)
            {
                JSONObject josnArgument = jsonArguments.getJSONObject(i);
                Argument argument = new Argument();
                arguments.add(argument);
                argument.conclusion=josnArgument.getString("conclusion");
                JSONObject premise = josnArgument.getJSONArray("premises").getJSONObject(0);
                argument.premise= premise.getString("text");
                argument.stance = premise.getString("stance");
                argument.id = josnArgument.getString("id");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arguments;
    }
}
