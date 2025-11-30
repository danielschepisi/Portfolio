import java.util.ArrayList;

public class AFLGame
{
    public static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;
    private final int NUMBER_OF_PERIODS = 4; //??? static
    public static final int EVENTS_PER_PERIOD = 80;

    private GameModel gameModel;
    private Event[] gameEvents;

    public AFLGame()
    {
        this.gameModel = new GameModel();
        this.gameEvents = new Event[NUMBER_OF_PERIODS * EVENTS_PER_PERIOD];
    }

    public AFLGame(GameModel gameModel)
    {
        this.gameModel = gameModel;
        this.gameEvents = new Event[NUMBER_OF_PERIODS * EVENTS_PER_PERIOD];
    }

    public GameModel getGameModel()
    {
        return this.gameModel;
    }

    public void setGameModel(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }

    public static void main(String[] args)
    {
        Input consoleReader = new Input();
        Validator validator = new Validator();
        int numberOfStarPlayers = 0;
        boolean proceed = false;
        do
        {
            try
            {
                numberOfStarPlayers = consoleReader.askForIntegerInput(
                    "Enter number of star players for each team:");
                if (validator.intWithinRange(
                        numberOfStarPlayers, 0, AFLGame.MAX_NUMBER_OF_STAR_PLAYERS
                    ))
                    proceed = true;
                else
                    System.out.println(
                        "Please enter an integer from 0 - " + AFLGame.MAX_NUMBER_OF_STAR_PLAYERS
                    );
            }
            catch (Exception e)
            {
                System.out.println(
                    "Please enter an integer from 0 - " + AFLGame.MAX_NUMBER_OF_STAR_PLAYERS
                );
            }
        } while (proceed == false);

        AFLGame game = new AFLGame(new GameModel(numberOfStarPlayers));

        game.playGame();
        game.printStatistics();
        game.getGameModel().saveStatsToFile();
    }

    private void printLine()
    {
        System.out.println("\t------------------------------");
    }

    private void print(String text)
    {
        System.out.println(text);
    }

    private void printGameOverResults()
    {
        printLine();
        print("\t\tGame Over");
        Team winner = getGameModel().getWinningTeam();
        if (winner == null)
            print("\tThe game was a draw!");
        else
            print("\tThe winner is " + winner.getTeamName());
        printScore();
        printLine();
    }

    private void printMostKicks(Statistics stats)
    {
        ArrayList<PlayerStats> mostKicks = stats.getMost("Kicks");
        print("Players with the most kicks:");
        for (PlayerStats playerStats : mostKicks)
        { //coupling
            print("\t" + playerStats.getName() 
                + " " + playerStats.getKicks() + " kicks."
            );
        }
    }

    private void printMostGoals(Statistics stats)
    {
        ArrayList<PlayerStats> mostGoals = stats.getMost("Goals");
        print("Players with the most goals:");
        for (PlayerStats playerStats : mostGoals) //coupling
        {
            print("\t" + playerStats.getName() 
                + " " + playerStats.getKicks() + " goals."
            );
        }
    }

    private void printPlayerStats(Statistics stats)
    {
        print("Individual Player Stats:");
        ArrayList<PlayerStats> allPlayerStats = stats.getAllPlayerStats();
        for(PlayerStats playerStats : allPlayerStats)
        {
            String playerName = playerStats.getName();
            String injured = "was" + (playerStats.isInjured() ? " " : "n't ") + "injured,";
            String reported = "was" + (playerStats.isReported() ? " " : "n't ") + "reported,";
            String kicks = playerStats.getKicks() 
                + (playerStats.getKicks() == 1 ? " kick," : " kicks,");
            String goals = playerStats.getGoals() 
                + (playerStats.getGoals() == 1 ? " goal" : " goals");
            String disposals = "and " 
                + String.format("%.2f", playerStats.getEffectiveDisposals()) 
                + "% effective disposals.";

            String formattedData = String.format("%-10s %-15s %-16s %-9s %-8s %-31s", 
                playerName, injured, reported, kicks, goals, disposals);
            print("\t" + formattedData);
        }
    }

