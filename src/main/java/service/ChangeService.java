package service;

import model.Coin;

import java.util.Collection;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public interface ChangeService {

    public Collection<Coin> getChangeFor(int pence);

}
