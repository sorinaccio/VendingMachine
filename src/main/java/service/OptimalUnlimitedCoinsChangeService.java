package service;

import model.Coin;

import java.util.Collection;
import java.util.List;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public interface OptimalUnlimitedCoinsChangeService {
    public Collection<Coin> getOptimalChangeFor(int pence);

    public List<Coin> getCoins();

    public void setCoins(List<Coin> coins);

}
