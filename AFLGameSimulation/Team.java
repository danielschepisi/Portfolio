import java.util.ArrayList;
import java.util.Collections;

public class Team
{
    private String teamName;
    private Score score;
    private ArrayList<Player> players;

    private ArrayList<Player> forwards;
    private ArrayList<Player> midfielders;
    private ArrayList<Player> defenders;
    private ArrayList<Player> reserves;
    private ArrayList<Player> injuredPlayers;

    //reported players ArrayList

    public Team()
    {
        this.teamName = "No Name";
        this.players = new ArrayList<Player>();
        this.score = new Score();

        this.players = new ArrayList<Player>();
        this.forwards = new ArrayList<Player>();
        this.midfielders = new ArrayList<Player>();
        this.defenders = new ArrayList<Player>();
        this.reserves = new ArrayList<Player>();
        this.injuredPlayers = new ArrayList<Player>();
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
                String[] playerData = element.split(",");
                Player player = new Player(playerData[0], playerData[1], Integer.parseInt(playerData[2])); //fix later
                players.add(player);

                switch (player.getFieldPosition().toLowerCase())
                {
                    case "forward":
                        this.forwards.add(player);
                        break;
                    case "midfielder":
                        this.midfielders.add(player);
                        break;
                    case "defender":
                        this.defenders.add(player);
                        break;
                    case "reserve":
                        this.reserves.add(player);
                        break;
                    default:
                        System.out.println("Error assigning the player a position."); //???
                        break;
                }
            }

            i++;
        }

        //all players like below, or only those on the field??
        //set star players
        if (starPlayers > 0)
        {
            ArrayList<Player> temp = new ArrayList<Player>(getPlayers());
            Collections.shuffle(temp);
            for(int x = 0 ; x < starPlayers ; x++)
            {
                temp.get(x).setStarPlayer(true);
            }
        }
    }

    public ArrayList<Player> getActivePlayers()
    {
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        activePlayers.addAll(getForwards());
        activePlayers.addAll(getMidfielders());
        activePlayers.addAll(getDefenders());
        return activePlayers;
    }

    public ArrayList<Player> getForwards()
    {
        return this.forwards;
    }

    public ArrayList<Player> getMidfielders()
    {
        return this.midfielders;
    }

    public ArrayList<Player> getDefenders()
    {
        return this.defenders;
    }

    public ArrayList<Player> getReserves()
    {
        return this.reserves;
    }

    public ArrayList<Player> getInjuredPlayers()
    {
        return this.injuredPlayers;
    }

    public String injurePlayer() //check validation
    {
        String injuredPlayerName = "";
        ArrayList<Player> activePlayers = getActivePlayers();
        if(activePlayers.size() > 0)
        {
            Collections.shuffle(activePlayers);
            Player injuredPlayer = activePlayers.get(0);
            injuredPlayer.injure();
            getInjuredPlayers().add(injuredPlayer); //we want this?


            replacePlayer(injuredPlayer);
            injuredPlayerName = injuredPlayer.getPlayerName();
        }

        return injuredPlayerName;
    }

    private void replacePlayer(Player injuredPlayer) //surely can improve this
    {
        String position = injuredPlayer.getFieldPosition();
        if(getReserves().size() > 0)
        {
            switch (position.toLowerCase())
            {
                case "forward":
                    getForwards().remove(injuredPlayer);
                    getReserves().get(0).setFieldPosition("Forward");
                    getForwards().add(getReserves().get(0));
                    getReserves().remove(0);
                    break;
                case "midfielder":
                    getMidfielders().remove(injuredPlayer);
                    getReserves().get(0).setFieldPosition("Midfielder");
                    getMidfielders().add(getReserves().get(0));
                    getReserves().remove(0);
                    break;
                case "defender":
                    getDefenders().remove(injuredPlayer);
                    getReserves().get(0).setFieldPosition("Defender");
                    getDefenders().add(getReserves().get(0));
                    getReserves().remove(0);
                    break;
            }
        }
        else
        {
            switch (position.toLowerCase())
            {
                case "forward":
                    getForwards().remove(injuredPlayer);
                    break;
                case "midfielder":
                    getMidfielders().remove(injuredPlayer);
                    break;
                case "defender":
                    getDefenders().remove(injuredPlayer);
                    break;
            }
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

    public String statsToWrite()
    {
        String data = getTeamName();
        for(Player player : getPlayers())
        {
            data += "\n" + player.getPlayerName() + "," + player.getFieldPosition() + "," + player.getSeasonGoals();
        }

        return data;
    }

    public String getTeamName()
    {
        return this.teamName;
    }

    public Player chooseRandomForward()
    {
        Collections.shuffle(this.forwards);
        return this.forwards.get(0); //need validation checks and do I want shuffle arrayLIst??
    }

    public Player chooseRandomForwardExcluding(Player player)
    {
        ArrayList<Player> temp = new ArrayList<Player>(this.forwards);
        if (temp.contains(player))
            temp.remove(player);
        Collections.shuffle(temp); //need validation checks
        return temp.get(0); //need validation checks
    }

    public Player chooseRandomMidfielder()
    {
        Collections.shuffle(this.midfielders); //need validation checks
        return this.midfielders.get(0); //need validation checks
    }

    public Player chooseRandomMidfielderExcluding(Player player)
    {
        ArrayList<Player> temp = new ArrayList<Player>(this.midfielders);
        if (temp.contains(player))
            temp.remove(player);
        Collections.shuffle(temp); //need validation checks
        return temp.get(0); //need validation checks
    }

    public Player chooseRandomDefender()
    {
        Collections.shuffle(this.defenders); //need validation checks
        return this.defenders.get(0); //need validation checks
    }



    // public Player chooseRandomReserve()
    // {
    //     return Collections.shuffle(this.reserves)[0]; //need validation checks
    // }


    // private Player chooseRandomPlayer(String position)
    // {
    //     switch position
    // }

    public String display() //do better version
    {
        String state = this.teamName;

        for (Player player : players)
        {
            state += "\n" + player.display();
        }

        return state;
    }

    public String displayScore()
    {
        return this.score.display();
    }

    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    public Score getScore()
    {
        return this.score;
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