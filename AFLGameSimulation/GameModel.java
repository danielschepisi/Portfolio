import java.util.ArrayList;
import java.util.Collections;


public class GameModel
{
    private final double REPORTED_CHANCE_PER_GAME = 0.01;
    private final double INJURED_CHANCE_PER_EVENT = 0.02;
	private final int EVENTS_PER_QUARTER = 10;
	// private final int EVENTS_PER_QUARTER = 80;

    private Team[] teams;
	private StringBuffer gamePlayNarrative;

	private Player playerWithPossession;
	private Team teamWithPossession;

    public GameModel()
    {
		this.gamePlayNarrative = new StringBuffer();
    }

    public GameModel(int starPlayers)
    {
    	this();
		createTeams(starPlayers); //******* not sure about this constructor combo
    }

	private Player getPlayerWithPossession()
	{
		return this.playerWithPossession;
	}

	private Team getTeamWithPossession()
	{
		return this.teamWithPossession;
	}

	private void setPlayerWithPossession(Player player)
	{
		this.playerWithPossession = player;
	}

	private void setTeamWithPossession(Team team)
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
        return REPORTED_CHANCE_PER_GAME / EVENTS_PER_QUARTER / 4;
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

	public String playGame()
	{
		int quarter = 1;
		boolean continueGame = true;

		do 
        {
			//could all this quarter stuff go to. a method
			gamePlayNarrative.append("-------------------------\n"); //will have to changea ll these to get
			gamePlayNarrative.append("Quarter: " + quarter + "\n");
			gamePlayNarrative.append("-------------------------\n");
			gamePlayNarrative.append("ADD SCORE XXXXXXXXXXXXXXXX\n"); //*******
			gamePlayNarrative.append("ADD SCORE XXXXXXXXXXXXXXXX\n"); //*******
					
			for (int i = 1 ; i <= EVENTS_PER_QUARTER ; i++)
			{
				gamePlayNarrative.append("#" + i + "\t");
				playEvent();
				continueGame = enoughUninjuredPlayers();
				if (!continueGame)
					break;
			}

			setPlayerWithPossession(null);
			setTeamWithPossession(null);

			quarter++;
		} while (quarter <= 4 && continueGame);

		return gamePlayNarrative.toString();
	}

	private boolean enoughUninjuredPlayers() //name change, check injured players
	{
		for(Team team : getTeams())
		{
			if(team.getActivePlayers().size() < 18)
			{
				gamePlayNarrative.append("Game forfeit!\n");
				return false;
			}
		}

		return true;
	}

	private String currentPlayersName()
	{
		return getPlayerWithPossession().getPlayerName();
	}

	private void playEvent()
	{
		if (getPlayerWithPossession() == null)
		{
			setTeamWithPossession(pickRandomTeam()); 
			setPlayerWithPossession(
				getTeamWithPossession().chooseRandomPlayerFromPosition("Midfielder")
			);
			gamePlayNarrative
			.append(currentPlayersName() + "\t" + "Wins the ball from the bounce"+ "\n\t");
		}

		String[] eventOutcome = getPlayerWithPossession().kick();

		// gamePlayNarrative.append(player.getPlayerName() + "\t" + eventOutcome[0] + eventOutcome[1] + "\n"); //too long? //must deal with pritn statements. this has nulls

		switch (eventOutcome[0]) //watch for validation on assigning from teams
		{
			case "Goal":
				gamePlayNarrative.append(currentPlayersName() + "\t" + "Scores a goal!"+ "\n");
				//assign goal
				getTeamWithPossession().scoreGoal();
				// this.printScore();
				setPlayerWithPossession(null);
				break;
			case "Behind":
				gamePlayNarrative.append(currentPlayersName() + "\t" + "Scores a behind."+ "\n");
				// assign behind
				getTeamWithPossession().scoreBehind();
				// this.printScore();
				swapTeams();
				setPlayerWithPossession(
					getTeamWithPossession().chooseRandomPlayerFromPosition("Defender")
				);
				break;
			case "Pass":
				gamePlayNarrative
				.append(currentPlayersName() + "\t" + "Passes to " + eventOutcome[1].toLowerCase());
				setPlayerWithPossession(
					getTeamWithPossession()
						.chooseRandomPlayerFromPositionExcluding(eventOutcome[1], getPlayerWithPossession())
				);
				gamePlayNarrative.append(" " + currentPlayersName() + "\n");
				break;
			case "Turnover":
				gamePlayNarrative
				.append(currentPlayersName() + "\t" + "Turns over to " + eventOutcome[1].toLowerCase());
				swapTeams();
				setPlayerWithPossession(
					getTeamWithPossession().chooseRandomPlayerFromPosition(eventOutcome[1]));
				gamePlayNarrative.append(" " + currentPlayersName() + "\n");
				break;
			default:
				//something went wrong
				break;     
		}

		injurePlayers();
		reportPlayers();
	}

	private void injurePlayers()
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
				gamePlayNarrative.append("\t" + injuredPlayer.getPlayerName() + " was injured.\n");
			}
		}
	}

	private void reportPlayers()
	{
		for(Team team : getTeams())
		{
			for(Player player : team.getActivePlayers())
			{
				if(Math.random() < chanceOfBeingReportedPerEvent())
				{
					player.setIsReported(true); //maybe chagne to report
					gamePlayNarrative.append("\t" + player.getPlayerName() + " was reported.\n");
				}
			}
		}
	}

    private Team pickRandomTeam()
    {
    	return (Math.random() < 0.5) ? teams[0] : teams[1];
    }
}