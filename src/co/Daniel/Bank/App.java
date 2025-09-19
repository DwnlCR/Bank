package co.Daniel.Bank;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank santander = new Bank("0001");
        while(true){
            System.out.println("Qual operacao deseja fazer: Criar conta - C | Entrar em Conta - S | Sair - E");
            String op = scanner.nextLine();

            if (op.equalsIgnoreCase("C")){
                while(true) {
                    System.out.println("Digite seu nome: ");
                    String name = scanner.nextLine();
                    try {
                        Account account = santander.generateAccount(name);
                        santander.insertAccount(account);
                        System.out.println("Conta criada com sucesso!");
                        account.setName(name);
                        operateAccount(account);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nome inválido! Digite apenas letras e espaços.");
                    }
                }
            } else if (op.equalsIgnoreCase("S")) {
                while(true) {
                    System.out.println("Digite seu nome: ");
                    String name = scanner.nextLine();
                    try {
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nome inválido! Digite apenas letras e espaços.");
                    }
                }
                System.out.println("Digite sua conta: ");
                int Scanner = scanner.nextInt();

            } else if (op.equalsIgnoreCase("E")) {
                System.out.println("Saindo do sistema...");
                break;
            }
            else{
                System.out.println("Operacao invalida");
            }
        }
    }
    static void operateAccount(Account account){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Qual operacao deseja fazer: Depositar - D  " + "Sacar - S  " + "Sair - E  ");
            String op = scanner.nextLine();
            if (op.equalsIgnoreCase("D")){
                double val;
                while (true) {
                    System.out.println("Qual valor deseja depositar: ");
                    String entrada = scanner.nextLine();
                    try{
                        val = Double.parseDouble(entrada);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada invalida! Digite apenas numeros");
                    }
                }
                account.deposit(val);
            }
            else if (op.equalsIgnoreCase("S")) {
                System.out.println("Qual valor deseja Sacar: ");
                double val = scanner.nextDouble();
                val = Math.round(val * 100.0) / 100.0;
                account.withDraw(val);
                scanner.nextLine();
            }
            else if (op.equalsIgnoreCase("E")){
                System.out.println("Saindo do sistema...");
                break;
            }
            else {
                System.out.println("Operacao invalida");
            }
            scanner = new Scanner(System.in);
        }
    }
}