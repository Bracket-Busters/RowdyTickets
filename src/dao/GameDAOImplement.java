package dao;

import dto.Game;

import java.sql.Connection;
import java.util.List;

public class GameDAOImplement implements GameDAO {
    private Connection conn;
    public GameDAOImplement(Connection conn) { this.conn = conn; }

    @Override
    public Game getGame(int id) {
        return null;
    }

    @Override
    public List<Game> getGames() {
        return List.of();
    }

    @Override
    public void addGame(Game game) {

    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void deleteGame(Game game) {

    }
}
