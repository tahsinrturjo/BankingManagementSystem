import java.util.ArrayList;

public class Bank {

    private final ArrayList<BankAccount> accounts;

    public Bank(){
        accounts = FileManager.loadAccounts();;
    }

    public BankAccount addAccount(BankAccount account){
        String accNo = "ACC" + String.format("%03d", accounts.size() + 1);
        account.setAccountNumber(accNo);
        accounts.add(account);
        FileManager.saveAccounts(accounts);
        return account;
    }

    public BankAccount findAccount(String accNo) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accNo)) {
                return accounts.get(i);
            }
        }
        System.out.println("Account not Found");
        return null;
    }

    public void listAccount(){
        if(accounts.isEmpty()){
            System.out.println("No Accounts Found");
            return;
        }
        for(int i = 0; i<accounts.size(); i++){
            System.out.println("\n" + accounts.get(i) );
        }
    }

    public BankAccount deleteAccount(String accNo){
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accNo)) {
                BankAccount removed = accounts.get(i);
                accounts.remove(i);
                FileManager.saveAccounts(accounts);
                return removed;
            }
        }
        return null;
    }

    public void save() {
        FileManager.saveAccounts(accounts);
    }
}
