package service;

import exception.NegativeAmountException;
import exception.UnrealAmountException;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by sorinaccio on 1/9/2017.
 */
@RunWith(Theories.class)
public class OptimalUnlimitedCoinsChangeServiceImplTest {

    List<Coin> availableCoins;
    OptimalUnlimitedCoinsChangeService optimalService;

    /*
        All these candidate amounts have a solution with 4 coins
        43: 20, 20, 2, 1
        37: 20, 10, 5, 2
        18: 10, 5, 2, 1
        180: 100, 50, 20, 10
     */
    public static @DataPoints
    int[] candidates = {43, 37, 18, 180};

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

        assertEquals(solution.size(),4);
    }

    @Test
    public void testZeroAmount() {
        Collection<Coin> zeroSolution = optimalService.getOptimalChangeFor(0);
        assertTrue(zeroSolution.isEmpty());
    }

    @Test(expected = NegativeAmountException.class)
    public void testNegativeAmount() {
        Random random=new Random();
        int randomNegativeAmount=(random.nextInt(101)-200);
        optimalService.getOptimalChangeFor(randomNegativeAmount);
    }

    @Test(expected = UnrealAmountException.class)
    public void testBigAmunt() {
        Random random = new Random();
        int randomBigAmount = 100000 + random.nextInt(100000);
        optimalService.getOptimalChangeFor(randomBigAmount);
    }

    /*
        Testing the solution with: 149 - 100, 20, 20, 5, 2, 2
     */
    @Test
    public void testSolution() {
        Collection<Coin> expectedSolution = new ArrayList<Coin>();
        int [] solution = {100,20,20,5,2,2};
        for (int i: solution) {
            expectedSolution.add(new Coin(i));
        }
        Collection<Coin> retrievedSolution = optimalService.getOptimalChangeFor(149);

        assertThat(retrievedSolution, is(equalTo(expectedSolution)));

    }
}