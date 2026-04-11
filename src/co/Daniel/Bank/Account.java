package co.Daniel.Bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Account {
    private String name;
    private String agency;
    private String account;
    private String passwordHash;
    private BigDecimal balance = BigDecimal.ZERO;
    private static final int MAX_LENGTH = 12;
    private Log logger;

    public Account(String agency, String account, String name, int password){
        this.agency = agency;
        this.account = account;
        setName(name);
        setPassword(password);
        logger = new Log();
    }

    public String getAccountNumber(){
        return account;
    }

    public void setName(String name) {
        if (!name.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("O nome deve conter apenas letras e espaços.");
        }
        if (name.length() > MAX_LENGTH) {
            this.name = name.substring(0, MAX_LENGTH);
        } else {
            this.name = name;
        }
    }

    public void setPassword(int password) {
        if (password > 999999 || password < 100000) throw new IllegalArgumentException("A senha deve conter 6 digitos");
        this.passwordHash = hash(String.valueOf(password));
    }

    public boolean checkPassword(int password){
        return hash(String.valueOf(password)).equals(this.passwordHash);
    }

    public boolean withDraw(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Valor invalido");
            return false;
        } else if (balance.compareTo(value) < 0) {
            logger.out("SAQUE - R$" + value + " - Saldo insuficiente, sua conta atualmente possui R$" + balance);
            return false;
        } else {
            balance = balance.subtract(value).setScale(2, RoundingMode.HALF_UP);
            logger.out("SAQUE - R$" + value + " - Seu saldo atual é de R$" + balance);
            logger.out("Saque realizado com sucesso!");
            logger.outDateTime();
            return true;
        }
    }

    public void deposit(BigDecimal value){
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Erro: Valor invalido! ");
        }
        else{
            balance = balance.add(value).setScale(2, RoundingMode.HALF_UP);
            logger.out("DEPOSITO - R$" + value + " - Seu saldo atual é de R$" + balance);
            logger.out("Deposito realizado com sucesso!");
            logger.outDateTime();
        }
    }

    @Override
    public String toString() {
        String result = "A conta " + this.name + " Agencia " + this.agency + " / " + this.account + " possui: R$" + balance;
        return result;
    }

    private String hash(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte [] bytes = messageDigest.digest(password.getBytes());
            StringBuilder content = new StringBuilder();
            for(var b : bytes) content.append(String.format("%2x", b));
            return content.toString();
        }catch (NoSuchAlgorithmException ex){
            throw new RuntimeException("SHA-indisponivel para uso", ex);
        }
    }
}

