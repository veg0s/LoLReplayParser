package replay;

import java.io.File;

/**
 * Created by philipp on 06.09.2014.
 */
public class StatFile {

    private File statFile;

    public StatFile(File file)
    {
        this.statFile = file;
    }

    public File getStatFile() {
        return statFile;
    }


    }
