package dto;

import java.util.Date;

public class Game {
    private int gameID;
    private String teamOne;
    private String teamTwo;
    private Date date;
    private int totalSeats;
    private int availableSeats;

    public Game(int gameID, String teamOne, String teamTwo, Date date, int totalSeats, int availableSeats) {
        this.gameID = gameID;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.date = date;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public int getGameID() { return gameID; }
    public String getTeamOne() { return teamOne; }
    public String getTeamTwo() { return teamTwo; }
    public Date getDate() { return date; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public void setGameID(int gameID) { this.gameID = gameID; }
    public void setTeamOne(String teamOne) { this.teamOne = teamOne; }
    public void setTeamTwo(String teamTwo) { this.teamTwo = teamTwo; }
    public void setDate(Date date) { this.date = date; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
