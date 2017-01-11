package service;

import model.Coin;
import repository.InventoryRepository;

import java.util.*;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private Map<Coin, Integer> inventory;

    public int getQuantity(Coin coin) {
        Integer value = inventory.get(coin);
        return value == null ? 0 : value;
    }

    /*
        Return true in case we could load the inventory
     */
    public boolean loadInventory() {
        if (inventoryRepository == null) return false;
        inventory = inventoryRepository.readInventory();
        if(inventory == null) return false;
        return true;
    }

    public void persistInventory() {
        if(inventoryRepository != null && inventory != null) {
            inventoryRepository.saveInventory(inventory);
        }
    }

    public Coin getMinimalAvailableCoin() {
        List<Coin> allCoins = new ArrayList<>(inventory.keySet());
        Collections.sort(allCoins);
        for (Coin coin : allCoins) {
            if (hasCoin(coin)) {
                return coin;
            }
        }
        return null;
    }

    public void add(Coin coin) {
        int count = inventory.get(coin);
        inventory.put(coin, count + 1);
    }

    public void deduct(Coin coin) {
        if (hasCoin(coin)) {
            int count = inventory.get(coin);
            inventory.put(coin, count - 1);
        }
    }

    public boolean hasCoin(Coin coin) {
        return getQuantity(coin) > 0;
    }

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Map<Coin, Integer> getInventory() {
        return inventory;
    }

    public List<Coin> getAvailableDenomination() {
        List<Coin> availableCoinList = new ArrayList<Coin>();
        Iterator it = inventory.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Coin, Integer> pair = (Map.Entry) it.next();
            if (pair.getValue() > 0) {
                availableCoinList.add(pair.getKey());
            }
        }
        Collections.sort(availableCoinList);
        return availableCoinList;
    }

    public int getTotalAmount() {
        int amount = 0;
        Iterator it = inventory.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Coin, Integer> pair = (Map.Entry) it.next();
            amount += pair.getKey().getDenomination() * pair.getValue();
        }
        return amount;
    }
}
