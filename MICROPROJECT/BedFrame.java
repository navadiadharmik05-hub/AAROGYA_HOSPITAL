package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BedFrame extends JFrame {

    private JComboBox<String> comboWards;
    private JTextField txtPatientId;
    private JTable tblBeds;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;

    private final WardDAO wardDAO = new WardDAO();
    private final BedDAO bedDAO = new BedDAO();

    private ArrayList<Ward> wardList = new ArrayList<>();
    private ArrayList<Bed> displayedBeds = new ArrayList<>();

    public BedFrame() {
        setTitle("Beds & Wards - Aarogya Hospital");
        setSize(980, 620);
        setMinimumSize(new Dimension(860, 520));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JButton btnBack = Theme.backButton();
        btnBack.addActionListener(e -> dispose());
        JPanel headerBar = Theme.buildHeaderBar("Beds & Wards Management", "Admit patients to available ward beds and process bed discharges", btnBack);
        add(headerBar, BorderLayout.NORTH);

        JPanel mainContainer = new JPanel(new BorderLayout(0, 14));
        mainContainer.setOpaque(false);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Admission Controls Card (2-row Grid for clean non-clipping layout)
        JPanel formCard = Theme.sectionPanel(new BorderLayout(0, 10));

        JPanel inputsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        inputsRow.setOpaque(false);

        comboWards = new JComboBox<>();
        Theme.styleCombo(comboWards);
        comboWards.setPreferredSize(new Dimension(220, 34));

        txtPatientId = Theme.placeholderField("Patient ID");
        txtPatientId.setPreferredSize(new Dimension(140, 34));
        Theme.addNumberOnlyFilter(txtPatientId, 6);

        inputsRow.add(formLabel("Target Ward:"));
        inputsRow.add(comboWards);
        inputsRow.add(Box.createRigidArea(new Dimension(10, 0)));
        inputsRow.add(formLabel("Patient ID (Numbers Only):"));
        inputsRow.add(txtPatientId);

        JPanel actionsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        actionsRow.setOpaque(false);

        JButton btnAdmit = Theme.primaryButton("Admit Patient");
        JButton btnDischarge = Theme.secondaryButton("Discharge Selected Bed");
        JButton btnRefresh = Theme.secondaryButton("Refresh");

        btnAdmit.setPreferredSize(new Dimension(140, 36));
        btnDischarge.setPreferredSize(new Dimension(200, 36));
        btnRefresh.setPreferredSize(new Dimension(100, 36));

        btnAdmit.addActionListener(e -> admitPatient());
        btnDischarge.addActionListener(e -> dischargeBed());
        btnRefresh.addActionListener(e -> loadBeds());

        actionsRow.add(btnAdmit);
        actionsRow.add(btnDischarge);
        actionsRow.add(btnRefresh);

        formCard.add(inputsRow, BorderLayout.NORTH);
        formCard.add(actionsRow, BorderLayout.SOUTH);

        mainContainer.add(formCard, BorderLayout.NORTH);

        // Bed Table Card
        JPanel tableCard = Theme.sectionPanel(new BorderLayout(0, 8));

        lblStatus = Theme.emptyStateLabel("");
        tableCard.add(lblStatus, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Bed ID", "Ward ID", "Bed Number", "Status", "Occupying Patient ID"}, 0);
        tblBeds = new JTable(tableModel);
        Theme.styleTable(tblBeds);

        JScrollPane scrollPane = new JScrollPane(tblBeds);
        scrollPane.setBorder(Theme.rounded(Theme.SKY_BLUE, 6));
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        tableCard.add(scrollPane, BorderLayout.CENTER);

        mainContainer.add(tableCard, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);

        loadWards();
        loadBeds();
    }

    private JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_LABEL);
        label.setForeground(Theme.DEEP_NAVY);
        return label;
    }

    private void loadWards() {
        comboWards.removeAllItems();
        wardList = wardDAO.getAllWards();

        if (wardList == null || wardList.isEmpty()) {
            wardList = new ArrayList<>();
            wardList.add(new Ward(1, "General Ward A", "General"));
            wardList.add(new Ward(2, "ICU Ward B", "Intensive Care"));
            wardList.add(new Ward(3, "Special Care Ward C", "Private"));
            wardList.add(new Ward(4, "Emergency Ward D", "Emergency"));
        }

        for (Ward w : wardList) {
            comboWards.addItem(w.getWardId() + " - " + w.getWardName());
        }
    }

    private void loadBeds() {
        tableModel.setRowCount(0);
        ArrayList<Bed> dbBeds = bedDAO.getAllBeds();

        if (dbBeds == null || dbBeds.isEmpty()) {
            if (displayedBeds.isEmpty()) {
                displayedBeds.add(new Bed(1, 1, "G01", "Available", null));
                displayedBeds.add(new Bed(2, 1, "G02", "Available", null));
                displayedBeds.add(new Bed(3, 1, "G03", "Available", null));
                displayedBeds.add(new Bed(4, 1, "G04", "Available", null));
                displayedBeds.add(new Bed(5, 2, "I01", "Available", null));
                displayedBeds.add(new Bed(6, 2, "I02", "Available", null));
                displayedBeds.add(new Bed(7, 3, "M01", "Available", null));
                displayedBeds.add(new Bed(8, 3, "M02", "Available", null));
            }
        } else {
            displayedBeds = dbBeds;
        }

        for (Bed b : displayedBeds) {
            tableModel.addRow(new Object[]{
                    b.getBedId(),
                    b.getWardId(),
                    b.getBedNumber(),
                    b.getStatus(),
                    b.getPatientId() != null ? b.getPatientId() : "-"
            });
        }
        int count = displayedBeds.size();
        lblStatus.setText(count + " beds currently configured across hospital wards.");
    }

    private void admitPatient() {
        if (comboWards.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No wards available.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientIdText = txtPatientId.getText().trim();
        if (patientIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Patient ID to admit.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int patientId = Integer.parseInt(patientIdText);
            Ward selectedWard = wardList.get(comboWards.getSelectedIndex());

            // Find first available bed in displayed list for target ward
            Bed targetBed = null;
            for (Bed b : displayedBeds) {
                if (b.getWardId() == selectedWard.getWardId() && (b.getStatus().equalsIgnoreCase("Available") || b.getPatientId() == null)) {
                    targetBed = b;
                    break;
                }
            }

            if (targetBed == null) {
                // Try bedDAO search
                targetBed = bedDAO.getFirstAvailableBedInWard(selectedWard.getWardId());
            }

            if (targetBed != null) {
                // Execute DB assignment
                bedDAO.assignBed(targetBed.getBedId(), patientId);

                // Update local memory state & refresh UI
                targetBed.setStatus("Occupied");
                targetBed.setPatientId(patientId);

                JOptionPane.showMessageDialog(this, "Patient " + patientId + " admitted to Bed " + targetBed.getBedNumber() + " in " + selectedWard.getWardName() + ".");
                txtPatientId.setText("");
                loadBeds();
            } else {
                JOptionPane.showMessageDialog(this, "No available beds in " + selectedWard.getWardName() + ".", "Admission Failed", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void dischargeBed() {
        int selectedRow = tblBeds.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a bed from the table first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bedId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentStatus = (String) tableModel.getValueAt(selectedRow, 3);

        if (currentStatus.equalsIgnoreCase("Available")) {
            JOptionPane.showMessageDialog(this, "This bed is already available.", "No Action Needed", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Execute DB discharge
        bedDAO.dischargeBed(bedId);

        // Update local memory state & refresh UI
        for (Bed b : displayedBeds) {
            if (b.getBedId() == bedId) {
                b.setStatus("Available");
                b.setPatientId(null);
                break;
            }
        }

        JOptionPane.showMessageDialog(this, "Bed ID " + bedId + " discharged and marked Available.");
        loadBeds();
    }

    public static void main(String[] args) {
        Theme.initLookAndFeel();
        SwingUtilities.invokeLater(() -> new BedFrame().setVisible(true));
    }
}
