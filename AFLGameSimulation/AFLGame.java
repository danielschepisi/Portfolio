import java.util.ArrayList;

public class AFLGame
{
    private static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;

    private GameModel gameModel;

    public AFLGame()
    {
        this.gameModel = new GameModel();
    }

    public GameModel getGameModel()
    {
        return this.gameModel;
    }

    public void setGameModel(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }

    public static int getMaxNumberOfStarPlayers()
    {
        return MAX_NUMBER_OF_STAR_PLAYERS;
    }

    private int askUserForNumberOfStarPlayers()
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
                if (validator.intWithinRange(numberOfStarPlayers, 0, getMaxNumberOfStarPlayers())) //??code elsewhere
                    proceed = true;
                else
                    System.out.println("Please enter an integer from 0 - " + getMaxNumberOfStarPlayers());
            }
            catch (Exception e)
            {
                System.out.println("Please enter an integer from 0 - " + getMaxNumberOfStarPlayers());
            }
        } while (proceed == false);

        return numberOfStarPlayers;
    }

    public static void main(String[] args)
    {
        AFLGame game = new AFLGame();
        int numberOfStarPlayers = game.askUserForNumberOfStarPlayers();
        game.setGameModel(new GameModel(numberOfStarPlayers));
        String gamePlay = game.getGameModel().playGame();
        System.out.println(gamePlay);
    }

    // private void printScore()
    // {
    //     //ditch all this, but if not, reduce coupling at least
    //     System.out.println(teams[0].getTeamName() + ": " + teams[0].displayScore());
    //     System.out.println(teams[1].getTeamName() + ": " + teams[1].displayScore());
    // }

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