package corm.kenny.jack.dao;

import java.util.List;

import corm.kenny.jack.types.Matchup;

public interface MatchupDao {

	void saveMatchup(Matchup matchup);

	void deleteMatchup(String matchup);
	
	List<Matchup> listMatchups();
	
	Matchup getMatchupById(String id);

}