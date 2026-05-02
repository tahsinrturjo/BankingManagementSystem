# 🏦 Banking Management System

A GUI-based Banking Management System built in Java as a university project to demonstrate core Object-Oriented Programming concepts.

---

## What It Does

The app simulates a basic bank with two roles:

**Customer**
- Create a bank account (Basic, Savings, or Current)
- Deposit and withdraw money
- Transfer money to other accounts
- View full transaction history

**Admin**
- View all accounts in the system
- Delete any account

All data is saved to CSV files and persists between sessions.

---

## Account Types

| Type | Feature |
|---|---|
| Basic Account | Standard deposit and withdrawal |
| Savings Account | 5% interest rate |
| Current Account | Overdraft limit of 5000 |

---

## Tech Stack

- **Language:** Java (JDK 8+)
- **GUI:** Java Swing (JOptionPane)
- **Storage:** CSV file persistence
- **Pattern:** OOP — Inheritance, Polymorphism, Encapsulation, Abstraction

---

## How to Run

1. Clone the repository
   ```
   git clone https://github.com/yourusername/banking-management-system.git
   ```
2. Open the project in your IDE (Eclipse, IntelliJ, etc.)
3. Run `MainGUI.java`

---

## Default Admin Login

```
Username: admin
Password: admin123
```

> ⚠️ This is a demo project. Passwords are stored as plain text in CSV files. Not intended for production use.

---

## Project Structure

```
Banking/
├── MainGUI.java        ← Entry point, GUI logic
├── Bank.java           ← Manages all accounts and users
├── FileManager.java    ← CSV read/write
├── BankAccount.java    ← Base account class
├── SavingsAccount.java ← Extends BankAccount
├── CurrentAccount.java ← Extends BankAccount
├── User.java           ← Abstract base for Admin/Customer
├── Admin.java          ← Admin role
├── Customer.java       ← Customer role
└── Transaction.java    ← Transaction record
```

---

## Known Limitations

- Passwords stored as plain text (no hashing)
- CSV storage instead of a proper database
- Transfer shows as "Withdrawal" in transaction history

---

## Author

CS Student — Built as a university OOP course project.
