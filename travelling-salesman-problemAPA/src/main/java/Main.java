import lib.*;
import lib.filesHandle.AdjacentReader;
import lib.filesHandle.CitiesNameReader;
import lib.utils.ResultsHandler;
import lib.utils.RoutesHandler;
import lib.utils.TimeHandler;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {
        CitiesNameReader names = new CitiesNameReader();
        AdjacentReader adjacents = new AdjacentReader(names);
        Integer[] firstSolution;
       // Integer[] buildSwapWay;
        Integer[] twoOpt;
        Integer[] twoOpt2;
        Integer[] Insertion;
        Integer[] buildInsertionSwapWay;

        Double[][] adjacentMatrix = adjacents.createFullAdjacentMatrix(false);
       RoutesHandler routesHandler = new RoutesHandler(adjacentMatrix);
        TravelingSalesman travelingSalesman = new TravelingSalesman(adjacentMatrix);
        VND vnd = new VND(adjacentMatrix);
        firstSolution = travelingSalesman.buildRoute();
        int solutionsTime = 10;


        Integer[] aleatorySolution = travelingSalesman.buildAleatoryRoute(firstSolution);
 /*       Integer[] circleWay = travelingSalesman.buildByCircleWay(false, firstSolution);
 */       twoOpt = vnd.twoOpt(firstSolution);
        twoOpt2 = vnd.twoOpt2(firstSolution);
        Insertion = vnd.twoOpt(firstSolution);
     //   buildInsertionSwapWay = vnd.twoOpt(firstSolution);
        GRASP grasp = new GRASP(adjacentMatrix);

        routesHandler.printRoute("Criando rota por near neighbor", firstSolution, true, true);
        routesHandler.printRoute("Criando rota por choosing aleatory neighbor", aleatorySolution, true, true);
 /*       routesHandler.printRoute("Criando rota por circle", circleWay, true, true);
*/
     ////   routesHandler.printRoute("Criando rota por 2opt neighbor", twoOpt, true, true);
      ///  routesHandler.printRoute("Criando rota por 2OPT RANDOM neighbor", twoOpt2, true, true);
     //   routesHandler.printRoute("Criando rota por insertion swap neighbor", buildInsertionSwapWay, true, true);
      ////  routesHandler.printRoute("Criando rota por insertion neighbor", Insertion, true, true);


        Integer[] graspSolution = grasp.buildGRASP(0.5, firstSolution);
        routesHandler.printRoute("Melhorando rota por GRASP", graspSolution, true, true);





//        //NEAR NEIGHBOR
        TravelingSalesman routes = new TravelingSalesman(adjacentMatrix);
//
        ResultsHandler buildRouteResults = new ResultsHandler("BUILD FIRST SOLUTION RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildRouteResults.addAlgorithmResult(routes.buildRoute());
            buildRouteResults.addTime(routes.getTime().getLastTime());
        }
//
//        // ALEATORY NEIGHBOR
        ResultsHandler buildAleatoryRouteResults = new ResultsHandler("BUILD ALEATORY ROUTE RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildAleatoryRouteResults.addAlgorithmResult(routes.buildAleatoryRoute(firstSolution)); ///FIRSTSOLUTION MESMO??
            buildAleatoryRouteResults.addTime(routes.getTime().getLastTime());
        }
//        // CIRCLE NEIGHBOR
 /*       ResultsHandler buildByCircleWayRouteResults = new ResultsHandler("BUILD BY CIRCLE WAY ROUTE RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildByCircleWayRouteResults.addAlgorithmResult(routes.buildByCircleWay(false, firstSolution));
            buildByCircleWayRouteResults.addTime(routes.getTime().getLastTime());
        }
 */
/*        // VND
        ResultsHandler buildVNDResults = new ResultsHandler("BUILD VND WAY ROUTE RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildVNDResults.addAlgorithmResult(vnd.buildVND(firstSolution, false));
            buildVNDResults.addTime(vnd.getTime().getLastTime());
        }*/
/*
        // INSERTION SWAP WAY
        ResultsHandler buildInsertionSwapWayResults = new ResultsHandler("BUILD INSERTION SWAP WAY RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildInsertionSwapWayResults.addAlgorithmResult(vnd.buildInsertionSwapWay(firstSolution, false));
            buildInsertionSwapWayResults.addTime(vnd.getInsertionSwapTime().getLastTime());
        }
*/

       // 2-OPT WAY
        ResultsHandler buildtwoOptResults = new ResultsHandler("BUILD 2-OPT WAY RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildtwoOptResults.addAlgorithmResult(vnd.twoOpt(firstSolution));
            buildtwoOptResults.addTime(vnd.getSwapTime().getLastTime());

        }


        // 2-INSERTION
        ResultsHandler buildtwoOpt2Results = new ResultsHandler("BUILD INSERTION WAY RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildtwoOpt2Results.addAlgorithmResult(vnd.twoOpt2(firstSolution));
            buildtwoOpt2Results.addTime(vnd.getSwapTime().getLastTime());

        }

/*
        // INSERTION
        ResultsHandler buildInsertionResults = new ResultsHandler("BUILD INSERTION WAY RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            buildInsertionResults.addAlgorithmResult(vnd.Insertion(firstSolution));
            buildInsertionResults.addTime(vnd.getSwapTime().getLastTime());

        }

*/
        // GRASP
        ResultsHandler graspResults = new ResultsHandler("GRASP RESULTS", adjacentMatrix);
        for(int count = solutionsTime; count > 0; count--){
            graspResults.addAlgorithmResult(grasp.buildGRASP(0.5, firstSolution));
            graspResults.addTime(grasp.getTime().getLastTime());
        }

        long mediaGeral = 0;
        long mediaSemSwaps = 0;

//        // 1 - FIRST RESULT
        buildRouteResults.showBestResult();
        buildRouteResults.showMediaTime();
        mediaGeral += buildRouteResults.getMediaTime();
//
//        // 2 - ALEATORY
        buildAleatoryRouteResults.showBestResult();
        buildAleatoryRouteResults.showMediaTime();
        mediaGeral += buildAleatoryRouteResults.getMediaTime();
//
        // 3 -  BY CIRCLE
/*        buildByCircleWayRouteResults.showBestResult();
        buildByCircleWayRouteResults.showMediaTime();
        mediaGeral += buildByCircleWayRouteResults.getMediaTime();
*/
//
//        // 4 - By VND
  /*      buildVNDResults.showBestResult();
        buildVNDResults.showMediaTime();
        mediaGeral += buildVNDResults.getMediaTime();
*/
//
       // 5 - GRASP
        graspResults.showBestResult();
        graspResults.showMediaTime();
        mediaGeral += graspResults.getMediaTime();
        mediaSemSwaps = mediaGeral;

       // 6 - 2-OPT SWAP
        buildtwoOptResults.showBestResult();
        buildtwoOptResults.showMediaTime();
        mediaGeral += buildtwoOptResults.getMediaTime();

        // 6 - 2-OPT SWAP 2v
        buildtwoOpt2Results.showBestResult();
        buildtwoOpt2Results.showMediaTime();
        mediaGeral += buildtwoOpt2Results.getMediaTime();
/*
        // 7 - Insertion
        buildInsertionSwapWayResults.showBestResult();
        buildInsertionSwapWayResults.showMediaTime();
        mediaGeral += buildInsertionSwapWayResults.getMediaTime();
*/
  /*      // 7 - Insertion
        buildInsertionResults.showBestResult();
        buildInsertionResults.showMediaTime();
        mediaGeral += buildInsertionResults.getMediaTime();

*/
        mediaGeral /= 7;
        mediaSemSwaps /= 5;
        System.out.println("Media computacional em millisegundos: " + mediaGeral);
       // System.out.println("Media computacional em millisegundos sem SWAPS: " + mediaSemSwaps);
       // RoutesHandler routesHandler = new RoutesHandler(adjacentMatrix);
        TimeHandler runTimesExecutions = new TimeHandler();
        TimeHandler graspTimeExecutions = new TimeHandler();
 //  Integer[] graspSolution;
        Integer[] vndSolution;
        Integer[] result = firstSolution;
        double graspDistance = 0.0;
        double vndDistance = 0.0;
        long tempoDeExecucaoDoGrasp = 0;
        long tempoDeExecucaoComputacional = 0;

        runTimesExecutions.startTime();
        for (int count = 10; count > 0; count--) {
            graspTimeExecutions.startTime();
            for(int graspCount=0; graspCount < 1000; graspCount++) {
                graspSolution = grasp.buildGRASP(0.4, result);
             //   routesHandler.printRoute("Melhorando rota por GRASP", graspSolution, true, true);
                graspDistance = routesHandler.buildWeight(graspSolution, false, false);
                vndSolution = vnd.buildVND(graspSolution, false);
                vndDistance = routesHandler.buildWeight(vndSolution, false, false);
                if(vndDistance < graspDistance) {
                    result = Arrays.copyOf(vndSolution, vndSolution.length);
                    routesHandler.printRoute("Melhorando rota por GRASP", vndSolution, true, true);
                }
            }
            graspTimeExecutions.stopTime();
        }
        runTimesExecutions.stopTime();
        tempoDeExecucaoComputacional = runTimesExecutions.getLastTime();

        System.out.println(graspTimeExecutions.generateMediaOfTimes("Grasp"));
        routesHandler.buildWeight(result, true, true);
        System.out.println("Tempo de execução computacional: " + tempoDeExecucaoComputacional);

    }
}