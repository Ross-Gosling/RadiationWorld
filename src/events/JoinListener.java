package events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import functions.Radiation;

public class JoinListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		// Loads player radiation level to hashmap
		Radiation.loadToHashMap(event.getPlayer());
		
		// Sets title time settings
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "title " + event.getPlayer().getName() + " times 0 3 0");	
	}	
}
