package events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import functions.Radiation;

public class RespawnListener implements Listener 
{
	public void onPlayerRespawn(PlayerRespawnEvent event) 
	{
		// Gets respawning player
		Player player = event.getPlayer();
		
		// Clears player radiation
		Radiation.set(player, 0.0);
		
		// Resets player health
		player.setHealth(player.getMaxHealth());
	}
}