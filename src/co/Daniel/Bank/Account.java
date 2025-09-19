package co.Daniel.Bank;

public class Account {
    private String name;
    private String agency;
    private String account;
    private double balance;
    private static final int MAX_LENGTH = 12;
    private Log logger;

    public Account(String agency, String account, String name){
        this.agency = agency;
        this.account = account;
        setName(name);
        logger = new Log();
    }

    public void setName(String name) {
        if (!name.matches("[\\p{L} ]+")) { // aceita só letras e espaços
            throw new IllegalArgumentException("O nome deve conter apenas letras e espaços.");
        }

        if (name.length() > MAX_LENGTH) {
            this.name = name.substring(0, MAX_LENGTH);
        } else {
            this.name = name;
        }
    }


    public boolean withDraw(double value){
        boolean result = true;
        if(balance < value){
            logger.out("SAQUE - R$" + value + " - Saldo insuficiente, sua conta atualmente possui R$" + balance);
            result = false;
        }
        else if(balance >= value && value > 0){
            balance -= value;
            logger.out("SAQUE - R$" + value + " - Seu saldo atual é de R$" + balance);
            logger.out("Saque realizado com sucesso!");
        }
        else{
            System.out.println("Erro: Valor invalido! ");
        }
        return result;
    }

    public void deposit(double value){
        if (value > 0) {
            balance += value;
            logger.out("DEPOSITO - R$" + value + " - Seu saldo atual é de R$" + balance);
            logger.out("Deposito realizado com sucesso!");
        }
        else{
            System.out.println("Erro: Valor invalido! ");
        }
    }

    @Override
    public String toString() {
        String result = "A conta " + this.name + " Agencia " + this.agency + " / " + this.account + " possui: R$" + balance;
        return result;
    }
}

