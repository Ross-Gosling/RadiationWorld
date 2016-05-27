package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import functions.Radiation;

public class KickListener implements Listener 
{
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) 
	{
		// Saves player radiation level to config
		Radiation.saveToConfig(event.getPlayer());
	}
}
