package co.Daniel.Bank.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private static final int MAX_LENGTH = 12;
    private String name;
    private String agency;
    private String account;
    private String passwordHash;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> history = new ArrayList<>();
    private Log logger;

    public Account(String agency, String account, String name, int password) {
        this.agency = agency;
        this.account = account;
        setName(name);
        setPassword(password);
        logger = new Log();
    }

    public String getAccountNumber() {
        return account;
    }

    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void setName(String name) {
        if (!name.matches("[\\p{L} ]+"))
            throw new IllegalArgumentException("O nome deve conter apenas letras e espaços.");
        this.name = name.length() > MAX_LENGTH ? name.substring(0, MAX_LENGTH) : name;
    }

    public void setPassword(int password) {
        if (password > 999999 || password < 100000)
            throw new IllegalArgumentException("A senha deve conter 6 digitos");
        this.passwordHash = hash(String.valueOf(password));
    }

    public boolean checkPassword(int password) {
        return hash(String.valueOf(password)).equals(this.passwordHash);
    }

    public boolean deposit(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Erro: Valor invalido!");
            return false;
        }
        balance = balance.add(value).setScale(2, RoundingMode.HALF_UP);
        history.add(new Transaction("DEPOSITO", value, balance, LocalDateTime.now()));
        logger.out("DEPOSITO - R$" + value + " - Seu saldo atual e de R$" + balance);
        logger.out("Deposito realizado com sucesso!");
        logger.outDateTime();
        return true;
    }

    public boolean withdraw(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Valor invalido");
            return false;
        }
        if (balance.compareTo(value) < 0) {
            logger.out("SAQUE - R$" + value + " - Saldo insuficiente, sua conta possui R$" + balance);
            return false;
        }
        balance = balance.subtract(value).setScale(2, RoundingMode.HALF_UP);
        history.add(new Transaction("SAQUE", value, balance, LocalDateTime.now()));
        logger.out("SAQUE - R$" + value + " - Seu saldo atual e de R$" + balance);
        logger.out("Saque realizado com sucesso!");
        logger.outDateTime();
        return true;
    }

    private String hash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = messageDigest.digest(password.getBytes());
            StringBuilder content = new StringBuilder();
            for (var b : bytes) content.append(String.format("%02x", b));
            return content.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("SHA-256 indisponivel para uso", ex);
        }
    }

    @Override
    public String toString() {
        return "A conta " + name + " Agencia " + agency + " / " + account + " possui: R$" + balance;
    }
}