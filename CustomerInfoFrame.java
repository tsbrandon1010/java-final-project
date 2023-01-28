import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

// Thomas Brandon - 000769693
// MCS 3603
// 12/11/2022

// I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else's work as my own

// this class creates a window that allows us to take the customer number from the user
public class CustomerInfoFrame extends JFrame implements ActionListener {

    JButton submitButton;
    JTextField userIdText;
    String userId;
    Boolean windowIsOpen;

    CustomerInfoFrame(){
        windowIsOpen = true;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Enter user ID");
        this.setLayout(new FlowLayout());

        JLabel userInstructions = new JLabel("Enter your user ID: ");

        // creating a button with an action listener
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        // creating a text field for the customer number
        userIdText = new JTextField();
        userIdText.setPreferredSize(new Dimension(250,40));

        // this segment prevents the user from entering invalid input into the field
        // (can't be over 8 characters, and cannot contain non-numeric values)
        userIdText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (userIdText.getText().length() >= 9) {
                    e.consume();
                }
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });

        this.add(userInstructions);
        this.add(submitButton);
        this.add(userIdText);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // this is the action listener for the submit button
        if (e.getSource() == submitButton) {
            // if the input field is not empty
            if(!Objects.equals(userIdText.getText(), "")){

                // we set the userID to the text in the field
                userId = userIdText.getText();
                System.out.println("User ID: " + userId);

                // close the window
                this.windowIsOpen = false;
                this.setVisible(false);
            }
        }
    }
}
