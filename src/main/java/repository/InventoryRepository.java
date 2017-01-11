package repository;

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
            coinInventory.put(new Coin(Integer.parseInt(key)), Integer.parseInt(value));
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
