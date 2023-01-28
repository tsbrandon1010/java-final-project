import java.util.Objects;
import  java.util.Scanner;

// Thomas Brandon - 000769693
// MCS 3603
// 12/11/2022

// I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else's work as my own

public class PointOfSaleTerminal {

    // creating a function that prints out and formats the information for our Product class
    public static void printProduct(Main.Product prod) { // passing an instance of the Product class into the function
        System.out.println("\nProduct Code: " + prod.getProductCode());
        System.out.println("\tName: " + prod.getName());
        System.out.println("\tProduct Description: " + prod.getProductDescription());
        System.out.printf("\tPrice:  $" + "%,.2f", prod.getPrice());
    }

    // creating a function to format and print the final invoice
    public static void printInvoice(Main.Product[] catalog) { // we are passing in an array of products, and an array of number of products
        for(int i = 0; i < catalog.length; i++) {
            System.out.print("\nItem: " + catalog[i].getName() + "\n  Price: $");
            System.out.printf( "%,.2f", catalog[i].getPrice());
            System.out.printf("\n\tCount: " + catalog[i].getNumOfItems() + "\n\tSubtotal: $" + "%,.2f", Main.calculateSubTotal(catalog[i]));
        }
    }

    public static void main(String[] args) {

        // creating our catalog, and our user class
        Main.Product[] catalog = new Main.Product[5];
        Main.User user = new Main.User();

        // using our function to read the catalog from a file
        Main.readCatalog(catalog);

        Scanner input = new Scanner(System.in);
        // printing the catalog
        System.out.println("\n\t********** Our Catalog **********");
        for(int i = 0; i < catalog.length; i++) {
            printProduct(catalog[i]);
        }

        // taking the customer code
        System.out.println("\n\nPlease enter your customer code (8 digits): ");
        String customerCode = input.nextLine();

        // passing the customer code, and our customer class to the function that reads cust. info from a file
        Main.readCustomerFile(customerCode, user);

        // if an invalid customer id is entered, or the ID is not found we use "guest mode"
        if (Objects.equals(user.userId, "CUSTOMER NOT FOUND")) {
            System.out.println("\t\t**** An invalid customer number was entered, running in guest mode ****");
            user.userName = "Guest";
        }
        else {
            System.out.println("Welcome, " + user.userName + "!");
            System.out.print("Your last transaction was on " + user.lastTransactionDate + " for a total of: " + user.lastTransactionTotal + "\n\n");
        }


        for(int i = 0; i < catalog.length; i++) {
            try {
                System.out.println("How much of the following item would you like to purchase: " + catalog[i].getName());
                int numOfItems = input.nextInt();
                if (numOfItems < 0) {
                    throw new ArithmeticException("\t************* ERROR: a negative number of items cannot be entered *************\n");
                }
                catalog[i].setNumOfItems(numOfItems);
            }
            catch (Exception e) {
                // if it was a custom exception we display that message
                if (e.getMessage() != null){
                    System.out.println(e.getMessage());
                }
                // otherwise we display a generic message
                else {
                    System.out.println("\t************* ERROR: invalid data was inputted to the field *************\n");
                    input.nextLine(); // we have to clear the invalid input from the scanner
                }
                // if there was an exception we set the number of items to 0, and continue
                catalog[i].setNumOfItems(0);
            }
        }

        // calculating the total
        double total = 0;
        double subTotal = 0;
        for(int i = 0; i < catalog.length; i++) {
            subTotal += Main.calculateSubTotal(catalog[i]);
        }

        System.out.printf("Your subtotal is: $" + "%,.2f", subTotal);

        // determining the payment method
        System.out.println("\nWould you like to pay with 'full payment' or 'installments'? (Answer with 'full' or 'installments')");
        String paymentMethod = input.next();

        // calculating total if full payment type is selected

        // I had to use this Objects.equals() method in order to compare the string, comparing strings using '==' wasn't working (I might have been trying to use the C++ implementation)
        if (Objects.equals(paymentMethod, "full")) {
            total = subTotal * .95; // adding the 5% off
            total = total * 1.06; // adding the 6% sales tax

            System.out.println("\t********** Invoice **********");
            System.out.println(user.userName + ", you have selected full payment. A 5% discount has been added to your order. Your invoice is attached below.");

        }

        // calculating total if installment payment type is selected
        else {
            total = subTotal * 1.06;; // adding the 6% sales tax

            System.out.println("\t********** Invoice **********");
            if (Objects.equals(paymentMethod, "installments")) {
                System.out.println(user.userName + ", you have selected installments. Your invoice is attached below.");
            }
            else {
                System.out.println(user.userName + ", invalid payment type was entered. We have automatically selected installments. Your invoice is attached below.");
            }

        }
        printInvoice(catalog);
        System.out.printf("\nTotal: $" + "%,.2f", total);

        // asking the customer if they would like to save the invoice to a file
        System.out.println("\nWould you like to save your invoice? (Y/N)");
        String saveInvoice = input.next();
        if (Objects.equals(saveInvoice, "Y")) {

            // if they answer yes we call the function that saves the invoice to a file
            Main.saveInvoiceToFile(catalog, subTotal, total, user.userName);
        }
    }
}