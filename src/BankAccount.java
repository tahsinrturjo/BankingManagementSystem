import java.util.ArrayList;

public class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;
    protected ArrayList<Transaction> transactions;

    public BankAccount(){}

    public BankAccount(String ownerName, double balance){
        this.ownerName = ownerName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
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
            transactions.add(new Transaction("Deposit", amount));
            return true;
        }
    }

    //METHODS
    public boolean withdraw(double amount){
        if(this.balance == 0){
            System.out.println("Withdrawal cannot be zero.");
        }
        if(this.balance < amount){
            System.out.println("Insufficient Balance");
            return false;
        }
        else {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
            return true;
        }
    }

    public void printHistory(){
        if(transactions.isEmpty()){
            System.out.println("No Transaction History");
        }
        else{
            for(int i = 0; i < transactions.size(); i++ ){
                System.out.println(transactions.get(i));
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Account [%s] | Owner: %s | Balance: %.2f",
                accountNumber, ownerName, balance);
    }
}

class SavingsAccount extends BankAccount{
    private double interestRate;

    public SavingsAccount(String ownerName, double balance, double interestRate){
        super(ownerName, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void applyInterest(){
        double interest = getBalance() * getInterestRate();
        deposit(interest);
    }
}


class CurrentAccount extends BankAccount{
    private double overdraftLimit;

    public CurrentAccount(String ownerName, double balance, double overdraftLimit){
        super(ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if(getBalance() - amount >= -overdraftLimit){
            setBalance(getBalance() - amount);
            transactions.add(new Transaction("Withdrawal", amount));
            return true;
        }
        else {
            System.out.println("Overdraft Limit Exceeded. Your Limit is: " + getOverdraftLimit());
            return false;
        }
    }
}