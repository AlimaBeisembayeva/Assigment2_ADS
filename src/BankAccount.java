public class BankAccount {
    String accountNumber;
    String username;
    double balance;

    public BankAccount(String accountNumber, String username, double balance){
        this.accountNumber=accountNumber;
        this.username=username;
        this.balance=balance;
    }

    public void deposit(double amount){
        this.balance +=amount;
    }

    public void withdraw(double amount){
        this.balance -= amount;
    }

    public void display(){
        System.out.println("Account number: " + accountNumber);
        System.out.println("Username: " + username);
        System.out.println("Balance: " + balance);
    }
}

