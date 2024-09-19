import java.util.Date;

public class Transaction {
    private Date date;
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date.toString() + " - " + type + " - ₹" + String.format("%.2f", amount);
    }
}
