package service;

import model.Coin;

import java.util.List;
import java.util.Map;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public interface InventoryService {
    int getQuantity(Coin coin);

    Coin getMinimalAvailableCoin();

    void add(Coin coin);

    void deduct(Coin coin);

    boolean hasCoin(Coin coin);

    Map<Coin, Integer> getInventory();

    List<Coin> getAvailableDenomination();
}
