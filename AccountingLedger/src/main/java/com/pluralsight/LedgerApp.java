package com.pluralsight;

import java.util.ArrayList;

public class LedgerApp {

    public static void main(String[] args) {                                      // ✅ the program starts here
        ArrayList<Transaction> list = TransactionFileManager.loadTransactions(); // load the file
        for (Transaction t : list) {                                             // each transaction
            System.out.println(t.getDate() + " " + t.getVendor() + " " + t.getAmount()); // print to check
        }
    }
}
