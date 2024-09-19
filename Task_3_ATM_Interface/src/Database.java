import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, List<Transaction>> transactionsMap = new HashMap<>();

    public Database() {
        users.put("5290395779", new User("5290395779", "20221103", "Brati", 1000.00));
        transactionsMap.put("5290395779", new ArrayList<>());
        users.put("777 888 9997", new User("777 888 9997", "150478", "Bishal", 1000.00));
        transactionsMap.put("777 888 9997", new ArrayList<>());
        users.put("888 999 0008", new User("888 999 0008", "880098", "Tonay", 1000.00));
        transactionsMap.put("888 999 0008", new ArrayList<>());
        users.put("888 999 0009", new User("888 999 0009", "987000", "Rakesh", 1000.00));
        transactionsMap.put("888 999 0009", new ArrayList<>());
    }

    public void addUser(String userId, String pin, String name, double initialAmount) {
        users.put(userId, new User(userId, pin, name, initialAmount));
        transactionsMap.put(userId, new ArrayList<>());
    }

    public User getUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        }
        return null;
    }

    public void recordTransaction(String userId, Transaction transaction) {
        transactionsMap.get(userId).add(transaction);
    }

    public List<Transaction> getTransactions(String userId) {
        return transactionsMap.get(userId);
    }


}
