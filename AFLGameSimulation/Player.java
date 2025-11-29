public class Player
{
    private String playerName;
    private String fieldPosition;
    private int seasonGoals;
    private boolean starPlayer;
    private boolean isInjured;
    private boolean isReported;
    private Score score;
    private int effectiveDisposals;
    private int kicks;

    //team??

    public Player()
    {
        this.playerName = "";
        this.fieldPosition = "";
        this.seasonGoals = 0;
        this.starPlayer = false;
        this.isInjured = false;
        this.isReported = false;
        this.score = new Score();
        this.effectiveDisposals = 0;
        this.kicks = 0;
    }

    public Player(String playerName, String fieldPosition, int seasonGoals)
    {
        this();
        this.playerName = playerName;
        this.fieldPosition = fieldPosition;
        this.seasonGoals = seasonGoals; //do i have to use setters if i'm calling this()
    }

    public String display() //need all fields, clarify bools
    {
        return getPlayerName() + " " + getFieldPosition() + " " + isStarPlayer() + " " + getSeasonGoals();
    }

    public int getKicks()
    {
        return this.kicks;
    }

    public int getEffectiveDisposals()
    {
        return this.effectiveDisposals;
    }

    public double getEffectiveDisposalsPercentage()
    {
        if (getKicks() == 0)
            return 0;
        else
            return (double)getEffectiveDisposals() * 100 / (double)getKicks();
    }

    public String getFieldPosition()
    {
        return this.fieldPosition;
    }

    public String getPlayerName()
    {
        return this.playerName;
    }

    public Score getScore()
    {
        return this.score;
    }

    public int getSeasonGoals()
    {
        return this.seasonGoals;
    }

    public String getStats()
    {
        //maybe move this to some stats model as well??
        String playerStats = "";
        playerStats += getPlayerName() + " is " + (isInjured() ? "" : "not ") + "injured,";
        playerStats += " is " + (isReported() ? "" : "not ") + "reported, ";
        playerStats += Integer.toString(getKicks()) + " kicks, ";
        playerStats += Integer.toString(getScore().getGoals()) + " goals, ";
        playerStats += "disposals " + String.format("%.2f", getEffectiveDisposalsPercentage()) +"%.";
        return playerStats;
    }

    private void incrementKicks()
    {
        this.kicks++;
    }

    private void incrementSeasonGoals()
    {
        this.seasonGoals++;
    }

    private void incrementEffectiveDisposals()
    {
        this.effectiveDisposals++;
    }

    public void setIsInjured(boolean isInjured)
    {
        this.isInjured = isInjured;
    }

    public void setIsReported(boolean isReported)
    {
        this.isReported = isReported;
    }

    public void injure()
    {
        setIsInjured(true);
    }

    public boolean isInjured()
    {
        return this.isInjured;
    }

    public boolean isReported()
    {
        return this.isReported;
    }

    public boolean isStarPlayer()
    {
        return this.starPlayer;
    }

    public void setFieldPosition(String fieldPosition)
    {
        this.fieldPosition = fieldPosition;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public void setSeasonGoals(int seasonGoals)
    {
        this.seasonGoals = seasonGoals;
    }

    public void setStarPlayer(boolean starPlayer)
    {
        this.starPlayer = starPlayer;
    }

    public String[] kick()
    {
        Event event = new Event();
        String[] outcome = event.decideOutcome(Math.random(), this.fieldPosition, this.starPlayer);
        
        if(outcome[0].equals("Goal"))
        {
            this.score.addGoal();
            this.incrementSeasonGoals();
        }
        if(outcome[0].equals("Behind"))
        {
            this.score.addBehind();
            this.incrementEffectiveDisposals();
        }
        if(outcome[0].equals("Pass"))
            this.incrementEffectiveDisposals();

        this.incrementKicks();

        return outcome;
    }
}