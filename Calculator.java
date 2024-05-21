import java.awt.*;
import java.awt.event.*;

public class Calculator extends Frame implements ActionListener {
    private TextField display;
    private String currentOperator;
    private double firstOperand, secondOperand, result;

    public Calculator() {
        // Frame properties
        setLayout(new BorderLayout());
        setSize(400, 500);
        setTitle("Simple Calculator");
        setBackground(Color.lightGray);

        // Display field
        display = new TextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Button panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);

        // Buttons
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        // Clear button
        Button clearButton = new Button("C");
        clearButton.setFont(new Font("Arial", Font.BOLD, 20));
        clearButton.addActionListener(this);
        add(clearButton, BorderLayout.SOUTH);

        // Window closing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals("C")) {
            display.setText("");
            currentOperator = null;
            firstOperand = secondOperand = result = 0;
            return;
        }

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            display.setText(display.getText() + command);
        } else if (command.equals("=")) {
            if (currentOperator != null) {
                secondOperand = Double.parseDouble(display.getText());
                switch (currentOperator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                currentOperator = null;
            }
        } else {
            if (display.getText().isEmpty())
                return;
            firstOperand = Double.parseDouble(display.getText());
            display.setText("");
            currentOperator = command;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
