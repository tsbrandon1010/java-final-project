import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;


// Thomas Brandon - 000769693
// MCS 3603
// 12/11/2022

// I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else's work as my own

public class PointOfSaleGUI extends JFrame implements ActionListener{

    // initializing our product catalog, and user class
    Main.Product[] catalog = new Main.Product[5];
    static Main.User user = new Main.User();

    JButton submitButton;
    JTextField item1QuantityText;
    JTextField item2QuantityText;
    JTextField item3QuantityText;
    JTextField item4QuantityText;
    JTextField item5QuantityText;
    Border lineBorder =  new LineBorder(Color.BLACK, 2);
    JButton saveInvoice;
    JPanel rightPanel = new JPanel(new GridLayout(5, 1, 5, 5));
    JRadioButton fullPaymentButton;
    JRadioButton installmentPaymentButton;
    double fTotal;
    double fSubtotal;
    boolean fullPay;

    JLabel item1Name = new JLabel();
    JLabel item1Quantity = new JLabel();
    JLabel item1SubTotal = new JLabel();
    JLabel item2Name = new JLabel();
    JLabel item2Quantity = new JLabel();
    JLabel item2SubTotal = new JLabel();
    JLabel item3Name = new JLabel();
    JLabel item3Quantity = new JLabel();
    JLabel item3SubTotal = new JLabel();
    JLabel item4Name = new JLabel();
    JLabel item4Quantity = new JLabel();
    JLabel item4SubTotal = new JLabel();
    JLabel item5Name = new JLabel();
    JLabel item5Quantity = new JLabel();
    JLabel item5SubTotal = new JLabel();

    JLabel subPrice = new JLabel();
    JLabel totalPrice = new JLabel();

    // this function creates the panel we use to display the invoice
    void createInvoicePanel() {

        updateInvoicePanel();
        JPanel invoiceItem1 = new JPanel(new GridLayout(0, 1, 5, 5));
        invoiceItem1.add(item1Name);
        invoiceItem1.add(item1Quantity);
        invoiceItem1.add(item1SubTotal);
        invoiceItem1.setBorder(lineBorder);

        JPanel invoiceItem2 = new JPanel(new GridLayout(0, 1, 5, 5));
        invoiceItem2.add(item2Name);
        invoiceItem2.add(item2Quantity);
        invoiceItem2.add(item2SubTotal);
        invoiceItem2.setBorder(lineBorder);

        JPanel invoiceItem3 = new JPanel(new GridLayout(0, 1, 5, 5));
        invoiceItem3.add(item3Name);
        invoiceItem3.add(item3Quantity);
        invoiceItem3.add(item3SubTotal);
        invoiceItem3.setBorder(lineBorder);

        JPanel invoiceItem4 = new JPanel(new GridLayout(0, 1, 5, 5));
        invoiceItem4.add(item4Name);
        invoiceItem4.add(item4Quantity);
        invoiceItem4.add(item4SubTotal);
        invoiceItem4.setBorder(lineBorder);

        JPanel invoiceItem5 = new JPanel(new GridLayout(0, 1, 5, 5));
        invoiceItem5.add(item5Name);
        invoiceItem5.add(item5Quantity);
        invoiceItem5.add(item5SubTotal);
        invoiceItem5.setBorder(lineBorder);

        JPanel total = new JPanel(new GridLayout(0, 1, 5, 5));
        total.add(subPrice);
        total.add(totalPrice);

        // adding an action listener to the button
        saveInvoice = new JButton("Save Invoice");
        saveInvoice.addActionListener(this);
        total.add(saveInvoice);

        rightPanel.add(invoiceItem1);
        rightPanel.add(invoiceItem2);
        rightPanel.add(invoiceItem3);
        rightPanel.add(invoiceItem4);
        rightPanel.add(invoiceItem5);
        rightPanel.add(total);
    };

    // this function will update the information in the invoice panel
    // this will allow the user to update their invoice when the want
    void updateInvoicePanel() {
        item1Name.setText("Item 1: " + catalog[0].getName());
        item1Quantity.setText("Quantity: " + catalog[0].getNumOfItems());
        item1SubTotal.setText("Price: $" + String.format("%.2f" ,Main.calculateSubTotal(catalog[0])));

        item2Name.setText("Item 2: " + catalog[1].getName());
        item2Quantity.setText("Quantity: " + catalog[1].getNumOfItems());
        item2SubTotal.setText("Price: $" + String.format("%.2f" ,Main.calculateSubTotal(catalog[1])));

        item3Name.setText("Item 3: " + catalog[2].getName());
        item3Quantity.setText("Quantity: " + catalog[2].getNumOfItems());
        item3SubTotal.setText("Price: $" + String.format("%.2f" ,Main.calculateSubTotal(catalog[2])));

        item4Name.setText("Item 5: " + catalog[3].getName());
        item4Quantity.setText("Quantity: " + catalog[3].getNumOfItems());
        item4SubTotal.setText("Price: $" + String.format("%.2f" ,Main.calculateSubTotal(catalog[3])));

        item5Name.setText("Item 5: " + catalog[4].getName());
        item5Quantity.setText("Quantity: " + catalog[4].getNumOfItems());
        item5SubTotal.setText("Price: $" + String.format("%.2f" ,Main.calculateSubTotal(catalog[4])));


        // here we are calculating the total and subtotal
        fSubtotal = 0;
        for (int i = 0; i < catalog.length; i++) {
            fSubtotal += Main.calculateSubTotal(catalog[i]);
        }
        fTotal = (fSubtotal * 1.06);
        if(fullPay) {
            fTotal *= .95;
        }
        subPrice.setText("Your Subtotal is: $" + String.format("%.2f", fSubtotal));
        totalPrice.setText("Your Total is: $" + String.format("%.2f", fTotal));

    };

