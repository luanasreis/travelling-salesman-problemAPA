package lib.filesHandle;
import lib.City;

import java.io.*;
import java.util.ArrayList;

public class AdjacentReader {
    private ArrayList<City> cities;
    private final String dataFilesPath = "./src/main/java/data/adjacent/adjacent15.txt";
    private ArrayList<ArrayList<Double>> Matrix;

    public AdjacentReader(CitiesNameReader names) {
        File file = new File(this.dataFilesPath);
        FileReader inputStream = null;
//        ArrayList<Double> matrix = new ArrayList<Double>();
        this.Matrix = new ArrayList<>();
        this.cities = new ArrayList<City>();
        try {
                inputStream = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                bufferedReader.lines().forEach( line -> {
                    String[] fileData = line.split(" ");

                    City initialCity = new City(names.getAleatoryName(), 0.0);
                    ArrayList<Double> matrix = new ArrayList<Double>();
                    for(String weight : fileData) {
                        StringBuilder str = new StringBuilder(weight);
                        str.setCharAt(weight.indexOf(','), '.');
                        Double value = Double.parseDouble(str.toString());
                        matrix.add(value);
                        City city = new City(names.getAleatoryName(), value);
                        initialCity.addAdjacent(city);
                    }
                    cities.add(initialCity);
                    this.Matrix.add(matrix);
                });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<City> getCities() { return this.cities; }
    public ArrayList<ArrayList<Double>> getMatrix() { return this.Matrix; }

    public Double[][] createFullAdjacentMatrix(boolean printMatrix) {
        int matrizLength = this.Matrix.toArray().length + 1;
        Double Number[][] = new Double[matrizLength][matrizLength];
        Number[matrizLength-1][matrizLength-1] = 0.0;
        double value = 0.0;
        ArrayList<Double> lineX;
        int indice;
        indice = 0;
        for(int countY = 0; countY < matrizLength -1; countY++){
            lineX = this.getMatrix().get(countY);
            indice = 0;
            for(int countX = countY; countX < matrizLength; countX++) {
                if(countX == countY) {
                    Number[countY][countX] = 0.0;
                } else {
                    value = lineX.get(indice);
                    Number[countY][countX] = value;
                    Number[countX][countY] = value;
                    indice++;
                }
            }
        }
        if(printMatrix) {
            for (int countY = 0; countY < Number.length; countY++) {
                for (int countX = 0; countX < Number.length; countX++) {
                    System.out.print(Number[countY][countX]);
                    System.out.print(" ");
                }
                System.out.println(" ");
            }
        }
        return Number;
    }
}
