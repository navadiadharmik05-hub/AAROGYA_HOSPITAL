package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PatientFrame extends JFrame {

    private JTextField txtName, txtAge, txtContact, txtAddress, txtSearchId;
    private JRadioButton rdoMale, rdoFemale, rdoOther;
    private ButtonGroup genderGroup;
    private JTable tblPatients;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;

    private final PatientDAO patientDAO = new PatientDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public PatientFrame() {
        setTitle("Patient Management - Aarogya Hospital");
        setSize(1040, 640);
        setMinimumSize(new Dimension(900, 540));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JButton btnBack = Theme.backButton();
        btnBack.addActionListener(e -> dispose());
        JPanel headerBar = Theme.buildHeaderBar("Patient Directory & Registration", "Manage patient profiles and appointment history", btnBack);
        add(headerBar, BorderLayout.NORTH);

        // Split Pane (Left: Form Card, Right: Table & Search Card)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildLeftFormPanel(), buildRightTablePanel());
        splitPane.setDividerLocation(360);
        splitPane.setResizeWeight(0.35);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        splitPane.setBackground(Theme.BG);

        add(splitPane, BorderLayout.CENTER);

        loadAllPatients();
    }

    private JPanel buildLeftFormPanel() {
        JPanel formCard = Theme.sectionPanel(new BorderLayout(0, 14));

        JLabel title = new JLabel("Register New Patient");
        title.setFont(Theme.FONT_HEADER);
        title.setForeground(Theme.DEEP_NAVY);
        formCard.add(title, BorderLayout.NORTH);

        JPanel fieldsGrid = new JPanel(new GridLayout(10, 1, 0, 4));
        fieldsGrid.setOpaque(false);

        txtName = Theme.placeholderField("Full Name");
        txtAge = Theme.placeholderField("Age");
        txtContact = Theme.placeholderField("10-Digit Phone Number");
        txtAddress = Theme.placeholderField("Home Address");

        // Input Parameter Restrictions & Filters
        Theme.addAlphabetOnlyFilter(txtName);             // Text/Letters only
        Theme.addNumberOnlyFilter(txtAge, 3);              // Numbers only (max 3 digits)
        Theme.addNumberOnlyFilter(txtContact, 10);         // Numbers only (max 10 digits)

        // Gender Selection Radio Buttons
        rdoMale = new JRadioButton("Male", true);
        rdoFemale = new JRadioButton("Female");
        rdoOther = new JRadioButton("Other");

        Theme.styleRadioButton(rdoMale);
        Theme.styleRadioButton(rdoFemale);
        Theme.styleRadioButton(rdoOther);

        genderGroup = new ButtonGroup();
        genderGroup.add(rdoMale);
        genderGroup.add(rdoFemale);
        genderGroup.add(rdoOther);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setOpaque(false);
        genderPanel.add(rdoMale);
        genderPanel.add(rdoFemale);
        genderPanel.add(rdoOther);

        fieldsGrid.add(formLabel("Full Name (Letters Only):"));
        fieldsGrid.add(txtName);
        fieldsGrid.add(formLabel("Age (Numbers Only):"));
        fieldsGrid.add(txtAge);
        fieldsGrid.add(formLabel("Gender Selection:"));
        fieldsGrid.add(genderPanel);
        fieldsGrid.add(formLabel("Contact Number (Digits Only):"));
        fieldsGrid.add(txtContact);
        fieldsGrid.add(formLabel("Address:"));
        fieldsGrid.add(txtAddress);

        JButton btnAdd = Theme.primaryButton("Add Patient");
        btnAdd.setPreferredSize(new Dimension(0, 38));
        btnAdd.addActionListener(e -> addPatient());

        JPanel bottomBox = new JPanel(new BorderLayout());
        bottomBox.setOpaque(false);
        bottomBox.add(btnAdd, BorderLayout.CENTER);

        formCard.add(fieldsGrid, BorderLayout.CENTER);
        formCard.add(bottomBox, BorderLayout.SOUTH);

        return formCard;
    }

    private JPanel buildRightTablePanel() {
        JPanel tableCard = Theme.sectionPanel(new BorderLayout(0, 10));

        // Search and Action Toolbar
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchBar.setOpaque(false);

        txtSearchId = Theme.placeholderField("Search ID");
        txtSearchId.setPreferredSize(new Dimension(100, 34));
        Theme.addNumberOnlyFilter(txtSearchId, 6);

        JButton btnSearch = Theme.secondaryButton("Search ID");
        JButton btnShowAll = Theme.secondaryButton("Show All");
        JButton btnHistory = Theme.secondaryButton("View History");
        JButton btnDelete = Theme.dangerButton("Delete Patient");

        btnSearch.addActionListener(e -> searchPatient());
        btnShowAll.addActionListener(e -> loadAllPatients());
        btnHistory.addActionListener(e -> showHistory());
        btnDelete.addActionListener(e -> deleteSelectedPatient());

        searchBar.add(formLabel("ID:"));
        searchBar.add(txtSearchId);
        searchBar.add(btnSearch);
        searchBar.add(btnShowAll);
        searchBar.add(btnHistory);
        searchBar.add(btnDelete);

        lblStatus = Theme.emptyStateLabel("");

        JPanel topBar = new JPanel(new BorderLayout(0, 4));
        topBar.setOpaque(false);
        topBar.add(searchBar, BorderLayout.NORTH);
        topBar.add(lblStatus, BorderLayout.SOUTH);

        tableCard.add(topBar, BorderLayout.NORTH);

        // Data Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Gender", "Contact", "Address"}, 0);
        tblPatients = new JTable(tableModel);
        Theme.styleTable(tblPatients);

        JScrollPane scrollPane = new JScrollPane(tblPatients);
        scrollPane.setBorder(Theme.rounded(Theme.SKY_BLUE, 6));
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        tableCard.add(scrollPane, BorderLayout.CENTER);

        return tableCard;
    }

    private JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.DEEP_NAVY);
        return label;
    }

    private void addPatient() {
        try {
            String name = txtName.getText().trim();
            String ageText = txtAge.getText().trim();
            String gender = rdoMale.isSelected() ? "Male" : (rdoFemale.isSelected() ? "Female" : "Other");
            String contact = txtContact.getText().trim();
            String address = txtAddress.getText().trim();

            if (name.isEmpty() || ageText.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int age = Integer.parseInt(ageText);
            if (age <= 0 || age > 130) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age (1 - 130).", "Invalid Age", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (contact.length() < 7) {
                JOptionPane.showMessageDialog(this, "Contact number must be at least 7 digits.", "Invalid Contact", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int newId = patientDAO.getNextPatientId();
            Patient newPatient = new Patient(newId, name, age, gender, contact, address);

            boolean success = patientDAO.addPatient(newPatient);
            if (success) {
                JOptionPane.showMessageDialog(this, "Patient added successfully with ID: " + newId);
                clearForm();
                loadAllPatients();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age and Contact must be numbers.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedPatient() {
        int selectedRow = tblPatients.getSelectedRow();
        int patientIdToDelete = -1;
        String patientName = "";

        if (selectedRow != -1) {
            patientIdToDelete = (int) tableModel.getValueAt(selectedRow, 0);
            patientName = (String) tableModel.getValueAt(selectedRow, 1);
        } else {
            String searchIdText = txtSearchId.getText().trim();
            if (!searchIdText.isEmpty()) {
                try {
                    patientIdToDelete = Integer.parseInt(searchIdText);
                } catch (NumberFormatException ignored) {}
            }
        }

        if (patientIdToDelete == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient from the table or enter a Patient ID to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String displayName = patientName.isEmpty() ? "ID " + patientIdToDelete : patientName + " (ID " + patientIdToDelete + ")";
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete patient " + displayName + "?\nThis action cannot be undone.",
                "Confirm Delete Patient",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = patientDAO.deletePatient(patientIdToDelete);
            if (success) {
                JOptionPane.showMessageDialog(this, "Patient " + displayName + " deleted successfully.");
                txtSearchId.setText("");
                loadAllPatients();
            } else {
                // If DB was offline or couldn't execute, update local table view
                boolean removed = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if ((int) tableModel.getValueAt(i, 0) == patientIdToDelete) {
                        tableModel.removeRow(i);
                        removed = true;
                        break;
                    }
                }
                if (removed) {
                    JOptionPane.showMessageDialog(this, "Patient " + displayName + " removed.");
                    txtSearchId.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete patient.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtAge.setText("");
        rdoMale.setSelected(true);
        txtContact.setText("");
        txtAddress.setText("");
    }

    private void loadAllPatients() {
        tableModel.setRowCount(0);
        ArrayList<Patient> patients = patientDAO.getAllPatients();
        if (patients != null) {
            for (Patient p : patients) {
                tableModel.addRow(new Object[]{p.getPatientId(), p.getName(), p.getAge(), p.getGender(), p.getContact(), p.getAddress()});
            }
        }
        updateStatus(patients);
    }

    private void searchPatient() {
        String idText = txtSearchId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a patient ID to search.", "Missing ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            ArrayList<Patient> patients = patientDAO.getAllPatients();
            tableModel.setRowCount(0);

            boolean found = false;
            if (patients != null) {
                for (Patient p : patients) {
                    if (p.getPatientId() == id) {
                        tableModel.addRow(new Object[]{p.getPatientId(), p.getName(), p.getAge(), p.getGender(), p.getContact(), p.getAddress()});
                        found = true;
                        break;
                    }
                }
            }

            lblStatus.setText(found ? "1 record found for ID " + id : "No patient found with ID " + id);

            if (!found) {
                JOptionPane.showMessageDialog(this, "No patient found with ID " + id, "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showHistory() {
        int selectedRow = tblPatients.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient from the table first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);
        ArrayList<String> history = appointmentDAO.getPatientHistory(patientId);

        StringBuilder sb = new StringBuilder();
        if (history == null || history.isEmpty()) {
            sb.append("No appointment history found for this patient.");
        } else {
            for (String record : history) {
                sb.append(record).append("\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setRows(10);
        textArea.setColumns(40);
        textArea.setFont(Theme.FONT_BODY);
        JScrollPane scroll = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scroll, "Appointment History - Patient ID " + patientId, JOptionPane.PLAIN_MESSAGE);
    }

    private void updateStatus(ArrayList<Patient> patients) {
        int count = patients == null ? 0 : patients.size();
        lblStatus.setText(count == 0 ? "No patient records yet - register one on the left to get started." : count + " patient record" + (count == 1 ? "" : "s") + " found.");
    }

    public static void main(String[] args) {
        Theme.initLookAndFeel();
        SwingUtilities.invokeLater(() -> new PatientFrame().setVisible(true));
    }
}
