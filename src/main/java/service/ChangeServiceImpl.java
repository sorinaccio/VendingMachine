package service;

import exception.InsufficientCoinsException;
import model.Coin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class ChangeServiceImpl implements ChangeService {

    public Collection<Coin> getChangeFor(int pence) {

        int amountToBeChanged = pence;
        InventoryService inventory = new InventoryServiceImpl();
        OptimalUnlimitedCoinsChangeService optimalChangeService =new OptimalUnlimitedCoinsChangeServiceImpl();

        // coins available
        List<Coin> availableCoins = inventory.getAvailableDenomination();

        optimalChangeService.setCoins(availableCoins);

        int balance = amountToBeChanged;
        List<Coin> finalSoulution = new ArrayList<Coin>();

        boolean foundFinalSolution = false;
        List<Coin> temporarySolution = new ArrayList<Coin>(optimalChangeService.getOptimalChangeFor(amountToBeChanged));
        //Collections.reverse(temporarySolution);

        while(temporarySolution.size() > 0 && balance > 0 ) {
            Coin currentCoin = temporarySolution.get(0);
            if (inventory.hasCoin(currentCoin)) {
                temporarySolution.remove(0);
                finalSoulution.add(currentCoin);
                balance = balance - currentCoin.getDenomination();
                inventory.deduct(currentCoin);
            } else {
                // Call again the optimalChange algorithm for the new balance
                // and consider as denominations only coins available
                if(inventory.getAvailableDenomination().size() ==0 ||
                        inventory.getMinimalAvailableCoin().getDenomination() > balance ) {
                    throw new InsufficientCoinsException("Insufficient Coins left to change the balance: " + balance);
                }
                availableCoins = inventory.getAvailableDenomination();
                optimalChangeService.setCoins(availableCoins);
                temporarySolution = new ArrayList<Coin>((optimalChangeService.getOptimalChangeFor(balance)));
            }
        }

        return finalSoulution;
    }

}
