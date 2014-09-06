package maptypes;

import exceptions.FileNotValidException;
import exceptions.SummonerNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import replay.ReplayFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class DateMap implements ReplayDataMap {

    private String[] names;
	private Map<String,Map<Date,Integer>> map = new TreeMap<String, Map<Date,Integer>>();
    private ReplayFile repFile;

    public DateMap(String[] names)
    {
        this.names = names;
    }

	public Map<String, Map<Date, Integer>> getMap() {
		return map;
	}
    @Override
    public void addReplay(JSONObject file) throws JSONException, SummonerNotFoundException, IOException, FileNotValidException {
        ReplayFile repFile = new ReplayFile(names,file);
        String championname = MapFactory.fixName(repFile.getChampion());
        Map<Date,Integer> datumAnzahl = map.get(championname);
        Date datum = repFile.getDate();
        if (datumAnzahl == null)
        {
            datumAnzahl = new HashMap<Date,Integer>();
            datumAnzahl.put(datum,1);
        } else
        {
            Object anzahl = datumAnzahl.get(datum);
            if(anzahl == null)
            {
                datumAnzahl.put(datum, 1);
                map.put(championname, datumAnzahl);
            } else
            {
                datumAnzahl.put(datum, (Integer)anzahl + 1);
                map.put(championname, datumAnzahl);
            }
        }
        map.put(championname, datumAnzahl);

    }
}
