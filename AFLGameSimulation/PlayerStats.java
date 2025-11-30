public class PlayerStats
{
	private Player player;
	private String name;
	private Team team;
	private int kicks;
	private int goals;
	private int behinds;
	private int passes;
	private int turnovers;
	private boolean isInjured;
	private boolean isReported;
	private double effectiveDisposals;

	public PlayerStats()
	{

	}

    public PlayerStats(Player player, Team team) {
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

	public Team getTeam()
	{
		return this.team;
	}

	public void setTeam(Team team)
	{
		this.team = team;
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

	public String display() //add the rest
	{
		return getKicks() + " " + getGoals();
	}

	public int getPasses()
	{
		return this.passes;
	}

	public int getTurnovers()
	{
		return this.turnovers;
	}

	public String getName()
	{
		return this.name;
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

	public void setTurnovers(int turnover)
	{
		this.turnovers = turnovers;
		calculateEffectDisposals();
	}

    public Player getPlayer()
	{
        return this.player;
    }

    public void setPlayer(Player player)
	{
        this.player = player;
    }

    public int getKicks()
	{
        return this.kicks;
    }

    public void setKicks(int kicks)
	{
        this.kicks = kicks;
		calculateEffectDisposals();
    }

    public int getGoals()
	{
        return this.goals;
    }

    public void setGoals(int goals)
	{
        this.goals = goals;
		calculateEffectDisposals();
    }

    public int getBehinds()
	{
        return this.behinds;
    }

    public void setBehinds(int behinds)
	{
        this.behinds = behinds;
		calculateEffectDisposals();
    }
	
    public boolean isInjured()
	{
        return this.isInjured;
    }

    public void setInjured(boolean injured)
	{ 
        this.isInjured = injured;
    }

    public boolean isReported()
	{
        return this.isReported;
    }

    public void setReported(boolean isReported)
	{
        this.isReported = isReported;
    }

    public double getEffectiveDisposals() //maybe rename to percent
	{
        return this.effectiveDisposals;
    }

    public void setEffectiveDisposals(double effectiveDisposals)
	{
        this.effectiveDisposals = effectiveDisposals;
    }

	private void calculateEffectDisposals()
	{
		setEffectiveDisposals((double)(getGoals() + getPasses() + getBehinds()) * 100 / (double)getKicks());
	}
}