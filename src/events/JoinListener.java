package events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import functions.Radiation;

public class JoinListener implements Listener 
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		// Declares Player
		Player player = event.getPlayer();
		
		// Loads player radiation level to hashmap
		Radiation.loadToHashMap(player);
		
		// Sets title time settings
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "title " + player.getName() + " times 0 3 0");	
	}	
}
