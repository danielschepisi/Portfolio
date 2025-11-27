import java.util.ArrayList;

public class AFLGame
{
    private final int EVENTS_PER_QUARTER = 80;
    // private final int EVENTS_PER_QUARTER = 80;
    private final double REPORTED_CHANCE_PER_GAME = 0.01;
    // private final double INJURED_CHANCE_PER_EVENT = 1.5;
    private final double INJURED_CHANCE_PER_EVENT = 0.02;
    private static final int MAX_NUMBER_OF_STAR_PLAYERS = 8;

        
    // private int numberOfStarPlayers;
    private Team[] teams; //length 2
    private int currentQuarter;
    private Team teamWithPossession;
    private Player playerWithPossession;
    // private Score gameScore; //need both teams

    //??default constructor

    public AFLGame(int starPlayers)
    {
        // this.numberOfStarPlayers = starPlayers;
        this.teams = new Team[2];
        this.currentQuarter = 1;
        createTeams(starPlayers);
    }

    private void createTeams(int starPlayers)
    {
        FileIO fileio = new FileIO();

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
        game.startGame();
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
            for (int i = 0 ; i < EVENTS_PER_QUARTER ; i++)
            {
                if (this.playerWithPossession == null)
                {
                    this.teamWithPossession = pickRandomTeam(); //these fields should change to setTeamWithPossession
                    this.playerWithPossession = teamWithPossession.chooseRandomMidfielder();
                }
            

                String eventOutcome = this.playerWithPossession.kick();

                //you will need to print this stuff
                // System.out.print(this.teamWithPossession.getTeamName() + "\t"); //to delete
                System.out.print(this.playerWithPossession.getPlayerName() + "\t"); //to delete
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
                    //maybe pickRandomTeam method coz used elsewhere too
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
        } while (currentQuarter <= 4);


        endGame(false); //this reads poorly
        //repeat
        //50/50 choose team > choose midfielder
        //or
        //player outcome

        //assign player aka affect outcome

        //repeat
        //if less than 4 quarters
        //if less than 80 events
        //if no playerWithPossession then 50/50 assign
        //if player > player.play() > outcome 

        //assign new player (inc behind) 
        //grab correct team > random play from team who doesn't equal activePlayer
        //or no player (w score update)

        //if injured remove and replace player > team.playerInjured(player)
        // OR 2% chance then random player injured (public Player checkForInjuries())
        // also 1% / 80 / 4 of someone being reported (public Player checkForReportedPlayers())
        // (umpire) 
        //check teams have enough players (flag to end game continueGame, but affectOutcome first)

        //print result of event
        //print score if changed

        //end of quarter, display score
    }

    private double chanceOfBeingReportedPerEvent()
    {
        return REPORTED_CHANCE_PER_GAME / EVENTS_PER_QUARTER / 4;
    }

    private void printScore()
    {
        System.out.println(teams[0].getTeamName() + ": " + teams[0].displayScore());
        System.out.println(teams[1].getTeamName() + ": " + teams[1].displayScore());
    }

    private void swapTeams() //maybe call turnover
    {
        if (this.teams[0] == this.teamWithPossession)
            this.teamWithPossession = teams[1];
        else
            this.teamWithPossession = teams[0];
    }

    public void endGame(boolean forfeit)
    {
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

        //uncomment, just for ease of viewing
        
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
}