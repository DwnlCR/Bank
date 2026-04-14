package co.Daniel.Bank.controler;

import co.Daniel.Bank.model.Account;
import co.Daniel.Bank.model.Bank;
import co.Daniel.Bank.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class BankController {
    private final Bank bank;

    public BankController(Bank bank){
        this.bank = bank;
    }

    public Account createAccount(String name, int password){
        Account account = bank.generateAccount(name, password);
        bank.insertAccount(account);
        return account;
    }

    public Account enterAccount(String accountNumber, int password){
        return bank.findAccount(accountNumber, password);
    }

    public boolean deposit(Account account, BigDecimal value){
        return account.deposit(value);
    }

    public boolean withdraw(Account account, BigDecimal value){
        return account.withdraw(value);
    }

    public List<Transaction> getHistory(Account account){
        return account.getHistory();
    }
}
