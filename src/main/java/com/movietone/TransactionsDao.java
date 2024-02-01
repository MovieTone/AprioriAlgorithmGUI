package com.movietone;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TransactionsDao class is responsible only for interacting with database
 *
 * @author Name
 */
public class TransactionsDao {

    // protocol needed for connection
    public static final String PROTOCOL = "jdbc:derby:";
    // instance of database connection
    private Connection conn = null;
    // list of items' names
    private static ArrayList<String> itemNames;
    // list of transactions
    private ArrayList<ArrayList<Integer>> transactions;
    // database name (transactions1 - transactions5)
    private final String database;

    /**
     * creates a TransactionsDao object, adds items and transactions data
     *
     * @param database Database name (transactions1 - transactions5)
     */
    public TransactionsDao(String database) {
        this.database = database;
        addItemNames();
        char databaseIdentifier = database.charAt(database.length() - 1);
        switch (databaseIdentifier) {
            case ('1') -> addTransactions1();
            case ('2') -> addTransactions2();
            case ('3') -> addTransactions3();
            case ('4') -> addTransactions4();
            case ('5') -> addTransactions5();
        }
    }

    /**
     * creates 5 databases (transactions1 - transactions5)
     */
    public static void createDatabases() {
        String database = "transactions";
        // for each database
        for (int i = 1; i < 6; i++) {
            TransactionsDao transDao = new TransactionsDao(database + i);
            try {
                transDao.connect();
                transDao.createTables();
                transDao.insertData();
                transDao.close();
            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * adds items' names to the list
     */
    private void addItemNames() {
        itemNames = new ArrayList<>();
        itemNames.add("Shirt");
        itemNames.add("Socks");
        itemNames.add("Pants");
        itemNames.add("Pajamas");
        itemNames.add("Underwear");
        itemNames.add("Jeans");
        itemNames.add("Joggers");
        itemNames.add("Sweater");
        itemNames.add("Jacket");
        itemNames.add("Swimwear");
    }

    /**
     * adds transactions data to the list for the transactions1 database
     */
    private void addTransactions1() {
        transactions = new ArrayList<>();
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 4, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 4)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 4, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 5, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 5, 6, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 4, 8, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 5, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 3, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 4, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 7, 8, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 6, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 4, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 5, 6, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 3, 5, 7, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(7, 9)));
    }

