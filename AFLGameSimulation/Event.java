public class Event
{
    public Event()
    {

    }

    public String[] decideOutcome(String fieldPosition, boolean isStarPlayer) //move rand into here?
    {
        Double rand = Math.random();
        String[] outcome = new String[2];
        if (isStarPlayer)
        {
            switch (fieldPosition)
            {
                case "Forward":
                    if (rand < 0.45)
                        outcome[0] = "Goal";
                    else if (rand < 0.85)
                        outcome[0] = "Behind";
                    else if (rand < 0.95)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Forward";
                    }
                    else
                    {
                        outcome[0] = "Turnover";
                        outcome[1] = "Defender";
                    }
                    break;

                case "Midfielder":
                    if (rand < 0.10)
                        outcome[0] = "Goal";
                    else if (rand < 0.20)
                        outcome[0] = "Behind";
                    else if (rand < 0.55)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Forward";
                    }
                    else if (rand < 0.90)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Midfielder";
                    }
                    else
                    {
                        outcome[0] = "Turnover";
                        outcome[1] = "Midfielder";
                    }
                    break;

                case "Defender":
                    if (rand < 0.95)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Midfielder";
                    }
                    else
                    {
                        outcome[0] = "Turnover";
                        outcome[1] = "Forward";
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
                        outcome[0] = "Goal";
                    else if (rand < 0.70)
                        outcome[0] = "Behind";
                    else if (rand < 0.90)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Forward";
                    }
                    else
                    {
                        outcome[0] = "Turnover";
                        outcome[1] = "Defender";
                    }
                    break;

                case "Midfielder":
                    if (rand < 0.05)
                        outcome[0] = "Goal";
                    else if (rand < 0.15)
                        outcome[0] = "Behind";
                    else if (rand < 0.45)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Forward";
                    }
                    else if (rand < 0.75)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Midfielder";
                    }
                    else
                    {
                        outcome[0] = "Turnover";
                        outcome[1] = "Midfielder";
                    }
                    break;

                case "Defender":
                    if (rand < 0.80)
                    {
                        outcome[0] = "Pass";
                        outcome[1] = "Midfielder";
                    }
                    else
                    {
                        outcome[0] = "Turnover";
                        outcome[1] = "Forward";
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