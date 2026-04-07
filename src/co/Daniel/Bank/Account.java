package co.Daniel.Bank;

public class Account {
    private String name;
    private String agency;
    private String account;
    private int password;
    private double balance;
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

    public int getPassword(){
        return password;
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

    public void setPassword(int password){
        if(password > 999999 || password < 100000){
            throw new IllegalArgumentException("A senha deve conter 6 digitos");
        }
        else {
            this.password = password;
        }
    }

    public boolean withDraw(double value) {
        if (value <= 0) {
            System.out.println("Valor invalido");
            return false;
        } else if (balance < value) {
            logger.out("SAQUE - R$" + value + " - Saldo insuficiente, sua conta atualmente possui R$" + balance);
            return false;
        } else {
            balance -= value;
            logger.out("SAQUE - R$" + value + " - Seu saldo atual é de R$" + balance);
            logger.out("Saque realizado com sucesso!");
            return true;
        }
    }

    public void deposit(double value){
        if (value < 0) {
            System.out.println("Erro: Valor invalido! ");
        }
        else{
            balance += value;
            logger.out("DEPOSITO - R$" + value + " - Seu saldo atual é de R$" + balance);
            logger.out("Deposito realizado com sucesso!");
        }
    }

    @Override
    public String toString() {
        String result = "A conta " + this.name + " Agencia " + this.agency + " / " + this.account + " possui: R$" + balance;
        return result;
    }
}

