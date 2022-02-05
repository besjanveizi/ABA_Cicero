package view;

import java.util.Scanner;

public class IConsoleView implements IView<String> {

    @Override
    public String ask(String stringMessage) {
        System.out.println(stringMessage);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    @Override
    public void message(String stringMessage) {
        System.out.println(stringMessage);
    }
}
