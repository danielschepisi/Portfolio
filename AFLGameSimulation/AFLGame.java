import java.util.ArrayList;

public class AFLGame
{
    public static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;
    private final int NUMBER_OF_PERIODS = 4; //??? static
    public static final int EVENTS_PER_PERIOD = 1;
	// private final int EVENTS_PER_PERIOD = 80;

    private GameModel gameModel;
    // private Score gameScore;
    private Event[] gameEvents;

    public AFLGame()
    {
        this.gameModel = new GameModel();
        // this.gameScore = new Score();
        this.gameEvents = new Event[NUMBER_OF_PERIODS * EVENTS_PER_PERIOD];
    }

    public AFLGame(GameModel gameModel)
    {
        this.gameModel = gameModel;
        // this.gameScore = new Score();
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
                numberOfStarPlayers = consoleReader.askForIntegerInput("Enter number of star players for each team:"); //too long
                if (validator.intWithinRange(
                        numberOfStarPlayers, 0, AFLGame.MAX_NUMBER_OF_STAR_PLAYERS
                    )) //??code elsewhere
                    proceed = true;
                else
                    System.out.println(
                        "Please enter an integer from 0 - " + AFLGame.MAX_NUMBER_OF_STAR_PLAYERS);
            }
            catch (Exception e)
            {
                System.out.println("Please enter an integer from 0 - " + AFLGame.MAX_NUMBER_OF_STAR_PLAYERS);
            }
        } while (proceed == false);

        AFLGame game = new AFLGame(new GameModel(numberOfStarPlayers));

        game.playGame();

        game.printStatistics();

    }

    private void printLine()
    {
        System.out.println("-----------------------");
    }

    private void print(String text)
    {
        System.out.println(text);
    }

    private void printStatistics()
    {
        printLine();
        printLine();
        print("Game Over");

        Team winner = getGameModel().getWinningTeam();
        if (winner == null)
            print("The game was a draw!");
        else
            print("The winner is " + winner.getTeamName());
        printScore();

        


        Statistics stats = new Statistics(getGameEvents(), getGameModel().getTeams());
        ArrayList<PlayerStats> mostKicks = stats.getMost("Kicks");
        System.out.println("Players with the most kicks:");
        for (PlayerStats playerStats : mostKicks) //coupling
            System.out.println("\t" + playerStats.getPlayer().getPlayerName() + " " + playerStats.getKicks() + " kicks.");

        ArrayList<PlayerStats> mostGoals = stats.getMost("Goals");
        System.out.println("Players with the most goals:");
        for (PlayerStats playerStats : mostGoals) //coupling
            System.out.println("\t" + playerStats.getPlayer().getPlayerName() + " " + playerStats.getKicks() + " goals.");

        System.out.println("Individual Player Stats:");
        ArrayList<PlayerStats> allPlayerStats = stats.getAllPlayerStats();
        for(PlayerStats playerStats : allPlayerStats)
        {
            StringBuffer playerDisplay = new StringBuffer();
            playerDisplay.append(playerStats.getPlayer().getPlayerName());
            playerDisplay.append(" was " + (playerStats.isInjured() ? "" : "not ") + "injured,");
            playerDisplay.append(" was " + (playerStats.isReported() ? "" : "not ") + "reported, ");
            playerDisplay.append(playerStats.getKicks() + (playerStats.getKicks() == 1 ? " kick, " : " kicks, "));
            playerDisplay.append(playerStats.getGoals() + (playerStats.getGoals() == 1 ? " goal " : " goals "));
            playerDisplay.append("and " + String.format("%.2f", playerStats.getEffectiveDisposals()) + "% effective disposals.");
            System.out.println("\t" + playerDisplay.toString());
        }

        System.out.println("Injured Players:");
        ArrayList<Player> injuredPlayers = stats.getInjuredPlayers();
        for (Player player : injuredPlayers) //coupling
            System.out.println("\t" + player.getPlayerName());

        System.out.println("Reported Players:");
        ArrayList<Player> reportedPlayers = stats.getReportedPlayers();
        for (Player player : reportedPlayers) //coupling
            System.out.println("\t" + player.getPlayerName());

    }

    public Event[] getGameEvents()
    {
        return this.gameEvents;
    }

    private void printPeriodStart(int period)
    {
        printLine();
        System.out.println("Quarter: " + period + "\n");
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
        System.out.println(getGameModel().getTeams()[0].getTeamName() + ": " + getGameModel().getTeams()[0].getScore().display());
        System.out.println(getGameModel().getTeams()[1].getTeamName() + ": " + getGameModel().getTeams()[1].displayScore());
    }

    // public void endGame(boolean forfeit)
    // {
    //     //probably here ln 221 > 331 to a statistics model
    //     if(forfeit)
    //     {
    //         String winner = (teams[0].getActivePlayers().size() < 18) ? teams[1].getTeamName() : teams[0].getTeamName();
    //         System.out.println("\nTHE WINNER IS: " + winner + " (due to forfeit)!");
    //     }
    //     else
    //     {
    //         //must change
    //         String winner = teams[0].getScore().getPoints() > teams[1].getScore().getPoints() ? teams[0].getTeamName() : teams[1].getTeamName();
    //         if(teams[0].getScore().getPoints() == teams[1].getScore().getPoints())
    //             System.out.println("\nIT'S A DRAW!");
    //         else
    //             System.out.println("\nTHE WINNER IS: " + winner + "!");
    //     }


    //     System.out.println("\nFinal Score (Behinds, Goals, Points):");
    //     this.printScore();
        
    //     //most kicks
    //     for(Team team : teams)
    //     {
    //         System.out.println("\nMost Kicks for " + team.getTeamName());
    //         ArrayList<Player> mostKicks = new ArrayList<Player>();
    //         for(Player player : team.getPlayers()) //I'd like to sort the players
    //         {
    //             int playerKicks = player.getKicks();
    //             if(mostKicks.size() > 0)
    //             {
    //                 int max = mostKicks.get(0).getKicks();
    //                 if(playerKicks > max)
    //                 {
    //                     mostKicks.clear();
    //                     mostKicks.add(player);
    //                 }
    //                 else if (playerKicks == max)
    //                     mostKicks.add(player);
    //             }
    //             else
    //             {
    //                 mostKicks.add(player);
    //             }
    //         }

    //         for (Player player : mostKicks)
    //         {
    //             System.out.println(player.getPlayerName());
    //         }
    //     }

    //     //most goals
    //     for(Team team : teams)
    //     {
    //         System.out.println("\nMost Goals for " + team.getTeamName());
    //         ArrayList<Player> mostGoals = new ArrayList<Player>();
    //         for(Player player : team.getPlayers()) 
    //         {
    //             int playerGoals = player.getScore().getGoals(); //write helper method if this doesn't move
    //             if(mostGoals.size() > 0)
    //             {
    //                 int max = mostGoals.get(0).getKicks();
    //                 if(playerGoals > max)
    //                 {
    //                     mostGoals.clear();
    //                     mostGoals.add(player);
    //                 }
    //                 else if (playerGoals == max)
    //                     mostGoals.add(player);
    //             }
    //             else
    //             {
    //                 if(playerGoals > 0)
    //                     mostGoals.add(player);
    //             }
    //         }

    //         if (mostGoals.size() > 0)
    //         {
    //             for (Player player : mostGoals)
    //             {
    //                 System.out.println(player.getPlayerName());
    //             }
    //         }
    //         else
    //             System.out.println("No goal scorers.");
    //     }
        
    //     //player stats
    //     for(Team team : teams)
    //     {
    //         System.out.println("\n" + team.getTeamName());
    //         for(Player player : team.getPlayers()) //I'd like to sort the players
    //         {
    //             System.out.println(player.getStats());
    //         }
    //     }

    //     System.out.println("\nInjured Players:");

    //     for(Team team : teams)
    //     {
    //         System.out.print(team.listInjuredPlayers());
    //     }

    //     System.out.println("\nReported Players:");

    //     for(Team team : teams)
    //     {
    //         System.out.print(team.listReportedPlayers());
    //     }

    //     //prep all data for print from stats above
    //     FileIO fileio = new FileIO();

    //     for(Team team : teams)
    //     {
    //         String teamData = team.statsToWrite();
    //         String fileName = team.getTeamName() + "Updated.txt"; //these are currently the wrong file names
    //         fileio.writeFile(teamData, fileName);
    //     }
    // }
} //currently 354, lets see what we can drop this to