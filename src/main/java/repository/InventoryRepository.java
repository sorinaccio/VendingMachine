package repository;

import exception.UnrealAmountException;
import model.Coin;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Created by sorinaccio on 1/8/2017.
 */
public class InventoryRepository {

    private String filename = "coin-inventory.properties";
    private Map<Coin, Integer> coinInventory;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Map<Coin, Integer> readInventory() {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        coinInventory = new TreeMap<Coin, Integer>();

        try (InputStream resourceStream = loader.getResourceAsStream(filename)) {
            properties.load(resourceStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            int denomination = Integer.parseInt(value);
            int availableCoins = Integer.parseInt((value));
            if(denomination <= 0)
                throw new UnrealAmountException("Coin denomination should be positive. Current value: " + denomination);
            if(availableCoins < 0)
                throw new UnrealAmountException("Amount of coins in the inventory cannot be negative");
            coinInventory.put(new Coin(denomination), availableCoins);
        }

        return coinInventory;
    }

    public void saveInventory(Map<Coin, Integer> inventory) {
        Properties properties = new Properties();
        for (Map.Entry<Coin, Integer> entry : inventory.entrySet()) {
            properties.setProperty(Integer.toString(entry.getKey().getDenomination()),
                    Integer.toString(entry.getValue()));
        }

        try {
            properties.store(new FileWriter(filename), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
