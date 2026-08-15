package org.example;

public class Bed {
    private int bedId;
    private int wardId;
    private String bedNumber;
    private String status;
    private Integer patientId; // Can be null when empty

    // Constructor with 4 parameters (for unassigned beds)
    public Bed(int bedId, int wardId, String bedNumber, String status) {
        this(bedId, wardId, bedNumber, status, null);
    }

    // Constructor with 5 parameters (includes patientId)
    public Bed(int bedId, int wardId, String bedNumber, String status, Integer patientId) {
        this.bedId = bedId;
        this.wardId = wardId;
        this.bedNumber = bedNumber;
        this.status = status;
        this.patientId = patientId;
    }

    public int getBedId() { return bedId; }
    public int getWardId() { return wardId; }
    public String getBedNumber() { return bedNumber; }
    public String getStatus() { return status; }
    public Integer getPatientId() { return patientId; }

    public void setStatus(String status) { this.status = status; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
}