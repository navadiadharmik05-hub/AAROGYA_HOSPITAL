package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DoctorFrame extends JFrame {

    private JTable tblDoctors;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;
    private final DoctorDAO doctorDAO = new DoctorDAO();

    public DoctorFrame() {
        setTitle("Doctors - Aarogya Hospital");
        setSize(880, 560);
        setMinimumSize(new Dimension(750, 480));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JButton btnBack = Theme.backButton();
        btnBack.addActionListener(e -> dispose());
        JPanel headerBar = Theme.buildHeaderBar("Doctor Directory", "View medical staff specializations and availability schedules", btnBack);
        add(headerBar, BorderLayout.NORTH);

        JPanel mainContainer = new JPanel(new BorderLayout(0, 14));
        mainContainer.setOpaque(false);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel tableCard = Theme.sectionPanel(new BorderLayout(0, 10));

        lblStatus = Theme.emptyStateLabel("");

        JButton btnRefresh = Theme.secondaryButton("Refresh Directory");
        btnRefresh.addActionListener(e -> loadDoctors());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(lblStatus, BorderLayout.WEST);

        JPanel rightWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightWrap.setOpaque(false);
        rightWrap.add(btnRefresh);
        topBar.add(rightWrap, BorderLayout.EAST);

        tableCard.add(topBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Doctor Name", "Specialization", "Available Days", "Available Time"}, 0);
        tblDoctors = new JTable(tableModel);
        Theme.styleTable(tblDoctors);

        JScrollPane scrollPane = new JScrollPane(tblDoctors);
        scrollPane.setBorder(Theme.rounded(Theme.SKY_BLUE, 6));
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        tableCard.add(scrollPane, BorderLayout.CENTER);
        mainContainer.add(tableCard, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);

        loadDoctors();
    }

    private void loadDoctors() {
        tableModel.setRowCount(0);
        ArrayList<Doctor> doctors = doctorDAO.getAllDoctors();
        if (doctors != null) {
            for (Doctor d : doctors) {
                tableModel.addRow(new Object[]{
                        d.getDoctorId(), d.getName(), d.getSpecialization(), d.getAvailableDays(), d.getAvailableTime()
                });
            }
        }
        int count = doctors == null ? 0 : doctors.size();
        lblStatus.setText(count == 0 ? "No doctors on file yet." : count + " doctor" + (count == 1 ? "" : "s") + " currently on file.");
    }

    public static void main(String[] args) {
        Theme.initLookAndFeel();
        SwingUtilities.invokeLater(() -> new DoctorFrame().setVisible(true));
    }
}
