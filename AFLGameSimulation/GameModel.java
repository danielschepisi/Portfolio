import java.util.ArrayList;
import java.util.Collections;

public class GameModel
{
    private final double REPORTED_CHANCE_PER_GAME = 0.01;
    private final double INJURED_CHANCE_PER_EVENT = 0.02;

    private Team[] teams;
	private Player playerWithPossession;
	private Team teamWithPossession;

    public GameModel()
    {

    }

    public GameModel(int starPlayers)
    {
    	this();
		createTeamsFromFile(starPlayers); //******* not sure about this constructor combo
    }

	public Player getPlayerWithPossession()
	{
		return this.playerWithPossession;
	}

	public String getTeamNameForTeam(Team team)
	{
		int index = (team == getTeams()[0]) ? 0 : 1;
		return getTeams()[index].getTeamName();
	}

	public Team getTeamWithPossession()
	{
		return this.teamWithPossession;
	}

	public void setPlayerWithPossession(Player player)
	{
		this.playerWithPossession = player;
	}

	public void setTeamWithPossession(Team team)
	{
		this.teamWithPossession = team;
	}

	public void setTeams(Team[] teams)
    {
        this.teams = teams;
    }

    public Team[] getTeams()
    {
        return this.teams;
    }

    public void setTeams(Team teamOne, Team teamTwo)
    {
        Team[] teamArray = {teamOne, teamTwo};
        setTeams(teamArray);
    }

    private double chanceOfBeingReportedPerEvent()
    {
        return REPORTED_CHANCE_PER_GAME / AFLGame.EVENTS_PER_PERIOD / 4; //FIX THIS EVENTS_PER_PERIOD
    }

	private void swapTeams() //maybe call turnover
    {
        if (getTeams()[0] == getTeamWithPossession())
            setTeamWithPossession(teams[1]);
        else
            setTeamWithPossession(teams[0]);
    }

	private final void createTeamsFromFile(int starPlayers)
    {
        FileIO fileio = new FileIO();

        //need to catch error around here and terminate if Exception
        //maybe we need this class to throw an Exception so that we can catch it here
        //maybe we can put a throws in teh catch in fileio?
        String teamAText = fileio.readFile("teamA.txt");
        String teamBText = fileio.readFile("teamB.txt");

        setTeams(new Team(teamAText, starPlayers), new Team(teamBText, starPlayers)); //condense?
    }

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

	private String currentPlayersName()
	{
		return getPlayerWithPossession().getPlayerName();
	}

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
				//assign goal
				getTeamWithPossession().scoreGoal();
				setPlayerWithPossession(null);
				break;
			case "Behind":
				// assign behind
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

	public String displayScoreForTeam(Team team)
	{
		int index = (team == getTeams()[0]) ? 0 : 1;
		return getTeams()[index].displayScore();
	}


	private void reportPlayers(Event currentEvent)
	{
		for(Team team : getTeams())
		{
			for(Player player : team.getActivePlayers())
			{
				if(Math.random() < chanceOfBeingReportedPerEvent())
				{
					player.setIsReported(true); //maybe chagne to report
					currentEvent.addReportedPlayer(player);
				}
			}
		}
	}

    private Team pickRandomTeam()
    {
    	return (Math.random() < 0.5) ? teams[0] : teams[1];
    }

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
}