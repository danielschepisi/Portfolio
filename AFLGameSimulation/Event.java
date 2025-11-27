public class Event
{
    //constructor

    public String decideOutcome(double rand, String fieldPosition, boolean isStarPlayer) //move rand into here?
    {
        String outcome = "";
        if (isStarPlayer)
        {
            switch (fieldPosition.toLowerCase())
            {
                case "forward":
                    if (rand < 0.45)
                        outcome = "Goal";
                    else if (rand < 0.85)
                        outcome = "Behind";
                    else if (rand < 0.95)
                        outcome = "Pass Forward";
                    else
                        outcome = "Turnover Defender";
                        break;

                case "midfielder":
                    if (rand < 0.10)
                        outcome = "Goal";
                    else if (rand < 0.20)
                        outcome = "Behind";
                    else if (rand < 0.55)
                        outcome = "Pass Forward";
                    else if (rand < 0.90)
                        outcome = "Pass Midfielder";
                    else
                        outcome = "Turnover Midfielder";
                        break;

                case "defender":
                    if (rand < 0.95)
                        outcome = "Pass Midfielder";
                    else
                        outcome = "Turnover Forward";
                        break;

                default:
                    //something went wrong
                    break;
            }
        }
        else 
        {
            switch (fieldPosition.toLowerCase())
            {
                case "forward":
                    if (rand < 0.30)
                        outcome = "Goal";
                    else if (rand < 0.70)
                        outcome = "Behind";
                    else if (rand < 0.90)
                        outcome = "Pass Forward";
                    else
                        outcome = "Turnover Defender";
                        break;

                case "midfielder":
                    if (rand < 0.05)
                        outcome = "Goal";
                    else if (rand < 0.15)
                        outcome = "Behind";
                    else if (rand < 0.45)
                        outcome = "Pass Forward";
                    else if (rand < 0.75)
                        outcome = "Pass Midfielder";
                    else
                        outcome = "Turnover Midfielder";
                        break;

                case "defender":
                    if (rand < 0.80)
                        outcome = "Pass Midfielder";
                    else
                        outcome = "Turnover Forward";
                        break;

                default:
                    //something went wrong
                    break;
            }

        }

        return outcome;
    }

}