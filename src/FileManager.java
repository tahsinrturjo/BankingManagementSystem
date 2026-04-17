import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "accounts.csv";

    public static void saveAccounts(ArrayList<BankAccount> accounts) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < accounts.size(); i++) {
                BankAccount acc = accounts.get(i);

                if (acc instanceof SavingsAccount) {
                    SavingsAccount sa = (SavingsAccount) acc;
                    bw.write(acc.getAccountNumber() + ",SAVINGS," + acc.getOwnerName() + "," + acc.getBalance() + "," + sa.getInterestRate());
                } else if (acc instanceof CurrentAccount) {
                    CurrentAccount ca = (CurrentAccount) acc;
                    bw.write(acc.getAccountNumber() + ",CURRENT," + acc.getOwnerName() + "," + acc.getBalance() + "," + ca.getOverdraftLimit());
                } else {
                    bw.write(acc.getAccountNumber() + ",BASIC," + acc.getOwnerName() + "," + acc.getBalance());
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

                if (type.equals("SAVINGS") && parts.length == 5) {
                    double interestRate = Double.parseDouble(parts[4]);
                    acc = new SavingsAccount(name, balance, interestRate);
                } else if (type.equals("CURRENT") && parts.length == 5) {
                    double overdraftLimit = Double.parseDouble(parts[4]);
                    acc = new CurrentAccount(name, balance, overdraftLimit);
                } else {
                    acc = new BankAccount(name, balance);
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
}