public abstract class User {

    private String username;
    private String name;
    private String password;

    public User(){}

    public User(String username, String name, String password){
        this.username = username;
        this.name = name;
        this.password = password;
    }

    //GETTERS
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String pass){
        if(password.equals(pass)){
            return true;
        }
        else {
            return false;
        }
    }

}

class Admin extends User {

    public Admin(String username, String name, String password){
        super(username, name, password);
    }

}

class Customer extends User {

    private BankAccount account;

    public Customer(String username, String name, String password, BankAccount account){
        super(username, name, password);
        this.account = account;
    }

    public BankAccount getAccount() {
        return account;
    }
}