package sample;

public class Model {
    public long calculation(long a, long b, String operator){
        switch (operator){ //proverjaem switchem calculjator
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "/":
                if (b==0)return 0;//chtoby ne delit na 0
                return a/b;
        }
        System.out.println("Неизвестный оператор!");
        return 0;
    }
}
