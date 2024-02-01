package com.movietone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The Apriori class contains methods for running the Apriori algorithm, operating data and making all appropriate calculations
 *
 * @author Name
 */
public class Apriori {

    // storing transaction number along with its list of items
    private Map<Integer, ArrayList<Integer>> transactions;
    // map with key of item's index and value of item's name
    private Map<Integer, String> indicesItems = new HashMap<>();
    // map with key of frequent itemset and value of its count
    private final Map<String, Integer> itemSetCounts = new HashMap<>();
    // used to generate frequent itemsets
    private List<int[]> itemSets;
    // an object to interact with GUI window
    private AprioriGUI apGUI;
    // number of items in the database
    private int numItems;
    // overall number of transactions
    private int countTransactions;
    // minimum confidence value entered by user
    private double minConfidence;
    // minimum support value entered by user
    private double minSupport;

    /**
     * Construcs an Apriori object, calling a method to run a GUI window
     */
    public Apriori() {
        startGUI();
    }

    public static void main(String[] args) {
        new Apriori();
    }

    /**
     * loads data and runs the Apriori algorithm
     *
     * @param database   Database name (transactions1 - transactions5)
     * @param support    Minimum support in percent
     * @param confidence Minimum confidence in percent
     * @throws Exception When can not connect to database
     */
    public void run(String database, double support, double confidence) throws Exception {
        loadData(database);

        // converting percents to doubles
        minSupport = support / 100;
        minConfidence = confidence / 100;

        while (!itemSets.isEmpty()) {
            // find frequent itemsets
            findFreqItemsets();
            if (!itemSets.isEmpty()) {
                // create new itemsets with new size
                updateItemSets();
            }
        }
    }

    /**
     * loads data from a database
     *
     * @param database Database name (transactions1 - transactions5)
     * @throws Exception When can not connect to database
     */
    public void loadData(String database) throws Exception {
        // create 5 databases if not exist
        TransactionsDao.createDatabases();
        apGUI.appendInfoLine("Database created");

        TransactionsDao transDao = new TransactionsDao(database);
        // connect to the database chosen by user
        transDao.connect();
        indicesItems = transDao.getItems();
        transactions = transDao.getTransactions();
        countTransactions = transactions.size();
        itemSets = new ArrayList<>();

        numItems = 0;
        Iterator<Entry<Integer, ArrayList<Integer>>> it = transactions.entrySet().iterator();
        // adding distinct items contained in the transactions to the itemSets list
        while (it.hasNext()) {
            Entry<Integer, ArrayList<Integer>> pair = it.next();
            for (Integer val : pair.getValue()) {
                boolean exists = false;
                int[] candidate = {val};
                // check if item already exists in the list
                for (int[] itemSet : itemSets) {
                    if (itemSet[0] == val) {
                        exists = true;
                        break;
                    }
                }
                // if item does not already exist in the list
                if (!exists) {
                    // add this item to list
                    ++numItems;
                    itemSets.add(candidate);
                }
            }
        }

        apGUI.appendInfoLine(numItems + " items loaded. \n" + countTransactions + " transactions. \n");

        it = transactions.entrySet().iterator();
        // display input transactions in a text area
        while (it.hasNext()) {
            Entry<Integer, ArrayList<Integer>> pair = it.next();
            StringBuilder line = new StringBuilder(pair.getKey() + ": ");
            for (Integer val : pair.getValue()) {
                line.append(indicesItems.get(val)).append(", ");
            }
            line = new StringBuilder(line.substring(0, line.length() - 2));
            apGUI.appendTransactionsLine(line.toString());
        }

    }

    /**
     * loads the GUI window
     */
    private void startGUI() {
        apGUI = new AprioriGUI(this);
    }

    /**
     * finds frequent itemsets that satisfy the minimum support requirement
     */
    public void findFreqItemsets() {
        apGUI.appendInfoLine("\nFinding frequent itemsets...");
        apGUI.appendInfoLine(
            "Calculating " + itemSets.size() + " itemsets' frequencies of size " + itemSets.get(0).length + "\n");
        List<int[]> freqCandidates = new ArrayList<>();

        int[] itemSetsCount = new int[itemSets.size()];

        boolean[] itemsFound = new boolean[100];
        boolean matchesCandidate;

        for (Entry<Integer, ArrayList<Integer>> pair : transactions.entrySet()) {
            // mark items found in a separate array
            Arrays.fill(itemsFound, false);
            for (int item : pair.getValue()) {
                itemsFound[item] = true;
            }
            // for each itemset
            for (int j = 0; j < itemSets.size(); j++) {
                int[] candidateItemSet = itemSets.get(j);
                // find if given itemset matches a candidate
                matchesCandidate = true;
                for (int num : candidateItemSet) {
                    if (!itemsFound[num]) {
                        matchesCandidate = false;
                        break;
                    }
                }
                if (matchesCandidate) {
                    // increment a count of this itemset
                    ++itemSetsCount[j];
                }
            }
        }

        apGUI.appendInfoLine("Removing candidates...");
        // print frequent itemsets found
        for (int i = 0; i < itemSets.size(); i++) {
            int itemSetCount = itemSetsCount[i];
            // calculate the support value
            double itemSetSupport = (itemSetCount / (double) countTransactions);
            // print the itemset if it satisfies the minimum support requirement entered by
            // user
            if (itemSetSupport >= minSupport) {
                int[] freqCandidate = itemSets.get(i);
                printItemset(freqCandidate, itemSetCount, itemSetSupport);
                itemSetCounts.put(Arrays.toString(freqCandidate), itemSetCount);
                freqCandidates.add(freqCandidate);
                findAssociationRules(freqCandidate);
            } else {
                apGUI.appendInfoLine(
                    "Removing candidate " + indicesToNames(itemSets.get(i)) + " with support: " + (itemSetSupport));
            }
        }

        itemSets = freqCandidates;
    }

