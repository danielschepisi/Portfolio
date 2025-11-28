import java.util.ArrayList;

public class AFLGame
{
    private final int EVENTS_PER_QUARTER = 80;
    private final double REPORTED_CHANCE_PER_GAME = 0.01;
    private final double INJURED_CHANCE_PER_EVENT = 0.02;
    private static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;

    private Team[] teams;
    private int currentQuarter;
    private Team teamWithPossession;
    private Player playerWithPossession;

    //??default constructor

    public AFLGame(int starPlayers)
    {
        this.teams = new Team[2];
        this.currentQuarter = 1;
        createTeams(starPlayers);
    }

    private void createTeams(int starPlayers)
    {
        FileIO fileio = new FileIO();

        //need to catch error around here and terminate if Exception
        //maybe we need this class to throw an Exception so that we can catch it here
        //maybe we can put a throws in teh catch in fileio?
        String teamAText = fileio.readFile("teamA.txt");
        String teamBText = fileio.readFile("teamB.txt");

        setTeams(new Team(teamAText, starPlayers), new Team(teamBText, starPlayers)); //condense?
    }

    public static int getMaxNumberOfStarPlayers()
    {
        return MAX_NUMBER_OF_STAR_PLAYERS;
    }

    public static void main(String[] args)
    {
        //ask for number of star players
        //maybe put into method and init AFLGame first
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

        AFLGame game = new AFLGame(numberOfStarPlayers);
        //maybe GameModel in constructor? or in start game
        game.startGame();
        game.endGame(false); //this reads poorly, plus can't have this boolean
    }

    public void setTeams(Team[] teams)
    {
        this.teams = teams;
    }

    public void setTeams(Team teamOne, Team teamTwo)
    {
        Team[] teamArray = {teamOne, teamTwo};
        setTeams(teamArray);
    }

    //gameModel
    private Team pickRandomTeam()
    {
        return (Math.random() < 0.5) ? teams[0] : teams[1];
    }

    public void startGame()
    {
        do 
        {
            System.out.println("-------------------------");
            System.out.println("Quarter: " + this.currentQuarter);
            System.out.println("-------------------------");
            this.printScore();
            //maybe this becomes while loop to allow for forfeit boolean
            for (int i = 0 ; i < EVENTS_PER_QUARTER ; i++)
            {
                //pass all this to a game model that can have access to the player (and team)
                //then pretty much here 97 > 183 can go and just return the outcomes that can then be printed to screen
                //will have to think through what is passed back and how it's handled. esp scores and forfeits.
                //maybe gameModel always passes back continueGame/gameForfeit boolean

                if (this.playerWithPossession == null)
                {
                    this.teamWithPossession = pickRandomTeam(); //these fields should change to setTeamWithPossession
                    this.playerWithPossession = teamWithPossession.chooseRandomMidfielder();
                }
            

                String eventOutcome = this.playerWithPossession.kick();

                //you will need to print this stuff
                // System.out.print(this.teamWithPossession.getTeamName() + "\t"); //to delete
                System.out.print(this.playerWithPossession.getPlayerName() + "\t"); //write direct method for this
                System.out.print(eventOutcome + "\n"); //to delete

        
                switch (eventOutcome) //watch for validation on assigning from teams
                {
                    case "Goal":
                        //assign goal
                        this.teamWithPossession.scoreGoal();
                        this.printScore();
                        this.playerWithPossession = null;
                        break;
                    case "Behind":
                        // assign behind
                        this.teamWithPossession.scoreBehind();
                        this.printScore();
                        swapTeams();
                        this.playerWithPossession = teamWithPossession.chooseRandomDefender();
                        break;
                    case "Pass Forward":
                        this.playerWithPossession = teamWithPossession.chooseRandomForwardExcluding(this.playerWithPossession);
                        break;
                    case "Pass Midfielder":
                        this.playerWithPossession = teamWithPossession.chooseRandomMidfielderExcluding(this.playerWithPossession);
                        break;
                    case "Turnover Forward":
                        swapTeams();
                        this.playerWithPossession = teamWithPossession.chooseRandomForward();
                        break;
                    case "Turnover Midfielder":
                        swapTeams();
                        this.playerWithPossession = teamWithPossession.chooseRandomMidfielder();
                        break;
                    case "Turnover Defender":
                        swapTeams();
                        this.playerWithPossession = teamWithPossession.chooseRandomDefender();
                        break;
                    default:
                        //something went wrong
                        break;     
                }

                //injured Player
                if(Math.random() < INJURED_CHANCE_PER_EVENT)
                {
                    String injuredPlayerName = pickRandomTeam().injurePlayer(); //don't like this
                    injuredPlayerName += " was injured.";
                    System.out.println(injuredPlayerName);

                    //check enough players still
                    for(Team team : teams)
                    {
                        if(team.getActivePlayers().size() < 18)
                        {
                            System.out.println("Game forfeit!");
                            endGame(true);
                            return; //check these two lines of logic with the break out

                        }
                    }
                }

                //reported players
                for(Team team : teams)
                {
                    for(Player player : team.getActivePlayers())
                    {
                        if(Math.random() < chanceOfBeingReportedPerEvent())
                        {
                            player.setIsReported(true); //maybe chagne to report
                            System.out.println(player.getPlayerName() + " was reported.");
                        }
                    }
                }
            }

            this.currentQuarter++;
        // && continueGame/gameNotForfeit boolean
        } while (currentQuarter <= 4);
    }

