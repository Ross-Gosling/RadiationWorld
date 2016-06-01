package uk.co.cherrygoose.radiationworld.functions;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uk.co.cherrygoose.radiationworld.Main;

public class Radiation 
{
	// HashMap for storing online player radiation levels
	private static HashMap<Player, Double> radiation = new HashMap<Player, Double>();
	public static double dLethal = Main.config().getDouble("Radiation.Lethal");
	
	public static void update()
	{
		// For all Players
    	for(Player player: Bukkit.getServer().getOnlinePlayers())
    	{
    		// If player is alive & in survival/adv mode
    		if((!player.isDead()) && ((player.getGameMode().equals(GameMode.SURVIVAL)) || (player.getGameMode().equals(GameMode.ADVENTURE))))
    		{
	    		// Declares double to increment radiation
	    		double dRadIncrement = 0.0;
	    		    		
	    		// If Player in the Overworld
	    		if(player.getWorld().getEnvironment().equals(Environment.NORMAL)) 
	    		{
	    			// Gets the distance from the edge of the haven
	    			double dDistFromHaven = Math.floor(player.getLocation().distance(player.getWorld().getSpawnLocation())) - Main.config().getDouble("Radiation.Overworld.Haven.Radius");
	
	    			// If outside safe haven
	    			if(dDistFromHaven > 0)
	    			{
	        			// Gets the background radiation
	        			double dBGRadiaiton = Main.config().getDouble("Radiation.Overworld.Background") / 20; // Divided by 20 to get per tick
	        			// Gets the buffer distance from safe 0 haven rads to full BGRads
	        			double dBuffer = Main.config().getDouble("Radiation.Overworld.Haven.Buffer");
	        			
	        			// Calcs radiation gain 
	        			double dRadiationGain = dBGRadiaiton / dBuffer * dDistFromHaven;
	        			
	        			// If radiation gain is higher than BGRadiation
	        			if (dRadiationGain > dBGRadiaiton)
	        			{
	        				// Caps radiation gain to BGRadiation
	        				dRadiationGain = dBGRadiaiton;
	        			}
	        			
	        			// Applies calculated radiation gain to increment
	        			dRadIncrement += dRadiationGain;
		    		}
	    			
	    			// If Player in sunlight
	    			if(inSunlight(player))
	    			{
	    				// Adds the sun's radiation to the increment
	    				dRadIncrement += Main.config().getDouble("Radiation.Sun") / 20; // Divided by 20 to get per tick
	    			}
	    		}
	    		
	    		// If Player in the Nether
	    		else if(player.getWorld().getEnvironment().equals(Environment.NETHER)) 
	    		{
	    			// Adds the background radiation to the increment
	    			dRadIncrement = Main.config().getDouble("Radiation.Nether.Background") / 20; // Divided by 20 to get per tick
	    		}
	    		
	    		// If Player in the End
	    		else if(player.getWorld().getEnvironment().equals(Environment.THE_END)) 
	    		{
	    			// Adds the background radiation to the increment
	    			dRadIncrement = Main.config().getDouble("Radiation.End.Background") / 20; // Divided by 20 to get per tick
	    		}
	    		
    			// If increment is less than or equal to 0
    			if(dRadIncrement <= 0)
    			{
    				// Sets the increment to the deradiation value
    				dRadIncrement = -(Main.config().getDouble("Radiation.Recovery") / 20); // Divided by 20 to get per tick
    			}
    			// Else increment is greater than 0
    			else
    			{
    	    		// Applies resistance to the increment of radiation
    	    		dRadIncrement = applyResistance(player, dRadIncrement);
    			}
				
				// Applies the increment to the player
	    		set(player, get(player) + dRadIncrement);
	    		
	    		// Applies effects to the player based on their rad level
	    		applyEffects(player, get(player));
    		}

    		// Calls displayRadiation with the player
    		display(player, get(player));
    	}
	}
		
	private static double applyResistance(Player player, double dRadiation)
	{
		// Declares double for resistance percentage
		double dResistance = 0.0;
		
		double resistLeather = Main.config().getDouble("Radiation.Resists.Leather");
		double resistGold = Main.config().getDouble("Radiation.Resists.Gold");
		double resistIron = Main.config().getDouble("Radiation.Resists.Iron");
		double resistDiamond = Main.config().getDouble("Radiation.Resists.Diamond");
		
		if(((player.getInventory().getHelmet() != null)) && (player.getInventory().getHelmet().getType() != Material.AIR))
		{
			double proportion = ((double)5/24);
			
			if(player.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET))
				dResistance += resistLeather * proportion;

			else if(player.getInventory().getHelmet().getType().equals(Material.GOLD_HELMET))
				dResistance += resistGold * proportion;
			
			else if((player.getInventory().getHelmet().getType().equals(Material.IRON_HELMET)) || (player.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_HELMET)))
				dResistance += resistIron * proportion;
				
