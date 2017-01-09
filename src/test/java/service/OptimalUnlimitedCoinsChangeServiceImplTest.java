package service;

import exception.NegativeAmountException;
import model.Coin;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by sorinaccio on 1/9/2017.
 */
@RunWith(Theories.class)
public class OptimalUnlimitedCoinsChangeServiceImplTest {

    List<Coin> availableCoins;
    OptimalUnlimitedCoinsChangeService optimalService;

    public static @DataPoints
    int[] candidates = {1,25, 95, 65, Integer.MAX_VALUE};

    @Before
    public void setUp() throws Exception {
        availableCoins = new ArrayList<Coin>();
        availableCoins.add(new Coin(1));
        availableCoins.add(new Coin(2));
        availableCoins.add(new Coin(5));
        availableCoins.add(new Coin(10));
        availableCoins.add(new Coin(20));
        availableCoins.add(new Coin(50));
        availableCoins.add(new Coin(100));

        optimalService = new OptimalUnlimitedCoinsChangeServiceImpl();
        optimalService.setCoins(availableCoins);
    }

    @Test
    @Theory
    public void testGetOptimalChangeFor(int amount) throws Exception {
        Collection<Coin> solution = optimalService.getOptimalChangeFor(amount);

        System.out.print(amount + " --> ");
        for(Coin coin: solution) {
            System.out.print(coin.getDenomination() + " ");
        }
        System.out.println();
    }

    @Test
    public void testZeroAmount() {
        Collection<Coin> zeroSolution = optimalService.getOptimalChangeFor(0);
        assertTrue(zeroSolution.isEmpty());
    }

    @Test(expected = NegativeAmountException.class)
    public void testNegativeAmount() {
        Random random=new Random();
        int randomNegativeAmount=(random.nextInt(65536)-32768);
        optimalService.getOptimalChangeFor(randomNegativeAmount);
    }
}