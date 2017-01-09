## Synopsis

This application receives as input an amount of money (pence) and based on a Inventory of coins (available coins) gives the optimal change. The denomination used is below

Denomination
  o	One pound = 100p
  o	Fifty pence = 50p
  o	Twenty pence = 20p
  o	Ten pence = 10p
  o	Five pence = 5p
  o	Two pence = 2p
  o	One penny = 1p

There is a limited amount of coins for each denomination.

## Code Example

All the code is around 2 interfaces which serve as optimal change for unlimited coin inventory and optimal change for limited amount of denominations.

public interface OptimalUnlimitedCoinsChangeService {
    public Collection<Coin> getOptimalChangeFor(int pence);
}

public interface ChangeService {
    public Collection<Coin> getChangeFor(int pence);
}


## Motivation

This project could be used in many real-world scenarios: BUS tickets, coffee machines, self-service accounting machine.


## Installation

1. Clone the git repository or download as ZIP file.
2. Open a command prompt and go to the project directory
3. Build the project with maven: mvn package 
  a. This will run also the Unit Tests
  b. It will create a target folder
  c. A jar is also build

4. From the <project_root>/target/classes/ folder run the application as:
  java VendingMachine <amount_to_be_changed>

5. The inventory coins will be read from coin-inventory.properties file.
6. The Optimal Solution based on the Inventory will be displayed on the terminal.
7. The Inventory will be saved in the same file.
  
## API Reference

As a first version the tool can be run only from the command line.

## Tests

Describe and show how to run the tests with code examples.

## Contributors

Sorin Amza <amzasorin@yahoo.com>

## License

You are free to use, modify and redistribute the software.