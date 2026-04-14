package co.Daniel.Bank.view;

import co.Daniel.Bank.controler.BankController;
import co.Daniel.Bank.model.Account;

import java.util.Scanner;

public class MainMenu {
    private final BankController controller;
    private final Scanner sc;
    private final AccountMenu accountMenu;

    public MainMenu(BankController controller){
        this.sc = new Scanner(System.in);
        this.controller = controller;
        this.accountMenu = new AccountMenu(sc, controller);
    }

    public void start(){
        while (true){
            System.out.println("Criar conta - C | Entrar em Conta - E | Sair - S");
            MainOption op = MainOption.parse(sc.nextLine());

            if (op == null){
                System.out.println("Operação invalida");
                continue;
            }
            switch (op) {
                case C -> createAccount();
                case E -> enterAccount();
                case S -> {
                    System.out.println("Saindo do Sistema...");
                    return;
                }
            }
        }
    }
    private void createAccount(){
        while (true){
            System.out.println("Digite seu nome: ");
            String name = sc.nextLine();
            System.out.println("Digite a senha de 6 digitos: ");
            String stringPassword = sc.nextLine();

            try {
                var password = Integer.parseInt(stringPassword.trim().replaceAll("[^0-9]", ""));
                Account account = controller.createAccount(name, password);
                System.out.println("Conta criada com sucesso");
                System.out.println("Numero da sua conta: " + account.getAccountNumber());
                accountMenu.start(account);
                break;
            }catch (IllegalArgumentException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    private void enterAccount(){
        System.out.println("Digite o numero da sua conta: ");
        String numberAccount = sc.nextLine().trim();
        System.out.println("Digite sua senha de 6 digitos: ");
        String stringPassword = sc.nextLine();

        try{
            int password = Integer.parseInt(stringPassword.trim().replaceAll("[^0-9]", ""));
            Account account = controller.enterAccount(numberAccount, password);
            if(account == null){
                System.out.println("Conta não encontrada");
            }
            else {
                System.out.println("Bem vindo, " + account);
                accountMenu.start(account);
            }
        }catch (NumberFormatException ex){
            System.out.println("Senha inválida, digite somente 6 numeros");
        }

    }
}
