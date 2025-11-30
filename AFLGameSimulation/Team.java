import java.util.ArrayList;
import java.util.Collections;

public class Team
{
    private String teamName;
    private Score score;
    private ArrayList<Player> players;

    public Team()
    {
        this.teamName = "No Name";
        this.players = new ArrayList<Player>();
        this.score = new Score();
    }

    public Team(String teamText, int starPlayers)
    {
        this();

        String[] teamData = teamText.split("\r\n");
        int i = 0;

        for (String element : teamData)
        {
            if (i == 0)
                teamName = element;
            else
            {
                //need to catch errors if doesn't work creating player

                String[] playerData = element.split(",");
                Player player = new Player(playerData[0], playerData[1], Integer.parseInt(playerData[2])); //fix later
                players.add(player);
            }

            i++;
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

    public ArrayList<Player> getActivePlayers()
    {
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        activePlayers.addAll(getPlayersOfPosition("Forward"));
        activePlayers.addAll(getPlayersOfPosition("Midfielder"));
        activePlayers.addAll(getPlayersOfPosition("Defender"));
        return activePlayers;
    }

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

    public void replacePlayer(Player injuredPlayer) //surely can improve this
    {
        String position = injuredPlayer.getFieldPosition();
        if(getPlayersOfPosition("Reserve").size() > 0)
        {
            getPlayersOfPosition("Reserve").get(0).setFieldPosition(position);
        }
    }

    public String listInjuredPlayers()
    {
        String injuredPlayers = "";
        for(Player player : getInjuredPlayers())
        {
            injuredPlayers += player.getPlayerName() + "\n";
        }

        return injuredPlayers;
    }

    public String listReportedPlayers()
    {
        String reportedPlayers = "";
        for(Player player : getPlayers())
        {
            if(player.isReported())
                reportedPlayers += player.getPlayerName() + "\n";
        }

        return reportedPlayers;
    }

    public String getTeamName()
    {
        return this.teamName;
    }

    public Player chooseRandomPlayerFromPosition(String position)
    {
        ArrayList<Player> temp = new ArrayList<Player>(getPlayersOfPosition(position));
        Collections.shuffle(temp);
        return temp.get(0); //need validation checks??
    }

    public Player chooseRandomPlayerFromPositionExcluding(String position, Player player)
    {
        ArrayList<Player> temp = new ArrayList<Player>(getPlayersOfPosition(position));
        if (temp.contains(player))
            temp.remove(player);
        Collections.shuffle(temp);
        return temp.get(0); //need validation checks??
    }

    public String display() //do better version
    {
        String state = getTeamName();

        for (Player player : players)
        {
            state += "\n" + player.display();
        }

        return state;
    }

    public String displayScore()
    {
        return getScore().display();
    }

    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    public Score getScore()
    {
        return this.score;
    }

    public int getPoints()
    {
        return getScore().getPoints();
    }

    public void scoreBehind()
    {
        getScore().addBehind();
    }

    public void scoreGoal()
    {
        getScore().addGoal();
    }
}