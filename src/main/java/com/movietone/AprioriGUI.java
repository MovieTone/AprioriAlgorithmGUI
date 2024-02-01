package com.movietone;

import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * AprioriGUI class is used to create a GUI window to solve the associative rules problem
 *
 * @author Name
 */
public class AprioriGUI extends JFrame {

    private final JPanel contentPane;
    // text field to enter a minimum support value
    private final JTextField txtFldSupport;
    // text field to enter a minimum confidence value
    private final JTextField txtFldConfidence;
    private final Apriori ap;
    // text area to output associative rules
    private final JTextArea txtAreaRules;
    private final JLabel lblDatabase;
    // text area to display input transactions
    private final JTextArea txtAreaTransactions;
    private final JScrollPane scrollPane;
    private final JScrollPane scrollPane2;
    // button to run the algorithm
    private final JButton btnRun;
    // combo box to choose a database
    private final JComboBox<String> cmbBoxDatabase;
    private final JLabel lblInputTransactions;
    private final JLabel lblAssociationRules;
    private final JScrollPane scrollPane3;
    private final JTextArea txtAreaInfo;
    private final JLabel lblIntermediateSteps;
    private final JLabel lblPle;

    /**
     * Create the frame.
     */
    public AprioriGUI(Apriori ap) {
        this.ap = ap;

        setTitle("Apriori");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 624, 559);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblSupport = new JLabel("Minimum support (%):");
        lblSupport.setBounds(113, 55, 167, 14);
        contentPane.add(lblSupport);

        JLabel lblConfidence = new JLabel("Minimum confidence (%):");
        lblConfidence.setBounds(113, 80, 167, 14);
        contentPane.add(lblConfidence);

        // text field to enter a minimum support value
        txtFldSupport = new JTextField();
        txtFldSupport.setBounds(348, 55, 173, 20);
        contentPane.add(txtFldSupport);
        txtFldSupport.setColumns(10);

        // text field to enter a minimum confidence value
        txtFldConfidence = new JTextField();
        txtFldConfidence.setBounds(348, 80, 173, 20);
        contentPane.add(txtFldConfidence);
        txtFldConfidence.setColumns(10);

        // button to run the algorithm
        btnRun = new JButton("Run");
        btnRun.setBounds(432, 111, 89, 23);
        contentPane.add(btnRun);

        lblDatabase = new JLabel("Database:");
        lblDatabase.setBounds(113, 30, 167, 14);
        contentPane.add(lblDatabase);

        // combo box to choose a database
        cmbBoxDatabase = new JComboBox<>();
        cmbBoxDatabase.setModel(
            new DefaultComboBoxModel<>(new String[] {"transactions1", "transactions2", "transactions3", "transactions4", "transactions5"}));
        cmbBoxDatabase.setBounds(348, 30, 173, 20);
        contentPane.add(cmbBoxDatabase);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 171, 287, 271);
        contentPane.add(scrollPane);

        // text area to display input transactions
        txtAreaTransactions = new JTextArea();
        txtAreaTransactions.setEditable(false);
        scrollPane.setViewportView(txtAreaTransactions);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(307, 170, 289, 272);
        contentPane.add(scrollPane2);

        // text area to output associative rules
        txtAreaRules = new JTextArea("");
        scrollPane2.setViewportView(txtAreaRules);
        txtAreaRules.setEditable(false);

        lblInputTransactions = new JLabel("Input Transactions:");
        lblInputTransactions.setBounds(111, 145, 119, 14);
        contentPane.add(lblInputTransactions);

        lblAssociationRules = new JLabel("Association Rules:");
        lblAssociationRules.setBounds(401, 145, 147, 14);
        contentPane.add(lblAssociationRules);

        scrollPane3 = new JScrollPane();
        scrollPane3.setBounds(10, 453, 586, 62);
        contentPane.add(scrollPane3);

        txtAreaInfo = new JTextArea();
        txtAreaInfo.setEditable(false);
        scrollPane3.setViewportView(txtAreaInfo);

        lblIntermediateSteps = new JLabel("Intermediate steps:");
        lblIntermediateSteps.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblIntermediateSteps.setBounds(253, 440, 141, 14);
        contentPane.add(lblIntermediateSteps);

        lblPle = new JLabel("Please input the minimum support and the minimum confidence values:");
        lblPle.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblPle.setBounds(151, 0, 408, 19);
        contentPane.add(lblPle);

        contentPane.updateUI();

        addButtonListener();
    }

    /**
     * adds action listener to Run button. Gets entered values and runs the algorithm
     */
    private void addButtonListener() {
        btnRun.addActionListener(actionListener -> {
            // clear text areas
            txtAreaRules.setText("");
            txtAreaTransactions.setText("");
            txtAreaInfo.setText("");
            // get chosen database name
            String database = cmbBoxDatabase.getSelectedItem().toString();
            // get support value
            double support = Double.parseDouble(txtFldSupport.getText().trim());
            // get confidence value
            double confidence = Double.parseDouble(txtFldConfidence.getText().trim());
            try {
                // run Apriori algorithm
                ap.run(database, support, confidence);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * append an associative rule to an appropriate text area
     *
     * @param line Associative rule or info to append
     */
    public void appendLine(String line) {
        txtAreaRules.append(line + "\n");
    }

    /**
     * appends a transaction to transactions text area
     *
     * @param line Transaction or info to append
     */
    public void appendTransactionsLine(String line) {
        txtAreaTransactions.append(line + "\n");
    }

    /**
     * changes text in info text area
     *
     * @param line to add to text area
     */
    public void appendInfoLine(String line) {
        txtAreaInfo.append(line + "\n");
    }
}
