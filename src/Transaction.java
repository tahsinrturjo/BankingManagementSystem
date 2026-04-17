import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDateTime dateTime;
    private String type;
    private double amount;

    public Transaction(String type, double amount){
        this.dateTime = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
    }

    public Transaction(String type, double amount, LocalDateTime dateTime){
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        return String.format("Time [%s] | Type: %s | Amount: %.2f",
                formattedDate, type, amount);
    }

}
