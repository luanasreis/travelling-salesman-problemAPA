package lib;

import lib.utils.RoutesHandler;
import lib.utils.TimeHandler;
import java.util.Collections;
import java.util.Arrays;
import java.util.Random;

public class VND {
    private RoutesHandler routesHandler;
    private TimeHandler timeHandler;
    private TimeHandler timeHandlerSwap;
    private TimeHandler timeHandlerInsertion;

    public VND(Double matrix[][]) {
        this.routesHandler = new RoutesHandler(matrix);
        this.timeHandler = new TimeHandler();
        this.timeHandlerSwap = new TimeHandler();
        this.timeHandlerInsertion = new TimeHandler();
    }


    public Integer[] buildVND(Integer[] route, boolean showTime) {
        Integer[] tmpSwapRoute = Arrays.copyOf(route, route.length);
        this.timeHandler.startTime();
        Integer[] bestSolution=Arrays.copyOf(route, route.length);
        double bestDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
        double tourInsertionDistance = 0.0;
        double tourTwoOptDistance = 0.0;
        Integer[] tourInsertion;
        Integer[] tourTwoOpt;

        // considera o algoritmo passado como o valor base de distancia inicial;
        tourInsertionDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);


        while (true) {

            tourInsertion = this.Insertion(tmpSwapRoute);
            tourInsertionDistance = this.routesHandler.buildWeight(tourInsertion, false, false);

            if (tourInsertionDistance < bestDistance) {
                bestSolution = Arrays.copyOf(tourInsertion, tourInsertion.length);
                bestDistance = tourInsertionDistance;
                continue;

            } else if (tourTwoOptDistance == bestDistance) {
                break;

            } else if (tourInsertionDistance == bestDistance) {

                //Tento aprimorar com o 2opt

                    tourTwoOpt = this.twoOpt(bestSolution);
                    tourTwoOptDistance = this.routesHandler.buildWeight(bestSolution, false, false);

                    if (tourTwoOptDistance < bestDistance) {
                        bestSolution = Arrays.copyOf(tourTwoOpt, tourTwoOpt.length);
                        bestDistance = tourTwoOptDistance;
                        continue;
                    } else if (tourInsertionDistance==bestDistance){
                        break;
                    }

            }}
            if (showTime) {
                System.out.println("VND Time: " + timeHandler.getLastTime());
            }

