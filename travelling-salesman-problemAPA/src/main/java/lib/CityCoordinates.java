package lib;

import java.util.ArrayList;

public class CityCoordinates {
    private int CityName;
    private double coordinatesX;
    private double coordinatesY;
    private CityCoordinates dad;
    private boolean visited;
//    private ArrayList<Adjacent> adjacent;

    public CityCoordinates(int CityName, double coordX, double coordY) {
        this.CityName = CityName;
        this.coordinatesX = coordX;
        this.coordinatesY = coordY;
        this.dad = null;
        this.visited = false;
    }


    public int getCityName() {
        return CityName;
    }

    public double getCoordinateX() {
        return coordinatesX;
    }

    public double getCoordinateY() {
        return coordinatesY;
    }

    public void setCoordinatesX(int coordX) {
        this.coordinatesX = coordX;
    }

    public void setCoordinatesY(int coordY) {
        this.coordinatesY = coordY;
    }

    public double distance(CityCoordinates nextCityCoordinates) {
        return Math.sqrt(
                Math.pow(nextCityCoordinates.getCoordinateX() - this.getCoordinateX(), 2) +
                        Math.pow(nextCityCoordinates.getCoordinateY() - this.getCoordinateY(), 2) * 1.0);
    }


}