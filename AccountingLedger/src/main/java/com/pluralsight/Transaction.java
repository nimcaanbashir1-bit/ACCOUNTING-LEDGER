package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

    public class Transaction {
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

        private LocalDate date;
        private LocalTime time;
        private String description;
        private String vendor;
        private double amount;


        public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount){
            this.date=date;
            this.time=time;
            this.description= description;
            this.vendor=vendor;
            this.amount=amount;
        }

        public LocalDate getDate(){
            return date;
        }
        public LocalTime getTime(){
            return time;
        }
        public String getDescription(){
            return description;
        }
        public String getVendor(){
            return vendor;
        }
        public double getAmount(){
            return amount;
        }


        public String toCsvLine() {

            return date + "|" + time.format(TIME_FORMATTER)+ "|"+ vendor + String.format("%.2f",amount);
        }
        public String toString(){
            return String.format("%-12s %-10s %-30s %-20s %10.2f", date,time.format(TIME_FORMATTER), description,vendor,amount);
        }
    }
