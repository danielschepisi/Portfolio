/**
 * This class is responsible for the logic and outcome of
 * a player kicking the ball
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
public class PlayerKick
{
    private Player player;
    private String result;
    private String toFieldPosition;

    public PlayerKick()
    {

    }

    public PlayerKick(Player player, boolean isStarPlayer)
    {
        this();
        this.player = player;
        decideOutcome();
    }

    /**
    * Decides the outcome of the player kicking the ball
    * and records the information on the fields
    */
    private final void decideOutcome()
    {
        String fieldPosition = getPlayer().getFieldPosition(); 
        Double rand = Math.random();
        if (getPlayer().isStarPlayer())
        {
            switch (fieldPosition)
            {
                case "Forward":
                    if (rand < 0.45)
                        setResult("Goal");
                    else if (rand < 0.85)
                        setResult("Behind");
                    else if (rand < 0.95)
                    {
                        setResult("Pass");
                        setToFieldPosition("Forward");
                    }
                    else
                    {
                        setResult("Turnover");
                        setToFieldPosition("Defender");
                    }
                    break;

                case "Midfielder":
                    if (rand < 0.10)
                        setResult("Goal");
                    else if (rand < 0.20)
                        setResult("Behind");
                    else if (rand < 0.55)
                    {
                        setResult("Pass");
                        setToFieldPosition("Forward");
                    }
                    else if (rand < 0.90)
                    {
                        setResult("Pass");
                        setToFieldPosition("Midfielder");
                    }
                    else
                    {
                        setResult("Turnover");
                        setToFieldPosition("Midfielder");
                    }
                    break;

                case "Defender":
                    if (rand < 0.95)
                    {
                        setResult("Pass");
                        setToFieldPosition("Midfielder");
                    }
                    else
                    {
                        setResult("Turnover");
                        setToFieldPosition("Forward");
                    }
                    break;

                default:
                    //something went wrong
                    break;
            }
        }
        else 
        {
            switch (fieldPosition)
            {
                case "Forward":
                    if (rand < 0.30)
                        setResult("Goal");
                    else if (rand < 0.70)
                        setResult("Behind");
                    else if (rand < 0.90)
                    {
                        setResult("Pass");
                        setToFieldPosition("Forward");
                    }
                    else
                    {
                        setResult("Turnover");
                        setToFieldPosition("Defender");
                    }
                    break;

                case "Midfielder":
                    if (rand < 0.05)
                        setResult("Goal");
                    else if (rand < 0.15)
                        setResult("Behind");
                    else if (rand < 0.45)
                    {
                        setResult("Pass");
                        setToFieldPosition("Forward");
                    }
                    else if (rand < 0.75)
                    {
                        setResult("Pass");
                        setToFieldPosition("Midfielder");
                    }
                    else
                    {
                        setResult("Turnover");
                        setToFieldPosition("Midfielder");
                    }
                    break;

                case "Defender":
                    if (rand < 0.80)
                    {
                        setResult("Pass");
                        setToFieldPosition("Midfielder");
                    }
                    else
                    {
                        setResult("Turnover");
                        setToFieldPosition("Forward");
                    }
                    break;

                default:
                    //something went wrong
                    break;
            }
        }
    }

    /**
    * Gets the playe who is kicking the ball
    */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
    * Gets the player's name
    */
    public String getPlayerName()
    {
        return getPlayer().getPlayerName();
    }
    
    /**
    * Returns the result from the kick
    */
    public String getResult()
    {
        return this.result;
    }

    /**
    * Gets the position where the ball was kicked
    */
    public String getToFieldPosition()
    {
        return this.toFieldPosition;
    }

    /**
    * Sets the player who kicked the ball
    * @param    player    The player kicking the ball
    */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
    * Sets the result of the kick
    * @param    result    The result of the kick
    */
    public void setResult(String result)
    {
        this.result = result;
    }

    /**
    * Sets the position of the player where the ball ends up
    * @param    position    The position of the player
    * wher the ball ends up
    */
    public void setToFieldPosition(String position)
    {
        this.toFieldPosition = position;
    }
}