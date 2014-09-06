package maptypes;

import exceptions.FileNotValidException;
import exceptions.SummonerNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import replay.ReplayFile;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by philipp on 16.08.2014.
 */
public class ChampMap implements ReplayDataMap {

    private String[] names;

    public ChampMap(String[] lol)
    {
        this.names = lol;
    }

    private Map<String, Integer> championMap = new TreeMap<String, Integer>();

    public Map<String, Integer> getChampionMap() {
        return championMap;
    }

    public void addReplay(JSONObject file) throws JSONException, SummonerNotFoundException, IOException, FileNotValidException {
        ReplayFile repFile = new ReplayFile(names,file);
        String championname = MapFactory.fixName(repFile.getChampion());
        Integer anzahl = championMap.get(championname);
        if (anzahl == null)
            anzahl = 0;
        championMap.put(championname, ++anzahl);
    }
}
