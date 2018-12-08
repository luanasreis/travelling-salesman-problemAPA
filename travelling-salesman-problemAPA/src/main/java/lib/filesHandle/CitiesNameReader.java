package lib.filesHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class CitiesNameReader {
    private final String namesPath = "./src/main/java/data/names/names.txt";
    private ArrayList<String> names;

    public CitiesNameReader() {
        File file = new File(this.namesPath);
        FileReader inputStream = null;
        names = new ArrayList<String>();
        try {
            inputStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            bufferedReader.lines().forEach( line -> {
                names.add(line);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getAleatoryName() {
        int rnd = new Random().nextInt(this.names.size() -1);
        String name = this.names.get(rnd);
        this.names.remove(rnd);
        return name;
    }
}
