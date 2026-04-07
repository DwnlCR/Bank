package co.Daniel.Bank;

import java.util.List;
import java.util.ArrayList;

public class Bank {
    private String agency;
    private List<Account> accounts;
    private int lastAccount = 1;

    public Bank(String agency){
       this.agency = agency;
       this.accounts = new ArrayList<>();
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public void insertAccount(Account account){
        accounts.add(account);
    }
    public Account generateAccount(String name, int password){
        Account account = new Account(agency, "" + lastAccount , name, password);
        lastAccount++;
        return account;
    }
    public Account findAccount(String accountNumber, int password){
        for (Account account : accounts){
            if(account.getAccountNumber().equals(accountNumber) && account.getPassword() == password) return account;
        }
        return null;
    }
}
