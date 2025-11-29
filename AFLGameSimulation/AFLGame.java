import java.util.ArrayList;

public class AFLGame
{
    public static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;
    private final int NUMBER_OF_PERIODS = 4; //??? static
    public static final int EVENTS_PER_PERIOD = 10;
	// private final int EVENTS_PER_PERIOD = 80;

    private GameModel gameModel;
    private Score gameScore;
    private Event[] gameEvents;
    // private ArrayList<Player> injuredPlayers;
    // private ArrayList<Player> reportedPlayers;

    public AFLGame()
    {
        this.gameModel = new GameModel();
        this.gameScore = new Score();
        this.gameEvents = new Event[NUMBER_OF_PERIODS * EVENTS_PER_PERIOD];
    }

    public AFLGame(GameModel gameModel)
    {
        this.gameModel = gameModel;
        this.gameScore = new Score();
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

    // public static int getMaxNumberOfStarPlayers()
    // {
    //     return MAX_NUMBER_OF_STAR_PLAYERS;
    // }

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






        // String gamePlayNarrative = game.getGameModel().playGame();
        // System.out.println(gamePlayNarrative);
    }

    private void printPeriodStart(int period)
    {
        System.out.println("-------------------------\n");
        System.out.println("Quarter: " + period + "\n");
        System.out.println("-------------------------\n");
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
                System.out.println("#" + i + "\t");
                int gameEventNumber = (i-1) + ((period - 1) * EVENTS_PER_PERIOD);
				Event currentEvent = playEvent();
				gameEvents[gameEventNumber] = currentEvent;

                Player bouncerWinner = currentEvent.getBounceWinner();
                if (bouncerWinner != null)
                    System.out.println(bouncerWinner.getPlayerName() + "\t" + "wins the ball from the bounce"); //strong coupling w player name

                switch (currentEvent.getPlayerKick().getResult()) //styrong coupling
                {
                    case "Goal":
                        //assign goal
                        System.out.println("Goal");
                        break;
                    case "Behind":
                        //assign behind
                        System.out.println("Behind");
                        break;
                    case "Pass":
                        //print what needed including who to
                        System.out.println("Pass");
                        break;
                    case "Turnover":
                        //print what needed including who to
                        System.out.println("Turnover");
                        break;
                    default:
                        //something went wrong
                    break;
                }

                Player injuredPlayer = currentEvent.getInjuredPlayer();
                if (injuredPlayer != null)
                    System.out.println(injuredPlayer.getPlayerName() + "\t" + "was injured"); //strong coupling w player name
                    
                ArrayList<Player> reportedPlayers = currentEvent.getReportedPlayers();
                if (reportedPlayers != null)
                {
                    for (Player player : reportedPlayers)
                    {
                        System.out.println(player.getPlayerName() + "\t" + "was reported"); //strong coupling w player name
                    }
                }


				continueGame = getGameModel().enoughUninjuredPlayers();

				if (!continueGame)
                {
				    System.out.println("Game forfeit!");
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
        // //ditch all this, but if not, reduce coupling at least
        // System.out.println(teams[0].getTeamName() + ": " + getScore().display(););
        // System.out.println(teams[1].getTeamName() + ": " + teams[1].displayScore());
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