    /**
     * converts an array of items' indices to appropriate names
     *
     * @param itemSet Array of items' indices
     * @return Line containing names of items
     */
    private String indicesToNames(int[] itemSet) {
        StringBuilder result = new StringBuilder("[");
        for (int item : itemSet) {
            result.append(indicesItems.get(item)).append(", ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append("]");
        return result.toString();
    }

    /**
     * creates new itemsets with size incremented by 1
     */
    private void updateItemSets() {
        apGUI.appendInfoLine("\nGenerating itemsets with size " + (itemSets.get(0).length + 1) + " using "
            + itemSets.size() + " itemsets of size " + itemSets.get(0).length + "...");
        Map<String, int[]> candidates = new HashMap<>();

        // comparing all pair of items
        for (int i = 0; i < itemSets.size(); i++) {
            for (int j = i + 1; j < itemSets.size(); j++) {
                int[] itemSet1 = itemSets.get(i);
                int[] itemSet2 = itemSets.get(j);

                // new itemSet with size incremented by 1
                int[] newCandidate = new int[itemSets.get(0).length + 1];
                // add all items from itemSet1 to the newly created itemset
                System.arraycopy(itemSet1, 0, newCandidate, 0, newCandidate.length - 1);

                int differentItemsQuantity = 0;
                for (int item2 : itemSet2) {
                    boolean found = false;
                    for (int item1 : itemSet1) {
                        if (item1 == item2) {
                            found = true;
                            break;
                        }
                    }
                    // if there are different items then add them to the newly created itemset
                    if (!found) {
                        ++differentItemsQuantity;
                        newCandidate[newCandidate.length - 1] = item2;
                    }
                }
                // items quantity difference is 1
                if (differentItemsQuantity == 1) {
                    // sort new candidate itemset
                    Arrays.sort(newCandidate);
                    // put the itemset to candidates map
                    candidates.put(Arrays.toString(newCandidate), newCandidate);
                }
            }
        }
        itemSets = new ArrayList<>(candidates.values());
        apGUI.appendInfoLine(
            "Generated " + itemSets.size() + " distinct itemsets with size " + (itemSets.get(0).length) + ".");
    }

    /**
     * finds association rules for current itemset
     *
     * @param itemSet
     */
    private void findAssociationRules(int[] itemSet) {
        Set<String> itemsIndices = new HashSet<>();
        Set<Integer> itemsNames = new HashSet<>();

        // for each item in the itemset
        for (int item : itemSet) {
            // add it to the current items set
            itemsNames.add(item);
            itemsIndices.add(indicesItems.get(item));
        }

        for (int i = 1; i < itemSet.length; i++) {
            for (int j = 0; j < itemSet.length; j++) {
                // create left and right sets of the associative rule
                Set<String> leftItemsIndices = new HashSet<>();
                Set<String> rightItemsIndices = new HashSet<>(itemsIndices);

                Set<Integer> leftItems = new HashSet<>();
                Set<Integer> rightItems = new HashSet<>(itemsNames);

                int count = 0;
                // generate a left set of the rule
                for (int k = j; k < itemSet.length && count < i; k++) {
                    // add item to the left set
                    leftItemsIndices.add(indicesItems.get(itemSet[k]));
                    leftItems.add(itemSet[k]);
                    ++count;
                }
                while (count < i) {
                    for (int k = 0; k < itemSet.length && count < i; k++) {
                        // add item to the left set
                        leftItemsIndices.add(indicesItems.get(itemSet[k]));
                        leftItems.add(itemSet[k]);
                        ++count;
                    }
                }
                // remove all left-side items of the rule from the right set
                rightItemsIndices.removeAll(leftItemsIndices);
                rightItems.removeAll(leftItems);
                // calculate confidence value for the itemset by dividing support values
                double unionItemsSupport = (double) itemSetCounts.get(itemsNames.toString());
                double leftItemSupport = itemSetCounts.get(leftItems.toString());
                double confidence = unionItemsSupport / leftItemSupport;
                // if confidence value satisfies the minimum value entered by user then display
                // the itemset
                if (confidence >= minConfidence) {
                    apGUI.appendLine(leftItemsIndices + "=>" + rightItemsIndices);
                }
            }
        }
    }

    /**
     * prints the itemset along with its count and support value
     *
     * @param freqCandidate  The itemset, array of items
     * @param itemSetCount   The itemset's number of occurences
     * @param itemSetSupport The itemset's support
     */
    private void printItemset(int[] freqCandidate, int itemSetCount, double itemSetSupport) {
        StringBuilder candidateString = new StringBuilder();
        for (int item : freqCandidate) {
            candidateString.append(indicesItems.get(item)).append(", ");
        }
        candidateString = new StringBuilder(candidateString.substring(0, candidateString.length() - 2));
        apGUI.appendLine("\nItemset: |" + candidateString + "| \nSuch itemsets in the database: " + itemSetCount
            + ", Support: " + String.format("%.2f", itemSetSupport));
    }

}
