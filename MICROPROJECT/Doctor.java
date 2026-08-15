package org.example;

public class Doctor
{
    private int doctorId;
    private String name;
    private String specialization;
    private String availableDays;
    private String availableTime;

    public Doctor(int doctorId,String name,String specialization,String availableDays,String availableTime)
    {
        this.doctorId=doctorId;
        this.name=name;
        this.specialization=specialization;
        this.availableDays=availableDays;
        this.availableTime=availableTime;
    }

    public int getDoctorId() {return doctorId;}
    public String getName() {return name;}
    public String getSpecialization(){return specialization;}
    public String getAvailableDays(){return availableDays;}
    public String getAvailableTime(){return availableTime;}

}
