import model.Coin;
import repository.InventoryRepository;
import service.ChangeService;
import service.ChangeServiceImpl;
import service.InventoryService;
import service.InventoryServiceImpl;

import java.util.Collection;

/**
 * Created by sorinaccio on 1/9/2017.
 */
public class VendingMachine {

    public static final String INVENTORY_FILE = "coin-inventory.properties";

    public static void main(String[] args) {


        if(args.length != 1)
                throw new RuntimeException("Illegal Number of parameters passed. Pass the amount to be changed");
        int amount;
        try {
            amount = Integer.valueOf(args[0]);
        }
        catch(Exception ex) {
            throw new RuntimeException("Argument provided " + args[0] + " cannot be parsed to int");
        }

        InventoryRepository repository = new InventoryRepository();
        repository.setFilename(INVENTORY_FILE);
        InventoryService inventoryService = new InventoryServiceImpl(repository);
        if(!inventoryService.loadInventory()) {
            throw new RuntimeException("Inventory could not be loaded. No available coins!");
        }

        ChangeService changeService = new ChangeServiceImpl(inventoryService);
        Collection<Coin> changeCoinList = changeService.getChangeFor(amount);

        inventoryService.persistInventory();

        System.out.println("Optimal Change for " + amount + " with available coins: ");
        for(Coin coin : changeCoinList) {
            System.out.print(coin.getDenomination() + " ");
        }

    }

}
