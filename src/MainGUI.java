import javax.swing.JOptionPane;

public class MainGUI 
{
    public static void main(String[] args) 
    {
        // 1. Initialize Backend
        Bank bank = new Bank();

        // 2. The Main Loop
        while (true) 
        {
            String[] mainOptions = {"Create Account", "User Login", "Admin Login", "Exit"};
            
            int mainChoice = JOptionPane.showOptionDialog(null, "Welcome to TRT Banking", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, mainOptions, mainOptions[0]);

            // If they click the "X" on the window or "Exit", end the program
            if (mainChoice == -1 || mainChoice == 3) 
            {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                break; 
            }

            // --- 1. CREATE ACCOUNT ---
            if (mainChoice == 0) 
            {
                // Step A: Ask for Account Type First
                String[] typeOptions = {"1. Basic", "2. Savings", "3. Current"};
                int typeChoice = JOptionPane.showOptionDialog(null, "Choose Account Type:", "Account Type",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, typeOptions, typeOptions[0]);

                if (typeChoice == -1) continue; // User clicked the X, return to main menu

                // Step B: Get standard info
                String name = JOptionPane.showInputDialog("Enter Full Name:");
                if (name == null) continue; 
                
                String username = JOptionPane.showInputDialog("Create Username:");
                if (username == null) continue;

                String password = JOptionPane.showInputDialog("Create Password:");
                if (password == null) continue;

                String balInput = JOptionPane.showInputDialog("Enter Initial Balance:");
                if (balInput == null) continue;

                try 
                {
                    double balance = Double.parseDouble(balInput);
                    BankAccount acc = null;
                    String message = "";

                    // Step C: Create the specific account type based on their choice
                    if (typeChoice == 0) 
                    {
                        acc = new BankAccount(name, balance, password);
                        message = "Account Created Successfully.\n";
                    } 
                    else if (typeChoice == 1) 
                    {
                        acc = new SavingsAccount(name, balance, 0.05, password);
                        message = "Savings Account Created Successfully. Interest Rate: 5%.\n";
                    } 
                    else if (typeChoice == 2) 
                    {
                        acc = new CurrentAccount(name, balance, 5000, password);
                        message = "Current Account Created Successfully. Overdraft Limit: 5000.\n";
                    }

                    // Step D: Save it to the bank
                    if (acc != null) 
                    {
                        Customer newCustomer = new Customer(username, name, password, acc);
                        bank.addAccount(acc);
                        bank.addUser(newCustomer);

                        JOptionPane.showMessageDialog(null, message + "Account Number: " + acc.getAccountNumber());
                    }
                } 
                catch (NumberFormatException ex) 
                {
                    JOptionPane.showMessageDialog(null, "Invalid Input. Please enter a valid number for balance.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // --- 2. USER LOGIN ---
            else if (mainChoice == 1) 
            {
                String username = JOptionPane.showInputDialog("Enter Username:");
                if (username == null) continue;

                String password = JOptionPane.showInputDialog("Enter Password:");
                if (password == null) continue;
                
                User user = bank.loginUser(username, password);

                if (user instanceof Customer)
                {
                    Customer customer = (Customer) user;
                    BankAccount acc = customer.getAccount();
                    
                    // The Logged-In Customer Loop
                    while (true) 
                    {
                        String[] dashOptions = {"Deposit", "Withdraw", "Check Balance", "Transaction History", "Transfer", "Logout"};
                        int dashChoice = JOptionPane.showOptionDialog(null, 
                            "Welcome " + customer.getName(),
                            "Customer Dashboard",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, dashOptions, dashOptions[0]);

                        if (dashChoice == -1 || dashChoice == 5)
                            break; // Logout

                        if (dashChoice == 0) 
                        {
                            String input = JOptionPane.showInputDialog("Deposit Amount:");
                            if (input != null && !input.isEmpty())
                            {
                                try 
                                {
                                    double amount = Double.parseDouble(input);
                                    acc.deposit(amount);
                                    bank.save();
                                    JOptionPane.showMessageDialog(null, "Amount Deposited Successfully!\nNew Balance: $" + acc.getBalance());
                                } 
                                catch (NumberFormatException e) 
                                {
                                    JOptionPane.showMessageDialog(null, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                        else if (dashChoice == 1) 
                        {
                            String input = JOptionPane.showInputDialog("Withdraw Amount:");
                            if (input != null && !input.isEmpty())
                            {
                                try 
                                {
                                    double amount = Double.parseDouble(input);
                                    boolean success = acc.withdraw(amount);
                                    
                                    if (success) 
                                    {
                                        bank.save();
                                        JOptionPane.showMessageDialog(null, "Amount Withdrawn Successfully!\nNew Balance: $" + acc.getBalance());
                                    } 
                                    else 
                                    {
                                        JOptionPane.showMessageDialog(null, "Withdrawal Failed: Insufficient Balance or Overdraft Limit Reached.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } 
                                catch (NumberFormatException e) 
                                {
                                    JOptionPane.showMessageDialog(null, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                        else if (dashChoice == 2) 
                        {
                            JOptionPane.showMessageDialog(null, acc.toString());
                        }
                        else if (dashChoice == 3)
                        {
                            if (acc.transactions.isEmpty())
                            {
                                JOptionPane.showMessageDialog(null, "No Transaction History.");
                            }
                            else
                            {
                                String history = "";
                                for (int i = 0; i < acc.transactions.size(); i++)
                                {
                                    history += acc.transactions.get(i).toString() + "\n";
                                }
                                JOptionPane.showMessageDialog(null, history, "Transaction History", JOptionPane.PLAIN_MESSAGE);
                            }

                        }
                        else if (dashChoice == 4)
                        {
                            String recipientAccNo = JOptionPane.showInputDialog("Enter Recipient Account Number:");
                            if (recipientAccNo == null || recipientAccNo.isEmpty()) continue;

                            BankAccount recipient = bank.findAccount(recipientAccNo);

                            if (recipient == null)
                            {
                                JOptionPane.showMessageDialog(null, "Recipient Account Not Found.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (recipient.getAccountNumber().equals(acc.getAccountNumber()))
                            {
                                JOptionPane.showMessageDialog(null, "You cannot transfer to your own account.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                String input = JOptionPane.showInputDialog("Enter Amount to Transfer:");
                                if (input != null && !input.isEmpty())
                                {
                                    try
                                    {
                                        double amount = Double.parseDouble(input);
                                        boolean success = acc.withdraw(amount);

                                        if (success)
                                        {
                                            recipient.deposit(amount);
                                            bank.save();
                                            JOptionPane.showMessageDialog(null, "Transfer Successful!\nNew Balance: $" + acc.getBalance());
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null, "Transfer Failed: Insufficient Balance.", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        JOptionPane.showMessageDialog(null, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }

            }


            // --- 3. ADMIN LOGIN ---
            else if (mainChoice == 2) 
            {
                String username = JOptionPane.showInputDialog("Admin Username:");
                if (username == null) continue;

                String password = JOptionPane.showInputDialog("Admin Password:");
                if (password == null) continue;
                
                User user = bank.loginUser(username, password);

                if (user instanceof Admin)
                {
                    // The Logged-In Admin Loop
                    while (true) 
                    {
                        String[] adminOptions = {"View Accounts", "Delete Account", "Logout"};
                        int adminChoice = JOptionPane.showOptionDialog(null, "Admin Dashboard", "Admin",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, adminOptions, adminOptions[0]);

                        if (adminChoice == -1 || adminChoice == 2) break; // Logout

                        if (adminChoice == 0) 
                        {
                            JOptionPane.showMessageDialog(null, bank.listAccount());;
                        }
                        else if (adminChoice == 1) 
                        {
                            String accNo = JOptionPane.showInputDialog("Enter Account Number to Delete:");
                            if (accNo != null && !accNo.isEmpty())
                            {
                                BankAccount toDelete = bank.findAccount(accNo);
                                if (toDelete != null)
                                {
                                    int confirm = JOptionPane.showConfirmDialog(null, "Delete account for " + toDelete.getOwnerName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION)
                                    {
                                        bank.deleteAccount(accNo);
                                        JOptionPane.showMessageDialog(null, "Account Deleted.");
                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(null, "Invalid Admin Login.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}