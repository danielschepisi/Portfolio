/**
 * This class is responible for holding all the information
 * for all the resulting outcomes during an event of the game
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
import java.util.ArrayList;

public class Event
{
	private Player bouncerWinner;
	private Player injuredPlayer;
	private PlayerKick playerKick;
	private Player receivingPlayer;
	private ArrayList<Player> reportedPlayers;

	public Event()
	{
		this.reportedPlayers = new ArrayList<Player>();
	}

	/**
    * Adds a reported player to the event
    * @param    reported    The player who was reported
    */
	public void addReportedPlayer(Player reported)
	{
		getReportedPlayers().add(reported);
	}

	/**
    * Gets the winner of the bounce if there is one
    */
	public Player getBounceWinner()
	{
		return this.bouncerWinner;
	}

	/**
    * Gets any injured players
    */
	public Player getInjuredPlayer()
	{
		return this.injuredPlayer;
	}

	/**
    * Returns the name of the player who kicked
	* the ball
    */
	public String getKickingPlayerName()
	{
		return getPlayerKick().getPlayerName();
	}

	/**
    * Returns the playerKick associated with the event
    */
	public PlayerKick getPlayerKick()
	{
		return this.playerKick;
	}

	/**
    * Returns the player receiving the ball if there is one
    */
	public Player getReceivingPlayer()
	{
		return this.receivingPlayer;
	}

	/**
    * Returns any reported players during the event
    */
	public ArrayList<Player> getReportedPlayers()
	{
		return this.reportedPlayers;
	}

	/**
    * Returns the result from the event's playerKick
    */
	public String getResult()
	{
		return getPlayerKick().getResult();
	}

	/**
    * Set the bounce winner on the event
    * @param    winner    The player who won the bounce
    */
	public void setBounceWinner(Player winner)
	{
		this.bouncerWinner = winner;
	}

	/**
    * Adds any injured players to the event
    * @param    injured    The player who was injured
    */
	public void setInjuredPlayer(Player injured)
	{
		this.injuredPlayer = injured;
	}

	/**
    * Sets the playerKick on the event
    * @param    kick    The playerKick for the event
    */
	public void setPlayerKick(PlayerKick kick)
	{
		this.playerKick = kick;
	}

	/**
    * Sets the receiving player on the event
    * @param    player    The player receiving the ball
    */
	public void setReceivingPlayer(Player player)
	{
		this.receivingPlayer = player;
	}

	/**
    * Sets the reported players on the event
    * @param    players    The players who were reported
    */
	public void setReportedPlayers(ArrayList<Player> players)
	{
		this.reportedPlayers = players;
	}
}