    PointOfSaleGUI(Main.User user) {

        // first we are reading the catalog from the file, and then inserting the information into our array
        Main.readCatalog(catalog);


        // creating the panel that stores the customer information
        JPanel leftPanel = new JPanel();
        JPanel customerInfoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JLabel nameLabel = new JLabel("Name: " + user.userName);
        nameLabel.setBorder(lineBorder);
        JLabel lastTranDateLabel = new JLabel("Last Transaction Date: " + user.lastTransactionDate);
        lastTranDateLabel.setBorder(lineBorder);
        JLabel lastTranTotalLabel = new JLabel("Last Transaction Total: " + user.lastTransactionTotal);
        lastTranTotalLabel.setBorder(lineBorder);

        customerInfoPanel.setBorder(lineBorder);
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(lastTranDateLabel);
        customerInfoPanel.add(lastTranTotalLabel);

        // the catalog/ordering panel that holds the item information, and text box to set the item quantity
        JPanel item1 = new JPanel(new GridLayout(4, 1, 5, 5));
        ImageIcon appleIcon = new ImageIcon("apple.png");
        JLabel appleLabel = new JLabel(appleIcon);
        JLabel item1L1 = new JLabel("Name: " + catalog[0].getName());
        JLabel item1L2 = new JLabel("Description: " + catalog[0].getProductDescription());
        JLabel item1L3 = new JLabel("Price: $" + String.format("%.2f", catalog[0].getPrice()));
        JPanel item1QuantityPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        item1QuantityText = new JTextField();
        item1QuantityText.setPreferredSize(new Dimension(10,40));
        item1QuantityText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        JLabel quantity1Text = new JLabel("Quantity: ");
        item1QuantityPanel.add(quantity1Text);
        item1QuantityPanel.add(item1QuantityText);
        item1.add(appleLabel);
        item1.add(item1L1);
        item1.add(item1L2);
        item1.add(item1L3);
        item1.setBorder(lineBorder);

        JPanel item2 = new JPanel(new GridLayout(4, 1, 5, 5));
        ImageIcon breadIcon = new ImageIcon("bread.png");
        JLabel breadLabel = new JLabel(breadIcon);
        JLabel item2L1 = new JLabel("Name: " + catalog[1].getName());
        JLabel item2L2 = new JLabel("Description: " + catalog[1].getProductDescription());
        JLabel item2L3 = new JLabel("Price: $" + String.format("%.2f", catalog[1].getPrice()));
        JPanel item2QuantityPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JLabel quantity2Text = new JLabel("Quantity: ");
        item2QuantityText = new JTextField();
        item2QuantityText.setPreferredSize(new Dimension(10,40));
        item2QuantityText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        item2QuantityPanel.add(quantity2Text);
        item2QuantityPanel.add(item2QuantityText);

        item2.add(breadLabel);
        item2.add(item2L1);
        item2.add(item2L2);
        item2.add(item2L3);
        item2.setBorder(lineBorder);

        JPanel item3 = new JPanel(new GridLayout(4, 1, 5, 5));
        ImageIcon waterIcon = new ImageIcon("water-bottle.png");
        JLabel waterLabel = new JLabel(waterIcon);
        JLabel item3L1 = new JLabel("Name: " + catalog[2].getName());
        JLabel item3L2 = new JLabel("Description: " + catalog[2].getProductDescription());
        JLabel item3L3 = new JLabel("Price: $" + String.format("%.2f", catalog[2].getPrice()));

        JPanel item3QuantityPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        item3QuantityText = new JTextField();
        JLabel quantity3text = new JLabel("Quantity: ");
        item3QuantityText.setPreferredSize(new Dimension(10,40));
        item3QuantityText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        item3QuantityPanel.add(quantity3text);
        item3QuantityPanel.add(item3QuantityText);
        item3.add(waterLabel);
        item3.add(item3L1);
        item3.add(item3L2);
        item3.add(item3L3);
        item3.setBorder(lineBorder);

        JPanel item4 = new JPanel(new GridLayout(4, 1, 5, 5));
        ImageIcon orangeIcon = new ImageIcon("orange.png");
        JLabel orangeLabel = new JLabel(orangeIcon);
        JLabel item4L1 = new JLabel("Name: " + catalog[3].getName());
        JLabel item4L2 = new JLabel("Description: " + catalog[3].getProductDescription());
        JLabel item4L3 = new JLabel("Price: $" + String.format("%.2f", catalog[3].getPrice()));

        JPanel item4QuantityPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        item4QuantityText = new JTextField();
        JLabel quantity4text = new JLabel("Quantity: ");
        item4QuantityText.setPreferredSize(new Dimension(10,40));
        item4QuantityText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        item4QuantityPanel.add(quantity4text);
        item4QuantityPanel.add(item4QuantityText);
        item4.add(orangeLabel);
        item4.add(item4L1);
        item4.add(item4L2);
        item4.add(item4L3);
        item4.setBorder(lineBorder);

        JPanel item5 = new JPanel(new GridLayout(4, 1, 5, 5));
        ImageIcon chocolateIcon = new ImageIcon("chocolate-bar.png");
        JLabel chocolateLabel = new JLabel(chocolateIcon);
        JLabel item5L1 = new JLabel("Name: " + catalog[4].getName());
        JLabel item5L2 = new JLabel("Description: " + catalog[4].getProductDescription());
        JLabel item5L3 = new JLabel("Price: $" + String.format("%.2f", catalog[4].getPrice()));

        JPanel item5QuantityPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        item5QuantityText = new JTextField();
        JLabel quantity5text = new JLabel("Quantity: ");
        item5QuantityText.setPreferredSize(new Dimension(10,40));
        item5QuantityText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        item5QuantityPanel.add(quantity5text);
        item5QuantityPanel.add(item5QuantityText);
        item5.add(chocolateLabel);
        item5.add(item5L1);
        item5.add(item5L2);
        item5.add(item5L3);
        item5.setBorder(lineBorder);


        JPanel catalogPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        catalogPanel.add(item1);
        catalogPanel.add(item1QuantityPanel);
        catalogPanel.add(item2);
        catalogPanel.add(item2QuantityPanel);
        catalogPanel.add(item3);
        catalogPanel.add(item3QuantityPanel);
        catalogPanel.add(item4);
        catalogPanel.add(item4QuantityPanel);
        catalogPanel.add(item5);
        catalogPanel.add(item5QuantityPanel);

        // creating the button, with an action listener, that will allow the customer to submit the order
        submitButton = new JButton("Submit Order");
        submitButton.addActionListener(this);
        catalogPanel.add(submitButton);

        // creating the radio buttons that allows the user to select either full payment, or installments. default is installments
        fullPaymentButton = new JRadioButton();
        installmentPaymentButton = new JRadioButton();
        fullPaymentButton.setText("Full Payment Method");
        installmentPaymentButton.setText("Installment Payment Method");
        ButtonGroup G1 = new ButtonGroup();
        G1.add(fullPaymentButton);
        G1.add(installmentPaymentButton);
        G1.setSelected(installmentPaymentButton.getModel(), true);

        leftPanel.add(customerInfoPanel);
        leftPanel.add(catalogPanel);
        leftPanel.add(installmentPaymentButton);
        leftPanel.add(fullPaymentButton);
        setLayout(new GridLayout(1,2,5,5));
        add(leftPanel);
        fullPay = fullPaymentButton.isSelected();
        createInvoicePanel();
        add(rightPanel);

    }

