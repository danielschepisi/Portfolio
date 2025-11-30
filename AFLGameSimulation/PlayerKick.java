public class PlayerKick
{
    private Player player;
    private String result;
    private String toFieldPosition;

    public PlayerKick()
    {

    }

    public PlayerKick(Player player, boolean isStarPlayer) //??? this()??
    {
        this();
        this.player = player;
        decideOutcome();
    }
    
    public String getResult()
    {
        return this.result;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public String getPlayerName()
    {
        return getPlayer().getPlayerName();
    }

    public String getToFieldPosition()
    {
        return this.toFieldPosition;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }

    public void setToFieldPosition(String position)
    {
        this.toFieldPosition = position;
    }

    public final String[] decideOutcome() //check final w constructors
    {
        String fieldPosition = getPlayer().getFieldPosition(); 
        Double rand = Math.random();
        String[] outcome = new String[2];
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

        return outcome;
    }
}