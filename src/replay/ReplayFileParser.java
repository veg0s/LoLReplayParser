package replay;

import Exception.FileNotValidException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by philipp on 06.09.2014.
 */
public class ReplayFileParser {
    public static JSONObject getJsonFromReplay(File replayFile) throws IOException, JSONException, FileNotValidException {
        JSONObject obj;
        BufferedReader reader = new BufferedReader(new FileReader(replayFile));
        String jsonString;
        while(!(jsonString = reader.readLine()).contains("{"));
        int start = jsonString.indexOf("{");
        int end = jsonString.lastIndexOf("}") + 1;
        if (start != -1 && end != -1 && start < end) {
            jsonString = jsonString.substring(start, end);
            obj = new JSONObject(jsonString);
        } else
            throw new FileNotValidException("File not valid [" + replayFile.getName() + "]");
        return obj;
    }
}
