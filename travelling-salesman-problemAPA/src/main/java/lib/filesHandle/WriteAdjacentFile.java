package lib.filesHandle;


import lib.CityCoordinates;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteAdjacentFile {
    private String dataFilesPath = "./src/main/java/data/adjacent/adjacent";

    public WriteAdjacentFile(ArrayList<CityCoordinates> cities, String pathToNewFile) {
        if (!pathToNewFile.isEmpty()) {
            this.dataFilesPath = pathToNewFile;
        }
        this.dataFilesPath = this.dataFilesPath + cities.size() + ".txt";
        File fileOut = new File(dataFilesPath);
        Double finalDistance;
        String distanceString;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut, true));
            for (int count = 0; count < cities.size(); count++) {
                for (int neighbors = count +1;  neighbors < cities.size(); neighbors++) {
                    finalDistance = cities.get(count).distance(cities.get(neighbors));
                    distanceString = String.format("%.2f", finalDistance);
                    System.out.println(distanceString);
                    writer.write(distanceString);
                    writer.write(" ");
                }
                writer.newLine();
            }
            writer.write("EOF");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
