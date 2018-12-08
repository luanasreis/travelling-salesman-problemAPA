package lib.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class RoutesHandler {

    private Double[][] matrix;

    public RoutesHandler(Double matrix[][]){
        this.matrix = Arrays.copyOf(matrix, matrix.length);
    }

    public void printRoute(Integer route[]) {
        StringBuilder result = new StringBuilder();
        for(int count = 0; count < route.length; count++) {
            result.append(route[count]).append(" -> ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 4));

        System.out.println(result);
    }

    public void printRoute(String algorithmName, ArrayList<Integer> route, boolean printDistance, boolean printWay) {
        System.out.println(algorithmName);
        this.buildWeight(route, printDistance, printWay);
        System.out.println("--------------------------------------");
    }

    public void printRoute(String algorithmName, Integer[] route, boolean printDistance, boolean printWay) {
        System.out.println(algorithmName);
        this.buildWeight(route, printDistance, printWay);
        System.out.println("--------------------------------------");
    }

    public Double buildWeight(Integer[] route, boolean printDistance, boolean printWay) {
        Double sumCount= 0.0;
        for (int count = 0; count < route.length -1; count ++) {
            sumCount += this.matrix[route[count]][route[count + 1]];
        }

        if (printDistance) {
            System.out.println("Distância total: "  + sumCount);
        }

        if(printWay) {
            this.printRoute(route);
        }

        return sumCount;
    }

    public Double buildWeight(ArrayList<Integer> route, boolean printDistance, boolean printWay) {
        double weight = 0.0;
        int roundTrip;
        for(int count = 0; count < route.size(); count++) {
            if((count+1) == route.size()) {
                roundTrip = count -1;
            } else {
                roundTrip = count+1;
            }
            weight += this.matrix[route.get(count)][route.get(roundTrip)];
        }
        if(printDistance){
            System.out.println("Distância Total: " + weight);
        }
        if(printWay) {
            StringBuilder result = new StringBuilder();
            for(int count = 0; count < route.size(); count++) {
                result.append(route.get(count)).append(" -> ");
            }
            result.append(route.get(0));
            System.out.println(result);
        }
        return weight;
    }

    public Double buildWeight(boolean printDistance, Integer[] route) {
        return this.buildWeight(route, printDistance, false);
    }

    public ArrayList<Integer> appendNode(ArrayList<Integer> route, int position, Integer node) {
        ArrayList<Integer> tmpRoute = new ArrayList<>(route);
        if(position == 0){
            position += 1;
        }
        if( position == route.size()) {
            position -= 1;
        }
        tmpRoute.add(position, node);
        return tmpRoute;
    }
}