            this.timeHandler.stopTime();
            return bestSolution;
        }




    private Integer[] findBestSolution(Integer[] route, double bestDistance) {
        Integer[] tmpSwapRoute = Arrays.copyOf(route, route.length);
        double tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
        do {
            tmpSwapRoute = this.Insertion(tmpSwapRoute);
            tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
        } while (tmpDistance < bestDistance);

        return Arrays.copyOf(tmpSwapRoute, tmpSwapRoute.length);
    }




    //MOVIMENTO DE VIZINHANÇA 2-OPT
    public Integer[] twoOpt(Integer[] route) {
        timeHandlerSwap.startTime();
        Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
        double baseWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
        Integer[] bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //melhor solução caso não haja melhor vizinho


        double tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
        int improve = 0;

        while (improve < 20) {

            for (int i = 1; i < tmpSolution.length - 1; i++) {
                for (int k = i + 1; k < tmpSolution.length - 1; k++) {

                    tmpSolution = twoOPTSwap(tmpSolution, i, k); //passo o vetor e 2 pontos para ser swap.
                    tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

                    if (tmpWeight < baseWeight) { // se a troca resultar em uma distância melhorada, contadores de incremento e distância de atualização / turno
                        baseWeight = tmpWeight;
                        bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length);
                        improve = 0;

                    }
                }
            }
            improve++;
        }

        timeHandlerSwap.stopTime();
        return Arrays.copyOf(bestSolution, bestSolution.length);
    }


    //MOVIMENTO DE VIZINHANÇA 2-OPT RANDOM
    public Integer[] twoOpt2(Integer[] route) {
        timeHandlerSwap.startTime();
        Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
        double baseWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
        Integer[] bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //melhor solução caso não haja melhor vizinho


        double tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

        int improve = 0;

        while (improve < 20) {

            Random gerador = new Random();
            int node1 = gerador.nextInt(tmpSolution.length - 1);
            int node2 = gerador.nextInt(tmpSolution.length - 1);

            node1 = node1 == 0 ? 1 : node1;
            node2 = node2 == 0 ? 1 : node2;

            while (node1 == node2) {
                node1 = gerador.nextInt(tmpSolution.length - 1);
            }

            for (int i = 1; i < tmpSolution.length - 1; i++) {
                for (int j = i + 1; j < tmpSolution.length; j++) {

                    tmpSolution = twoOPTSwap(tmpSolution, node1, node2); //passo o vetor e 2 pontos para ser swap.

                    tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

                    if (tmpWeight < baseWeight) { // se a troca resultar em uma distância melhorada, contadores de incremento e distância de atualização / turno
                        baseWeight = tmpWeight;
                        bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length);
                        improve = 0;

                    }
                }
            }
            improve++;
        }

        timeHandlerSwap.stopTime();
        return Arrays.copyOf(bestSolution, bestSolution.length);
    }


    // SWAP DE VIZINHOS AUX DO 2-OPT
    public Integer[] twoOPTSwap(Integer[] routeSwap, int i, int j) { //realiza uma troca de 2 opt invertendo a ordem dos pontos entre i e j

        Integer[] tmpSolution = Arrays.copyOf(routeSwap, routeSwap.length); //solução temporária
        Integer[] tmpSolutionSwap = Arrays.copyOf(routeSwap, routeSwap.length); //solução temporária

        int size = tmpSolution.length;

        //copior em um novo vetor até o primeiro ponto i
        for (int part1 = 0; part1 <= i - 1; ++part1) {
            tmpSolutionSwap[part1] = tmpSolution[part1];
        }

        //inverte a ordem entre 2 pontos passados ​​i e j ao novo vetor
        int cont = 0;
        for (int partSwap = i; partSwap <= j; ++partSwap) {
            tmpSolutionSwap[partSwap] = tmpSolution[j - cont];
            cont++;
        }

        //copio no novo vetor do ponto j+1 ate o final
        for (int part2 = j + 1; part2 < size; ++part2) {
            tmpSolutionSwap[part2] = tmpSolution[part2];
        }

        return tmpSolutionSwap;
    }


    public Integer[] Insertion(Integer[] route) {
        timeHandlerSwap.startTime();
        Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
        double baseWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
        Integer[] bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //melhor solução caso não haja melhor vizinho


        double tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

        int improve = 0;

        while (improve < 20) {

            for (int i = 1; i < tmpSolution.length - 1; i++) {
                for (int j = i + 1; j < tmpSolution.length - 1; j++) {
                    tmpSolution[j] = tmpSolution[i];
                   // reinsertion(tmpSolution, i, j);


                    tmpSolution = reinsertion(tmpSolution, i, j); //passo o vetor e 2 pontos para ser swap.
                    tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

                    if (tmpWeight < baseWeight) { // se a troca resultar em uma distância melhorada, contadores de incremento e distância de atualização / turno
                        baseWeight = tmpWeight;
                        bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length);
                        improve = 0;

                    }
                   // bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length);
                }
            }
            improve++;
        }

        timeHandlerSwap.stopTime();
        return Arrays.copyOf(bestSolution, bestSolution.length);
    }


    public Integer[] reinsertion(Integer[] route, int i, int j) {

        Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
        Integer[] tmpSolutionSwap = Arrays.copyOf(route, route.length); //solução temporária


        for (int k = i; k < j; k++) {
            tmpSolutionSwap[k] = tmpSolution[k + 1];
        }


        return tmpSolutionSwap;

    }


     /*  public Integer[] buildVND(Integer[] route, boolean showTime) {
          Integer[] tmpSwapRoute = Arrays.copyOf(route, route.length);
          this.timeHandler.startTime();
          Integer[] bestSolution;
          double tmpDistance = 0.0;
          double bestDistance;
          // considera o algoritmo passado como o valor base de distancia inicial;
          tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);

          int test = 0;

          while(true){
              tmpSwapRoute = this.findBestSolutionInsertion(tmpSwapRoute, tmpDistance);
              tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
              bestSolution = this.twoOpt2(tmpSwapRoute);
              bestDistance = this.routesHandler.buildWeight(bestSolution, false, false);

  //            bestSolution = this.findBestSolution(tmpSwapRoute, tmpDistance);
  //            bestDistance = this.routesHandler.buildWeight(bestSolution, false, false);
  //            tmpSwapRoute = this.buildInsertionSwapWay(tmpSwapRoute, false);
  //            tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);

              if(tmpDistance < bestDistance) {
                  bestSolution = Arrays.copyOf(tmpSwapRoute, tmpSwapRoute.length);
              }
              else {
                  break;
              }
          }


          if(showTime) {
              System.out.println("VND Time: " + timeHandler.getLastTime());
          }
          this.timeHandler.stopTime();
          return bestSolution;
      }
  */



     /*
         private Integer[] findBestSolutiontwoOpt2(Integer[] route, double bestDistance) {
        Integer[] tmpSwapRoute = Arrays.copyOf(route, route.length);
        double tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
        do {
            tmpSwapRoute = this.twoOpt2(tmpSwapRoute);
            tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
        } while (tmpDistance < bestDistance);

        return Arrays.copyOf(tmpSwapRoute, tmpSwapRoute.length);
    }
      */


/*
    public Integer[] copy(Integer[] route,  int i, int j) {


    }
*/


//}

