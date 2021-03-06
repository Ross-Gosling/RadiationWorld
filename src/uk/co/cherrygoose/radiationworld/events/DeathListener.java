package uk.co.cherrygoose.radiationworld.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import uk.co.cherrygoose.radiationworld.functions.Radiation;

public class DeathListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		// Gets dead player
		Player player = event.getEntity();
		
		// If player recieved lethal dose of radiation
		if(Radiation.get(player) >= Radiation.dLethal)
		{
			// Sets death message appropriately
			event.setDeathMessage(player.getName() + " was killed by radiation");
		}
		
		// Clears player radiation
		Radiation.set(player, 0.0);
	}
}
