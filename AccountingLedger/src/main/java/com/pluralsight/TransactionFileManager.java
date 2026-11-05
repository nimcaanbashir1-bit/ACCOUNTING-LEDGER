package com.pluralsight;

import javax.imageio.IIOException;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionFileManager {
        private static String FILE_NAME = "TransactionCVS.java";

        public static ArrayList<Transaction>LoadTransaction() {
            ArrayList<Transaction> transactions = new ArrayList<>();

            File file = new File(FILE_NAME);
            if (!file.exists()) {
                createFileWithHeader();
                return transactions;
                return transactions;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank() || line.startsWith("date|")) {
                        continue;
                    }
                    String[] parts = line.split("\\|");
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            } catch (IIOException e) {
                System.out.println("Error Reading File: " + e.getMessage());
            }
            return transactions;
        }
        public static void SaveTransaction(Transaction transaction){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true))){
                writer.write(transaction.toCsvLine());
                writer.newLine();
            }catch (IIOException e) {
                System.out.println("Error saving transaction: "+e.getMessage());
            }

        }
        private static void createFileWithHeader(){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
                writer.write("date|time|description|vendor|amount");
                writer.newLine();
            }catch (IIOException e){
                System.out.println("Error creating file: "+e.getMessage());
            }
        }

    }
