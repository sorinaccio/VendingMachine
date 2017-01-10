package service;

import exception.InsufficientCoinsException;
import exception.NegativeAmountException;
import exception.UnrealAmountException;
import model.Coin;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import repository.InventoryRepository;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by sorinaccio on 1/10/2017.
 */
public class ChangeServiceImplTest {

    Map<Coin, Integer> testedInventory;

    @Mock
    InventoryRepository inventoryRepositoryMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    InventoryService inventoryService;

    ChangeService changeService;

    @Before
    public void setUp() throws Exception {

        testedInventory = new TreeMap<>();
        testedInventory.put(new Coin(100), 11);
        testedInventory.put(new Coin(50), 24);
        testedInventory.put(new Coin(20), 0);
        testedInventory.put(new Coin(10), 99);
        testedInventory.put(new Coin(5), 200);
        testedInventory.put(new Coin(2), 11);
        testedInventory.put(new Coin(1), 23);

        inventoryService = new InventoryServiceImpl(inventoryRepositoryMock);
        when(inventoryRepositoryMock.readInventory()).thenReturn(testedInventory);
        inventoryService.loadInventory();

        changeService = new ChangeServiceImpl(inventoryService);
    }

    @Test
    public void testGetChangeFor127() throws Exception {

        Collection<Coin> expectedSolution = new ArrayList<Coin>();
        int [] solution = {100, 10, 10, 5, 2};
        for (int i: solution) {
            expectedSolution.add(new Coin(i));
        }

        Collection<Coin> computedSolution = changeService.getChangeFor(127);
        assertThat(computedSolution, is(equalTo(expectedSolution)));

    }

    @Test
    public void testGetChangeFor145() throws Exception {

        Collection<Coin> expectedSolution = new ArrayList<Coin>();
        int [] solution = {100, 10, 10, 10, 10, 5};
        for (int i: solution) {
            expectedSolution.add(new Coin(i));
        }

        Collection<Coin> computedSolution = changeService.getChangeFor(145);
        assertThat(computedSolution, is(equalTo(expectedSolution)));
    }

    @Test
    public void testGetChangeFor31() throws Exception {

        Collection<Coin> expectedSolution = new ArrayList<Coin>();
        int [] solution = {10, 10, 10, 1};
        for (int i: solution) {
            expectedSolution.add(new Coin(i));
        }

        Collection<Coin> computedSolution = changeService.getChangeFor(31);
        assertThat(computedSolution, is(equalTo(expectedSolution)));
    }

    @Test
    public void testGetChangeFor51() throws Exception {

        Collection<Coin> expectedSolution = new ArrayList<Coin>();
        int [] solution = {50, 1};
        for (int i: solution) {
            expectedSolution.add(new Coin(i));
        }

        Collection<Coin> computedSolution = changeService.getChangeFor(51);
        assertThat(computedSolution, is(equalTo(expectedSolution)));
    }

    @Test
    public void testGetChangeFor1221() throws Exception {

        Collection<Coin> expectedSolution = new ArrayList<Coin>();
        int [] solution = {100,100,100,100,100,100,100,100,100,100,100, 50, 50, 10, 10, 1};
        for (int i: solution) {
            expectedSolution.add(new Coin(i));
        }

        Collection<Coin> computedSolution = changeService.getChangeFor(1221);
        assertThat(computedSolution, is(equalTo(expectedSolution)));
    }

    @Test
    public void testGetChangeForZero() throws Exception {
        Collection<Coin> computedSolution = changeService.getChangeFor(0);
        assertTrue(computedSolution.isEmpty());
    }

    @Test(expected = NegativeAmountException.class)
    public void testNegativeAmount() throws Exception {
        Random random=new Random();
        int randomNegativeAmount=(random.nextInt(101)-200);
        Collection<Coin> computedSolution = changeService.getChangeFor(randomNegativeAmount);
    }

    @Test(expected = UnrealAmountException.class)
    public void testBigAmount() {
        Random random = new Random();
        int randomBigAmount = 100000 + random.nextInt(100000);
        Collection<Coin> computedSolution = changeService.getChangeFor(randomBigAmount);
    }

    @Test(expected = InsufficientCoinsException.class)
    public void testInsufficientAmount() {
        int maxMoney = inventoryService.getTotalAmount();
        Collection<Coin> computedSolution = changeService.getChangeFor(maxMoney+1);
    }

    @Test
    public void testTotalAmountOfMoney() {
        int maxMoney = inventoryService.getTotalAmount();

        Iterator it = testedInventory.entrySet().iterator();
        List<Coin> expectedSolution = new ArrayList<>();
        while( it.hasNext()) {
            Map.Entry<Coin, Integer> pair = (Map.Entry) it.next();
            int count = pair.getValue();
            while(count > 0) {
                expectedSolution.add(pair.getKey());
                count--;
            }
        }

        List<Coin> computedSolution = new ArrayList<Coin>(changeService.getChangeFor(maxMoney));
        Collections.sort(computedSolution);
        Collections.sort(expectedSolution);
        assertThat(computedSolution, is(equalTo(expectedSolution)));

    }
}