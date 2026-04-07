package co.Daniel.Bank;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    public void out(String message){
        System.out.println("LOG: " + message);
    }
    public void outDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        System.out.println("Operação realizada em: " + OffsetDateTime.now().format(formatter) + "\n");
    }
}
