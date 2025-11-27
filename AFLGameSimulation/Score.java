public class Score
{
    private int goals;
    private int behinds;

    public Score()
    {
        this.goals = 0;
        this.behinds = 0;
    }

    public void addBehind()
    {
        this.behinds++;
    }

    public void addGoal()
    {
        this.goals++;
    }

    public String display()
    {
        return getGoals() + ", " + getBehinds() + ", " + getPoints();
    }

    public int getBehinds()
    {
        return this.behinds;
    }

    public int getGoals()
    {
        return this.goals;
    }

    public int getPoints()
    {
        return getGoals() * 6 + getBehinds();
    }

    public void setBehinds(int behinds)
    {
        this.behinds = behinds;
    }

    public void setGoals(int goals)
    {
        this.goals = goals;
    }
}