import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TransferQueue;

public class Main {
    static LinkedList<BankAccount> accounts = new LinkedList<>();
    static Stack<String> transactionHistory=new Stack<>();
    static Queue<String> billQueue= new LinkedList<>();
    static Queue<BankAccount> accountRequests=new LinkedList<>();
    static Scanner scanner=new Scanner(System.in);

    public static void main(String[] args){
        BankAccount[] physicalArray= new BankAccount[3];
        physicalArray[0] = new BankAccount("101", "Ali", 150000);
        physicalArray[1]= new BankAccount("102", "Sara", 220000);
        physicalArray[2]= new BankAccount("103", "Emmy", 527000);
        for (BankAccount acc: physicalArray){
            System.out.println(acc.toString());
        }

        boolean running = true;
        while(running){
            System.out.println("===Main Menu===");
            System.out.println("1. Enter Bank");
            System.out.println("2. Enter ATM");
            System.out.println("3. Admin Area");
            System.out.println("0. Exit");
            System.out.println("Choose option: ");

            int choice= scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1: bankMenu(); break;
                case 2: atmMenu(); break;
                case 3: adminMenu(); break;
                case 0:
                    running=false;
                    System.out.println("Existing system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void bankMenu(){
        System.out.println("---Bank Menu---");
        System.out.println("1. Submit account opening request");
        System.out.println("2. Add bill payment request");
        System.out.println("3. Deposit money");
        System.out.println("4. Withdraw money");
        System.out.println("Choose option: ");
        int choice=scanner.nextInt();
        scanner.nextLine();

        if (choice==1){
            System.out.println("Enter username for new account: ");
            String name= scanner.nextLine();
            System.out.println("Enter initial deposit: ");
            double deposit= scanner.nextDouble();
            String accNum= "ACC" + (int)(Math.random() * 10000);
            accountRequests.add(new BankAccount(accNum, name, deposit));
            System.out.println("Request submitted to Admin queue.");
        }else if (choice==2){
            System.out.println("Enter bill name (e.g., Internet Bill): ");
            String bill= scanner.nextLine();
            billQueue.add(bill);
            transactionHistory.push("Added bill: " + bill);
            System.out.println("Bill added to queue.");
        }else if (choice==3){
            processDeposit();
        } else if (choice==4) {
            processWithdraw();
        }
    }

    private static void atmMenu(){
        System.out.println("--- ATM Menu ---");
        System.out.println("1. Balance enquiry");
        System.out.println("2. Withdraw money");
        System.out.print("Choose option: ");
        int choice=scanner.nextInt();
        scanner.nextLine();

        if (choice==1){
            System.out.println("Enter username: ");
            String name= scanner.nextLine();
            BankAccount acc=findAccount(name);
            if (acc != null){
                System.out.println("Current balance: " + acc.getBalance());
            }else{
                System.out.println("Account not found.");
            }
        }else if (choice==2){
            processWithdraw();
        }
    }

    private static void adminMenu(){
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Process account requests queue");
        System.out.println("2. Process bill payment queue");
        System.out.println("3. View all active accounts");
        System.out.println("4. Undo last transaction");
        System.out.print("Choose option: ");
        int choice=scanner.nextInt();
        scanner.nextLine();

        if (choice==1){
            if (accountRequests.isEmpty()){
                System.out.println("No pending account requests.");
            }else{
                BankAccount newAcc= accountRequests.poll();
                accounts.add(newAcc);
                System.out.println("Processed and activated account for: " + newAcc.getUsername());
            }
        } else if (choice==2) {
            if (billQueue.isEmpty()){
                System.out.println("No pending bills.");
            }else{
                System.out.println("Processing: " + billQueue.poll());
            }
        } else if (choice==3) {
            if (accounts.isEmpty()){
                System.out.println("No active accounts.");
            }
            for (BankAccount acc: accounts){
                System.out.println(acc);
            }
        } else if (choice==4) {
            if (!transactionHistory.isEmpty()){
                System.out.println("Undo action: Removed transaction [" + transactionHistory.pop() + "]");
            }else{
                System.out.println("No history to undo.");
            }
        }
    }

    private static BankAccount findAccount(String username){
        for (BankAccount acc: accounts){
            if (acc.getUsername().equalsIgnoreCase(username))
                return acc;
        }
        return null;
    }

    private static void processDeposit(){
        System.out.println("Enter username: ");
        String name= scanner.nextLine();
        BankAccount acc= findAccount(name);
        if (acc!=null){
            System.out.println("Enter deposit amount: ");
            double amount= scanner.nextDouble();
            acc.deposit(amount);
            transactionHistory.push("Deposit " + amount + " to " + name);
            System.out.println("New balance: " + acc.getBalance());
        }else {
            System.out.println("Account not found");
        }
    }

    private static void processWithdraw(){
        System.out.println("Enter username: ");
        String name=scanner.nextLine();
        BankAccount acc=findAccount(name);
        if (acc!=null){
            System.out.println("Enter withdrawal amount: ");
            double amount= scanner.nextDouble();
            if (acc.withdraw(amount)){
                transactionHistory.push("Withdrawal " + amount + " from " + name);
                System.out.println("New balance: " + acc.getBalance());
            }else{
                System.out.println("Insufficient funds!");
            }
        }else{
            System.out.println("Account not found.");
        }
    }
}