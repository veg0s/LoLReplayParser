import Exception.FileNotValidException;
import Exception.SummonerNotFoundException;
import org.json.JSONException;
import replay.ReplayFile;
import replay.ReplayFileParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StatSaver {
	
	 BufferedWriter writer;
	
	public StatSaver() throws IOException
	{
		  writer = new BufferedWriter(new FileWriter("stats.stats"));		 
	}

	public void ReplaysToStatsFile(File replayfolder, File outputfile) {
		for (File tempFile : replayfolder.listFiles()) {

			if (tempFile.isDirectory())
				ReplaysToStatsFile(tempFile, outputfile);
			else {
				try {
					ReplayFile file = new ReplayFile(new String[]{"Fozruk","Fac3m3lt0r"}, ReplayFileParser.getJsonFromReplay(tempFile));
					String test = file.getJson();
					writer.write(test+"\n");
					writer.flush();					
				} catch (NullPointerException | IOException
						| SummonerNotFoundException | FileNotValidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
                    e.printStackTrace();
                }
            }
		}
	}

}