    /**
     * adds transactions data to the list for the transactions2 database
     */
    private void addTransactions2() {
        transactions = new ArrayList<>();
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 6, 7)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 8, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 6, 7, 9, 3)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 6, 8, 7, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 3, 2, 5, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 3, 5, 6, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 7, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 5, 6, 7, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 3, 6, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 6, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 7, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 2, 1, 5, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 4, 5, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(6, 7, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 3, 4, 1)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 5, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 4, 6, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 6, 4, 9)));
    }

    /**
     * adds transactions data to the list for the transactions3 database
     */
    private void addTransactions3() {
        transactions = new ArrayList<>();
        transactions.add(new ArrayList<>(Arrays.asList(3, 4)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 6, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 3, 4, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 4, 6, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 4, 6, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 4, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 5, 6, 8, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 6, 7, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 4, 6, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 6, 7, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 6, 7, 10, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 4, 3, 5, 6, 7, 8)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 6, 7, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 4, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 5, 7, 8, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 6, 10, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 3, 4, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 6)));
    }

    /**
     * adds transactions data to the list for the transactions4 database
     */
    private void addTransactions4() {
        transactions = new ArrayList<>();
        transactions.add(new ArrayList<>(Arrays.asList(1, 5, 6, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 4, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 7, 4)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 8, 3, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 6, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 9, 10, 8, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3, 4, 6, 7, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 6, 7, 8, 9, 10, 2)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 4, 5, 6, 7, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 6, 7, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 5, 6, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 4, 7, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 6, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(8, 7, 6, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(7, 4, 5, 6, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 7, 8, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(8, 9, 10)));
    }

    /**
     * adds transactions data to the list for the transactions5 database
     */
    private void addTransactions5() {
        transactions = new ArrayList<>();
        transactions.add(new ArrayList<>(Arrays.asList(6, 2, 3)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 2, 5, 7, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 2, 4, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 3)));
        transactions.add(new ArrayList<>(Arrays.asList(1, 4)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(7, 3, 4, 5)));
        transactions.add(new ArrayList<>(Arrays.asList(8, 5, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 2, 6, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 4, 9, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 6, 8, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 2, 5, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 6, 7, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(4, 8, 7)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 8, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(10, 2, 4, 6)));
        transactions.add(new ArrayList<>(Arrays.asList(2, 4, 5, 8, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(3, 5, 7, 9)));
        transactions.add(new ArrayList<>(Arrays.asList(7, 8, 4, 10)));
        transactions.add(new ArrayList<>(Arrays.asList(5, 9, 10)));
    }

    /**
     * connects to the database specified
     *
     * @return Connection instance
     * @throws InstantiationException When object cannot be instantiated
     * @throws IllegalAccessException When method does not have access to the element
     * @throws ClassNotFoundException When driver's class is not found
     * @throws SQLException
     */
    public Connection connect()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

        //Class.forName(DRIVER).newInstance();
        new org.apache.derby.iapi.jdbc.AutoloadedDriver();
        conn = DriverManager.getConnection(PROTOCOL + database + ";create=true");

        return conn;
    }

    /**
     * closes a connection
     *
     * @throws SQLException if not able to close the connection
     */
    public void close() throws SQLException {
        // if connection exists
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * executes scripts to create tables in the database
     */
    public void createTables() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            DatabaseMetaData meta = conn.getMetaData();
            // get information about ITEMS table
            ResultSet tables = meta.getTables(conn.getCatalog(), null, "ITEMS", null);
            // if ITEMS table does not exist
            if (!tables.next()) {
                // create ITEMS table
                String itemsTable = "CREATE TABLE items ("
                    + "itemID int PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "itemName varchar(128) NOT NULL)";
                stmt.execute(itemsTable);
            }
            // get information about TRANSACTIONS table
            tables = meta.getTables(conn.getCatalog(), null, "TRANSACTIONS", null);
            if (!tables.next()) {
                // create TRANSACTIONS table
                String transTable = "CREATE TABLE transactions ("
                    + "transID int NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "itemID int NOT NULL, PRIMARY KEY (transID, itemID), " + "FOREIGN KEY (itemID) REFERENCES items(itemID)" + ")";
                stmt.execute(transTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * gets a map of items
     *
     * @return HashMap containing items ids and names
     */
    public HashMap<Integer, String> getItems() {
        Statement stmt = null;
        HashMap<Integer, String> items = new HashMap<>();
        try {
            stmt = conn.createStatement();
            // get all data from ITEMS table
            String query = "SELECT * FROM items";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int itemId = rs.getInt("itemID");
                String itemName = rs.getString("itemName");
                // put each record from table to the items list
                items.put(itemId, itemName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    /**
     * gets a map of transactions
     *
     * @return Map of transactions' ids and itemsets
     */
    public Map<Integer, ArrayList<Integer>> getTransactions() {
        Statement stmt = null;
        Map<Integer, ArrayList<Integer>> transactions = new HashMap<>();
        try {
            stmt = conn.createStatement();
            // get all data from TRANSACTIONS table
            String query = "SELECT * FROM transactions";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int transId = rs.getInt("transID");
                int itemId = rs.getInt("itemID");
                // if transactions map already contains this transaction ID
                if (transactions.containsKey(transId)) {
                    // just append a new item to the list with this transaction ID
                    ArrayList<Integer> items = transactions.get(transId);
                    items.add(itemId);
                    transactions.replace(transId, items);
                    // if this is a new transaction
                } else {
                    // add the transaction with the new item
                    transactions.put(transId, new ArrayList<>(List.of(itemId)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return transactions;
    }

    /**
     * executes statements to insert data into the database
     */
    public void insertData() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String query;
            // for each item name
            for (String itemName : itemNames) {
                // insert it into ITEMS table
                query = "INSERT INTO items (itemName) VALUES ('" + itemName + "')";
                stmt.execute(query);
            }

            int transId = 1;
            // for each transaction list
            for (ArrayList<Integer> list : transactions) {
                // for each item in this list
                for (int itemId : list) {
                    // insert it into TRANSACTIONS table
                    query = "INSERT INTO transactions VALUES (" + transId + ", " + itemId + ")";
                    stmt.execute(query);
                }
                // update transaction ID to insert, incrementing by 1
                transId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