    //gameModel
    private double chanceOfBeingReportedPerEvent()
    {
        return REPORTED_CHANCE_PER_GAME / EVENTS_PER_QUARTER / 4;
    }

    private void printScore()
    {
        //ditch all this, but if not, reduce coupling at least
        System.out.println(teams[0].getTeamName() + ": " + teams[0].displayScore());
        System.out.println(teams[1].getTeamName() + ": " + teams[1].displayScore());
    }

    //go to game model
    private void swapTeams() //maybe call turnover
    {
        if (this.teams[0] == this.teamWithPossession)
            this.teamWithPossession = teams[1];
        else
            this.teamWithPossession = teams[0];
    }

    public void endGame(boolean forfeit)
    {
        //probably here ln 221 > 331 to a statistics model
        if(forfeit)
        {
            String winner = (teams[0].getActivePlayers().size() < 18) ? teams[1].getTeamName() : teams[0].getTeamName();
            System.out.println("\nTHE WINNER IS: " + winner + " (due to forfeit)!");
        }
        else
        {
            //must change
            String winner = teams[0].getScore().getPoints() > teams[1].getScore().getPoints() ? teams[0].getTeamName() : teams[1].getTeamName();
            if(teams[0].getScore().getPoints() == teams[1].getScore().getPoints())
                System.out.println("\nIT'S A DRAW!");
            else
                System.out.println("\nTHE WINNER IS: " + winner + "!");
        }


        System.out.println("\nFinal Score (Behinds, Goals, Points):");
        this.printScore();
        
        //most kicks
        for(Team team : teams)
        {
            System.out.println("\nMost Kicks for " + team.getTeamName());
            ArrayList<Player> mostKicks = new ArrayList<Player>();
            for(Player player : team.getPlayers()) //I'd like to sort the players
            {
                int playerKicks = player.getKicks();
                if(mostKicks.size() > 0)
                {
                    int max = mostKicks.get(0).getKicks();
                    if(playerKicks > max)
                    {
                        mostKicks.clear();
                        mostKicks.add(player);
                    }
                    else if (playerKicks == max)
                        mostKicks.add(player);
                }
                else
                {
                    mostKicks.add(player);
                }
            }

            for (Player player : mostKicks)
            {
                System.out.println(player.getPlayerName());
            }
        }

        //most goals
        for(Team team : teams)
        {
            System.out.println("\nMost Goals for " + team.getTeamName());
            ArrayList<Player> mostGoals = new ArrayList<Player>();
            for(Player player : team.getPlayers()) 
            {
                int playerGoals = player.getScore().getGoals(); //write helper method if this doesn't move
                if(mostGoals.size() > 0)
                {
                    int max = mostGoals.get(0).getKicks();
                    if(playerGoals > max)
                    {
                        mostGoals.clear();
                        mostGoals.add(player);
                    }
                    else if (playerGoals == max)
                        mostGoals.add(player);
                }
                else
                {
                    if(playerGoals > 0)
                        mostGoals.add(player);
                }
            }

            if (mostGoals.size() > 0)
            {
                for (Player player : mostGoals)
                {
                    System.out.println(player.getPlayerName());
                }
            }
            else
                System.out.println("No goal scorers.");
        }
        
        //player stats
        for(Team team : teams)
        {
            System.out.println("\n" + team.getTeamName());
            for(Player player : team.getPlayers()) //I'd like to sort the players
            {
                System.out.println(player.getStats());
            }
        }

        System.out.println("\nInjured Players:");

        for(Team team : teams)
        {
            System.out.print(team.listInjuredPlayers());
        }

        System.out.println("\nReported Players:");

        for(Team team : teams)
        {
            System.out.print(team.listReportedPlayers());
        }

        //prep all data for print from stats above
        FileIO fileio = new FileIO();

        for(Team team : teams)
        {
            String teamData = team.statsToWrite();
            String fileName = team.getTeamName() + "Updated.txt"; //these are currently the wrong file names
            fileio.writeFile(teamData, fileName);
        }
    }
} //currently 354, lets see what we can drop this to