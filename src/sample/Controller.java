package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;

public class Controller {
    @FXML
    private Text output;
    private long num1 = 0;
    // long num2 = 0;

    private boolean start = true;
    private String operator = "";
    private Model model = new Model();


    @FXML //для того, чтобы fxml мог обращаться к приватным методам
    private void processNum(javafx.event.ActionEvent event) {
        if (start) {
            output.setText("");//ochishaem pole
            start = false;
        }
        String value = ((Button) event.getSource()).getText();//ispolzovat znachenie knopki, poluchit znachenie knopki
        output.setText(output.getText() + value);//k cifram, chto imeem dobavlyaem eche odnu
    }

    @FXML
    private void processOperator(javafx.event.ActionEvent event) {
        String value = ((Button) event.getSource()).getText();//ispolzovat znachenie knopki, poluchit znachenie knopki
        if (!"=".equals(value)) { //esli operacija ne ravno
            if (!operator.isEmpty()) return;//esli ne pustaja
            operator = value; //operator - znachenie
            num1 = Long.parseLong(output.getText());
            output.setText("");//ochishaem
        } else {
            if (operator.isEmpty()) return;//prosto vyvoditsja znachenie, nichego ne proishodit
            output.setText(String.valueOf(model.calculation(num1, Long.parseLong(output.getText()), operator)));//pervoe chislo,chislo posle operatora, operator
            operator = "";
            start = true;

        }


    }

}
