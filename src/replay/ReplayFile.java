package replay;

import Exception.FileNotValidException;
import Exception.SummonerNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Created by philipp on 16.08.2014.
 */
public class ReplayFile {
    JSONObject obj;
    String[] summonerName;
    String champion;

    public ReplayFile(String[] summonerName,JSONObject jsonString) throws SummonerNotFoundException, FileNotValidException, IOException,NullPointerException {
        try {
            this.summonerName = summonerName;
            this.obj = jsonString;
            this.champion = getValueFromPlayer("champion",summonerName);

        } catch (JSONException e) {
            throw new FileNotValidException("JSON");
        }
    }

    private String getValueFromPlayer(String key,String[] playerNames) throws JSONException, SummonerNotFoundException {
        JSONArray array = obj.getJSONArray("players");
        for (int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            for(String naem : playerNames)
            {
                if (row.getString("summoner").toLowerCase().equals(naem.toLowerCase())) {
                    String value = row.getString(key).replaceAll("[^a-zA-Z]", "");
                    return value;
                }
            }
        }
        throw new SummonerNotFoundException();
    }

    public Object getRepayValue(String key) throws JSONException, SummonerNotFoundException {
        if(!isPlayerInReplay())
            throw new SummonerNotFoundException();
        return obj.get(key);
    }

    public boolean isPlayerInReplay() throws JSONException {
        JSONArray array = obj.getJSONArray("players");
        for (int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            for(String naem : summonerName)
            {
                if (row.getString("summoner").toLowerCase().equals(naem.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getChampion() {
        return champion;
    }

    public Date getDate() throws JSONException, SummonerNotFoundException {
        long test = Long.parseLong(getRepayValue("timestamp").toString());
        Date date = new Date((test*1000L));
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setDate(1);
    	return date;
    }

    public String[] getSummonerName() {
        return summonerName;
    }
    
    public String getJson()
    {
    	return obj.toString();
    }

}
