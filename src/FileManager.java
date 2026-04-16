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
                bw.write(acc.getAccountNumber() + "," + acc.getOwnerName() + "," + acc.getBalance());
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
            return accounts; // no file yet, return empty list
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String accNo = parts[0];
                    String name = parts[1];
                    double balance = Double.parseDouble(parts[2]);

                    BankAccount acc = new BankAccount(name, balance);
                    acc.setAccountNumber(accNo);
                    accounts.add(acc);
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        return accounts;
    }
}