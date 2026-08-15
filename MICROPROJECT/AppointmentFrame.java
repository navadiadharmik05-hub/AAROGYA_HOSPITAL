package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentFrame extends JFrame {

    private JComboBox<String> comboPatients, comboDoctors, comboDate, comboTime;
    private JTable tblAppointments;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;

    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    private ArrayList<Patient> patientList;
    private ArrayList<Doctor> doctorList;

    public AppointmentFrame() {
        setTitle("Appointments - Aarogya Hospital");
        setSize(1020, 640);
        setMinimumSize(new Dimension(880, 540));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JButton btnBack = Theme.backButton();
        btnBack.addActionListener(e -> dispose());
        JPanel headerBar = Theme.buildHeaderBar("Appointment Management", "Book patient appointments and monitor doctor availability", btnBack);
        add(headerBar, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildLeftFormPanel(), buildRightTablePanel());
        splitPane.setDividerLocation(360);
        splitPane.setResizeWeight(0.35);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        splitPane.setBackground(Theme.BG);

        add(splitPane, BorderLayout.CENTER);

        loadDropdowns();
        loadAppointments();
    }

    private JPanel buildLeftFormPanel() {
        JPanel formCard = Theme.sectionPanel(new BorderLayout(0, 14));

        JLabel title = new JLabel("Book New Appointment");
        title.setFont(Theme.FONT_HEADER);
        title.setForeground(Theme.DEEP_NAVY);
        formCard.add(title, BorderLayout.NORTH);

        JPanel fieldsGrid = new JPanel(new GridLayout(8, 1, 0, 4));
        fieldsGrid.setOpaque(false);

        comboPatients = new JComboBox<>();
        comboDoctors = new JComboBox<>();
        comboDate = new JComboBox<>();
        comboTime = new JComboBox<>();

        Theme.styleCombo(comboPatients);
        Theme.styleCombo(comboDoctors);
        Theme.styleCombo(comboDate);
        Theme.styleCombo(comboTime);

        // Populate Date Dropdown with upcoming 30 days
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 30; i++) {
            comboDate.addItem(today.plusDays(i).format(formatter));
        }

        // Populate Time Dropdown with standard slots
        String[] slots = {
                "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00",
                "12:00:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00",
                "16:30:00", "17:00:00"
        };
        for (String slot : slots) {
            comboTime.addItem(slot);
        }

        fieldsGrid.add(formLabel("Select Patient:"));
        fieldsGrid.add(comboPatients);
        fieldsGrid.add(formLabel("Select Doctor:"));
        fieldsGrid.add(comboDoctors);
        fieldsGrid.add(formLabel("Appointment Date:"));
        fieldsGrid.add(comboDate);
        fieldsGrid.add(formLabel("Appointment Time:"));
        fieldsGrid.add(comboTime);

        JButton btnBook = Theme.primaryButton("Book Appointment");
        btnBook.setPreferredSize(new Dimension(0, 38));
        btnBook.addActionListener(e -> bookAppointment());

        formCard.add(fieldsGrid, BorderLayout.CENTER);
        formCard.add(btnBook, BorderLayout.SOUTH);

        return formCard;
    }

    private JPanel buildRightTablePanel() {
        JPanel tableCard = Theme.sectionPanel(new BorderLayout(0, 10));

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionRow.setOpaque(false);

        JButton btnRefresh = Theme.secondaryButton("Refresh List");
        btnRefresh.addActionListener(e -> {
            loadDropdowns();
            loadAppointments();
        });

        actionRow.add(btnRefresh);

        lblStatus = Theme.emptyStateLabel("");

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(lblStatus, BorderLayout.WEST);
        topBar.add(actionRow, BorderLayout.EAST);

        tableCard.add(topBar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Appt ID", "Patient ID", "Doctor ID", "Date", "Time", "Status"}, 0);
        tblAppointments = new JTable(tableModel);
        Theme.styleTable(tblAppointments);

        JScrollPane scrollPane = new JScrollPane(tblAppointments);
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

    private void loadDropdowns() {
        comboPatients.removeAllItems();
        comboDoctors.removeAllItems();

        patientList = patientDAO.getAllPatients();
        if (patientList != null) {
            for (Patient p : patientList) {
                comboPatients.addItem(p.getPatientId() + " - " + p.getName());
            }
        }

        doctorList = doctorDAO.getAllDoctors();
        if (doctorList != null) {
            for (Doctor d : doctorList) {
                comboDoctors.addItem(d.getDoctorId() + " - " + d.getName());
            }
        }
    }

    private void bookAppointment() {
        if (comboPatients.getSelectedIndex() == -1 || comboDoctors.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "No patients or doctors available to book.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String date = (String) comboDate.getSelectedItem();
        String time = (String) comboTime.getSelectedItem();

        if (date == null || time == null || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid date and time.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Patient selectedPatient = patientList.get(comboPatients.getSelectedIndex());
        Doctor selectedDoctor = doctorList.get(comboDoctors.getSelectedIndex());

        if (appointmentDAO.isSlotTaken(selectedDoctor.getDoctorId(), date, time)) {
            JOptionPane.showMessageDialog(this, "Dr. " + selectedDoctor.getName() + " already has an appointment at that date and time.", "Slot Unavailable", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int newApptId = appointmentDAO.getNextApptId();
        Appointment appt = new Appointment(newApptId, selectedPatient.getPatientId(), selectedDoctor.getDoctorId(), date, time, "Scheduled");

        boolean success = appointmentDAO.addAppointment(appt);
        if (success) {
            JOptionPane.showMessageDialog(this, "Appointment booked successfully (ID: " + newApptId + ")");
            loadAppointments();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to book appointment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);
        ArrayList<Appointment> appointments = appointmentDAO.getAllAppointments();
        if (appointments != null) {
            for (Appointment a : appointments) {
                tableModel.addRow(new Object[]{
                        a.getApptId(), a.getPatientId(), a.getDoctorId(), a.getApptDate(), a.getApptTime(), a.getStatus()
                });
            }
        }
        int count = appointments == null ? 0 : appointments.size();
        lblStatus.setText(count == 0 ? "No appointments booked yet." : count + " appointment" + (count == 1 ? "" : "s") + " on record.");
    }

    public static void main(String[] args) {
        Theme.initLookAndFeel();
        SwingUtilities.invokeLater(() -> new AppointmentFrame().setVisible(true));
    }
}
