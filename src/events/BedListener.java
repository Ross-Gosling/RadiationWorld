package events;

import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import RadiationWorld.Main;

public class BedListener implements Listener {

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event)
	{
		// Gets player
		Player player = event.getPlayer();
		
		// If player in Overworld
		if(player.getWorld().getEnvironment().equals(Environment.NORMAL))
		{
			// Gets the distance from the edge of the haven
			double dDistFromHaven = Math.floor(player.getLocation().distance(player.getWorld().getSpawnLocation())) - Main.config().getDouble("Radiation.Overworld.Haven.Radius");
	
			// If bed outside safe haven
			if (dDistFromHaven > 0)
			{
				// Cancels bed entry
				event.setCancelled(true);
				// Sends player feedback
				player.sendMessage(ChatColor.RED + "Radiation makes it unsafe to sleep here");
			}
			// Else bed inside safe haven
			else 
			{
				
			}
		}
	}
}
