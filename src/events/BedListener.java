package events;

import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import RadiationWorld.Main;

public class BedListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBedEnter(PlayerBedEnterEvent event)
	{
		// If player in Overworld
		if(event.getPlayer().getWorld().getEnvironment().equals(Environment.NORMAL))
		{
			// Gets the distance from the edge of the haven
			double dDistFromHaven = Math.floor(event.getBed().getLocation().distance(event.getPlayer().getWorld().getSpawnLocation())) - Main.config().getDouble("Radiation.Overworld.Haven.Radius");
	
			// If bed outside safe haven
			if (dDistFromHaven > 0)
			{
				// Cancels bed entry
				event.setCancelled(true);
				// Sends player feedback
				event.getPlayer().sendMessage(ChatColor.RED + "Radiation makes it unsafe to sleep here");
			}
		}
	}
}
