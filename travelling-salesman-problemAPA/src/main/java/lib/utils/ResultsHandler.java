package lib.utils;

import java.util.ArrayList;

public class ResultsHandler {
    private String algorithmName;
    private ArrayList<Integer[]> routes;
    private RoutesHandler routesHandler;
    private TimeHandler timesHandler;

    public ResultsHandler(String algorithmName, Double[][] matrix) {
        this.algorithmName = algorithmName;
        this.routes = new ArrayList<>();
        this.routesHandler = new RoutesHandler(matrix);
        timesHandler = new TimeHandler();
    }

    /**
     *
     * @param result resultado de algum algoritmo
     */
    public void addAlgorithmResult(Integer[] result) {
        this.routes.add(result);
    }

    /**
     * Mostra o melhor resultado de acordo com o array de resultados.
     */
    public void showBestResult() {
        StringBuilder result = new StringBuilder();
        double bestResult = this.getBestResult();
        result.append(this.algorithmName).append(", melhor resultado: ").append(bestResult);
        System.out.println(result);
    }

    /**
     *
     * @param time variavel long que add time ao array de times
     */
    public void addTime(long time) {
        this.timesHandler.addTime(time);
    }

    public void showMediaTime() {
        System.out.println(this.timesHandler.generateMediaOfTimes(this.algorithmName));
    }

    public void setTimesHandler(TimeHandler timesHandler) {
        this.timesHandler = timesHandler;
    }

    public double getBestResult() {
        double bestResult = Double.MAX_VALUE;
        double tmpResult;

        for(Integer[] route : this.routes) {
            tmpResult = this.routesHandler.buildWeight(route,false, false);
            if(tmpResult < bestResult) {
                bestResult = tmpResult;
            }
        }
        return bestResult;
    }

    public long getMediaTime() {
        return this.timesHandler.getMediaOfTimes();

    }


}
