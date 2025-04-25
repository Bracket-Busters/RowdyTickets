package dao;

import dto.Game;

import java.util.List;

public interface GameDAO {
    public Game getGame(int id);
    public List<Game> getGames();
    public void addGame(Game game);
    public void updateGame(Game game);
    public void deleteGame(Game game);
}
