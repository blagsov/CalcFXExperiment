package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import javax.management.Query;
import java.sql.*;

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

    private String storyElement = "";
    private static final String url = "jdbc:mysql://localhost:3306/test?user=root&password=Golovastik&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "Golovastik";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private void insert(double num1, double num2, String operatorCalc, String equally, double total) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();
            String query = "INSERT INTO test.test (number1, operatorCalculator, number2, equallyAll, totalNumber) VALUES (?, ?, ?, ?,?)";
            //значения с панели
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDouble(1, num1);
            preparedStatement.setString(2, operatorCalc);
            preparedStatement.setDouble(3, num2);
            preparedStatement.setString(4, equally);
            preparedStatement.setDouble(5, total);
            preparedStatement.executeUpdate();
            // executing SELECT query
            //   stmt.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt  here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }

        }
    }


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
    private void processOperator(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        String value = ((Button) event.getSource()).getText();//ispolzovat znachenie knopki, poluchit znachenie knopki
        if (!"=".equals(value)) { //esli operacija ne ravno
            if (!operator.isEmpty()) return;//esli ne pustaja
            operator = value; //operator - znachenie
            num1 = Double.parseDouble(output.getText());
            //  memoryTextPanel.setText(String.valueOf(num1));
            output.setText("");//ochishaem
        } else {
            if (operator.isEmpty()) return;//prosto vyvoditsja znachenie, nichego ne proishodit
            num2 = Double.parseDouble(output.getText());
            output.setText(String.valueOf(model.calculation(num1, num2, operator)));//pervoe chislo,chislo posle operatora, operator
            memoryTextPanel.setText(memoryTextPanel.getText() + num1 + " " + operator + " " + num2 +
                    " = " + model.calculation(num1, num2, operator) + "\n");

//            storyElement = memoryTextPanel.getText() + num1 + " " + operator + " " + num2 +
//                    " = " + model.calculation(num1, num2, operator);
            double total = model.calculation(num1, num2, operator);
            insert(num1, num2, operator, "=", total);
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
