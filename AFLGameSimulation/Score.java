/**
 * This class keeps track of AFL scoring for a single team 
 * which contains goals, behind and total points.
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
public class Score
{
    private int goals;
    private int behinds;

    public Score()
    {
        this.goals = 0;
        this.behinds = 0;
    }

    /**
    * Adds a behind to the score
    */
    public void addBehind()
    {
        this.behinds++;
    }

    /**
    * Adds a goal to the score
    */
    public void addGoal()
    {
        this.goals++;
    }

    /**
    * Returns a string with the state of the score
    */
    public String display()
    {
        return getGoals() + ", " + getBehinds() + ", " + getPoints();
    }

    /**
    * Returns the number of behinds
    */
    public int getBehinds()
    {
        return this.behinds;
    }

    /**
    * Returns the number of goals
    */
    public int getGoals()
    {
        return this.goals;
    }

    /**
    * Calculates and returns the number of points
    */
    public int getPoints()
    {
        return getGoals() * 6 + getBehinds();
    }

    /**
    * Sets the number of behinds
    * @param    behinds    The number of behind
    */
    public void setBehinds(int behinds)
    {
        this.behinds = behinds;
    }

    /**
    * Sets the number of goals
    * @param    goals    The number of goals
    */
    public void setGoals(int goals)
    {
        this.goals = goals;
    }
}