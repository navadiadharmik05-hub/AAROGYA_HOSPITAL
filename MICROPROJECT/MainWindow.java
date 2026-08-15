package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class MainWindow extends JFrame {

    private JLabel lblTotalPatients, lblTotalDoctors, lblBedsOccupied;
    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final BedDAO bedDAO = new BedDAO();

    public MainWindow() {
        Theme.initLookAndFeel();

        setTitle("Aarogya Hospital Management System - Dashboard");
        setSize(880, 620);
        setMinimumSize(new Dimension(800, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JButton btnLogout = Theme.secondaryButton("Log Out");
        btnLogout.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });

        JPanel headerBar = Theme.buildHeaderBar("Aarogya Hospital Management System", "Admin Overview Dashboard", btnLogout);
        add(headerBar, BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);

        // Window event listeners to automatically refresh stats when gaining focus or opening
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                refreshDashboardStats();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                refreshDashboardStats();
            }
        });

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                refreshDashboardStats();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
    }

    private JPanel buildBody() {
        JPanel body = new JPanel(new BorderLayout(0, 16));
        body.setBackground(Theme.BG);
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 24, 24));

        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setOpaque(false);

        JLabel statsHeader = new JLabel("System Overview");
        statsHeader.setFont(Theme.FONT_HEADER);
        statsHeader.setForeground(Theme.DEEP_NAVY);
        topSection.add(statsHeader, BorderLayout.NORTH);

        JPanel stats = buildStatsRow();
        topSection.add(stats, BorderLayout.CENTER);

        body.add(topSection, BorderLayout.NORTH);

        JPanel navSection = new JPanel(new BorderLayout(0, 12));
        navSection.setOpaque(false);

        JLabel navLabel = new JLabel("Quick Management Modules");
        navLabel.setFont(Theme.FONT_HEADER);
        navLabel.setForeground(Theme.DEEP_NAVY);
        navSection.add(navLabel, BorderLayout.NORTH);

        JPanel grid = buildNavGrid();
        navSection.add(grid, BorderLayout.CENTER);

        body.add(navSection, BorderLayout.CENTER);

        return body;
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setBackground(Theme.BG);
        row.setPreferredSize(new Dimension(0, 96));

        lblTotalPatients = new JLabel("-");
        lblTotalDoctors = new JLabel("-");
        lblBedsOccupied = new JLabel("-");

        row.add(statCard("Total Patients", lblTotalPatients, Theme.SOFT_TEAL));
        row.add(statCard("Total Doctors", lblTotalDoctors, Theme.DEEP_NAVY));
        row.add(statCard("Beds Occupied", lblBedsOccupied, Theme.DANGER));

        refreshDashboardStats();
        return row;
    }

    private JPanel statCard(String label, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Theme.CLEAN_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                Theme.rounded(Theme.DEEP_NAVY, 10),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)));

        JPanel stripe = new JPanel();
        stripe.setBackground(accentColor);
        stripe.setPreferredSize(new Dimension(6, 10));

        JPanel textBox = new JPanel();
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        textBox.setBackground(Theme.CLEAN_WHITE);
        textBox.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));

        JLabel lbl = new JLabel(label);
        lbl.setFont(Theme.FONT_SUBTITLE);
        lbl.setForeground(Theme.TEXT_SECONDARY);

        valueLabel.setFont(Theme.FONT_STAT);
        valueLabel.setForeground(Theme.DEEP_NAVY);

        textBox.add(lbl);
        textBox.add(Box.createRigidArea(new Dimension(0, 4)));
        textBox.add(valueLabel);

        card.add(stripe, BorderLayout.WEST);
        card.add(textBox, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildNavGrid() {
        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setBackground(Theme.BG);

        grid.add(navTile("Patients", "Register, search, and view history", () -> openTracked(new PatientFrame())));
        grid.add(navTile("Doctors", "Directory and schedule availability", () -> openTracked(new DoctorFrame())));
        grid.add(navTile("Appointments", "Book and manage patient slots", () -> openTracked(new AppointmentFrame())));
        grid.add(navTile("Beds & Wards", "Ward allocation and patient discharge", () -> openTracked(new BedFrame())));

        return grid;
    }

    private JPanel navTile(String title, String subtitle, Runnable action) {
        JPanel tile = new JPanel(new BorderLayout());
        tile.setBackground(Theme.CLEAN_WHITE);
        Border border = BorderFactory.createCompoundBorder(
                Theme.rounded(Theme.DEEP_NAVY, 10),
                BorderFactory.createEmptyBorder(18, 18, 18, 18));
        tile.setBorder(border);
        tile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Theme.DEEP_NAVY);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(Theme.FONT_SUBTITLE);
        lblSub.setForeground(Theme.TEXT_SECONDARY);

        textPanel.add(lblTitle);
        textPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        textPanel.add(lblSub);

        JButton btnOpen = Theme.primaryButton("Open Module");
        btnOpen.setPreferredSize(new Dimension(120, 36));
        btnOpen.addActionListener(e -> action.run());

        tile.add(textPanel, BorderLayout.CENTER);
        tile.add(btnOpen, BorderLayout.EAST);

        return tile;
    }

    private void openTracked(JFrame frame) {
        setVisible(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshDashboardStats();
                setVisible(true);
            }
        });
        frame.setVisible(true);
    }

    /**
     * Dynamically fetches live counts from MySQL database for Dashboard overview.
     */
    public void refreshDashboardStats() {
        try {
            int totalPatients = patientDAO.getPatientCount();
            int totalDoctors = doctorDAO.getDoctorCount();
            int occupiedBeds = bedDAO.getOccupiedBedCount();
            int totalBeds = bedDAO.getTotalBedCount();

            // Fallback for UI if beds table is empty/unseeded
            if (totalBeds == 0) {
                totalBeds = 8;
            }

            lblTotalPatients.setText(String.valueOf(totalPatients));
            lblTotalDoctors.setText(String.valueOf(totalDoctors));
            lblBedsOccupied.setText(occupiedBeds + " / " + totalBeds);
        } catch (Exception e) {
            System.err.println("Error refreshing dashboard stats: " + e.getMessage());
            lblTotalPatients.setText("-");
            lblTotalDoctors.setText("-");
            lblBedsOccupied.setText("-");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
