package uk.co.cherrygoose.radiationworld.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.cherrygoose.radiationworld.functions.Radiation;

public class QuitListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		// Saves player radiation level to config
		Radiation.saveToConfig(event.getPlayer());
	}
}
