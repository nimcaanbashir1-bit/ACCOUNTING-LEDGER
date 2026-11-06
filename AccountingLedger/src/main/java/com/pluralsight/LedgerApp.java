package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LedgerApp {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Transaction>transactions;

    public static void main(String[] args){
        transactions = TransactionFileManager.loadTransactions();

        System.out.println("===========================================");
        System.out.println("         WELCOME TO LEDGER TRACKER         ");
        System.out.println("===========================================");

        showHomeScreen();
    }
    private static void showHomeScreen(){
        boolean running = true;
        while(running){
            System.out.println("\nHOME ");
            System.out.println(" D) Add Deposit");
            System.out.println(" P) Make Payment (Debit)");
            System.out.println(" L) Ledger");
            System.out.println(" X) Exit");
            System.out.println(" Pick an Option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice){
                case "D":
                    addTransaction(true);
                    break;

                case "P":
                    addTransaction(false);
                    break;
                case "L":
                    showLedgeScreen();
                    break;
                case "X":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, try again.");

            }
        }
    }
    private static void addTransaction(boolean isDeposit){
        System.out.println("Description: ");
        String description = scanner.nextLine();

        System.out.print("vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Amount: ");
        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Invalid amount. Transaction not saved.");
            return;
        }

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount);
        TransactionFileManager.saveTransaction(transaction);
        transaction.add(transaction);

        if(isDeposit){
            System.out.println("Deposit saved.");
        } else {
            System.out.println("Payment saved. ");
        }

    }
    private static void showLedgeScreen(){
        boolean intLedger = true;
        while (intLedger){
            System.out.println("\nLEDGER ");
            System.out.println(" A) All");
            System.out.println(" D) Deposits");
            System.out.println(" P) Payments");
            System.out.println(" R) Reports");
            System.out.println(" H) Home");
            System.out.println(" Pick an Option: ");

            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice){
                case "A";
                    displayTransactions(transactions);
                    break;
                case "D":
                    displayDeposits():
                    break;
                case "P"
                    displayPayments();
                case "R":
                    showReportsScreen()
                    break;
                case "H":
                    intLedger = false;
                    break;
                default:
                    System.out.println("Invalid option, try again. ");
                case
            }
        }
    }
    private static void displayDeposits(){
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions){
            if(t.getAmount()>0){
                results.add(t);
            }
        }
        displayTransactions(results);
    }
    pr
}
