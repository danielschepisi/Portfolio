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

	}

	public void addReportedPlayer(Player reported)
	{
		getReportedPlayers().add(reported);
	}

	public Player getBounceWinner()
	{
		return this.bouncerWinner;
	}

	public Player getInjuredPlayer()
	{
		return this.injuredPlayer;
	}

	public PlayerKick getPlayerKick()
	{
		return this.playerKick;
	}

	public Player getReceivingPlayer()
	{
		return this.receivingPlayer;
	}

	public ArrayList<Player> getReportedPlayers()
	{
		return this.reportedPlayers;
	}

	public void setBounceWinner(Player winner)
	{
		this.bouncerWinner = winner;
	}

	public void setInjuredPlayer(Player injured)
	{
		this.injuredPlayer = injured;
	}

	public void setPlayerKick(PlayerKick kick)
	{
		this.playerKick = kick;
	}

	public void setReceivingPlayer(Player player)
	{
		this.receivingPlayer = player;
	}

	public void setReportedPlayers(ArrayList<Player> players)
	{
		this.reportedPlayers = players;
	}
}