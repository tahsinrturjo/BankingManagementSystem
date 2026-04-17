import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        int choice = 0;

        while(choice != 8){
            showMenu();
            System.out.println("Enter Your Option: ");
            choice = scanner.nextInt();
            handleChoice(choice, bank, scanner);
        }

        scanner.close();
    }

    private static void showMenu(){
        System.out.println("\nWelcome to Banking System TRT\n");
        System.out.println("1. Create Account \n2. Deposit \n3. Withdraw \n4. View Balance \n5. See All Accounts");
        System.out.println("6. Delete Account\n7. View Transaction History\n8. Exit");
    }

    private static void handleChoice(int choice, Bank bank, Scanner scanner){
        switch (choice){
            case 1:
                System.out.println("1. Basic\n2. Savings\n3. Current\nChoose Account Type:\n");
                int type = scanner.nextInt();

                System.out.println("Enter Your Name: ");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.println("Enter Initial Balance: ");
                double balance = 0;
                boolean valid = true;

                try {
                    balance = scanner.nextInt();
                }
                catch (InputMismatchException e){
                    System.out.println("Invalid Input. Please enter a number");
                    scanner.nextLine();
                    choice = 0;
                    valid = false;
                }
                if(valid){
                    BankAccount acc = null;
                    String message = "";
                    if(type == 1){
                        acc = new BankAccount(name, balance);
                        message = "Account Created Successfully.\n";
                    } else if(type == 2){
                        acc = new SavingsAccount(name, balance, 0.05);
                        message = "Savings Account Created Successfully. Interest Rate: 5%.\n";
                    } else if(type == 3){
                        acc = new CurrentAccount(name, balance, 5000);
                        message = "Current Account Created Successfully. Overdraft Limit: 5000.\n";
                    }

                    if(acc != null){
                        bank.addAccount(acc);
                        System.out.println(message + acc);
                    }

                }
                break;

            case 2:
                System.out.println("Enter Your Account Number: ");
                scanner.nextLine();
                String accNo2 = scanner.nextLine();
                BankAccount acc2 = bank.findAccount(accNo2);

                if(acc2 != null){
                    double amount2 = 0;
                    System.out.println("Enter Deposit Amount: ");
                    valid = true;
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
                        acc2.deposit(amount2);
                        System.out.println("Amount Deposited Successfully\n" + acc2);
                        bank.save();
                    }

                }
                else {
                    System.out.println("Account Not Found");
                }
                break;

            case 3:
                System.out.println("Enter Your Account Number: ");
                scanner.nextLine();
                String accNo3 = scanner.nextLine();
                BankAccount acc3 = bank.findAccount(accNo3);

                if(acc3 != null){
                    double amount3 = 0;
                    System.out.println("Enter Withdrawal Amount: ");
                    valid = true;
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
                        acc3.withdraw(amount3);
                        System.out.println("Amount Withdrawn Successfully\n" + acc3);
                        bank.save();
                    }

                }
                else {
                    System.out.println("Account Not Found");
                }
                break;

            case 4:
                System.out.println("Enter Your Account Number: ");
                scanner.nextLine();
                String accNo4 = scanner.nextLine();
                BankAccount acc4 = bank.findAccount(accNo4);

                if(acc4 != null){
                    System.out.println(acc4);
                }
                else {
                    System.out.println("Account Not Found");
                }
                break;
            case 5:
                bank.listAccount();
                break;

            case 6:
                System.out.println("Enter Your Account Number: ");
                scanner.nextLine();
                String accNo6 = scanner.nextLine();
                BankAccount acc6 = bank.deleteAccount(accNo6);

                if(acc6 != null){
                    System.out.println("Account Deleted Successfully\n" + acc6 );
                }
                else {
                    System.out.println("Account Not Found");
                }
                break;

            case 7:
                System.out.println("Enter Your Account Number: ");
                scanner.nextLine();
                String accNo7 = scanner.nextLine();
                BankAccount acc7 = bank.findAccount(accNo7);

                if(acc7 != null){
                    acc7.printHistory();
                }
                else{
                    System.out.println("Account not Found");
                }
                break;

            case 8:
                System.out.println("Thank You for Using our System. GoodBye");
                break;

            default:
                System.out.println("Invalid Choice. Try Again");
        }
    }

}