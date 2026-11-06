package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


//===================     THIS CLASS HAS THE MAIN METHOD AND ALL THE MENUS (TALKS TO THE USER)   ================================
public class LedgerApp {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Transaction> transactions;

    //==============================           START            ===================================================================

    public static void main(String[] args) {
        transactions = TransactionFileManager.loadTransactions();

        System.out.println("===========================================");
        System.out.println("         WELCOME TO MY MONEY TRACKER         ");
        System.out.println("===========================================");

        showHomeScreen();
    }


    //=====================     HOME SCREEN     ==========================================================================

    private static void showHomeScreen() {
        boolean running = true;
        while (running) {
            System.out.println("\nHOME ");
            System.out.println(" D) Add Deposit");
            System.out.println(" P) Make Payment (Debit)");
            System.out.println(" L) Ledger");
            System.out.println(" X) Exit");
            System.out.println(" Pick an Option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
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


    //==============================     ADD DEPOSIT / PAYMENT       ================================================
    private static void addTransaction(boolean isDeposit) {
        System.out.println("Description: ");
        String description = scanner.nextLine();

        System.out.print("vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Amount: ");
        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Transaction not saved.");
            return;
        }
        // ============= making whatever user typed positive, unless its a payment   =====================================
        amount = Math.abs(amount);
        if(!isDeposit){
            amount = -amount;
        }
        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount);
        TransactionFileManager.saveTransaction(transaction);
        transactions.add(transaction);

        if (isDeposit) {
            System.out.println("Deposit saved.");
        } else {
            System.out.println("Payment saved. ");
        }

    }
    //===========================     LEDGER SCREEN      ============================================================

    private static void showLedgeScreen() {
        boolean intLedger = true;
        while (intLedger) {
            System.out.println("\nLEDGER ");
            System.out.println(" A) All");
            System.out.println(" D) Deposits");
            System.out.println(" P) Payments");
            System.out.println(" R) Reports");
            System.out.println(" H) Home");
            System.out.println(" Pick an Option: ");

            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    showReportsScreen();
                    break;
                case "H":
                    intLedger = false;
                    break;
                default:
                    System.out.println("Invalid option, try again. ");
            }
        }
    }

    //=====================     Ledger filter: deposits      =======================================================

    private static void displayDeposits() {
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                results.add(t);
            }
        }
        displayTransactions(results);
    }

    //================     Ledger filter: payments      ===============================================================

    private static void displayPayments() {
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                results.add(t);
            }
        }
        displayTransactions(results);
    }

    //=========================     REPORT SCREEN         =================================================


    private static void showReportsScreen() {
        boolean inReports = true;
        while (inReports) {
            System.out.println("\nREPORTS ");
            System.out.println(" 1) Month To Date");
            System.out.println(" 2) Previous Month");
            System.out.println(" 3) Year To Date");
            System.out.println(" 4) Previous Year");
            System.out.println(" 5) Search by vendor");
            System.out.println(" 0) Back");
            System.out.println(" Pick an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    monthToDate();
                    break;
                case "2":
                    previousMonth();
                    break;
                case "3":
                    yearToDate();
                    break;
                case "4":
                    previousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    //===========================     REPORT 1: THIS MONTH,THIS YEAR   =========================

    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            boolean sameYear = t.getDate().getYear() == today.getYear();
            boolean sameMonth = t.getDate().getMonthValue() == today.getMonthValue();
            if (sameYear && sameMonth) {
                results.add(t);
            }
        }
        displayTransactions(results);
    }

    //============================   REPORT 2: ALL OF LAST MONTH  =====================================

    private static void previousMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            boolean sameYear = t.getDate().getYear() == lastMonth.getYear();
            boolean sameMonth = t.getDate().getMonthValue() == lastMonth.getMonthValue();
            if (sameYear && sameMonth) {
                results.add(t);
            }
        }
        displayTransactions(results);
    }

    //==========================    REPORT 3: EVERYTHING THIS YEAR   =======================================

    private static void yearToDate() {
        int thisYear = LocalDate.now().getYear();
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == thisYear) {
                results.add(t);
            }
        }

        displayTransactions(results);
    }

    // ==========================     REPORT 4: EVERYTHING LAST YEAR        ============================================

    private static void previousYear() {
        int lastYear = LocalDate.now().getYear() - 1;          // e.g. 2025
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == lastYear) {
                results.add(t);
            }
        }
        displayTransactions(results);
    }

    // ======================= ====       REPORT 5: SEARCH BY VENDOR         =========================================

    private static void searchByVendor() {
        System.out.print("Vendor name: ");                     // ask for the vendor
        String vendor = scanner.nextLine().trim();             // read it, remove extra spaces

        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(vendor)) {      // "amazon" matches "Amazon"
                results.add(t);
            }
        }
        displayTransactions(results);
    }

    //================    PRINT ANY LIST, NEWEST FIRST    =======================================================
    // Every screen above calls this , so the printing code lives in one place.

    private static void displayTransactions(ArrayList<Transaction> list) {
        if (list.isEmpty()) {
            System.out.println("No transaction found.");
            return;
        }
        // make a copy so sorting doesn't change the original list
        ArrayList<Transaction> sorted = new ArrayList<>(list);

        //sort by date then by time then .reversed() so the Newest is on top
        sorted.sort(Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime)
                .reversed());

        // Header row, same widths as Transactions.toString()
        System.out.printf("%n%-12s %-10s %-30s %-20s %10s%n",
                "DATE", "TIME", "DESCRIPTIONS", "VENDOR", "AMOUNT");
        System.out.println("-".repeat(86));

        for (Transaction t : sorted) {
            System.out.println(t);
        }
    }
}
