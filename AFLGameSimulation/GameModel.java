import java.util.ArrayList;
import java.util.Collections;

public class GameModel
{
    // private final double REPORTED_CHANCE_PER_GAME = 0.01;
    private final double REPORTED_CHANCE_PER_GAME = 0.51;
    private final double INJURED_CHANCE_PER_EVENT = 0.02;
    // private final double INJURED_CHANCE_PER_EVENT = 0.02;


    private Team[] teams;
	// private StringBuffer gamePlayNarrative;

	private Player playerWithPossession;
	private Team teamWithPossession;

    public GameModel()
    {
		// this.gamePlayNarrative = new StringBuffer();
    }

    public GameModel(int starPlayers)
    {
    	this();
		createTeams(starPlayers); //******* not sure about this constructor combo
    }

	public Player getPlayerWithPossession()
	{
		return this.playerWithPossession;
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

	private final void createTeams(int starPlayers)
    {
        FileIO fileio = new FileIO();

        //need to catch error around here and terminate if Exception
        //maybe we need this class to throw an Exception so that we can catch it here
        //maybe we can put a throws in teh catch in fileio?
        String teamAText = fileio.readFile("teamA.txt");
        String teamBText = fileio.readFile("teamB.txt");

        setTeams(new Team(teamAText, starPlayers), new Team(teamBText, starPlayers)); //condense?
    }

	public Team teamWithoutEnoughPlayers() //name change, check injured players
	{
		for(Team team : getTeams())
		{
			if(team.getActivePlayers().size() < 18)
			{
				// return false;
				return team;
			}
		}

		return null;
	}

	public Team getWinningTeam() //clean up
	{
		Team winningTeam = null;
		Team forfeitTeam = teamWithoutEnoughPlayers();
		if (forfeitTeam == getTeams()[0])
			winningTeam = getTeams()[1];
		if (forfeitTeam == getTeams()[1])
			winningTeam = getTeams()[0];

		if (getTeams()[0].getScore().getPoints() > getTeams()[1].getScore().getPoints()) //coupling
			winningTeam = getTeams()[0];
		if (getTeams()[1].getScore().getPoints() > getTeams()[1].getScore().getPoints())
			winningTeam = getTeams()[1];

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
}