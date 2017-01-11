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

    private InventoryService inventoryService;
    private OptimalUnlimitedCoinsChangeService optimalChangeService;

    public ChangeServiceImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.optimalChangeService = new OptimalUnlimitedCoinsChangeServiceImpl();
    }

    public Collection<Coin> getChangeFor(int pence) {

        int amountToBeChanged = pence;

        // coins available
        List<Coin> availableCoins = inventoryService.getAvailableDenomination();

        optimalChangeService.setCoins(availableCoins);

        int balance = amountToBeChanged;
        List<Coin> finalSolution = new ArrayList<Coin>();

        boolean foundFinalSolution = false;
        List<Coin> temporarySolution = new ArrayList<Coin>(optimalChangeService.getOptimalChangeFor(amountToBeChanged));
        //Collections.reverse(temporarySolution);

        while (temporarySolution.size() > 0 && balance > 0) {
            Coin currentCoin = temporarySolution.get(0);
            if (inventoryService.hasCoin(currentCoin)) {
                temporarySolution.remove(0);
                finalSolution.add(currentCoin);
                balance = balance - currentCoin.getDenomination();
                inventoryService.deduct(currentCoin);
            } else {
                // Call again the optimalChange algorithm for the new balance
                // and consider as denominations only coins available
                if (inventoryService.getAvailableDenomination().size() == 0 ||
                        inventoryService.getMinimalAvailableCoin().getDenomination() > balance) {
                    throw new InsufficientCoinsException("Insufficient Coins left to change the balance: " + balance);
                }
                availableCoins = inventoryService.getAvailableDenomination();
                optimalChangeService.setCoins(availableCoins);
                temporarySolution = new ArrayList<Coin>((optimalChangeService.getOptimalChangeFor(balance)));
            }
        }

        return finalSolution;
    }

}
