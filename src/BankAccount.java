public class BankAccount {
    private String accountNumber;
    private String username;
    private double balance;

    public BankAccount(String accountNumber, String username, double balance){
        this.accountNumber=accountNumber;
        this.username=username;
        this.balance=balance;
    }

    public String getUsername(){return username;}
    public double getBalance(){return balance;}

    public void deposit(double amount){
        this.balance +=amount;
    }

    public boolean withdraw(double amount){
        if (this.balance>=amount){
            this.balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return username + " (Account: " + accountNumber + ") - Balance: " + balance;
    }
}