			else if(player.getInventory().getHelmet().getType().equals(Material.DIAMOND_HELMET))
				dResistance += resistDiamond * proportion;
		}

		if(((player.getInventory().getChestplate() != null)) && (player.getInventory().getChestplate().getType() != Material.AIR))
		{
			double proportion = ((double)8/24);

			if(player.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE))
				dResistance += resistLeather * proportion;

			else if(player.getInventory().getChestplate().getType().equals(Material.GOLD_CHESTPLATE))
				dResistance += resistGold * proportion;
			
			else if((player.getInventory().getChestplate().getType().equals(Material.IRON_CHESTPLATE)) || (player.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_CHESTPLATE)))
				dResistance += resistIron * proportion;
				
			else if(player.getInventory().getChestplate().getType().equals(Material.DIAMOND_CHESTPLATE))
				dResistance += resistDiamond * proportion;
		}
		
		if(((player.getInventory().getLeggings() != null)) && (player.getInventory().getLeggings().getType() != Material.AIR))
		{
			double proportion = ((double)7/24);

			if(player.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS))
				dResistance += resistLeather * proportion;

			else if(player.getInventory().getLeggings().getType().equals(Material.GOLD_LEGGINGS))
				dResistance += resistGold * proportion;
			
			else if((player.getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS)) || (player.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_LEGGINGS)))
				dResistance += resistIron * proportion;
				
			else if(player.getInventory().getLeggings().getType().equals(Material.DIAMOND_LEGGINGS))
				dResistance += resistDiamond * proportion;
		}
		
		if(((player.getInventory().getBoots() != null)) && (player.getInventory().getBoots().getType() != Material.AIR))
		{
			double proportion = ((double)4/24);
			
			if(player.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS))
				dResistance += resistLeather * proportion;

			else if(player.getInventory().getBoots().getType().equals(Material.GOLD_BOOTS))
				dResistance += resistGold * proportion;
			
			else if((player.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) || (player.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_BOOTS)))
				dResistance += resistIron * proportion;
				
			else if(player.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS))
				dResistance += resistDiamond * proportion;
		}
		
		// Rounds resistance to 2 decimal places
		dResistance = Math.round(dResistance*100)/100.0d;
		
		// Returns resultant radiation increment * -resistance
		return dRadiation - dRadiation * dResistance;
	}
	
	private static void applyEffects(Player player, double dRadiation)
	{
		// Calculates the percentage of radiation you have until death
		double dPercentage = (dRadiation / dLethal * 100);

		// If radiation greater or equal to 50% lethality
		if(dPercentage >= 50)
		{
			double beforeMaxHP = player.getMaxHealth();
			
			// Declares int to store health reduction
			int reducedMaxHP = 0;
			
			// Calcs reducedMaxHP
			reducedMaxHP = (int) Math.floor((dPercentage - 50) / 5);
			
			// If player would have more than 0
			if((10 - reducedMaxHP) > 0)
			{			
				// If playerMaxHP is not the correct value
				if (player.getMaxHealth() != (10 - reducedMaxHP)*2)
				{
					// Subtracts reducedMaxHP from default max
					player.setMaxHealth((10 - reducedMaxHP)*2);
					
					// If maxhp has lowered since before
					if(player.getMaxHealth() < beforeMaxHP)
					{
						// Flicker player screen
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false, false));
						// Play sound
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f);
					}	
				}
			}
		}
		else 
		{
			// Sets player maxhp to normal
			player.setMaxHealth(20.0);
		}
				
		// If radiation greater or equal to 100% lethality
		if((dPercentage >= 100) && (player.getHealth() > 0))
		{	
			// Kills player
			player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 0, false, false));
		}
	}
	
	private static boolean inSunlight(Player player)
	{
		// If the sky is clear
		if(!((player.getWorld().hasStorm()) || (player.getWorld().isThundering())))
		{
			// If it's daytime
			if(!((player.getWorld().getTime() > 12900) && (player.getWorld().getTime() < 23100)))
			{
				// Defines var for the block at player head location
				Block headBlock = player.getLocation().add(0, 1, 0).getBlock();
				
				// If Player in water
				if((headBlock.getType().equals(Material.WATER)) || (headBlock.getType().equals(Material.STATIONARY_WATER)))
				{
					// Defines block at the surface of the water
					Block surfaceBlock = headBlock.getLocation().add(0, 1, 0).getBlock();
					// Declares depth of 1 block
					int depth = 1;
					
					// While the surface block is still actually water
					while((surfaceBlock.getType().equals(Material.WATER)) || (surfaceBlock.getType().equals(Material.STATIONARY_WATER)))
					{
						// Sets surface block to one above current position
						surfaceBlock = surfaceBlock.getLocation().add(0,1,0).getBlock();
						// Increments depth
						depth++;
					}
					
					// If surface in skylight level of 14 or greater
					if(surfaceBlock.getLightFromSky() >= (byte) 14) 
					{
						// If Player is equal to or less than 4 blocks deep in water
						if(depth <= 4)
						{
							// Returns True: Player in sunlight and not deep enough to avoid radiation
							return true;
						}
					}
				}
				else
				{
					// If Player in skylight level of 14 or greater
					if(headBlock.getLightFromSky() >= (byte) 14) 
					{
						// Returns True: Player in sunlight
						return true;
					}
				}
			}
		}
		
		// Returns False: Player not in sunlight
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private static void display(Player player, double dRadiation)
	{
		// Boolean to show/hide display
		boolean showRad = false;

		// If item in mainhand is not equal to null
		if(player.getInventory().getItemInMainHand() != null)
		{
			// If item in mainhand is a watch
			if (player.getInventory().getItemInMainHand().getType().equals(Material.WATCH))
			{
				// Set showRad to true
				showRad = true;	
			}
		}
		
		// If item in offhand is not equal to null
		if(player.getInventory().getItemInOffHand() != null)
		{
			// If item in offhand is a watch
			if (player.getInventory().getItemInOffHand().getType().equals(Material.WATCH))
			{
				// Set showRad to true
				showRad = true;	
			}
		}	
		
		// If showRad is true
		if(showRad)
		{
			// Calculates the percentage of radiation you have until death
			double dPercentage = (dRadiation / dLethal * 100);
			
			// Rounds percentage to 1dp
			dPercentage = Math.round(dPercentage*10)/10.0d;
			
			// Colour for display text
			ChatColor displayColour = ChatColor.WHITE;
				
			// If % less than 50
			if(dPercentage < 50)
			{
				// Sets display colour
				displayColour = ChatColor.GREEN;
			}
			// If % less than 75
			else if(dPercentage < 75)
			{
				// Sets display colour
				displayColour = ChatColor.GOLD;
			}
			// Else % great than 75
			else
			{
				// Sets display colour
				displayColour = ChatColor.DARK_RED;
			}
			
			// Displays a subtitle to player
			player.sendTitle("", displayColour + "Radiation: " + dPercentage + "%");
		}
	}
	
	public static void set(Player player, double dRadiation)
	{
		// Bounds radiation to 0
		if(dRadiation <= 0)
		{
			// Sets radiation to 0
			dRadiation = 0.0;
		}
		
		// Bounds radiation to dLethal
		if(dRadiation >= dLethal)
		{
			// Sets radiation to dLethal
			dRadiation = dLethal;
		}
		
		// Sets radiation level of Player in HashMap
		radiation.put(player, new Double(dRadiation));
	}
	
	public static double get(Player player)
	{
		// If radiation is not null
		if (radiation.get(player) != null)
		{
			// Return radiation level of Player
			return radiation.get(player).doubleValue();
		}
		
		// Else return 0.0
		return 0.0;
	}

	public static void saveToConfig() 
	{
		// For all online players
		for(Player player : Bukkit.getServer().getOnlinePlayers())
		{
			// Saves player radiation level
			saveToConfig(player);
		}
	}
	public static void saveToConfig(Player player) 
	{
		// If player exists in HashMap
		if(radiation.get(player) != null)
		{
			// Sets values in config to value in HashMap
			Main.config().set("PlayerData." + player.getUniqueId() + ".Radiation", radiation.get(player).doubleValue());
			// Saves config
			Main.plugin().saveConfig();
		}
	}
	
	public static void loadToHashMap() 
	{
		// For all online players
		for(Player player : Bukkit.getServer().getOnlinePlayers())
		{
			// Loads player radiation level
			loadToHashMap(player);
		}
	}
	public static void loadToHashMap(Player player) 
	{
		// Saves the default config
		Main.plugin().saveDefaultConfig();

		// Puts player radiation value in HashMap
		radiation.put(player, new Double(Main.config().getDouble("PlayerData." + player.getUniqueId() + ".Radiation")));
	}
}
