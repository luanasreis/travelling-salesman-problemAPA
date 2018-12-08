package lib.filesHandle;
import lib.CityCoordinates;

import java.io.*;
import java.util.ArrayList;

public class FileInputReader {
    private ArrayList<Integer> tour;
    private ArrayList<CityCoordinates> cities;
    private final String dataFilesPath = "./src/main/java/data/test";
    private final String tourFileSection = "TOUR_SECTION";
    private final String cityFileSection = "NODE_COORD_SECTION";
    private Boolean startToSave;
    private Boolean tourSave;
    private Boolean locationsSave;

    public FileInputReader() {
        this.tour = new ArrayList<Integer>();
        this.cities = new ArrayList<CityCoordinates>();
        File folder = new File(this.dataFilesPath);
        for (File file : folder.listFiles()){
            try {
                this.startToSave = false;
                this.tourSave = false;
                this.locationsSave = false;

                FileReader inputStream = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                bufferedReader.lines().forEach( line -> {
                    if(startToSave && this.tourSave && !line.equals("EOF")){
                        Integer locationToVisit = Integer.parseInt(line);
                        if(locationToVisit> 0) {
                            this.tour.add(locationToVisit);
                        }
                    } else if(startToSave && this.locationsSave && !line.equals("EOF")){
                        String[] fileData = line.split(" ");
                        CityCoordinates localCityCoordinates = new CityCoordinates(Integer.parseInt(fileData[0]), Double.parseDouble(fileData[1]), Double.parseDouble(fileData[2]));
                        this.cities.add(localCityCoordinates);
                    }
                    if(line.equals(this.tourFileSection) || line.equals(this.cityFileSection)){
                        if(line.equals(this.tourFileSection)) {
                            this.tourSave = true;
                        } else {
                            this.locationsSave = true;
                        }
                        this.startToSave = true;
                    }
                });
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public ArrayList<Integer> getTour() {
      return this.tour;
    };
    public ArrayList<CityCoordinates> getCities() { return this.cities; };

}
