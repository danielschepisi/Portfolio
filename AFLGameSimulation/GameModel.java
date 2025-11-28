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

    public GameModel()
    {
		this.gamePlayNarrative = new StringBuffer();
    }

    public GameModel(int starPlayers)
    {
    	this();
		createTeams(starPlayers);
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

	private void swapTeamsFrom(Team team) //maybe call turnover
    {
        if (getTeams()[0] == team)
            team = teams[1];
        else
            team = teams[0];
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
			Player playerWithPossession = null;
			Team teamWithPossession = null;

			gamePlayNarrative.append("-------------------------\n"); //will have to changea ll these to get
			gamePlayNarrative.append("Quarter: " + quarter + "\n");
			gamePlayNarrative.append("-------------------------\n");
			gamePlayNarrative.append("ADD SCORE XXXXXXXXXXXXXXXX\n"); //*******
			gamePlayNarrative.append("ADD SCORE XXXXXXXXXXXXXXXX\n"); //*******
					
			for (int i = 1 ; i <= EVENTS_PER_QUARTER ; i++)
			{
				gamePlayNarrative.append("#" + i + "\t");
				playEvent(playerWithPossession, teamWithPossession);
				continueGame = enoughUninjuredPlayers();
				if (!continueGame)
					break;
			}

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

	private void playEvent(Player player, Team team)
	{
		if (player == null)
		{
			team = pickRandomTeam(); 
			player = team.chooseRandomPlayerFromPosition("Midfielder");
		}

		String eventOutcome = player.kick();

		gamePlayNarrative.append(player.getPlayerName() + "\t" + eventOutcome + "\n"); //too long?

		switch (eventOutcome) //watch for validation on assigning from teams
		{
			case "Goal":
				//assign goal
				team.scoreGoal();
				// this.printScore();
				player = null;
				break;
			case "Behind":
				// assign behind
				team.scoreBehind();
				// this.printScore();
				swapTeamsFrom(team);
				player = team.chooseRandomPlayerFromPosition("Defender");;
				break;
			case "Pass Forward":
				player = team.chooseRandomPlayerFromPositionExcluding("Forward", player);
				break;
			case "Pass Midfielder":
				player = team.chooseRandomPlayerFromPositionExcluding("Midfielder", player);
				break;
			case "Turnover Forward":
				swapTeamsFrom(team);
				player = team.chooseRandomPlayerFromPosition("Forward");
				break;
			case "Turnover Midfielder":
				swapTeamsFrom(team);
				player = team.chooseRandomPlayerFromPosition("Midfielder");
				break;
			case "Turnover Defender":
				swapTeamsFrom(team);
				player = team.chooseRandomPlayerFromPosition("Defender");
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
				gamePlayNarrative.append(injuredPlayer.getPlayerName() + " was injured.\n");
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
					gamePlayNarrative.append(player.getPlayerName() + " was reported.\n");
				}
			}
		}
	}

    private Team pickRandomTeam()
    {
    	return (Math.random() < 0.5) ? teams[0] : teams[1];
    }
}