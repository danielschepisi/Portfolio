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

	private void calculateEffectDisposals()
	{
		setEffectiveDisposals((double)(getGoals() + getPasses() + getBehinds()) * 100 / (double)getKicks());
	}

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

    public int getBehinds()
	{
        return this.behinds;
    }

	public double getEffectiveDisposals()
	{
        return this.effectiveDisposals;
    }

	public int getGoals()
	{
        return this.goals;
    }

    public int getKicks()
	{
        return this.kicks;
    }

	public String getName()
	{
		return this.name;
	}

	public int getPasses()
	{
		return this.passes;
	}

    public Player getPlayer()
	{
        return this.player;
    }

	public Team getTeam()
	{
		return this.team;
	}

	public int getTurnovers()
	{
		return this.turnovers;
	}

    public boolean isInjured()
	{
        return this.isInjured;
    }

    public boolean isReported()
	{
        return this.isReported;
    }

    public void setBehinds(int behinds)
	{
        this.behinds = behinds;
		calculateEffectDisposals();
    }

    public void setEffectiveDisposals(double effectiveDisposals)
	{
        this.effectiveDisposals = effectiveDisposals;
    }

    public void setGoals(int goals)
	{
        this.goals = goals;
		calculateEffectDisposals();
    }

	public void setInjured(boolean injured)
	{ 
        this.isInjured = injured;
    }

    public void setKicks(int kicks)
	{
        this.kicks = kicks;
		calculateEffectDisposals();
    }

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPasses(int passes)
	{
		this.passes = passes;
		calculateEffectDisposals();
	}

    public void setPlayer(Player player)
	{
        this.player = player;
    }

    public void setReported(boolean isReported)
	{
        this.isReported = isReported;
    }

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public void setTurnovers(int turnover)
	{
		this.turnovers = turnovers;
		calculateEffectDisposals();
	}
}