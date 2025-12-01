/**
 * The class is a substitute for the testing strategy 
 * of the Player class.
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */

/* NOTE: This class is made instead of a testing strategy
   document becuase it can be submitted in Ed.
   There is a helper testing method listing all the required tests. 
   As this is meant to be a testing strategy document I've omitted
   documentation of the method but instead placed the expected and 
   actual test results below.
 */


public class Test
{
	public Test()
	{

	}

	public void allTests()
	{
		testConstructorDefault();
		testCustomConstructor();
		testCustomConstructorNegativeName();
		testCustomConstructorNegativePosition();
		testCustomConstructorNegativeGoals();
		testGetFieldPosition();
		testGetPlayerName();
		testGetSeasonGoals();
		testIncrementSeasonGoals();
		testInjure();
		testIsInjured();
		testIsReported();
		testIsStarPlayer();
		testKick();
		testSetFieldPosition();
		testSetFieldPositionNegative();
		testSetIsInjured();
		testSetIsReported();
		testSetPlayerName();
		testSetPlayerNameNegative();
		testSetSeasonGoals();
		testSetSeasonGoalsNegative();
		testSetStarPlayer();
	}


	
	public void testConstructorDefault()
	{
		System.out.println(new Player().display());
	}
	/*
	Expected: No Name Reserve starPlayer: false goals: 0 
			injured: false reported: false
	
	Actual:No Name Reserve starPlayer: false goals: 0 
			injured: false reported: false
	*/

	public void testCustomConstructor()
	{
		Player player = new Player("Tom", "Forward", 3);
		System.out.println(player.display());
	}
	/*
	Expected: Tom Forward starPlayer: false goals: 3 
			injured: false reported: false

	Actual: Tom Forward starPlayer: false goals: 3 
			injured: false reported: false
	*/

