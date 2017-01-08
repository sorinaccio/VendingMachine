package service;

import model.Coin;
import repository.InventoryRepository;

import java.util.*;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class InventoryServiceImpl implements InventoryService {

    InventoryRepository inventoryRepository;
    TreeMap<Coin,Integer> inventory;

    public int getQuantity(Coin coin) {
        Integer value = inventory.get(coin);
        return value == null? 0 : value;
    }

    public Coin getMinimalAvailableCoin() {
        Set<Coin> coins = inventory.keySet();
        for(Coin coin:coins) {
            if(hasCoin(coin)) {
                return coin;
            }
        }
        return null;
    }

    public void add(Coin coin) {
        int count = inventory.get(coin);
        inventory.put(coin,count+1);
    }

    public void deduct(Coin coin) {
        if (hasCoin(coin)) {
            int count = inventory.get(coin);
            inventory.put(coin,count-1);
        }
    }

    public boolean hasCoin(Coin coin) {
        return getQuantity(coin) > 0;
    }

    public InventoryServiceImpl() {
    }

    public Map<Coin, Integer> getInventory() {
        return inventory;
    }

    public List<Coin> getAvailableDenomination() {
        List<Coin> availableCoinList = new ArrayList<Coin>();
        Iterator it = inventory.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Coin,Integer> pair = (Map.Entry) it.next();
            if(pair.getValue() > 0 ) {
                availableCoinList.add(pair.getKey());
            }
        }
        return availableCoinList;
    }
}
