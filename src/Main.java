import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        int choice = 0;
        BankAccount loggedInAccount = null;

        System.out.println("\nWelcome to TRT Banking System");
        System.out.println("1. Create Account\n2. Login");
        System.out.println("Enter your Choice: ");
        int startChoice = scanner.nextInt();

        if(startChoice == 1){
            boolean allgood = true;
            System.out.println("1. Basic\n2. Savings\n3. Current\nChoose Account Type:\n");
            int type = scanner.nextInt();

            System.out.println("Enter Your Name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.println("Enter a Password: ");
            String password = scanner.nextLine();
            System.out.println("Enter Initial Balance: ");
            double balance = 0;

            try {
                balance = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid Input. Please enter a number");
                scanner.nextLine();
                choice = 0;
                allgood = false;
            }
            if(allgood){
                BankAccount acc = null;
                String message = "";
                if(type == 1){
                    acc = new BankAccount(name, balance, password);
                    message = "Account Created Successfully.\n";
                } else if(type == 2){
                    acc = new SavingsAccount(name, balance, 0.05, password);
                    message = "Savings Account Created Successfully. Interest Rate: 5%.\n";
                } else if(type == 3){
                    acc = new CurrentAccount(name, balance, 5000, password);
                    message = "Current Account Created Successfully. Overdraft Limit: 5000.\n";
                }

                if(acc != null){
                    bank.addAccount(acc);
                    System.out.println(message + acc);
                }

            }
        }

        while(loggedInAccount == null){
            scanner.nextLine();
            System.out.println("Enter Account Number: ");
            String accNo = scanner.nextLine();
            System.out.println("Enter Your Password: ");
            String password = scanner.nextLine();

            loggedInAccount = bank.login(accNo, password);

            if(loggedInAccount == null){
                System.out.println("Invalid Credentials. Please Try Again.");
            }
        }

        System.out.println("Login Successful. Welcome " + loggedInAccount.getOwnerName());

        while(choice != 7){
            showMenu();
            System.out.println("Enter Your Option: ");
            choice = scanner.nextInt();
            handleChoice(choice, bank, scanner, loggedInAccount);
        }

        scanner.close();
    }

    private static void showMenu(){
        System.out.println("1. Deposit \n2. Withdraw \n3. View Balance \n4. See All Accounts");
        System.out.println("5. Delete Account\n6. View Transaction History\n7. Exit");
    }

    private static void handleChoice(int choice, Bank bank, Scanner scanner, BankAccount loggedInAccount){
        boolean valid = true;
        switch (choice){
            case 1:
                if(loggedInAccount!= null){
                    double amount2 = 0;
                    System.out.println("Enter Deposit Amount: ");
                    try {
                        amount2 = scanner.nextDouble();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid Input. Please enter a number");
                        scanner.nextLine();
                        choice = 0;
                        valid = false;
                    }

                    if(valid){
                        loggedInAccount.deposit(amount2);
                        System.out.println("Amount Deposited Successfully\n" + loggedInAccount);
                        bank.save();
                    }

                }
                else {
                    System.out.println("Account Not Found");
                }
                break;

            case 2:
                if(loggedInAccount != null){
                    double amount3 = 0;
                    System.out.println("Enter Withdrawal Amount: ");
                    try{
                        amount3 = scanner.nextDouble();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid Input. Please enter a number");
                        scanner.nextLine();
                        choice = 0;
                        valid = false;
                    }

                    if(valid){
                        loggedInAccount.withdraw(amount3);
                        System.out.println("Amount Withdrawn Successfully\n" + loggedInAccount);
                        bank.save();
                    }

                }
                else {
                    System.out.println("Account Not Found");
                }
                break;

            case 3:
                if(loggedInAccount != null){
                    System.out.println(loggedInAccount);
                }
                else {
                    System.out.println("Account Not Found");
                }
                break;
            case 4:
                bank.listAccount();
                break;

            case 5:
                if(loggedInAccount != null){
                    String choose = "";
                    scanner.nextLine();
                    System.out.println("Are you Sure to Delete Account? Y/N: ");
                    choose = scanner.nextLine();
                    choose = choose.toLowerCase();
                    if(choose.equals("y")){
                        bank.deleteAccount(loggedInAccount.getAccountNumber());
                        System.out.println("Account Deleted Successfully\n" + loggedInAccount );
                        choice = 7;
                        break;
                    }
                    else {
                        System.out.println(loggedInAccount);
                        break;
                    }
                }
                else {
                    System.out.println("Account Not Found");
                }
                break;

            case 6:
                if(loggedInAccount != null){
                    loggedInAccount.printHistory();
                }
                else{
                    System.out.println("Account not Found");
                }
                break;

            case 7:
                System.out.println("Thank You for Using our System. GoodBye");
                break;

            default:
                System.out.println("Invalid Choice. Try Again");
        }
    }

}

/* A GUI-based Banking Management System, designed to simulate real-world banking operations.
The system supports two user roles — Admin and Customer. Customers can manage accounts,
deposit and withdraw funds, transfer money, view transaction history. Admins can manage users,
approve or reject loans, and monitor all transactions.
 */