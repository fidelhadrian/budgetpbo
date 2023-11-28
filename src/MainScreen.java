import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class MainScreen {
    private JPanel MainScreen;
    private JLabel txtHeader;
    private JTable tableCatatan;
    private JTextField Keterangan;
    private JTextField Jumlah;
    private JComboBox<String> Jenis;
    private JButton addButton;
    private JButton clearButton;

    private DefaultTableModel tableModel;
    private double totalBudget = 0;

    public MainScreen() {
        // Initialize components
        initComponents();

        // Add action listener for the Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });

        // Add action listener for the Clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTable();
            }
        });
    }

    private void initComponents() {
        // Set up the table model for the JTable
        String[] columnNames = {"Keterangan", "Jumlah", "Jenis"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableCatatan.setModel(tableModel);

        // Set the initial value of totalBudget
        updateTotalBudget();

        // Add options to JComboBox
        String[] jenisOptions = {"Pemasukan", "Pengeluaran"};
        Jenis.setModel(new DefaultComboBoxModel<>(jenisOptions));
    }

    private void addTransaction() {
        // Get values from text fields and combo box
        String keterangan = Keterangan.getText();
        String jumlahText = Jumlah.getText();

        // Validate input
        if (keterangan.isEmpty() || jumlahText.isEmpty()) {
            JOptionPane.showMessageDialog(MainScreen, "Input Kosong");
            return;
        }

        double jumlah = Double.parseDouble(jumlahText);
        String jenis = (String) Jenis.getSelectedItem();

        // Add the transaction to the table
        Object[] rowData = {keterangan, formatCurrency(jumlah), jenis};
        tableModel.addRow(rowData);

        // Update total budget based on transaction type
        if (jenis.equals("Pengeluaran")) {
            totalBudget -= jumlah;
        } else {
            totalBudget += jumlah;
        }

        // Update the label displaying Total Budget
        updateTotalBudget();

        // Clear input fields after adding a transaction
        Keterangan.setText("");
        Jumlah.setText("");
    }

    private void clearTable() {
        tableModel.setRowCount(0); // Remove all rows from the table
        totalBudget = 0; // Reset total budget
        updateTotalBudget();
    }

    private String formatCurrency(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(amount);
    }

    private void updateTotalBudget() {
        txtHeader.setText("Total Budget: " + formatCurrency(totalBudget));
    }

    public void showMainScreen() {
        JFrame frame = new JFrame("Main Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(MainScreen);
        frame.setSize(1200, 800); // Set the frame size
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Customize cell renderer for the "Jumlah" column
        tableCatatan.getColumnModel().getColumn(1).setCellRenderer(new CurrencyRenderer());

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create and show the MainScreen
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainScreen().showMainScreen();
            }
        });
    }

    // Custom cell renderer for currency formatting and cell coloring
    private class CurrencyRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String jenis = (String) table.getValueAt(row, 2);
            if ("Pengeluaran".equals(jenis)) {
                component.setForeground(Color.RED); // Set text color to red for expenses
            } else {
                component.setForeground(Color.BLUE); // Set text color to green for income
            }

            return component;
        }
    }
}
