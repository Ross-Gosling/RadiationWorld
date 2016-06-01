package uk.co.cherrygoose.radiationworld.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import uk.co.cherrygoose.radiationworld.functions.Radiation;

public class RespawnListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event) 
	{
		// Gets respawning player
		Player player = event.getPlayer();
		
		// Clears player radiation
		Radiation.set(player, 0.0);
		
		// Resets player max health
		player.setMaxHealth(20.0);
	}
}