package dao;

import dto.Game;

import java.util.List;

public interface GameDAO {
    public Game getGame(int id);
    public List<Game> getAllGames();
}
