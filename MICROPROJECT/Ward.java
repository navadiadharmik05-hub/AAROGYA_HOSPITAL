package org.example;

public class Ward {
    private int wardId;
    private String wardName;
    private String wardType;

    public Ward(int wardId, String wardName, String wardType) {
        this.wardId = wardId;
        this.wardName = wardName;
        this.wardType = wardType;
    }

    public int getWardId() { return wardId; }
    public String getWardName() { return wardName; }
    public String getWardType() { return wardType; }
}