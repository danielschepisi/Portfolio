/**
 * This class is the master class of the game, it is responsible for controlling the game
 * and displaying and receiving user interaction. It defers to GameModel for the logic
 * of individual events
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
import java.util.ArrayList;

public class AFLGame
{
    public static final int EVENTS_PER_PERIOD = 80;
    public static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;
    public static final int NUMBER_OF_PERIODS = 4;

    private final GameModel gameModel;
    private final Event[] gameEvents;

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

    /**
    * Gets the array which holds all of the game's events
    */
    public Event[] getGameEvents()
    {
        return this.gameEvents;
    }

    /**
    * Gets the gameModel associated with the game
    */
    public GameModel getGameModel()
    {
        return this.gameModel;
    }

    /**
    * This is the main method which begins the program execution
    * @param    args    An array of string passed in as command line parameters
    */
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

    /**
    * Starts the game and prints the result of each event to the screen
    */
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
				Event currentEvent = getGameModel().playEvent();
				gameEvents[gameEventNumber] = currentEvent;

                Player bouncerWinner = currentEvent.getBounceWinner();
                if (bouncerWinner != null)
                    System.out.print(bouncerWinner.getPlayerName() 
                        + " " + "wins the ball from the bounce.\n\t");

                String playerName = currentEvent.getKickingPlayerName();
                String newPlayerName = "";
                String newPlayerPosition = "";
                Player receivingPlayer = currentEvent.getReceivingPlayer();
                if (receivingPlayer != null)
                {
                    newPlayerName = receivingPlayer.getPlayerName();
                    newPlayerPosition = receivingPlayer.getFieldPosition().toLowerCase();
                }

                switch (currentEvent.getResult())
                {
                    case "Goal":
                        System.out.println(playerName + " kicks a goals! 6 points!");
                        printScore();
                        break;
                    case "Behind":
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
                        //something went wrong ?? throw exception??
                    break;
                }

                Player injuredPlayer = currentEvent.getInjuredPlayer();
                if (injuredPlayer != null)
                    print("\t" + injuredPlayer.getPlayerName() + " " + "was injured.");
                    
                ArrayList<Player> reportedPlayers = currentEvent.getReportedPlayers();
                    for (Player player : reportedPlayers)
                        print("\t" + player.getPlayerName() + " " + "was reported.");

				Team teamWithTooManyInjuires = getGameModel().teamWithoutEnoughPlayers();

				if (teamWithTooManyInjuires != null && !(period == NUMBER_OF_PERIODS && i == EVENTS_PER_PERIOD))
                {
				    System.out.println("Game forfeit by " + teamWithTooManyInjuires.getTeamName());
                    continueGame = false;
					break;
                }
			}

			getGameModel().setPlayerWithPossession(null);
			getGameModel().setTeamWithPossession(null);

			period++;
		} while (period <= NUMBER_OF_PERIODS && continueGame);
    }

    /**
    * A helper method to print to the screen
    * @param    text    A string to print out
    */
    private void print(String text)
    {
        System.out.println(text);
    }

    /**
    * Prints the results of the game to the screen
    */
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

    /**
    * Prints the injured players from the game to the screen
    * @param    stats    The Statistics that contain the data
    */
    private void printInjuredPlayers(Statistics stats)
    {
        ArrayList<PlayerStats> injuries = stats.getHighlights("Injuries");
        print("Injured Players:");
        for (PlayerStats playerStats : injuries)
            print("\t" + playerStats.getName());
    }

    /**
    * Helper method that prints a line to the screen
    */
    private void printLine()
    {
        System.out.println("\t------------------------------");
    }

    /**
    * Prints the players for each team who kicked the most goals
    * @param    stats    The Statistics that contain the data
    */
    private void printMostGoals(Statistics stats)
    {
        ArrayList<PlayerStats> mostGoals = stats.getHighlights("Goals");
        print("Players with the most goals for each team:");
        for (PlayerStats playerStats : mostGoals)
        {
            print("\t" + playerStats.getName() 
                + " " + playerStats.getGoals() + " goals."
            );
        }
    }

    /**
    * Prints the players from each team who had the most kicks
    * @param    stats    The Statistics that contains the data
    */
    private void printMostKicks(Statistics stats)
    {
        ArrayList<PlayerStats> mostKicks = stats.getHighlights("Kicks");
        print("Players with the most kicks for each team:");
        for (PlayerStats playerStats : mostKicks)
        { //coupling
            print("\t" + playerStats.getName() 
                + " " + playerStats.getKicks() + " kicks."
            );
        }
    }

    /**
    * Prints a block stating the quarter and the score
    * @param    period    An integer stating the period of the game
    */
    private void printPeriodStart(int period)
    {
        printLine();
        System.out.println("\t\tQuarter: " + period + "\n");
        printLine();
        printScore();
    }

    /**
    * Prints the stats for each player
    * @param    stats    The Statistics that contains the data
    */
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

    /**
    * Prints out the players who were reported during the game
    * @param    stats    The Statistics that contains the data
    */
    private void printReportedPlayers(Statistics stats)
    {
        ArrayList<PlayerStats> reported = stats.getHighlights("Reported");
        print("Reported Players:");
        for (PlayerStats playerStats : reported)
            print("\t" + playerStats.getName());
    }

    /**
    * Prints all the required statistics
    */
    private void printStatistics()
    {
        Statistics stats = new Statistics(getGameEvents(), getGameModel().getTeams());
        printGameOverResults();
        printMostKicks(stats);
        printMostGoals(stats);
        printPlayerStats(stats);
        printInjuredPlayers(stats);
        printReportedPlayers(stats);
    }

    /**
    * Prints the game score to teh screen
    */
    private void printScore()
    {
        for(Team team : getGameModel().getTeams())
            print("\t" + getGameModel().getTeamNameForTeam(team) 
                + ": " + getGameModel().displayScoreForTeam(team));
    }
} 