import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import  java.util.Scanner;
import java.util.Objects;


// Thomas Brandon - 000769693
// MCS 3603
// 12/11/2022

// I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else's work as my own

// this is the main function that is run to utilize the point of sale terminal, or point of sale GUI
public class Main {
    static class Product { // creating a product class
        // declaring the methods
        private int productCode;
        private String name;
        private String productDescription;
        private double price;

        private int numOfItems =  0;

        // this is our constructor
        Product(int productCode, String name, String productDescription, double price) {
            this.productCode = productCode;
            this.name = name;
            this.productDescription = productDescription;
            this.price = price;
        }

        //getters
        public int getProductCode() {
            return productCode;
        }

        public String getName() {
            return name;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public double getPrice() {
            return price;
        }

        public int getNumOfItems() {
            return numOfItems;
        }

        //setters
        public void setProductCode(int productCode) {
            this.productCode = productCode;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setNumOfItems(int numOfItems) {
            this.numOfItems = numOfItems;
        }
    }

    // creating a function that calculates the subtotal of an item
    public static double calculateSubTotal(Product prod) { // the number of items being purchased, and an item's object are being passed

        if (prod.getNumOfItems() <= 0) { // if no products are being purchased, the total is 0
            return 0;
        }

        // calculating the subtotal
        double subtotal = prod.getNumOfItems() * prod.getPrice();

        if (prod.getNumOfItems() >= 10) { // if there are 10 or more items, then we apply a 10% discount {
            subtotal = subtotal * .9;
        }

        return subtotal;
    }

    // our user class, that stores some basic information on the user
    public static class User {
        String userId;
        String userName;
        String lastTransactionDate;
        String lastTransactionTotal;

    }

    // this function reads the customer information from a file
    // we pass the customer ID, and the user that we want to save the information to
    static void readCustomerFile(String customerId, User user){
        boolean custFound = false;
        try {
            File file = new File("customerInfo.txt");
            Scanner reader = new Scanner(file);
            // we keep reading lines in the file until we find a line that matches our customer ID
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (Objects.equals(data, customerId)) {
                    custFound = true;
                    // because the format of the file is known, we are able to read all the information into the user class
                    user.userId = data;
                    user.userName = reader.nextLine();
                    user.lastTransactionDate = reader.nextLine();
                    user.lastTransactionTotal = reader.nextLine();
                    break;
                }
            }
        }
        // if the file is not found the program is exited
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(-1);
        }
        // if the customer is not found, we set the user information to default values
        if (!custFound) {
            user.userId = "CUSTOMER NOT FOUND";
            user.userName = "CUSTOMER NOT FOUND";
            user.lastTransactionDate = "CUSTOMER NOT FOUND";
            user.lastTransactionTotal = "CUSTOMER NOT FOUND";
        }
    }

    // this is our function that allows us to read the catalog from a file
    static void readCatalog(Product[] catalog) {
        try {
            File file = new File("catalog.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                for (int i = 0; i < catalog.length; i++) {
                    // using the Product constructor to populate the catalog from the file
                    catalog[i] = new Main.Product(Integer.parseInt(reader.nextLine()), reader.nextLine(), reader.nextLine(), Float.parseFloat(reader.nextLine()));
                    if( reader.hasNextLine()) {
                        reader.nextLine();
                    }
                }
            }
        }
        // if the file is not found, the program is exited
        catch (FileNotFoundException e) {
            System.out.println("Catalog File Not Found");
            System.exit(-1);
        }
    };

    // this function saves the invoice to a file
    static void saveInvoiceToFile(Product[] catalog, double subTotal, double total, String userName) {
        System.out.println("Saving invoice");
        String fileName = "invoice.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(userName);
            for(int i = 0; i < catalog.length; i++) {

                fileWriter.write("\nItem: " + catalog[i].getName() + "\n  Price: $");
                fileWriter.write( String.format("%,.2f", catalog[i].getPrice()));
                fileWriter.write("\n\tCount: " + catalog[i].getNumOfItems() + "\n\tSubtotal: $" + String.format("%,.2f", Main.calculateSubTotal(catalog[i]))  );
            }

            fileWriter.write("\n\nYour Subtotal is: $" + String.format("%.2f", subTotal) + "\n");
            fileWriter.write("Your Total is: $" + String.format("%.2f", total));
            fileWriter.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // We are taking the user's preference on GUI or Command Line interface.
        System.out.println("Would you like to use the GUI, or Command Line? (GUI: Y |  Command Prompt: N)");
        Scanner input = new Scanner(System.in);
        String guiPreference = input.nextLine();

        // If the CLI is selected, we run the CLI code
        if (Objects.equals(guiPreference.charAt(0), 'N')) {
            PointOfSaleTerminal.main(null);
        }

        // If the GUI is selected, we run the GUI code
        else{
            PointOfSaleGUI.main(null);
        }
    }
}
