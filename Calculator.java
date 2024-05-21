import java.awt.*;
import java.awt.event.*;

public class Calculator extends Frame implements ActionListener {
    private TextField display;
    private String currentOperator;
    private double firstOperand, secondOperand, result;
    private boolean isNegative, isDecimal;

    public Calculator() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 500);
        setTitle("Enhanced Calculator");
        setBackground(Color.lightGray);
        display = new TextField();
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);
       Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "±", "√", "^"
        };

        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        Button clearButton = new Button("Clear History");
        clearButton.setFont(new Font("Arial", Font.BOLD, 20));
        clearButton.addActionListener(this);
        add(clearButton, BorderLayout.SOUTH);

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
            clearDisplay();
        } else if (command.equals("±")) {
            toggleNegative();
        } else if (command.equals("√")) {
            calculateSquareRoot();
        } else if (command.equals("^")) {
            setPower();
        } else if (command.equals("=")) {
            performCalculation();
        } else if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            updateDisplay(command);
        } else {
            handleOperator(command);
        }
    }
    private void updateDisplay(String command) {
        if (command.equals(".") && isDecimal) {
            return; // Only allow one decimal point
        }
        if (command.equals(".")) {
            isDecimal = true;
        }
        display.setText(display.getText() + command);
    }

    private void handleOperator(String operator) {
        if (!display.getText().isEmpty()) {
            if (currentOperator == null) {
                firstOperand = Double.parseDouble(display.getText());
                currentOperator = operator;
                display.setText("");
            } else {
                secondOperand = Double.parseDouble(display.getText());
                performCalculation();
                currentOperator = operator;
            }
        }
    }

    private void performCalculation() {
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
                case "^":
                    result = Math.pow(firstOperand, secondOperand);
                    break;
            }
            display.setText(String.format("%.2f", result)); // Display result with 2 decimal places
            currentOperator = null;
        }
    }

    private void clearDisplay() {
        display.setText("");
        currentOperator = null;
        firstOperand = secondOperand = result = 0;
        isNegative = isDecimal = false;
    }

    private void toggleNegative() {
        if (!display.getText().isEmpty() && !isDecimal) {
            double currentValue = Double.parseDouble(display.getText());
            currentValue = -currentValue;
            display.setText(String.valueOf(currentValue));
        }
    }

    private void calculateSquareRoot() {
        if (!display.getText().isEmpty() && !isNegative) {
            double currentValue = Double.parseDouble(display.getText());
            if (currentValue >= 0) {
                double squareRoot = Math.sqrt(currentValue);
                display.setText(String.format("%.2f", squareRoot)); 
            } else {
                display.setText("Error");
            }
        }
    }

    private void setPower() {
        if (!display.getText().isEmpty()) {
            firstOperand = Double.parseDouble(display.getText());
            currentOperator = "^";
            display.setText("");
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
