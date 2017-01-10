package service;

import model.Coin;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import repository.InventoryRepository;

import java.util.*;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by sorinaccio on 1/10/2017.
 */
public class InventoryServiceImplTest {

    @Mock
    InventoryRepository inventoryRepositoryMock;

    Map<Coin, Integer> inventory;
    InventoryService inventoryService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        inventoryService = new InventoryServiceImpl(inventoryRepositoryMock);
        inventory = new TreeMap<>();
    }

    @Test
    public void testGetQuantity() throws Exception {
        Coin coinOne = new Coin(50);
        Coin coinTwo = new Coin(10);
        Coin coinThree = new Coin(20);
        inventory.put(coinOne, 22);
        inventory.put(coinThree, 3);

        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);
        inventoryService.loadInventory();

        assertEquals(inventoryService.getQuantity(coinOne), 22);
        assertEquals(inventoryService.getQuantity(coinTwo), 0);
        assertEquals(inventoryService.getQuantity(coinThree), 3);
        verify(inventoryRepositoryMock).readInventory();
    }

    @Test
    public void testLoadInventory() throws Exception {

        inventory.put(new Coin(10), 3);
        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);

        assertTrue(inventoryService.loadInventory());
        verify(inventoryRepositoryMock).readInventory();
    }

    @Test
    public void testPersistInventory() throws Exception {

        // when inventory is null saveInventory is never called
        when(inventoryRepositoryMock.readInventory()).thenReturn(null);
        inventoryService.loadInventory();
        inventoryService.persistInventory();
        verify(inventoryRepositoryMock, never()).saveInventory(inventoryService.getInventory());

        // when inventory is not null the saveInventory method is called
        Map<Coin, Integer> anotherInventory = new TreeMap<>();
        anotherInventory.put(new Coin(100), 4);
        when(inventoryRepositoryMock.readInventory()).thenReturn(anotherInventory);
        inventoryService.loadInventory();
        inventoryService.persistInventory();
        verify(inventoryRepositoryMock).saveInventory(anotherInventory);
    }

    @Test
    public void testGetMinimalAvailableCoin() throws Exception {
        inventory.put(new Coin(5), 1);
        inventory.put(new Coin(1), 0);
        inventory.put(new Coin(10), 1);
        inventory.put(new Coin(20), 22);

        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);
        inventoryService.loadInventory();

        assertEquals(inventoryService.getMinimalAvailableCoin(), new Coin(5));
        inventoryService.deduct(new Coin(5));
        assertEquals(inventoryService.getMinimalAvailableCoin(), new Coin(10));
    }

    @Test
    public void testAdd() throws Exception {
        inventory.put(new Coin(5), 1);
        inventory.put(new Coin(1), 0);
        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);
        inventoryService.loadInventory();

        assertEquals(inventoryService.getMinimalAvailableCoin(), new Coin(5));
        assertFalse(inventoryService.hasCoin(new Coin(1)));
        inventoryService.add(new Coin(1));
        assertEquals(inventoryService.getMinimalAvailableCoin(), new Coin(1));
        assertTrue(inventoryService.hasCoin(new Coin(1)));
    }

    @Test
    public void testDeduct() throws Exception {
        inventory.put(new Coin(5), 1);
        inventory.put(new Coin(1), 0);
        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);
        inventoryService.loadInventory();

        int beforeCoinFiveCount = inventoryService.getQuantity(new Coin(5));
        int beforeCoinOneCount = inventoryService.getQuantity(new Coin(0));
        assertTrue(inventoryService.hasCoin(new Coin(5)));
        assertFalse(inventoryService.hasCoin(new Coin(1)));
        // deduct one coin from each denmination
        inventoryService.deduct(new Coin(5));
        inventoryService.deduct(new Coin(1));
        int afterCoinFiveCount = inventoryService.getQuantity(new Coin(5));
        int afterCoinOneCount = inventoryService.getQuantity(new Coin(0));

        assertFalse(inventoryService.hasCoin(new Coin(5)));
        assertFalse(inventoryService.hasCoin(new Coin(1)));
        assertEquals(beforeCoinFiveCount, afterCoinFiveCount + 1);
        assertEquals(beforeCoinOneCount, afterCoinOneCount);

    }

    @Test
    public void testHasCoin() throws Exception {
        inventory.put(new Coin(5), 1);
        inventory.put(new Coin(1), 0);
        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);
        inventoryService.loadInventory();

        assertTrue(inventoryService.hasCoin(new Coin(5)));
        assertFalse(inventoryService.hasCoin(new Coin(1)));
        assertFalse(inventoryService.hasCoin(new Coin(10)));
    }


    @Test
    public void testGetAvailableDenomination() throws Exception {
        inventory.put(new Coin(5), 1);
        inventory.put(new Coin(1), 0);
        when(inventoryRepositoryMock.readInventory()).thenReturn(inventory);
        inventoryService.loadInventory();

        List<Coin> expectedDenomination = Arrays.asList(new Coin(5));
        Collections.sort(expectedDenomination);
        assertEquals(inventoryService.getAvailableDenomination(), expectedDenomination);

        inventoryService.add(new Coin(1));
        expectedDenomination = Arrays.asList(new Coin(5), new Coin(1));
        Collections.sort(expectedDenomination);
        assertEquals(inventoryService.getAvailableDenomination(), expectedDenomination);
    }
}