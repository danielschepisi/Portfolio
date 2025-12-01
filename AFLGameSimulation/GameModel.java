/**
 * This class contains the logic of the game. It is responsible for reading the team
 * data in and creating the teams/players. Running the logic of individual events and
 * then writing the team data to file.
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
import java.util.ArrayList;
import java.util.Collections;

public class GameModel
{
    private final double INJURED_CHANCE_PER_EVENT = 0.02;
	private final double REPORTED_CHANCE_PER_GAME = 0.01;

    private Player playerWithPossession;
	private Team[] teams;
	private Team teamWithPossession;

    public GameModel()
    {

    }

    public GameModel(int starPlayers)
    {
    	this();
		createTeamsFromFile(starPlayers);
    }

	/**
    * Calculates the odds of a player being reported during an event
    */
    private double chanceOfBeingReportedPerEvent()
    {
        return REPORTED_CHANCE_PER_GAME / AFLGame.EVENTS_PER_PERIOD / AFLGame.NUMBER_OF_PERIODS;
    }

	/**
    * Creates the teams from reaading in data from file
    * @param    starPlayers    The number of star players each team has
    */
	private final void createTeamsFromFile(int starPlayers)
    {
        FileIO fileio = new FileIO();
		try
		{
			String teamAText = fileio.readFile("teamA.txt");
			String teamBText = fileio.readFile("teamB.txt");
			setTeams(new Team(teamAText, starPlayers), new Team(teamBText, starPlayers)); //condense?
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("There was an error reading the file: " + e.getMessage());
			System.out.println("Exiting game.");
			System.exit(1);
		}
    }

	/**
    * Returns the name of the player who current has possession
    */
	private String currentPlayersName()
	{
		return getPlayerWithPossession().getPlayerName();
	}

	/**
    * Returns the score for the team as a string
    * @param    team    The team whose score is wanted
    */
	public String displayScoreForTeam(Team team)
	{
		int index = (team == getTeams()[0]) ? 0 : 1;
		return getTeams()[index].displayScore();
	}

	/**
    * Returns the player with possession of the ball
    */
	public Player getPlayerWithPossession()
	{
		return this.playerWithPossession;
	}

	/**
    * Returns the array of teams in the game
    */
    public Team[] getTeams()
    {
        return this.teams;
    }

	/**
    * Returns the name of the team
    * @param    team    The team whose name is wanted
    */
	public String getTeamNameForTeam(Team team)
	{
		int index = (team == getTeams()[0]) ? 0 : 1;
		return getTeams()[index].getTeamName();
	}

	/**
    * Returns the team with possession of the ball
    */
	public Team getTeamWithPossession()
	{
		return this.teamWithPossession;
	}

	/**
    * Determines and returns the winning team
    */
	public Team getWinningTeam() //clean up
	{
		Team winningTeam = null;
		Team forfeitTeam = teamWithoutEnoughPlayers();
		if (forfeitTeam != null)
		{
			if (forfeitTeam == getTeams()[0])
				winningTeam = getTeams()[1];
			if (forfeitTeam == getTeams()[1])
				winningTeam = getTeams()[0];
		}
		else
		{
			if (getTeams()[0].getPoints() > getTeams()[1].getPoints())
				winningTeam = getTeams()[0];
			if (getTeams()[1].getPoints() > getTeams()[0].getPoints())
				winningTeam = getTeams()[1];
		}

		return winningTeam;
	}

	/**
    * Determines if a player should be injured during an event,
	* injures the player and records it on the event
    * @param    currentEvent    The event on which to record the injury
    */
	private void injurePlayers(Event currentEvent)
	{
		if(Math.random() < INJURED_CHANCE_PER_EVENT)
		{
			Team team = pickRandomTeam();
			ArrayList<Player> activePlayers = team.getActivePlayers();
			if(activePlayers.size() > 0)
			{
				Collections.shuffle(activePlayers);
				Player injuredPlayer = activePlayers.get(0);
				injuredPlayer.injure();
				team.replacePlayer(injuredPlayer);
				currentEvent.setInjuredPlayer(injuredPlayer);
			}
		}
	}

	/**
    * Selects and returns a team at random
    */
    private Team pickRandomTeam()
    {
    	return (Math.random() < 0.5) ? teams[0] : teams[1];
    }

	/**
    * Creates, plays out and returns an event
    */
	public Event playEvent()
	{
		Event currentEvent = new Event();
		if (getPlayerWithPossession() == null)
		{
			setTeamWithPossession(pickRandomTeam()); 
			setPlayerWithPossession(
				getTeamWithPossession().chooseRandomPlayerFromPosition("Midfielder")
			);
			currentEvent.setBounceWinner(getPlayerWithPossession());
		}

		PlayerKick playerKick = getPlayerWithPossession().kick();
		currentEvent.setPlayerKick(playerKick);

		switch (playerKick.getResult()) //watch for validation on assigning from teams
		{
			case "Goal":
				getTeamWithPossession().scoreGoal();
				setPlayerWithPossession(null);
				break;
			case "Behind":
				getTeamWithPossession().scoreBehind();
				swapTeams();
				setPlayerWithPossession(
					getTeamWithPossession().chooseRandomPlayerFromPosition("Defender")
				);
				break;
			case "Pass":
				setPlayerWithPossession(
					getTeamWithPossession()
						.chooseRandomPlayerFromPositionExcluding(
							playerKick.getToFieldPosition(), getPlayerWithPossession()
						)
				);
				currentEvent.setReceivingPlayer(getPlayerWithPossession());
				break;
			case "Turnover":
				swapTeams();
				setPlayerWithPossession(
					getTeamWithPossession()
						.chooseRandomPlayerFromPosition(playerKick.getToFieldPosition())
				);
				currentEvent.setReceivingPlayer(getPlayerWithPossession());
				break;
			default:
				//something went wrong
				break;     
		}

		injurePlayers(currentEvent);
		reportPlayers(currentEvent);

		return currentEvent;
	}

	/**
    * Determines if a player should be reported during an event,
	* reports the palyer and records it on the event
    * @param    currentEvent    The event on which to record 
	* the reported player
    */
	private void reportPlayers(Event currentEvent)
	{
		for(Team team : getTeams())
		{
			for(Player player : team.getActivePlayers())
			{
				if(Math.random() < chanceOfBeingReportedPerEvent())
				{
					player.setIsReported(true);
					currentEvent.addReportedPlayer(player);
				}
			}
		}
	}

	/**
    * Writes the player data to file
    */
	public void saveStatsToFile()
	{
        FileIO fileio = new FileIO();
        for(Team team : teams)
        {
			String teamData = team.getTeamName();
			for(Player player : team.getPlayers())
			{
				teamData += "\n" + player.getPlayerName() + "," + player.getFieldPosition() + "," + player.getSeasonGoals();
			} 

            String fileName = team.getTeamName().replaceAll("\\s", "") + "Updated.txt";
            fileio.writeFile(teamData, fileName); //catch exceptions
        }
	}

	/**
    * Sets the player with possession
    * @param    player    The player who now has possession
    */
	public void setPlayerWithPossession(Player player)
	{
		this.playerWithPossession = player;
	}

	/**
    * Sets the teams of the game
    * @param    teams    The teams involved in the game
    */
	public void setTeams(Team[] teams)
    {
        this.teams = teams;
    }

	/**
    * A helper method to set the teams of the game
    * @param    teamOne    The first team playing
    * @param    teamTwo    The second team playing
    */
    public void setTeams(Team teamOne, Team teamTwo)
    {
        Team[] teamArray = {teamOne, teamTwo};
        setTeams(teamArray);
    }

	/**
    * Sets the team with possession of the ball
    * @param    team    The team with possession
    */
	public void setTeamWithPossession(Team team)
	{
		this.teamWithPossession = team;
	}

	/**
    * Swaps the team with possession of the ball
    */
	private void swapTeams()
    {
        if (getTeams()[0] == getTeamWithPossession())
            setTeamWithPossession(teams[1]);
        else
            setTeamWithPossession(teams[0]);
    }

	/**
    * Returns the first team without enough players if any
    */
	public Team teamWithoutEnoughPlayers()
	{
		for(Team team : getTeams())
		{
			if(team.getActivePlayers().size() < 18)
			{
				return team;
			}
		}

		return null;
	}
}