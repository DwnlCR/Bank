package co.Daniel.Bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank santander = new Bank("0001");
        while(true){
            System.out.println("Qual operacao deseja fazer: Criar conta - C | Entrar em Conta - E | Sair - S");
            String op = scanner.nextLine();

            if (op.equalsIgnoreCase("C")){
                while(true) {
                    System.out.println("Digite seu nome: ");
                    String name = scanner.nextLine();
                    System.out.println("Digite a senha de 6 digitos: ");
                    String stringPassword = scanner.nextLine();
                    int password = Integer.parseInt(stringPassword.trim().replaceAll("[^0-9]", ""));
                    try {
                        Account account = santander.generateAccount(name, password);
                        santander.insertAccount(account);
                        System.out.println("Conta criada com sucesso!");
                        account.setName(name);
                        System.out.println("Numero da sua conta: " + account.getAccountNumber());
                        operateAccount(account);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nome ou Senha inválidos, digite apenas letras e espaços para o nome e uma senha de 6 digitos.");
                    }
                }
            } else if (op.equalsIgnoreCase("E")) {
                System.out.println("Digite o numero da sua conta: ");
                String accountNumber = scanner.nextLine().trim();
                System.out.println("Digite sua senha de 6 digitos: ");
                String stringPassword = scanner.nextLine();
                try {
                    int password = Integer.parseInt(stringPassword.trim().replaceAll("[^0-9]", ""));
                    Account account = santander.findAccount(accountNumber, password);
                    if (account == null){
                        System.out.println("Conta não encontrada. Verifique o numero e tente novamente");
                    }
                    else {
                        System.out.println("Bem vindo, " + account);
                        operateAccount(account);
                    }
                }catch (NumberFormatException ex){
                    System.out.println("Senha invalida, digite apenas 6 numeros");
                }
            } else if (op.equalsIgnoreCase("S")) {
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
            System.out.println("Qual operacao deseja fazer: Depositar - D | " + "Sacar - S | " + "Ver Saldo - V | " + "Sair - E ");
            String op = scanner.nextLine();
            if (op.equalsIgnoreCase("D")){
                while (true) {
                    System.out.println("Qual valor deseja depositar: ");
                    String entrada = scanner.nextLine();
                    try{
                        BigDecimal val = new BigDecimal(entrada.replace(",", ".")).setScale(2, RoundingMode.HALF_UP);
                        account.deposit(val);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada invalida, digite apenas numeros");
                    }
                }
            }
            else if (op.equalsIgnoreCase("S")) {
                while (true){
                    System.out.println("Qual valor deseja Sacar: ");
                    String entrada = scanner.nextLine();
                    try {
                        BigDecimal val = new BigDecimal(entrada.replace(",", ".")).setScale(2, RoundingMode.HALF_UP);
                        account.withDraw(val);
                        break;
                    }
                    catch (NumberFormatException e){
                        System.out.println("Entrada invalida, digite apenas numeros");
                    }
                }
            }

            else if(op.equalsIgnoreCase("V")){
                System.out.println(account);
            }

            else if (op.equalsIgnoreCase("E")){
                System.out.println("Saindo do sistema...");
                break;
            }
            else {
                System.out.println("Operacao invalida");
            }
        }
    }
}