package events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import RadiationWorld.Main;
import functions.Radiation;

public class RespawnListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event) 
	{
    	// Logs to console
    	Bukkit.getLogger().info("["+Main.pluginName+"] onPlayerRespawn called");
		
		// Gets respawning player
		Player player = event.getPlayer();
		
		// Clears player radiation
		Radiation.set(player, 0.0);
		
		// Resets player health
		player.setMaxHealth(20.0);
		player.setHealth(player.getMaxHealth());
	}
}