    public static void main(String[] args) {

        // we are calling the class that creates the customer info input frame
        CustomerInfoFrame custInfoFrame = new CustomerInfoFrame();

        // this ensures that we do not open the main point of sale frame until the customer info frame is closed
        while(custInfoFrame.windowIsOpen) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

        // if the customer info frame is close, we can open the point of sale frame
        if (!custInfoFrame.windowIsOpen) {
            Main.readCustomerFile(custInfoFrame.userId, user);
            System.out.println(user.userName);

            JFrame frame = new PointOfSaleGUI(user);

            frame.setSize(1100, 900);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // then we call the constructor to this class
        }
    }

    // these are our action listeners
    @Override
    public void actionPerformed(ActionEvent e) {

        // this action listener is for the button that creates the invoice
        // if the create invoice button is pressed, then we populate the invoice frame with the newest data
        if (e.getSource() == submitButton) {
            // updating the number of items being purchased
            catalog[0].setNumOfItems(Integer.parseInt(item1QuantityText.getText()));
            catalog[1].setNumOfItems(Integer.parseInt(item2QuantityText.getText()));
            catalog[2].setNumOfItems(Integer.parseInt(item3QuantityText.getText()));
            catalog[3].setNumOfItems(Integer.parseInt(item4QuantityText.getText()));
            catalog[4].setNumOfItems(Integer.parseInt(item5QuantityText.getText()));
            fullPay = fullPaymentButton.isSelected();
            // we are calling the class that updates the invoice panel
            updateInvoicePanel();
        }

        // this action listener is for the button that allows the user to save their invoice to a file
        if (e.getSource() == saveInvoice) {
            // if the customer number that was entered was not found, we use the customer name "guest"
            if (Objects.equals(user.userName, "CUSTOMER NOT FOUND")) {
                user.userName = "Guest";
            }
            // we call our function that allows use to save the invoice to a file
            Main.saveInvoiceToFile(catalog, fSubtotal, fTotal, user.userName);
        }
    }
}