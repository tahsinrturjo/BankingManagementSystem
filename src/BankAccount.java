public class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;

    public BankAccount(String ownerName, double balance){
        this.ownerName = ownerName;
        this.balance = balance;
    }

    //GETTERS
    public String getAccountNumber(){
        return  accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    //SETTERS
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean deposit(double amount){
        if(amount <= 0){
            System.out.println("Deposit Amount Must be Greater than Zero.");
            return false;
        }
        else{
            balance += amount;
            return true;
        }
    }

    public boolean withdraw(double amount){
        if(this.balance == 0){
            System.out.println("Withdrawal amount must be greater than zero.");
        }
        if(this.balance < amount){
            System.out.println("Insufficient Balance");
            return false;
        }
        else {
            balance -= amount;
            return true;
        }
    }

    @Override
    public String toString() {
        return String.format("Account [%s] | Owner: %s | Balance: %.2f",
                accountNumber, ownerName, balance);
    }
}
