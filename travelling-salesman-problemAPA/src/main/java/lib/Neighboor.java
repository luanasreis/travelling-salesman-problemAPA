package lib;
import java.lang.*;

public class Neighboor implements Comparable<Neighboor> {
    private int indice;
    private double valor;

    public Neighboor(int indice, double valor) {
        this.indice = indice;
        this.valor = valor;
    }

    @Override
    public int compareTo(Neighboor outroNeighboor) {
        if (this.valor < outroNeighboor.getValor()) {
            return -1;
        }
        if (this.valor > outroNeighboor.getValor()) {
            return 1;
        }
        return 0;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
