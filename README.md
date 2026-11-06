# My Money Tracker 💰

A command-line personal finance tracker built in Java. It records every dollar coming in and going out, saves everything to a file, and shows reports so I always know where my money goes.

Built as Capstone 1 for the Year Up Application Development program.

## Features

**Home screen**
- **D) Add Deposit**: log money coming in (paychecks, paybacks)
- **P) Make Payment**: log money going out. Type a positive number and it's saved as negative automatically
- **L) Ledger**: view your history
- **X) Exit**

**Ledger** (newest entries first)
- **A) All**: every transaction
- **D) Deposits**: only money coming in
- **P) Payments**: only money going out
- **R) Reports**: run a report
- **H) Home**

**Reports**
1. Month To Date
2. Previous Month
3. Year To Date
4. Previous Year
5. Search by Vendor (not case-sensitive)
0. Back

## How to Run

**Requirements:** Java 17 or newer, and IntelliJ IDEA (or Maven).

**In IntelliJ**
1. Clone this repo and open the `AccountingLedger` folder.
2. Open `src/main/java/com/pluralsight/LedgerApp.java`.
3. Click the green ▶ next to `main`.

> If no transactions show up, go to **Run → Edit Configurations** and set the **Working directory** to the `AccountingLedger` folder, where `transactions.csv` lives.

**From the terminal**
```bash
cd AccountingLedger
mvn compile
java -cp target/classes com.pluralsight.LedgerApp
```

## Data File

All transactions are stored in `transactions.csv`, one per line, separated by pipes:

```
date|time|description|vendor|amount
2026-11-01|09:00:00|Rent|Landlord|-1450.00
2026-10-24|08:00:00|Paycheck|Employer|1620.00
```

Positive amounts are deposits, and negative amounts are payments. If the file doesn't exist, the app creates it on the first run.

## Project Structure

```
AccountingLedger/
├── src/main/java/com/pluralsight/
│   ├── Transaction.java              # the data: one transaction
│   ├── TransactionFileManager.java   # reads and writes transactions.csv
│   └── LedgerApp.java                # main method, menus and reports
├── transactions.csv
└── pom.xml
```

## Code I'm Proud Of

Every screen and report sends its list to one method, `displayTransactions()`, which sorts newest first and prints aligned columns:

```java
sorted.sort(Comparator.comparing(Transaction::getDate)
        .thenComparing(Transaction::getTime)
        .reversed());
```

## Future Improvements

- Custom search by any mix of date range, description, vendor and amount
- A running balance
- Editing and deleting transactions
- `BigDecimal` instead of `double` for money

## Author

Nimcaan Bashir
