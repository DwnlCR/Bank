package co.Daniel.Bank;

import co.Daniel.Bank.controler.BankController;
import co.Daniel.Bank.model.Bank;
import co.Daniel.Bank.view.MainMenu;


public class App {
    public static void main(String[] args) {
        BankController controller = new BankController(new Bank("0001"));
        new MainMenu(controller).start();
    }
}