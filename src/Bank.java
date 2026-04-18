import java.util.ArrayList;

public class Bank {

    private final ArrayList<BankAccount> accounts;
    private final ArrayList<User> users;

    public Bank(){
        accounts = FileManager.loadAccounts();
        users = FileManager.loadUsers(accounts);
        if (users.isEmpty()) {
            users.add(new Admin("admin", "Administrator", "admin123"));
        }
        FileManager.loadTransactions(accounts);
    }

    public User addUser(User user) {
        users.add(user);
        FileManager.saveUsers(users);
        return user;
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

    public BankAccount login(String accNo, String password){
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getAccountNumber().equals(accNo) && accounts.get(i).checkPassword(password)){
                return accounts.get(i);
            }
        }
        return null;
    }

    public void save() {
        FileManager.saveAccounts(accounts);
        FileManager.saveTransactions(accounts);
    }

    public User loginUser(String username, String password){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)){
                return users.get(i);
            }
        }
        return null;
    }
}
