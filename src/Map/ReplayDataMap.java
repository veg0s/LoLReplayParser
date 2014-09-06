package Map;

import org.json.JSONException;
import org.json.JSONObject;
import Exception.*;
import java.io.IOException;

/**
 * Created by philipp.hentschel on 04.09.2014.
 */
public interface ReplayDataMap {
    public void addReplay(JSONObject file) throws JSONException, SummonerNotFoundException, IOException, FileNotValidException;
}
