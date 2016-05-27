package events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import RadiationWorld.Main;

public class SpawnListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMobSpawn(CreatureSpawnEvent event) 
	{
		// Gets type of entity spawned
		EntityType type = event.getEntityType();
			
		// If the type of mob is any of the following
		if((type == EntityType.COW) || (type == EntityType.PIG) || (type == EntityType.SHEEP) || (type == EntityType.CHICKEN) || (type == EntityType.RABBIT)
		|| (type == EntityType.HORSE) || (type == EntityType.OCELOT) || (type == EntityType.WOLF) ||  (type == EntityType.BAT) || (type == EntityType.SQUID)) 
		{
			// Gets entity
		    Entity entity = event.getEntity();
		        
		    // If entity is a livingEntity
	        if(entity instanceof LivingEntity)
	        {
		        // Casts entity to livingEntity
	            LivingEntity livingEntity = (LivingEntity) entity;
	            
	            // Sets the mob to persistant
	            livingEntity.setRemoveWhenFarAway(false);

				// Creates a random number
				int random = 0; random = (int) (Math.random() * 100);
				
				// % chance to spawn
				if(random > Main.config().getDouble("Radiation.Overworld.Animals.ChanceToSpawn"))
				{
					// Nothing
				}
				else 
				{
					// Removes mob
		            event.setCancelled(true);
				}
	        }
		}
	}
}