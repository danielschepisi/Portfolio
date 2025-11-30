/**
 * This class is responsile for housing and calcuating all
 * the statistics of the game from the events that have
 * happened
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
import java.util.ArrayList;

public class Statistics
{
	private ArrayList<PlayerStats> allPlayerStats;
	private Event[] gameEvents;
	private Team[] teams;
	
	public Statistics()
	{

	}

	public Statistics(Event[] gameEvents, Team[] teams)
	{
		this();
		this.gameEvents = gameEvents;
		this.teams = teams;
		createAllPlayerStats();
	}

	/**
    * Creates all the players stats from the events
	* of the game
    */
	private final void createAllPlayerStats()
	{
		ArrayList<PlayerStats> allPlayerStats = new ArrayList<PlayerStats>();

		for (Team team : getTeams())
		{
			for (Player player : team.getPlayers())
			{
				allPlayerStats.add(new PlayerStats(player, team));
			}
		}

		if (getGameEvents() != null)
		{
			for (Event event : getGameEvents())
			{
				if (event != null)
				{
					for (PlayerStats playerStats : allPlayerStats)
					{
						PlayerKick playerKick = event.getPlayerKick();
						if (playerKick.getPlayer() == playerStats.getPlayer())
						{
							playerStats.add("Kick");

							if(playerKick.getResult().equals("Goal"))
								playerStats.add("Goal");
							if(playerKick.getResult().equals("Behind"))
								playerStats.add("Behind");
							if(playerKick.getResult().equals("Pass"))
								playerStats.add("Pass");
							if(playerKick.getResult().equals("Turnover"))
								playerStats.add("Turnover");
						}
					}

					if (event.getInjuredPlayer() != null)
					{
						for (PlayerStats playerStats : allPlayerStats)
						{
							if (playerStats.getPlayer() == event.getInjuredPlayer())
							{
								playerStats.add("isInjured");
							}
						}
					}

					for (Player player : event.getReportedPlayers())
					{
						for (PlayerStats playerStats : allPlayerStats)
						{
							if (player == playerStats.getPlayer())
							{
								playerStats.add("isReported");
							}
						}

					}
				}
			}
		}

		setAllPlayerStats(allPlayerStats);
	}

	/**
    * Gets all the playerStats
    */
	public ArrayList<PlayerStats> getAllPlayerStats()
	{
		return this.allPlayerStats;
	}

	/**
    * Returns the gameEvents array that contains all the game's
	* events
    */
	public Event[] getGameEvents()
	{
		return this.gameEvents;
	}

	/**
    * Returns a arrayList of playerStats for the specifid metric/field
    * @param    field    The field for the desired report/highlights
    */
	public ArrayList<PlayerStats> getHighlights(String field)
	{
		ArrayList<PlayerStats> team1PlayerStats = new ArrayList<PlayerStats>();
		ArrayList<PlayerStats> team2PlayerStats = new ArrayList<PlayerStats>();
		for (PlayerStats playerStats : getAllPlayerStats())
		{
			if (playerStats.getTeam() == getTeams()[0])
				team1PlayerStats.add(playerStats);
			else
				team2PlayerStats.add(playerStats);
		}

		ArrayList<PlayerStats> returnPlayerStats = new ArrayList<PlayerStats>();

		switch (field)
		{
			case "Kicks":
				returnPlayerStats.addAll(getMostKicksFrom(team1PlayerStats));
				returnPlayerStats.addAll(getMostKicksFrom(team2PlayerStats));
				break;
			case "Goals":
				returnPlayerStats.addAll(getMostGoalsFrom(team1PlayerStats));
				returnPlayerStats.addAll(getMostGoalsFrom(team2PlayerStats));
				break;
			case "Injuries":
				returnPlayerStats = getInjuriedPlayerStats();
				break;
			case "Reported":
				returnPlayerStats = getReportedPlayerStats();
				break;
		}
					
		return returnPlayerStats;
	}

	/**
    * Returns playerStats for all the injured players
    */
	private ArrayList<PlayerStats> getInjuriedPlayerStats()
	{
		ArrayList<PlayerStats> returnPlayerStats = new ArrayList<PlayerStats>();
		for (PlayerStats playerStat : getAllPlayerStats())
		{
			if (playerStat.isInjured())
				returnPlayerStats.add(playerStat);
		}

		return returnPlayerStats;
	}

	/**
    * Returns playerStats with the most goals from the data passed through
    * @param    playerStats    The playersStats to examine
    */
	private ArrayList<PlayerStats> getMostGoalsFrom(ArrayList<PlayerStats> playerStats)
	{
		ArrayList<PlayerStats> returnPlayerStats = new ArrayList<PlayerStats>();
		for (PlayerStats playerStat : playerStats)
		{
			if (returnPlayerStats.size() == 0)
				returnPlayerStats.add(playerStat);
			else
			{
				if (playerStat.getGoals() > returnPlayerStats.get(0).getGoals())
				{
					returnPlayerStats.clear();
					returnPlayerStats.add(playerStat);
				}
				else if (playerStat.getGoals() == returnPlayerStats.get(0).getGoals())
						returnPlayerStats.add(playerStat);
			}
		}

		return returnPlayerStats;
	}

	/**
    * Returns playerStats with the most kicks from the data passed through
    * @param    playerStats    The playerStats to examine
    */
	private ArrayList<PlayerStats> getMostKicksFrom(ArrayList<PlayerStats> playerStats)
	{
		ArrayList<PlayerStats> returnPlayerStats = new ArrayList<PlayerStats>();
		for (PlayerStats playerStat : playerStats)
		{
			if (returnPlayerStats.size() == 0)
				returnPlayerStats.add(playerStat);
			else
			{
				if (playerStat.getKicks() > returnPlayerStats.get(0).getKicks())
				{
					returnPlayerStats.clear();
					returnPlayerStats.add(playerStat);
				}
				else if (playerStat.getKicks() == returnPlayerStats.get(0).getKicks())
					returnPlayerStats.add(playerStat);
			}
		}

		return returnPlayerStats;
	}

	/**
    * Returns playerStats of all reported players
    */
	private ArrayList<PlayerStats> getReportedPlayerStats()
	{
		ArrayList<PlayerStats> returnPlayerStats = new ArrayList<PlayerStats>();
		for (PlayerStats playerStat : getAllPlayerStats())
		{
			if (playerStat.isReported())
				returnPlayerStats.add(playerStat);
		}

		return returnPlayerStats;
	}

	/**
    * Returns the array of teams in the game
    */
	public Team[] getTeams()
	{
		return this.teams;
	}

	/**
    * Sets the arrayList of allPllayerStats
    * @param    allPlayerStats    All player's playerStats
    */
	private void setAllPlayerStats(ArrayList<PlayerStats> allPlayerStats)
	{
		this.allPlayerStats = allPlayerStats;
	}

	/**
    * Sets the array containing all the game's events
    * @param    gameEvents    An array with the game's events
    */
	private void setGameEvents(Event[] gameEvents)
	{
		this.gameEvents = gameEvents;
	}

	/**
    * Sets the array of teams in the game
    * @param    teams    The teams in the game
    */
	private void setTeams(Team[] teams)
	{
		this.teams = teams;
	}
}