/*
    public Integer[] reInsertion(Integer[] route) {
        timeHandlerSwap.startTime();
        Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
        double baseWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
        Integer[] bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //melhor solução caso não haja melhor vizinho


        double tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
        int improve=0;

        while (improve<20) {

            for (int i = 1; i < tmpSolution.length - 1; i++) {
                for (int j = i + 1; j < tmpSolution.length; j++) {


                    tmpSolution= insertion(tmpSolution, i);


                    tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

                    if (tmpWeight < baseWeight) { // se a troca resultar em uma distância melhorada, contadores de incremento e distância de atualização / turno
                        baseWeight = tmpWeight;
                        bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length);
                        improve = 0;

                    }
                }
            }
            improve++;
        }

        timeHandlerSwap.stopTime();
        return Arrays.copyOf(bestSolution, bestSolution.length);
    }

    public Integer[] insertion(Integer[] routeSwap, int insert) { //realiza uma troca de 2 opt invertendo a ordem dos pontos entre i e j

        Integer[] tmpSolution = Arrays.copyOf(routeSwap, routeSwap.length); //solução temporária
        Integer[] tmpSolutionSwap = Arrays.copyOf(routeSwap, routeSwap.length); //solução temporária

        int size = tmpSolution.length;
        //int aux;
        //int neighbor = insert +1;
         //remove

        //remove elemento selecionado
        int swapPosition = tmpSolution[insert];

        for(int i = insert; i<tmpSolution.length-1; i++){
            tmpSolution[i] = tmpSolution[i+1];
        }

        for(int position=insert+1; position<tmpSolution.length-1; position++) {
            for (int j = size - 1; j >= insert; j--) {
                tmpSolution[j + 1] = tmpSolution[j];
            }
            tmpSolution[position+1] = tmpSolution[insert];

        }

        tmpSolutionSwap = Arrays.copyOf(tmpSolution, tmpSolution.length);

        return tmpSolutionSwap;
    }


*/


    /*

        public Integer[] buildSwapWay(Integer[] route) {
            timeHandlerSwap.startTime();
            Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
            double baseWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
            Integer[] bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //melhor solução caso não haja melhor vizinho

            for(int position = 1; position < route.length-1; position++) { //inicio do nó subsequente pq o primeiro nó é o depósito
                tmpSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //faço uma copia da solução corrente pra solução temporaria
    //            tmpSolution = Arrays.copyOf(bestSolution, bestSolution.length);
                for(int neighbor = position + 1; neighbor < route.length - 1; neighbor++) { //inicio do no apos o no que iniciei
                   //realizo as trocas dos vizinhos da posição atual com o neightor
                    int tmpWay = tmpSolution[position];
                    tmpSolution[position] = tmpSolution[neighbor];
                    tmpSolution[neighbor] = tmpWay;
                    double tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, false);

                    //verifico os custos aopos as trocas
                    if(tmpWeight < baseWeight) { //se o custo da minha solução temporaria é menor que o custo da solução atual
                        baseWeight = tmpWeight;
                        bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //a melhor solução passa a ser a atual solução temporária
                    }
                }
            }
            timeHandlerSwap.stopTime();
            return Arrays.copyOf(bestSolution, bestSolution.length);
        }

        public Integer[] buildInsertionSwapWay(Integer[] route, boolean printWay) {
            this.timeHandlerInsertion.startTime();
            Integer[] tmpSolution = Arrays.copyOf(route, route.length); //solução temporária
            double baseWeight = this.routesHandler.buildWeight(tmpSolution, false, false);
            Integer[] bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //melhor solução caso não haja melhor vizinho

            Random gerador = new Random();

            int insert = gerador.nextInt(route.length -1);

            insert = insert == 0 ? 1 : insert;

            if(printWay) {
                System.out.println("Insertion value: " + insert);
            }

            for(int position = 1; position < route.length -1 ; position++) {
                tmpSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //faço uma copia da solu~ção corrente pra solução temporaria
                for(int neighbor = position + 1; neighbor < route.length - 1; neighbor++) {  //inicio do no subsequente pq o primeiro nó é o deposito
                    if(neighbor == insert) {
                        ++neighbor;
                    }
                    //realizo as trocas
                    int swapPosition = tmpSolution[insert];
                    tmpSolution[insert] = tmpSolution[neighbor];
                    tmpSolution[neighbor] = swapPosition;
                    double tmpWeight = this.routesHandler.buildWeight(tmpSolution, false, printWay);

                    //verifico os custos apos as trocas
                    if(tmpWeight < baseWeight) { //se o custo da minha solução temporaria é menor que o custo da solução atual
                        baseWeight = tmpWeight;
                        bestSolution = Arrays.copyOf(tmpSolution, tmpSolution.length); //a melhor solução passa a ser a atual solução temporária
                    }
                }
            }
            this.timeHandlerInsertion.stopTime();
            return Arrays.copyOf(bestSolution, bestSolution.length);
        }
    */
    public TimeHandler getTime() {
        return this.timeHandler;
    }

    public TimeHandler getInsertionSwapTime() {
        return this.timeHandlerInsertion;
    }

    public TimeHandler getSwapTime() {
        return this.timeHandlerSwap;
    }


}

//            bestSolution = this.findBestSolution(tmpSwapRoute, tmpDistance);
//            bestDistance = this.routesHandler.buildWeight(bestSolution, false, false);
//            tmpSwapRoute = this.buildInsertionSwapWay(tmpSwapRoute, false);
//            tmpDistance = this.routesHandler.buildWeight(tmpSwapRoute, false, false);
