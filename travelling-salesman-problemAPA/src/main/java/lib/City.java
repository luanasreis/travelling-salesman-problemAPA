package lib;

import java.util.ArrayList;

public class City {
    private String CityName;
    private double weight;
    private City dad;
    private boolean visited;
    private ArrayList<City> adjacent;

    public City(String CityName, double weight) {
        this.CityName = CityName;
        this.weight = weight;
        this.dad = null;
        this.visited = false;
        this.adjacent = new ArrayList<City>();
    }

    public void setAdjacent(ArrayList<City> adj) {
        this.adjacent = adj;
    }

    public ArrayList<City> getAdjacents() {
        return this.adjacent;
    }

    public City getAdjacent(int adj) {
        return this.adjacent.get(adj);
    }

    public void addAdjacent(City city) {
        this.adjacent.add(city);
    }

    public int getGrau() { //quant de vertices adj tem uma cidade (quantas cidades vizinhas)
        return adjacent.size();
    }

    public String getCityName() {
        return this.CityName;
    }

    public double getWeight() {
        return this.weight;
    }

    public City getDad() {
        return this.dad;
    }

    public void setDad(City dad) {
        this.dad = dad;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }



}