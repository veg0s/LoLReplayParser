package Map;

import Exception.FileNotValidException;
import Exception.SummonerNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import replay.ReplayFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GameMap implements ReplayDataMap {

    private String[] names;
    private Map<Date,Integer> map = new HashMap();
    private ReplayFile repFile;

    public GameMap(String[] name)
    {
          this.names = name;
    }

	public Map<Date,Integer> getMap()
	{
		return map;
	}

    @Override
    public void addReplay(JSONObject file) throws JSONException, SummonerNotFoundException, IOException, FileNotValidException {
        ReplayFile repFile = new ReplayFile(names, file);
        Date date = repFile.getDate();
        Integer gaemz = map.get(date);
        if(gaemz == null)
            map.put(date, 1);
        else
            map.put(date, gaemz + 1);
    }
}
