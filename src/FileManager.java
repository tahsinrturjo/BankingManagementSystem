import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "accounts.csv";
    private static final String TRANSACTIONS_FILE = "transactions.csv";

    public static void saveAccounts(ArrayList<BankAccount> accounts) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < accounts.size(); i++) {
                BankAccount acc = accounts.get(i);

                if (acc instanceof SavingsAccount) {
                    SavingsAccount sa = (SavingsAccount) acc;
                    bw.write(acc.getAccountNumber() + ",SAVINGS," + acc.getOwnerName() + "," + acc.getBalance() + "," + sa.getInterestRate() + "," + sa.getPassword());
                } else if (acc instanceof CurrentAccount) {
                    CurrentAccount ca = (CurrentAccount) acc;
                    bw.write(acc.getAccountNumber() + ",CURRENT," + acc.getOwnerName() + "," + acc.getBalance() + "," + ca.getOverdraftLimit() + "," + ca.getPassword());
                } else {
                    bw.write(acc.getAccountNumber() + ",BASIC," + acc.getOwnerName() + "," + acc.getBalance() + "," + acc.getPassword());
                }

                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static ArrayList<BankAccount> loadAccounts() {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return accounts;
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 4) continue; // skip malformed lines

                String accNo   = parts[0];
                String type    = parts[1];
                String name    = parts[2];
                double balance = Double.parseDouble(parts[3]);

                BankAccount acc = null;

                if (type.equals("SAVINGS") && parts.length == 6) {
                    double interestRate = Double.parseDouble(parts[4]);
                    String password = parts[5];
                    acc = new SavingsAccount(name, balance, interestRate, password);
                } else if (type.equals("CURRENT") && parts.length == 6) {
                    double overdraftLimit = Double.parseDouble(parts[4]);
                    String password = parts[5];
                    acc = new CurrentAccount(name, balance, overdraftLimit, password);
                } else {
                    String password = parts[4];
                    acc = new BankAccount(name, balance, password);
                }

                acc.setAccountNumber(accNo);
                accounts.add(acc);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        return accounts;
    }

    public static void saveTransactions(ArrayList<BankAccount> accounts) {
        try {
            FileWriter fw = new FileWriter(TRANSACTIONS_FILE);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < accounts.size(); i++) {
                BankAccount acc = accounts.get(i);
                ArrayList<Transaction> transactions = acc.transactions;

                for (int j = 0; j < transactions.size(); j++) {
                    Transaction t = transactions.get(j);
                    bw.write(acc.getAccountNumber() + "," + t.getType() + "," + t.getAmount() + "," + t.getDateTime());
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public static void loadTransactions(ArrayList<BankAccount> accounts) {
        File file = new File(TRANSACTIONS_FILE);

        if (!file.exists()) {
            return;
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 4) continue;

                String accNo    = parts[0];
                String type     = parts[1];
                double amount   = Double.parseDouble(parts[2]);
                LocalDateTime dt = LocalDateTime.parse(parts[3]);

                // find the matching account and add the transaction to it
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountNumber().equals(accNo)) {
                        accounts.get(i).transactions.add(new Transaction(type, amount, dt));
                        break;
                    }
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
}