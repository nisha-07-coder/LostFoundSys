import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LostFoundSystem extends JFrame {

    // DB CONFIG
    static final String URL = "jdbc:mysql://localhost:3306/lost_found_system";
    static final String USER = "root";
    static final String PASSWORD = "root07nisha!";

    JTextField name, desc, location, reporter;
    JComboBox<String> status;

    DefaultTableModel model;
    JTable table;

    // Constructor
    public LostFoundSystem() {
        setTitle("Lost & Found System");
        setSize(850, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        name = new JTextField();
        desc = new JTextField();
        location = new JTextField();
        reporter = new JTextField();
        status = new JComboBox<>(new String[]{"LOST", "FOUND"});

        formPanel.add(new JLabel("Item Name"));
        formPanel.add(name);

        formPanel.add(new JLabel("Description"));
        formPanel.add(desc);

        formPanel.add(new JLabel("Location"));
        formPanel.add(location);

        formPanel.add(new JLabel("Reporter"));
        formPanel.add(reporter);

        formPanel.add(new JLabel("Status"));
        formPanel.add(status);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Description", "Location", "Reporter", "Status"}, 0
        );

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== TABLE CLICK (AUTO-FILL) =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                name.setText(model.getValueAt(row, 1).toString());
                desc.setText(model.getValueAt(row, 2).toString());
                location.setText(model.getValueAt(row, 3).toString());
                reporter.setText(model.getValueAt(row, 4).toString());
                status.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });

        // ===== BUTTON ACTIONS =====
        addBtn.addActionListener(e -> insertData());
        updateBtn.addActionListener(e -> updateData());
        deleteBtn.addActionListener(e -> deleteData());
        refreshBtn.addActionListener(e -> loadData());

        loadData();
        setVisible(true);
    }

    // ===== DB CONNECTION =====
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ===== INSERT =====
    void insertData() {
        String sql = "INSERT INTO items (item_name, description, location, reporter_name, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name.getText());
            pst.setString(2, desc.getText());
            pst.setString(3, location.getText());
            pst.setString(4, reporter.getText());
            pst.setString(5, status.getSelectedItem().toString());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item Added!");
            clearFields();
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== UPDATE =====
    void updateData() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        String sql = "UPDATE items SET item_name=?, description=?, location=?, reporter_name=?, status=? WHERE item_id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name.getText());
            pst.setString(2, desc.getText());
            pst.setString(3, location.getText());
            pst.setString(4, reporter.getText());
            pst.setString(5, status.getSelectedItem().toString());
            pst.setInt(6, id);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item Updated!");
            clearFields();
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== DELETE =====
    void deleteData() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this item?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM items WHERE item_id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item Deleted!");
            clearFields();
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== CLEAR =====
    void clearFields() {
        name.setText("");
        desc.setText("");
        location.setText("");
        reporter.setText("");
        status.setSelectedIndex(0);
    }

    // ===== LOAD =====
    void loadData() {
        String sql = "SELECT * FROM items";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("reporter_name"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        new LostFoundSystem();
    }
} 