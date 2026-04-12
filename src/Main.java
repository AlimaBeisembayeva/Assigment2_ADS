import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static Stack<String> transactionHistory= new Stack<>();
    static Queue<String> billQueue= new LinkedList<>();
    static Queue<BankAccount> accountRequests=new LinkedList<>();
    static BankAccount[] vipArray=new BankAccount[3];


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<BankAccount> accounts = new LinkedList<>();
        vipArray[0]= new BankAccount("VIP01", "Director", 1000000);
        vipArray[1]= new BankAccount("VIP02", "Investor", 5000000);
        vipArray[2]= new BankAccount("VIP03", "Manager", 300000);

        while (true) {
            System.out.println("===== SYSTEM MENU =====");
            System.out.println("--- 1. BANK AREA ---");
            System.out.println("1.Add Account");
            System.out.println("2. Deposit/Withdraw Money");
            System.out.println("3. Request New Account(User)");

            System.out.println("--- 2. ATM AREA ---");
            System.out.println("4. Balance Enquiry");
            System.out.println("5. ATM Withdraw");
            System.out.println("6. Add Bill to Queue");
            System.out.println("7. Process Next Bill");

            System.out.println("--- 3. ADMIN AREA ---");
            System.out.println("8. Show Accounts");
            System.out.println("9. Undo Last Action (Stack)");
            System.out.println("10. Approve Account(Admin)");
            System.out.println("11. Show VIP Accounts(Physical Array)");



            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accNumber = scanner.nextLine();

                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();

                    BankAccount acc = new BankAccount(accNumber, username, balance);
                    accounts.add(acc);
                    transactionHistory.push("Added account: " + username);

                    System.out.println("Account Created");
                    break;

                case 2:
                    System.out.println("Enter Username for Transaction: ");
                    String targetUser= scanner.nextLine();
                    boolean foundUser= false;

                    for (BankAccount a: accounts){
                        if (a.username.equalsIgnoreCase(targetUser)){
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            int action = scanner.nextInt();

                            System.out.println("Enter amount: ");
                            double amount= scanner.nextDouble();

                            if (action==1){
                                a.deposit(amount);
                                transactionHistory.push("Deposit " + amount + " to " + a.username);
                                System.out.println("Deposited succesfully.");
                            }else if (action==2){
                                if (amount<=a.balance){
                                    a.withdraw(amount);
                                    transactionHistory.push("Withdraw " + amount + " from " + a.username);
                                    System.out.println("Withdrawn successfully!");
                                }else {
                                    System.out.println("Not enough money!");
                                }
                            }
                            System.out.println("New balance: " + a.balance);
                            foundUser=true;
                            break;

                        }
                    }
                    if (!foundUser) System.out.println("User not found.");
                    break;

                case 3:
                    System.out.println("Enter your name for the request: ");
                    String reqName = scanner.nextLine();
                    System.out.println("Initial deposit: ");
                    double reqDeposit = scanner.nextDouble();

                    BankAccount pendingAcc = new BankAccount("PENDING", reqName, reqDeposit);
                    accountRequests.add(pendingAcc);
                    System.out.println("Your request is in the queue. Wait for admin approval.");
                    break;

                case 4:
                    System.out.println("Enter username: ");
                    String search = scanner.nextLine();

                    boolean found = false;

                    for (BankAccount a : accounts) {
                        if (a.username.equals(search)) {
                            a.display();
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Account not found");
                    }
                    break;

                case 5:
                    System.out.println("Enter username: ");
                    String withdrawUser=scanner.nextLine();
                    boolean foundWith=false;

                    for (BankAccount a: accounts){
                        if (a.username.equalsIgnoreCase(withdrawUser)){
                            System.out.println("Enter amount to withdraw: ");
                            double amount = scanner.nextDouble();
                            scanner.nextLine();

                            if (amount<=a.balance){
                                a.withdraw(amount);
                                transactionHistory.push("Withdraw " + amount+ " from " + a.username);
                                System.out.println("Withdrawn successfully! New balance: " + a.balance);
                            }else {
                                System.out.println("Error: Not enough money!");
                            }
                            foundWith=true;
                            break;
                        }
                    }
                    if (!foundWith) System.out.println("User not found!");
                    break;

                case 6:
                    System.out.println("Enter bill name: ");
                    String bill=scanner.nextLine();
                    billQueue.add(bill);
                    transactionHistory.push("Added bill: " + bill);
                    System.out.println("Bill added to the queue.");
                    break;

                case 7:
                    if (!billQueue.isEmpty()){
                        String processedBill = billQueue.poll();
                        System.out.println("Processing bill: " + processedBill);
                        System.out.println("Bill paid successfolly!");
                    }else{
                        System.out.println("No bills in the queue.");
                    }
                    break;

                case 8:
                    if (accounts.isEmpty()) {
                        System.out.println("No accounts");
                    } else {
                        for (BankAccount a : accounts) {
                            a.display();
                        }
                    }
                    break;

                case 9:
                    if (!transactionHistory.isEmpty()){
                        String lastAction = transactionHistory.pop();
                        System.out.println("Last action UNDONE: " + lastAction);
                    }else{
                        System.out.println("No history to undo.");
                    }
                    break;

                case 10:
                    if (!accountRequests.isEmpty()){
                        BankAccount approveAcc = accountRequests.poll();

                        approveAcc.accountNumber = "ACC" + (int)(Math.random() * 1000);

                        accounts.add(approveAcc);

                        System.out.println("Approved account for: " + approveAcc.accountNumber);
                        transactionHistory.push("Admin approved account: " + approveAcc.username);
                    }else {
                        System.out.println("No pending requests.");
                    }
                    break;

                case 11:
                    System.out.println("---VIP Accounts---");
                    for (int i=0; i<vipArray.length; i++){
                        if (vipArray[i]!=null){
                            vipArray[i].display();
                        }
                    }
                    System.out.println("Note: This array has a fixed size of " + vipArray.length);
                    break;

                case 0:
                    System.out.println("Good bye!");

                default:
                    System.out.println("Invalid choice");


            }
        }

    }
}