	public void testCustomConstructorNegativeName()
	{
		try
		{
			Player player = new Player(" ", "Forward", 3);
			System.out.println(player.display());
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
	/*
	Expected: PlayerName can't be blank.
	
	Actual: PlayerName can't be blank.
	*/

	public void testCustomConstructorNegativePosition()
	{
		try
		{
			Player player = new Player("Tom", "Anywhere", 3);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
	/*
	Expected: FieldPosition doesn't match avaiable options. Received: Anywhere
	
	Actual: FieldPosition doesn't match avaiable options. Received: Anywhere
	*/

	public void testCustomConstructorNegativeGoals()
	{
		try
		{
			Player player = new Player("Tom", "Forward", -2);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
	/*
	Expected: SeasonGoals can't be negative. Received: -2
	
	Actual: SeasonGoals can't be negative. Received: -2
	*/

	public void testGetFieldPosition()
	{
		Player player = new Player();
		System.out.println(player.getFieldPosition());
	}
	/*
	Expected: Reserve
	
	Actual: Reserve
	*/

	public void testGetPlayerName()
	{
		Player player = new Player();
		System.out.println(player.getPlayerName());
	}
	/*
	Expected: No Name
	
	Actual: No Name
	*/

	public void testGetSeasonGoals()
	{
		Player player = new Player();
		System.out.println(player.getSeasonGoals());
	}
	/*
	Expected: 0
	
	Actual: 0
	*/

	public void testIncrementSeasonGoals()
    {
		Player player = new Player();
		player.incrementSeasonGoals();
		System.out.println(player.getSeasonGoals());
    }
	/* 
	Expected: 1
	
	Actual: 1
	*/

	public void testInjure()
	{
		Player player = new Player();
		player.injure();
		System.out.println(player.display());
	}
	/*
	Expected: No Name Reserve starPlayer: false goals: 0 
			injured: true reported: false

	Actual: No Name Reserve starPlayer: false goals: 0 
			injured: true reported: false
	 */

	public void testIsInjured()
	{
		Player player = new Player();
		System.out.println(player.isInjured());
	}
	/* 
	Expected: false
	
	Actual: false
	*/

	public void testIsReported()
	{
		Player player = new Player();
		System.out.println(player.isReported());
	}
	/* 
	Expected: false
	
	Actual: false
	*/

	public void testIsStarPlayer()
	{
		Player player = new Player();
		System.out.println(player.isStarPlayer());
	}
	/* 
	Expected: false
	
	Actual: false
	*/

	public void testKick()
	{
		Player player = new Player("Tom", "Forward", 3);
		PlayerKick playerKick = player.kick();
		System.out.println(playerKick.getPlayer().equals(player));
	}
	/* 
	Expected: true
	
	Actual: true
	*/

	public void testSetFieldPosition()
	{
		Player player = new Player();
		player.setFieldPosition("Forward");
		System.out.println(player.display());
	}
	/*
	Expected: No Name Forward starPlayer: false goals: 0 
			injured: false reported: false
	
	Actual: No Name Forward starPlayer: false goals: 0 
			injured: false reported: false
	*/

	public void testSetFieldPositionNegative()
	{
		Player player = new Player();
		player.setFieldPosition("Bench");
		System.out.println(player.display());
	}
	/*
	Expected: No Name Bench starPlayer: false goals: 0 
			injured: false reported: false
	
	Actual: No Name Bench starPlayer: false goals: 0 
			injured: false reported: false
	*/

	public void testSetIsInjured()
	{
		Player player = new Player();
		player.setIsInjured(true);
		System.out.println(player.display());
	}
	/*
	Expected: No Name Reserve starPlayer: false goals: 0 
			injured: true reported: false
	
	Actual: No Name Reserve starPlayer: false goals: 0 
			injured: true reported: false
	*/

	public void testSetIsReported()
	{
		Player player = new Player();
		player.setIsReported(true);
		System.out.println(player.display());
	}
	/*
	Expected: No Name Reserve starPlayer: false goals: 0 
			injured: false reported: true
	
	Actual: No Name Reserve starPlayer: false goals: 0 
			injured: false reported: true
	*/

	public void testSetPlayerName()
	{
		Player player = new Player();
		player.setPlayerName("Wayne G");
		System.out.println(player.display());
	}
	/*
	Expected: Wayne G Reserve starPlayer: false goals: 0 
			injured: false reported: false
	
	Actual: Wayne G Reserve starPlayer: false goals: 0 
			injured: false reported: false
	*/

	public void testSetPlayerNameNegative()
	{
		Player player = new Player();
		player.setPlayerName(" ");
		System.out.println(player.display());
	}
	/*
	Expected: Can't set blank playerName.
		No Name Reserve starPlayer: false goals: 0 
		injured: false reported: false
	
	Actual: Can't set blank playerName.
		No Name Reserve starPlayer: false goals: 0 
		injured: false reported: false
	*/

	public void testSetSeasonGoals()
	{
		Player player = new Player();
		player.setSeasonGoals(4);
		System.out.println(player.display());
	}
	/*
	Expected: No Name Reserve starPlayer: false goals: 4 
		injured: false reported: false
	
	Actual: No Name Reserve starPlayer: false goals: 4 
		injured: false reported: false
	*/

	public void testSetSeasonGoalsNegative()
	{
		Player player = new Player();
		player.setSeasonGoals(-3);
		System.out.println(player.display());
	}
	/*
	Expected: SeasonGoals can't be negative
		No Name Reserve starPlayer: false goals: 0 
		injured: false reported: false
	
	Actual:
	*/

	public void testSetStarPlayer()
	{
		Player player = new Player();
		player.setStarPlayer(true);
		System.out.println(player.display());
	}
	/*
	Expected: No Name Reserve starPlayer: true goals: 0 
		injured: false reported: false
	
	Actual: No Name Reserve starPlayer: true goals: 0 
		injured: false reported: false
	*/
}