package uk.co.cherrygoose.radiationworld.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import uk.co.cherrygoose.radiationworld.functions.Radiation;

public class KickListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) 
	{
		// Saves player radiation level to config
		Radiation.saveToConfig(event.getPlayer());
	}
}
