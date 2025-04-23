package dto;

public class Seats {
    private int seatID;
    private String seatNumber;
    private String seatRow;
    private String availabilityType;

    public Seats(int seatID, String seatNumber, String seatRow, String availabilityType) {
        this.seatID = seatID;
        this.seatNumber = seatNumber;
        this.seatRow = seatRow;
        this.availabilityType = availabilityType;
    }

    public int getSeatID() {
        return seatID;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public String getAvailabilityType() {
        return availabilityType;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public void setAvailabilityType(String availabilityType) {
        this.availabilityType = availabilityType;
    }
}
