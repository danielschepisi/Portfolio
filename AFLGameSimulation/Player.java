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

    //starPlayer
    //injured

    //team??

    public Player()
    {
        playerName = "";
        fieldPosition = "";
        seasonGoals = 0;
        starPlayer = false;
        isInjured = false;
        isReported = false;
        score = new Score();

        // System.out.println("player init default"); //delete
    }

    public Player(String playerName, String fieldPosition, int seasonGoals)
    {
        this();
        this.playerName = playerName;
        this.fieldPosition = fieldPosition;
        this.seasonGoals = seasonGoals;

        // System.out.println("player init custom"); //delete
    }

    public String display()
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

    public String kick()
    {
        Event event = new Event();
        String outcome = event.decideOutcome(Math.random(), this.fieldPosition, this.starPlayer);
        
        if(outcome.startsWith("Goal")) //
        {
            this.score.addGoal();
            this.incrementSeasonGoals();
        }
        if(outcome.startsWith("Behind")) //
        {
            this.score.addBehind();
            this.incrementEffectiveDisposals();
        }
        if(outcome.startsWith("Pass"))
            this.incrementEffectiveDisposals();
        // if(outcome.startsWith("Turnover"))
        //     this.incrementEffectiveDisposals();

        this.incrementKicks();

        return outcome;
    }












    // public Outcome play()
    // {
    //     //kick()
    //     //isInjured()
    //     //updateStats()
    // }

    // private Outcome kick()
    // {
    //     //generate number between 0 and 1. 
    //     //pass in starPlayer and position get outcome (details in Outcome)
    // }

    // private boolean isInjured()
    // {
    //     //2% chance of injury
    // }

    private void updateStats()
    {

    }
    
}