package sample;

import javafx.scene.control.Button;

public class Model {

    double endResult = 0;
    double memoryStore = 0;
    Button button = new Button();

    public double calculation(double a, double b, String operator) {
        switch (operator) { //proverjaem switchem calculjator
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) return 0;//chtoby ne delit na 0
                return a / b;
        }
        System.out.println("Неизвестный оператор!");
        return 0;
    }

}
