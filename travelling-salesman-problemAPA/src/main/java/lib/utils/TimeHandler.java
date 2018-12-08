package lib.utils;

import java.util.ArrayList;

public class TimeHandler {
    private long initialTime;
    private long finishTime;
    private long finalTime;
    private ArrayList<Long> times;

    public TimeHandler() {
        this.times = new ArrayList<>();
    }

    /**
     * Inicia o contador de tempo em nanosegundos
     */
    public void startTime() {
        this.initialTime = System.nanoTime();
    }

    /**
     * Finaliza o contador de tempo, calcula a diferença e adiciona a um array de tempos.
     */
    public void stopTime() {
        this.finishTime = System.nanoTime();
        this.finalTime = (this.finishTime - this.initialTime) % 1000;
        this.times.add(this.finalTime);
    }

    /**
     *
     * @return ultimo valor de tempo calculado.
     */
    public long getLastTime() {
        return this.finalTime;
    }

    /**
     *
     * @param timeHandler add valor de um objecto TimeHandler o ultimo tempo calculado ao array de tempos
     */
    public void addTime(TimeHandler timeHandler) {
        this.times.add(timeHandler.finalTime);
    }

    /**
     *
     * @param time adiciona variavel de tempo ao array de tempos
     */
    public void addTime(long time) {
        this.times.add(time);
    }

    /**
     *
     * @param name nome do algoritmo
     * @return string com a média de tempos.
     */
    public String generateMediaOfTimes(String name) {
        StringBuilder string = new StringBuilder();
        long result = this.getMediaOfTimes();


        string.append("A média de tempo do algoritmo ");
        string.append(name + ": ");
        result = result/times.size();
        string.append(" ").append(result);

        return string.toString();
    }

    public long getMediaOfTimes() {
        long result = 0;
        for(long time : times) {
            result += time;
        }

        return result;
    }
}
