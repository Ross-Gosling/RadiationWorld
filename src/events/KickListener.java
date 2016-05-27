package events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import RadiationWorld.Main;
import functions.Radiation;

public class KickListener implements Listener 
{
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) 
	{
		// Gets kicked player
		Player player = event.getPlayer();
				
		// Removes vanilla message
		event.setLeaveMessage(null);

		// Saves player radiation level to config
		Radiation.saveToConfig(player);
		
    	// Logs to console
    	Bukkit.getLogger().info("["+Main.pluginName+"]" + player.getName() + " was kicked.");
	}
}
