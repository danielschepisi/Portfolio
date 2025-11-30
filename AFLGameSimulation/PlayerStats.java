/**
 * The class is responsible for housing all the statistics
 * of a player
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
public class PlayerStats
{
	private int behinds;
	private double effectiveDisposals;
	private int goals;
	private boolean isInjured;
	private boolean isReported;
	private int kicks;
	private String name;
	private int passes;
	private Player player;
	private Team team;
	private int turnovers;
	
	
	public PlayerStats()
	{

	}

    public PlayerStats(Player player, Team team) 
	{
        this.player = player;
		this.name = player.getPlayerName();
		this.team = team;
        this.kicks = 0;
        this.goals = 0;
        this.behinds = 0;
        this.isInjured = false;
        this.isReported = false;
        this.effectiveDisposals = 0.0;
    }

	/**
    * Increments the specified field by one. Or sets
	* true for booleans
    * @param    field    The field that is being incremented
    */
	public void add(String field)
	{
		switch (field)
		{
			case "Kick":
				setKicks(getKicks() + 1);
				break;
			case "Goal":
				setGoals(getGoals() + 1);
				break;
			case "Behind":
				setBehinds(getBehinds() + 1);
				break;
			case "Pass":
				setPasses(getPasses() + 1);
				break;
			case "Turnover":
				setTurnovers(getTurnovers() + 1);
				break;
			case "isInjured":
				setInjured(true);
				break;
			case "isReported":
				setReported(true);
				break;
			default:
				//something went wrong
				break;
		}
	}

	/**
    * Increments the specified field by one. Or sets
	* true for booleans
    * @param    field    The field that is being incremented
    */
	private void calculateEffectDisposals()
	{
		setEffectiveDisposals((double)(getGoals() + getPasses() + getBehinds()) * 100 / (double)getKicks());
	}

	/**
    * Returns a string with the state of the PlayerStats
    */
	public String display()
	{
		return "Name: " + getName()
			+ " Kicks: " + getKicks()
			+ " Goals: " + getGoals()
			+ " Behinds: " + getBehinds()
			+ " EffectiveDisposals: " + getEffectiveDisposals()
			+ " Injured : " + isInjured()
			+ " Reported: " + isReported()
			+ " Team: " + getTeam()
			+ " Passes: " + getPasses()
			+ " Turnover: " + getTurnovers();
	}

	/**
    * Gets the behinds for the player
    */
    public int getBehinds()
	{
        return this.behinds;
    }

	/**
    * Gets the effective disposals percentage
    */
	public double getEffectiveDisposals()
	{
        return this.effectiveDisposals;
    }

	/**
    * Gets the goals for the player
    */
	public int getGoals()
	{
        return this.goals;
    }

	/**
    * Gets the total kicks of the player
    */
    public int getKicks()
	{
        return this.kicks;
    }

	/**
    * Gets the name of the player
    */
	public String getName()
	{
		return this.name;
	}

	/**
    * Gets the passes of the player
    */
	public int getPasses()
	{
		return this.passes;
	}

	/**
    * Gets the player
    */
    public Player getPlayer()
	{
        return this.player;
    }

	/**
    * Gets the team of the player
    */
	public Team getTeam()
	{
		return this.team;
	}

	/**
    * Gets the turnovers of the player
    */
	public int getTurnovers()
	{
		return this.turnovers;
	}

	/**
    * Returns if the player is injured
    */
    public boolean isInjured()
	{
        return this.isInjured;
    }

	/**
    * Returns if the players is reported
    */
    public boolean isReported()
	{
        return this.isReported;
    }

	/**
    * Sets the behind for the player
    * @param    behinds    The number of behinds
    */
    public void setBehinds(int behinds)
	{
        this.behinds = behinds;
		calculateEffectDisposals();
    }

	/**
    * Sets the effective disposals
    * @param    effectiveDisposals    The effective disposal percentage
    */
    public void setEffectiveDisposals(double effectiveDisposals)
	{
        this.effectiveDisposals = effectiveDisposals;
    }

	/**
    * Sets the number of goals of the player
    * @param    goals    The number of goals
    */
    public void setGoals(int goals)
	{
        this.goals = goals;
		calculateEffectDisposals();
    }

	/**
    * Sets if the player is injured
    * @param    injured    If the player is injured
    */
	public void setInjured(boolean injured)
	{ 
        this.isInjured = injured;
    }

	/**
    * Sets the number of kicks of the player
    * @param    kicks    The number of kicks
    */
    public void setKicks(int kicks)
	{
        this.kicks = kicks;
		calculateEffectDisposals();
    }

	/**
    * Sets the name of the player
    * @param    name    The name of the player
    */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
    * Sets the number of passes of the player
    * @param    passes    The number of passes
    */
	public void setPasses(int passes)
	{
		this.passes = passes;
		calculateEffectDisposals();
	}

	/**
    * Sets the player who has the stats
    * @param    player    The player with the stats
    */
    public void setPlayer(Player player)
	{
        this.player = player;
    }

	/**
    * Sets if the player is reported or not
    * @param    isReported    If the player is reported
    */
    public void setReported(boolean isReported)
	{
        this.isReported = isReported;
    }

	/**
    * Sets the team of the player
    * @param    team    The player's team
    */
	public void setTeam(Team team)
	{
		this.team = team;
	}

	/**
    * Sets the number of turnovers for the player
    * @param    turnover    The number of turnovers
    */
	public void setTurnovers(int turnover)
	{
		this.turnovers = turnovers;
		calculateEffectDisposals();
	}
}