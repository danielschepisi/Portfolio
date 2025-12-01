/**
 * The class manages the state of the players of the game
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
public class Player
{
    private String fieldPosition;
    private boolean isInjured;
    private boolean isReported;
    private String playerName;
    private int seasonGoals;
    private boolean starPlayer;


    public Player()
    {
        this.playerName = "No Name";
        this.fieldPosition = "Reserve";
        this.seasonGoals = 0;
        this.starPlayer = false;
        this.isInjured = false;
        this.isReported = false;
    }

    /**
     * Constructor that validates input arguments
     * @param playerName The name of the player
     * @param fieldPosition Position of the player
     * @param seasonGoals The number of seasonGoals (must be non-negative)
     * @throws IllegalArgumentException if seasonGoals are negative or
     * fieldPosition isn't valid
     */
    public Player(String playerName, String fieldPosition, int seasonGoals)
    {
        if (Validator.isBlank(playerName))
            throw new IllegalArgumentException("PlayerName can't be blank.");
        this.playerName = playerName;
        if (fieldPosition.equals("Forward") 
            || fieldPosition.equals("Midfielder")
            || fieldPosition.equals("Defender")
            || fieldPosition.equals("Reserve"))
            this.fieldPosition = fieldPosition;
        else 
            throw new IllegalArgumentException("FieldPosition doesn't match avaiable options. Received: " + fieldPosition);

        if(seasonGoals < 0)
            throw new IllegalArgumentException("SeasonGoals can't be negative. Received: " + seasonGoals);
        else
            this.seasonGoals = seasonGoals;

        this.starPlayer = false;
        this.isInjured = false;
        this.isReported = false;
    }

    /**
    * Returns a string with the state of the player
    */
    public String display()
    {
        return getPlayerName() 
            + " " + getFieldPosition() + " starPlayer: " + isStarPlayer()
            + " goals: " + getSeasonGoals()  + " injured: " + isInjured() 
            + " reported: " + isReported();
    }

    /**
    * Returns the field position of the player
    */
    public String getFieldPosition()
    {
        return this.fieldPosition;
    }

    /**
    * Returns the name of the player
    */
    public String getPlayerName()
    {
        return this.playerName;
    }

    /**
    * Returns the player's season goals
    */
    public int getSeasonGoals()
    {
        return this.seasonGoals;
    }

    /**
    * Increments the season goals by one
    */
    public void incrementSeasonGoals()
    {
        this.seasonGoals++;
    }

    /**
    * Sets the player to be injured
    */
    public void injure()
    {
        setIsInjured(true);
    }

    /**
    * Returns if the player is injured
    */
    public boolean isInjured()
    {
        return this.isInjured;
    }

    /**
    * Returns if the place is reported
    */
    public boolean isReported()
    {
        return this.isReported;
    }

    /**
    * Returns if the player is a star player
    */
    public boolean isStarPlayer()
    {
        return this.starPlayer;
    }

    /**
    * Executes the player's kick and return the outcome
    * as a playerKick
    */
    public PlayerKick kick()
    {
        PlayerKick playerKick = new PlayerKick(this, isStarPlayer());
        if (playerKick.getResult().equals("Goal"))
            incrementSeasonGoals();

        return playerKick;
    }

    /**
    * Sets the field postion of the player
    * @param    fieldPosition    The desired field position
    */
    public void setFieldPosition(String fieldPosition)
    {
        this.fieldPosition = fieldPosition;
    }

    /**
    * Sets if the player is injured or not
    * @param    isInjured    The desired state of injury
    */
    public void setIsInjured(boolean isInjured)
    {
        this.isInjured = isInjured;
    }

    /**
    * Sets if the player is reported or not
    * @param    isReported    The desired state of being reported
    */
    public void setIsReported(boolean isReported)
    {
        this.isReported = isReported;
    }

    /**
    * Sets the player's name
    * @param    playerName    The player's name
    */
    public void setPlayerName(String playerName)
    {
        if (Validator.isBlank(playerName))
            System.out.println("Can't set blank playerName.");
        else
            this.playerName = playerName;
    }

    /**
    * Sets the player's season goals
    * @param    seasonGoals    the number of goals
    */
    public void setSeasonGoals(int seasonGoals)
    {
        if (seasonGoals < 0)
            System.out.println("SeasonGoals can't be negative");
        else
            this.seasonGoals = seasonGoals;
    }

    /**
    * Sets if the player is a star or not
    * @param    starPlayer    Desired state of star player
    */
    public void setStarPlayer(boolean starPlayer)
    {
        this.starPlayer = starPlayer;
    }
}