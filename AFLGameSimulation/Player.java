public class Player
{
    private String playerName;
    private String fieldPosition;
    private int seasonGoals;
    private boolean starPlayer;
    private boolean isInjured;
    private boolean isReported;

    public Player()
    {
        this.playerName = "";
        this.fieldPosition = "";
        this.seasonGoals = 0;
        this.starPlayer = false;
        this.isInjured = false;
        this.isReported = false;
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

    public String getFieldPosition()
    {
        return this.fieldPosition;
    }

    public String getPlayerName()
    {
        return this.playerName;
    }

    public int getSeasonGoals()
    {
        return this.seasonGoals;
    }

    private void incrementSeasonGoals()
    {
        this.seasonGoals++;
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

    public PlayerKick kick()
    {
        PlayerKick playerKick = new PlayerKick(this, isStarPlayer());
        if (playerKick.getResult().equals("Goal"))
            incrementSeasonGoals();

        return playerKick;
    }
}