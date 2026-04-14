package co.Daniel.Bank.view;

import co.Daniel.Bank.controler.BankController;
import co.Daniel.Bank.model.Account;
import co.Daniel.Bank.model.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class AccountMenu {

    private final Scanner sc;
    private final BankController controller;

    public AccountMenu( Scanner sc, BankController controller){
        this.sc = sc;
        this.controller = controller;
    }

    public void start(Account account){
        while(true) {
            System.out.println("Depositar - D | Sacar - S | Ver Saldo - V | Extrato - X | Sair - E");
            AccountOption op = AccountOption.parse(sc.nextLine());

            if (op == null) {
                System.out.println("Operação inválida");
                continue;
            }

            switch (op) {
                case D -> deposit(account);
                case S -> withdraw(account);
                case V -> System.out.println(account);
                case X -> showHistory(account);
                case E -> {
                    System.out.println("Saindo da conta...");
                    return;
                }
            }
        }
    }
    private void deposit(Account account){
        while (true) {
            System.out.println("Digite o valor para o depósito: ");
            String stringValue = sc.nextLine();

            try {
                BigDecimal value = new BigDecimal(stringValue.replace(",", ".")).setScale(2, RoundingMode.HALF_UP);
                controller.deposit(account, value);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Valor invalido, digite apenas numeros");
            }
        }
    }

    private void withdraw(Account account){
        while (true){
            System.out.println("Digite o valor a ser sacado: ");
            String stringValue = sc.nextLine();

            try {
                BigDecimal value = new BigDecimal(stringValue.replace(",", ".")).setScale(2, RoundingMode.HALF_UP);
                controller.withdraw(account ,value);
                break;
            }catch (NumberFormatException ex){
                System.out.println("Valor invalido, digite apenas numeros");
            }
        }
    }

    private void showHistory(Account account){
        var history = controller.getHistory(account);
        if (history.isEmpty()){
            System.out.println("Nenhuma transação até o momento");
            return;
        }
        System.out.println("===== Extrato =====");
        for (Transaction transaction : history){
            System.out.println(transaction.type() + " | " + transaction.amount() + "R$ | Saldo: " + transaction.balanceAfter() + "R$ | " + transaction.formattedWhen());
        }
        System.out.println("------------------------");
    }
}
