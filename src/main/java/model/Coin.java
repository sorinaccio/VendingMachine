package model;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class Coin implements Comparable<Coin>{

    private int denomination;

    public int getDenomination() {
        return denomination;
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

    @Override
    public int hashCode() {
        return denomination;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Coin)) return false;
        Coin secondCoin = (Coin) obj;
        if(this.denomination != secondCoin.denomination) return false;

        return true;
    }
}
