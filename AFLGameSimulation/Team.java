/**
 * The Team class contains the associated players and score
 * of the team
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */

import java.util.ArrayList;
import java.util.Collections;

public class Team
{
    private ArrayList<Player> players;
    private Score score;
    private String teamName;

    public Team()
    {
        this.teamName = "No Name";
        this.players = new ArrayList<Player>();
        this.score = new Score();
    }

    public Team(String teamText, int starPlayers)
    {
        this.players = new ArrayList<Player>();
        this.score = new Score();

        String[] teamData = teamText.split("\r\n");
        int i = 0;

        for (String element : teamData)
        {
            if (i == 0)
            {
                if(Validator.isBlank(element))
                {
                    System.out.println("Error creating team. Team name must not be blank");
                    System.out.println("Exiting game.");
                    System.exit(1);
                }
                else
                    this.teamName = element;
            }
            else
            {
                String[] playerData = element.split(",");
                try 
                {
                    Player player = new Player(playerData[0], 
                        playerData[1], Integer.parseInt(playerData[2]));
                    players.add(player);
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Error creating player. " + e.getMessage());
                    System.out.println("Exiting game.");
                    System.exit(1);
                }  
            }

            i++;
        }

        if(i != 23)
        {
            System.out.println("Team must have 22 players not " + i);
            System.out.println("Exiting game.");
            System.exit(1);
        }

        int forwards = 0;
        int midfielders = 0;
        int defenders = 0;
        for (Player player : this.players)
        {
            switch (player.getFieldPosition())
            {
                case "Forward":
                    forwards++;
                    break;
                case "Midfielder":
                    midfielders++;
                    break;
                case "Defender":
                    defenders++;
                    break;
                default:
                    break;
            }
        }

        if (forwards < 5 || midfielders < 5 || defenders < 5)
        {
            System.out.println("Team must have at least 5 players in each position.");
            System.out.println("Exiting game.");
            System.exit(1);
        }

        if (starPlayers > 0)
        {
            ArrayList<Player> activePlayers = new ArrayList<Player>(getActivePlayers());
            Collections.shuffle(activePlayers);
            for(int x = 0 ; x < starPlayers ; x++)
            {
                activePlayers.get(x).setStarPlayer(true);
            }
        }
    }

    /**
    * Chooses a random player from the provided position
    * @param    position    The position of the player required
    */
    public Player chooseRandomPlayerFromPosition(String position)
    {
        ArrayList<Player> temp = new ArrayList<Player>(getPlayersOfPosition(position));
        Collections.shuffle(temp);
        return temp.get(0);
    }

    /**
    * Chooses a random player from the provided position excluding
    * the given player
    * @param    position    The position of the player required
    * @param    player    The player to exculde from selection
    */
    public Player chooseRandomPlayerFromPositionExcluding(String position, Player player)
    {
        ArrayList<Player> temp = new ArrayList<Player>(getPlayersOfPosition(position));
        if (temp.contains(player))
            temp.remove(player);
        Collections.shuffle(temp);
        return temp.get(0);
    }

    /**
    * Returns a string of the state of the team
    */
    public String display()
    {
        String state = getTeamName();

        for (Player player : players)
        {
            state += "\n" + player.display();
        }

        return state;
    }

    /**
    * Returns a string with the score of the team
    */
    public String displayScore()
    {
        return getScore().display();
    }

    /**
    * Returns players who are currently in play on the field
    */
    public ArrayList<Player> getActivePlayers()
    {
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        activePlayers.addAll(getPlayersOfPosition("Forward"));
        activePlayers.addAll(getPlayersOfPosition("Midfielder"));
        activePlayers.addAll(getPlayersOfPosition("Defender"));
        return activePlayers;
    }

    /**
    * Returns players who are injured
    */
    public ArrayList<Player> getInjuredPlayers()
    {
        ArrayList<Player> injuredPlayers = new ArrayList<Player>();
        for(Player player : getPlayers())
        {
            if(player.isInjured())
            {
                injuredPlayers.add(player);
            }
        }
        return injuredPlayers;
    }

    /**
    * Returns all the players in the team
    */
    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    /**
    * Returns uninjured players of the requested position
    * @param    position    The position of the players required
    */
    public ArrayList<Player> getPlayersOfPosition(String position)
    {
        ArrayList<Player> requestedPlayers = new ArrayList<Player>();
        for(Player player : getPlayers())
        {
            if(player.isInjured() == false && player.getFieldPosition().equals(position))
            {
                requestedPlayers.add(player);
            }
        }

        return requestedPlayers;
    }

    /**
    * Returns the team's points
    */
    public int getPoints()
    {
        return getScore().getPoints();
    }

    /**
    * Returns the team's score
    */
    public Score getScore()
    {
        return this.score;
    }

    /**
    * Returns the team's name
    */
    public String getTeamName()
    {
        return this.teamName;
    }

    /**
    * Replaces the injured player with a reserve player 
    * if possible
    * @param    injuredPlayer    The player to be replaced
    */
    public void replacePlayer(Player injuredPlayer)
    {
        String position = injuredPlayer.getFieldPosition();
        if(getPlayersOfPosition("Reserve").size() > 0)
        {
            getPlayersOfPosition("Reserve").get(0).setFieldPosition(position);
        }
    }

    /**
    * Adds a behind to the score
    */
    public void scoreBehind()
    {
        getScore().addBehind();
    }

    /**
    * Adds a goal to the score
    */
    public void scoreGoal()
    {
        getScore().addGoal();
    }

    /**
    * Sets the players on the team
    * @param    players    The players to be on the team
    */
    private void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    /**
    * Sets the team score
    * @param    score    The team's score
    */
    private void setScore(Score score)
    {
        this.score = score;
    }

    /**
    * Sets the name of the team
    * @param    teamName    The name of the team
    */
    private void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }
}