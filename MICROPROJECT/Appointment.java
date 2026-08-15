package org.example;

public class Appointment
{
    private int apptId;
    private int patientId;
    private int doctorId;
    private String apptDate;
    private String apptTime;
    private String status;

    public Appointment(int apptId, int patientId, int doctorId, String apptDate, String apptTime, String status)
    {
        this.apptId = apptId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.status = status;
    }

    public int getApptId() { return apptId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getApptDate() { return apptDate; }
    public String getApptTime() { return apptTime; }
    public String getStatus() { return status; }
}
