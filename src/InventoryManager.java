import java.io.*;
import java.util.*;

public class InventoryManager {

    public static void main(String[] args) {
        String fileName = "src/inventory.txt";
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Inventory Management System");
                System.out.println("1. View Inventory");
                System.out.println("2. Add Item");
                System.out.println("3. Update Item");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        readInventory(fileName);
                        break;
                    case 2:
                        System.out.print("Enter item name: ");
                        String newItemName = scanner.nextLine();
                        System.out.print("Enter item count: ");
                        int newItemCount = scanner.nextInt();
                        addItem(fileName, newItemName, newItemCount);
                        break;
                    case 3:
                        System.out.print("Enter item name to update: ");
                        String updateItemName = scanner.nextLine();
                        System.out.print("Enter new item count: ");
                        int updateItemCount = scanner.nextInt();
                        updateItem(fileName, updateItemName, updateItemCount);
                        break;
                    case 4:
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    public static void readInventory(String fileName) {
        // TODO: Read and display inventory from file
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Inventory file not found. Creating a new one.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating inventory file: " + e.getMessage());
            }
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            System.out.println("Current Inventory:");
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }
    }

    public static void addItem(String fileName, String itemName, int itemCount) {
        // TODO: Add a new item to the inventory
        File file = new File(fileName);
        Map<String, Integer> inventory = loadInventoryFromFile(file);

        if (inventory.containsKey(itemName)) {
            System.out.println("Item already exists. Use the update option to modify the count.");
            return;
        }

        inventory.put(itemName, itemCount);
        saveInventoryToFile(file, inventory);
        System.out.println("Item added successfully.");
    }

    public static void updateItem(String fileName, String itemName, int itemCount) {
        // TODO: Update the count of an existing item
        File file = new File(fileName);
        Map<String, Integer> inventory = loadInventoryFromFile(file);

        if (!inventory.containsKey(itemName)) {
            System.out.println("Item does not exist. Use the add option to add it to the inventory.");
            return;
        }

        inventory.put(itemName, itemCount);
        saveInventoryToFile(file, inventory);
        System.out.println("Item updated successfully.");
    }

    private static Map<String, Integer> loadInventoryFromFile(File file) {
        Map<String, Integer> inventory = new HashMap<>();

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Scanner scanner = new Scanner(fis);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        inventory.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                    }
                }
                scanner.close();
            } catch (IOException e) {
                System.out.println("Error reading inventory file: " + e.getMessage());
            }
        }

        return inventory;
    }

    private static void saveInventoryToFile(File file, Map<String, Integer> inventory) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                fos.write((entry.getKey() + "," + entry.getValue() + "\n").getBytes());
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
}
