package service;

import exception.NegativeAmountException;
import model.Coin;

import java.util.*;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class OptimalUnlimitedCoinsChangeServiceImpl implements OptimalUnlimitedCoinsChangeService {

    public List<Coin> availableCoins;

    public List<Coin> getCoins() {
        return availableCoins;
    }

    public void setCoins(List<Coin> coins) {
        this.availableCoins = coins;
    }

    /*
        Getting optimal change for pence, based on availableCoins denominations
     */
    public Collection<Coin> getOptimalChangeFor(int pence) {

        if(pence < 0)
                throw new NegativeAmountException("Negative amount provided for change. Not Possible!");

        int amountToBeChanged = pence;
        // array where at each iteration we'll hold T[i] = min(T[i], 1 + T[i-coin[j])
        // T[i] represents total number of coins to form value i
        int T[] = new int[amountToBeChanged + 1];
        // Array to get the final answer
        int R[] = new int[amountToBeChanged + 1];
        // o coins required to form value of 0
        T[0] = 0;

        // initialize T[i] with infinte as we will apply a min function on iteration with already exiting values
        // R[i] initialized with -1, representing NO Possibility to make change for i
        for (int i = 1; i <= amountToBeChanged; i++) {
            T[i] = Integer.MAX_VALUE - 1;
            R[i] = -1;
        }

        // taking each coin in consideration
        for (int j = 0; j < availableCoins.size(); j++) {
            // making the change for all values until the amountToBeChanged
            for (int i = 1; i <= amountToBeChanged; i++) {
                // consider only when the current coin is less than the current value to be changed
                int currentCoinValue = availableCoins.get(j).getDenomination();
                if (i >= currentCoinValue) {
                    if (T[i - currentCoinValue] + 1 < T[i]) {
                        T[i] = 1 + T[i - currentCoinValue];
                        R[i] = j;
                    }
                }
            }
        }

        Collection<Coin> optimalSolution = new ArrayList<Coin>();
        // there is no Solution
        if(R[R.length - 1] == -1) {
            optimalSolution = Collections.EMPTY_LIST;
        }

        int startIndex = R.length - 1;
        while (startIndex != 0) {
            int j = R[startIndex];
            optimalSolution.add(availableCoins.get(j));
            startIndex = startIndex - availableCoins.get(j).getDenomination();
        }

        return optimalSolution;
    }
}
