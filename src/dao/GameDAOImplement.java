package dao;

import dto.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameDAOImplement implements GameDAO {
    private Connection conn;
    public GameDAOImplement(Connection conn) { this.conn = conn; }

    @Override
    public Game getGame(int gameId) {
        String sql = "SELECT * FROM Games WHERE gameId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, gameId);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Game(
                            rs.getInt("GameID"),
                            rs.getString("Team1"),
                            rs.getString("Team2"),
                            rs.getDate("GameDate"),
                            rs.getInt("TotalSeats"),
                            rs.getInt("AvailableSeats")
                    );
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String loadGames = "SELECT * FROM games";
        try (PreparedStatement ps = conn.prepareStatement(loadGames)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game(
                            rs.getInt("GameID"),
                            rs.getString("Team1"),
                            rs.getString("Team2"),
                            rs.getDate("GameDate"),
                            rs.getInt("TotalSeats"),
                            rs.getInt("AvailableSeats")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }
}