    private void printStatistics()
    {
        Statistics stats = new Statistics(getGameEvents(), getGameModel().getTeams());
        printGameOverResults();
        printMostKicks(stats);
        printMostGoals(stats);
        printPlayerStats(stats);

        print("Injured Players:");
        ArrayList<Player> injuredPlayers = stats.getInjuredPlayers();
        for (Player player : injuredPlayers) //coupling
            print("\t" + player.getPlayerName());

        print("Reported Players:");
        ArrayList<Player> reportedPlayers = stats.getReportedPlayers();
        for (Player player : reportedPlayers) //coupling
            print("\t" + player.getPlayerName());

    }

    public Event[] getGameEvents()
    {
        return this.gameEvents;
    }

    private void printPeriodStart(int period)
    {
        printLine();
        System.out.println("\t\tQuarter: " + period + "\n");
        printLine();
        printScore();
    }

    private void playGame()
    {
        int period = 1;
		boolean continueGame = true;

		do 
        {
			printPeriodStart(period);
					
			for (int i = 1 ; i <= EVENTS_PER_PERIOD ; i++)
			{
                System.out.print("#" + i + "\t");
                int gameEventNumber = (i-1) + ((period - 1) * EVENTS_PER_PERIOD);
				Event currentEvent = playEvent();
				gameEvents[gameEventNumber] = currentEvent;

                Player bouncerWinner = currentEvent.getBounceWinner();
                if (bouncerWinner != null)
                    System.out.print(bouncerWinner.getPlayerName() + " " + "wins the ball from the bounce.\n\t"); //strong coupling w player name

                String playerName = currentEvent.getPlayerKick().getPlayer().getPlayerName(); //cpupling

                String newPlayerName = "";
                String newPlayerPosition = "";
                Player receivingPlayer = currentEvent.getReceivingPlayer();
                if (receivingPlayer != null)
                {
                    newPlayerName = receivingPlayer.getPlayerName(); //cpupling and bit messy all this
                    newPlayerPosition = receivingPlayer.getFieldPosition().toLowerCase();
                }

                switch (currentEvent.getPlayerKick().getResult()) //styrong coupling
                {
                    case "Goal":
                        //assign goal
                        System.out.println(playerName + " kicks a goals! 6 points!");
                        printScore();
                        break;
                    case "Behind":
                        //assign behind
                        System.out.println(playerName + " kicks a behind, 1 point.");
                        printScore();
                        break;
                    case "Pass":
                        System.out.println(playerName + " passes the ball to " + newPlayerPosition + " " + newPlayerName + ".");
                        break;
                    case "Turnover":
                        System.out.println(playerName + " turns the ball over to " + newPlayerPosition + " " + newPlayerName + ".");
                        break;
                    default:
                        //something went wrong
                    break;
                }

                Player injuredPlayer = currentEvent.getInjuredPlayer();
                if (injuredPlayer != null)
                    System.out.println("\t" + injuredPlayer.getPlayerName() + " " + "was injured."); //strong coupling w player name
                    
                ArrayList<Player> reportedPlayers = currentEvent.getReportedPlayers();
                // if (reportedPlayers != null)
                // {
                    for (Player player : reportedPlayers)
                    {
                        System.out.println("\t" + player.getPlayerName() + " " + "was reported."); //strong coupling w player name
                    }
                // }


				Team teamWithTooManyInjuires = getGameModel().teamWithoutEnoughPlayers();

				if (teamWithTooManyInjuires != null) //and not last event!!!
                {
				    System.out.println("Game forfeit by " + teamWithTooManyInjuires.getTeamName());
                    continueGame = false;
					break;
                }
			}

			getGameModel().setPlayerWithPossession(null); //review
			getGameModel().setTeamWithPossession(null); //review

			period++;
		} while (period <= NUMBER_OF_PERIODS && continueGame);
    }

    private Event playEvent()
    {
        return getGameModel().playEvent();
    }


    private void printScore()
    {
        // reduce coupling at least
        print("\t" + getGameModel().getTeams()[0].getTeamName() + ": " + getGameModel().getTeams()[0].getScore().display());
        print("\t" + getGameModel().getTeams()[1].getTeamName() + ": " + getGameModel().getTeams()[1].displayScore());
    }


} 