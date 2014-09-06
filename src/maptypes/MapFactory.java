package maptypes;

import exceptions.FileNotValidException;
import exceptions.SummonerNotFoundException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import replay.ReplayFileParser;
import replay.StatFile;
import exceptions.*;

import javax.swing.*;
import java.io.*;

/**
 * Created by philipp on 16.08.2014.
 */
public class MapFactory {
    private Logger logger = Logger.getRootLogger();
    private int count;
    private File folder;
    private String[] name;
    private int counter = 0;
    private JProgressBar bar;
    private int ReplayFileCount;
    private int CorruptedFiles;
    private int FilesWithSummoner;
    private int FileWithoutSummoner;
    
   public MapFactory(JProgressBar bar) throws Exception, SummonerNotFoundException{
	   this.bar = bar;
	   bar.setMaximum(count);
   }
   
   public ReplayDataMap getMap(String[] names,StatFile file,File folder,Maps mapType) throws IOException, MapTypeNotFoundException {
       resetStats();
       ReplayDataMap map;
       ReplayDataMap test;
	   	bar.setValue(0);
       switch(mapType)
       {
           case GameMap:
               test = new GameMap(names);
               break;
           case TimeMap:
               test = new DateMap(names);
               break;
           case ChampMap:
               test = new ChampMap(names);
               break;
           default:
               throw new MapTypeNotFoundException();
       }
       if(file != null)
           map = getDataMap(file,test);
        else
           map = getDataMap(folder,test);
	   	return map;
   }


    private ReplayDataMap getDataMap(File folder, ReplayDataMap replayDataMap) {
    	for (File tempFile : folder.listFiles()) {
    		bar.setValue(++counter);
            if (tempFile.isDirectory())
                getDataMap(tempFile, replayDataMap);
            else {
                if(tempFile.getName().endsWith(".lrf"))
                {
                	this.ReplayFileCount++;
                	try {

                        replayDataMap.addReplay(ReplayFileParser.getJsonFromReplay(tempFile));
                        this.FilesWithSummoner++;
                    } catch (SummonerNotFoundException e) {
                       //logger.warn("Summoner not found [" + tempFile.getAbsolutePath() + "]");
                       this.FileWithoutSummoner++;

                    } catch (FileNotValidException e) {
                        logger.warn("File not Valid [" + tempFile.getAbsolutePath() + "]");
                        this.CorruptedFiles++;

                    } catch (NullPointerException e) {
                        logger.warn("NullPointerException [" + tempFile.getAbsolutePath() + "]");
                        this.CorruptedFiles++;
                    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
                        logger.warn("JSONException [" + tempFile.getAbsolutePath() + "]");
                        this.CorruptedFiles++;
                    }
                }
            	
            }
        }
        return replayDataMap;
    }

    public ReplayDataMap getDataMap(StatFile file, ReplayDataMap replayMap) throws IOException {
        ReplayDataMap map = replayMap;
        BufferedReader reader = new BufferedReader(new FileReader(file.getStatFile()));
        String line;
        while((line = reader.readLine()) != null)
            try {
                map.addReplay(new JSONObject(line));
            } catch (SummonerNotFoundException e) {
                logger.warn("Summoner not found [" + line + "]");
                this.FileWithoutSummoner++;

            } catch (FileNotValidException e) {
                logger.warn("File not Valid [" +line + "]");
                this.CorruptedFiles++;

            } catch (NullPointerException e) {
                logger.warn("NullPointerException [" +line + "]");
                this.CorruptedFiles++;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return map;
    }
    
    private void getFile(String dirPath){
        File f = new File(dirPath);
        File[] files  = f.listFiles();

        if(files != null)
        for(int i=0; i < files.length; i++)
        {
            count ++;
            File file = files[i];
            if(file.isDirectory())
            {   
                 getFile(file.getAbsolutePath()); 
            }
         }
    }

	public String getStatistics(){
		return "<html><head></head><body>Replays: " + this.ReplayFileCount +"<p>With Summoner: "+ this.FilesWithSummoner +"<p>Without Summoner: "+ this.FileWithoutSummoner+"<p>Corrupted: "+this.CorruptedFiles+"</body></html>";
	}

	public static String fixName(String Input){
		switch(Input)
		{
		case "Armordillo" : return "Rammus";
		case "Armsmaster" : return "Jax";
		case "Bowmaster" : return "Ashe";
		case "CardMaster" : return "TwistedFate";
		case "Cryophoenix" : return "Anivia";
		case "DarkChampion" : return "Tryndamere";
		case "FallenAngel" : return "Morgana";
		case "Jester" : return "Shaco";
		case "Judicator" : return "Kayle";
		case "Lich" : return "Karthus";
		case "Minotaur" : return "Alistar";
		case "MonkeyKing" : return "Wukong";
		case "Pirate" : return "Gangplank";
		case "Wolfman" : return "Warwick";
		case "XenZhao" : return "XinZhao";
		case "BlindMonk" : return "LeeSin";
		case "ChemicalMan" : return "Singed";
		case "GreenTerror" : return "Chogath";
		case "Oriana" : return "Orianna";
		case "SadMummy" : return "Amumu";
		case "SteamGolem" : return "Blitzcrank";
		case "Voidwalker" : return "Kassadin";
		case "Yeti" : return "Nunu";
		
		default: return Input;
		}
	}

    private void resetStats()
    {
        this.CorruptedFiles = 0;
        this.FilesWithSummoner = 0;
        this.FileWithoutSummoner = 0;
        this.ReplayFileCount = 0;
    }

    public static enum Maps{
        ChampMap,
        GameMap,
        TimeMap;
    }
}
