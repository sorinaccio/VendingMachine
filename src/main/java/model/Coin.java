package model;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class Coin implements Comparable<Coin>{

    private int denomination;

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public Coin(int denomination) {
        this.denomination = denomination;
    }

    public String toString() {
        return denomination + " pence";
    }

    public int compareTo(Coin o) {
        return Integer.compare(this.denomination, o.denomination);
    }
}
