package co.Daniel.Bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String type, BigDecimal amount, BigDecimal balanceAfter, LocalDateTime when){
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

    public String formattedWhen(){
        return when.format(FORMATTER);
    }
}
