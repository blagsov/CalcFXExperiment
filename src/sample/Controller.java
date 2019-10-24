package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private Text output;
    @FXML
    private Text memoryTextPanel;
    private double num1 = 0;
    private double num2 = 0;

    private boolean start = true;
    private String operator = "";
    private Model model = new Model();

    private double memoryStore = 0;


    @FXML //для того, чтобы fxml мог обращаться к приватным методам
    private void processNum(javafx.event.ActionEvent event) {
        if (start) {
            output.setText("");//ochishaem pole
            start = false;
        }
        String value = ((Button) event.getSource()).getText();//ispolzovat znachenie knopki, poluchit znachenie knopki
        output.setText(output.getText() + value);//k cifram, chto imeem dobavlyaem eche odnu
        //   memoryTextPanel.setText(value);
    }

    @FXML
    private void processOperator(javafx.event.ActionEvent event) {
        String value = ((Button) event.getSource()).getText();//ispolzovat znachenie knopki, poluchit znachenie knopki
        if (!"=".equals(value)) { //esli operacija ne ravno
            if (!operator.isEmpty()) return;//esli ne pustaja
            operator = value; //operator - znachenie
            num1 = Double.parseDouble(output.getText());
            //  memoryTextPanel.setText(String.valueOf(num1));
            output.setText("");//ochishaem
        } else {
            if (operator.isEmpty()) return;//prosto vyvoditsja znachenie, nichego ne proishodit
            num2 = Long.parseLong(output.getText());
            output.setText(String.valueOf(model.calculation(num1, num2, operator)));//pervoe chislo,chislo posle operatora, operator
            memoryTextPanel.setText(memoryTextPanel.getText() + num1 + " " + operator + " " + num2 +
                    " = " + model.calculation(num1, num2, operator) + "\n");
            operator = "";
            start = true;
        }
    }

    @FXML
    private void processMemory(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();//ispolzovat znachenie knopki, poluchit znachenie knopki

        if (value.equals("MS")) {
            memoryStore = Double.parseDouble(output.getText());
            return;
        }
        if (value.equals("MC")) {
            //Memory Clear
            memoryStore = 0;
            return;
        }
        if (value.equals("MR")) {
            //Memory Recall
            output.setText(String.valueOf(memoryStore));
            return;
        }
        double endResult = 0;
        if (value.equals("M-")) {
            // Memory subtract
            endResult = Double.parseDouble(output.getText());
            memoryStore -= endResult;
            return;
        }
        if (value.equals("M+")) {
            // Memory add
            endResult = Double.parseDouble(output.getText());
            memoryStore += endResult;
            return;
        }
        if (value.equals("C")) {
            output.setText("");
            operator = "";
            start = true;

        }
        if (value.equals("CE")) {
            output.setText("");
        }
    }


}
