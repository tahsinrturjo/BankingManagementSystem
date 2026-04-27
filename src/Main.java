import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        int choice = 0;
        User loggedInUser = null;
        int adminChoice = 0;

        System.out.println("\nWelcome to TRT Banking");
        System.out.println("1. Create Account\n2. User Login\n3. Admin Login");
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
            System.out.println("Enter A Username: ");
            String username = scanner.nextLine();
            System.out.println("Enter Initial Balance: ");
            double balance = 0;

            try {
                balance = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid Input. Please enter a number");
                scanner.nextLine();
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
                    Customer customer = new Customer(username, name, password, acc);
                    bank.addAccount(acc);
                    bank.addUser(customer);
                    System.out.println(message + acc);
                }

            }
        }

        while(loggedInUser == null){
            scanner.nextLine();
            System.out.println("Username: ");
            String username = scanner.nextLine();
            System.out.println("Password: ");
            String password = scanner.nextLine();

            loggedInUser = bank.loginUser(username, password);

            if(loggedInUser == null){
                System.out.println("Invalid Credentials. Please Try Again.");
            }
        }

        System.out.println("Login Successful. Welcome " + loggedInUser.getName());

        if(loggedInUser instanceof Admin){
            while(adminChoice != 3){
                showAdminMenu();
                System.out.println("Enter Your Option: ");
                adminChoice = scanner.nextInt();
                handleAdminChoice(adminChoice, bank, scanner);
            }
        }
        else if (loggedInUser instanceof Customer) {

            while(choice != 7){
                showMenu();
                System.out.println("Enter Your Option: ");
                choice = scanner.nextInt();
                handleChoice(choice, bank, scanner, ((Customer) loggedInUser).getAccount());
            }
        }

        scanner.close();
    }

    private static void showAdminMenu(){
        System.out.println("1. View All Accounts\n2. Delete An Account\n3. Exit");
    }

    private static void showMenu(){
        System.out.println("1. Deposit \n2. Withdraw \n3. View Balance");
        System.out.println("4. Delete Account\n5. View Transaction History\n6. Transfer\n7. Exit");
    }

    private static void handleAdminChoice(int choice, Bank bank, Scanner scanner){
        boolean running = true;
        switch (choice){
            case 1:
                System.out.println(bank.listAccount());
                break;
            case 2:
                System.out.println("Enter the Account Number: ");
                scanner.nextLine();
                String accNo = scanner.nextLine();

                BankAccount toDelete = bank.findAccount(accNo);

                if(toDelete == null){
                    System.out.println("Account Not Found.");
                }
                else{
                    System.out.println("Are You Sure you want to Delete? " + bank.findAccount(accNo).getOwnerName() + " Y/N: ");
                    String sure = scanner.nextLine().toLowerCase();

                    if(sure.equals("y")){
                        bank.deleteAccount(accNo);
                    }
                }
                break;

            case 3:
                System.out.println("Thank You for Using our System. GoodBye");
                break;
            default:
                System.out.println("Invalid Choice. Try Again...");
        }
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
                if(loggedInAccount != null){
                    String choose = "";
                    scanner.nextLine();
                    System.out.println("Are you Sure to Delete Account? Y/N: ");
                    choose = scanner.nextLine();
                    choose = choose.toLowerCase();
                    if(choose.equals("y")){
                        bank.deleteAccount(loggedInAccount.getAccountNumber());
                        System.out.println("Account Deleted Successfully\n" + loggedInAccount );
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

            case 5:
                if(loggedInAccount != null){
                    loggedInAccount.printHistory();
                }
                else{
                    System.out.println("Account not Found");
                }
                break;

            case 6:
                if(loggedInAccount != null){
                    System.out.println("Enter Recipient Account Number: ");
                    scanner.nextLine();
                    String recipientAccNo = scanner.nextLine();
                    BankAccount recipient = bank.findAccount(recipientAccNo);

                    if(recipient == null){
                        System.out.println("Cannot find Recipient Account");
                        break;
                    }

                    if(recipient.getAccountNumber().equals(loggedInAccount.getAccountNumber())){
                        System.out.println("You cannot Transfer to your Own Account.");
                        break;
                    }

                    System.out.println("Enter Amount to Transfer: ");
                    double transferAmount = scanner.nextDouble();

                    boolean deducted = loggedInAccount.withdraw(transferAmount);

                    if(deducted){
                        recipient.deposit(transferAmount);
                        bank.save();
                        System.out.println("Amount Transferred Successfully." + loggedInAccount);
                    }
                    break;

                }
                else {
                    System.out.println("Account Not Found");
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

//Choice Try Catch
