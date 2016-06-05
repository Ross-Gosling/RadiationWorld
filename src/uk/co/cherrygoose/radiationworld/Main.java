package uk.co.cherrygoose.radiationworld;

import java.io.FileNotFoundException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.cherrygoose.radiationworld.events.BedListener;
import uk.co.cherrygoose.radiationworld.events.DeathListener;
import uk.co.cherrygoose.radiationworld.events.JoinListener;
import uk.co.cherrygoose.radiationworld.events.KickListener;
import uk.co.cherrygoose.radiationworld.events.QuitListener;
import uk.co.cherrygoose.radiationworld.events.RespawnListener;
import uk.co.cherrygoose.radiationworld.functions.Radiation;

public class Main extends JavaPlugin 
{
	// Plugin for referencing in use
    private static Plugin plugin;
    public static String pluginName;
	// YamlConfiguration for player data storage
	private static YamlConfiguration playerDataFile = new YamlConfiguration();
	
    @Override
    public void onEnable() 
    {
    	// Assigns plugin to variable
		plugin = this;
		pluginName = plugin.getDescription().getName();  
		    	
		// Creates a config
		plugin.saveDefaultConfig();
	    
		// Loads playerData yaml file
		playerDataFile = loadYml("plugins/" + pluginName + "/playerdata.yml");
		
		// Registers all event listeners
		Bukkit.getServer().getPluginManager().registerEvents(new BedListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new KickListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new RespawnListener(), plugin);

		// Loads all player radiation levels into hashmap
		Radiation.loadToHashMap();
		
		// Creates a thread to perform systems every tick
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() 
		{
        	public void run() 
        	{
        		// Updates radiation
        		Radiation.update();
        	}
		}, 0, 1);

    	// Logs to console
		Bukkit.getLogger().info("["+pluginName+"] Enabled");
    }

    @Override
    public void onDisable() 
    {
		// Saves all player radiation levels to config
		Radiation.saveToConfig();

    	// Logs to console
		Bukkit.getLogger().info("["+pluginName+"] Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {
    	return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
		return null;
    }
    
    public static FileConfiguration config()
    {
    	// Returns config
    	return plugin.getConfig();
    }
    public static Plugin plugin()
    {
    	// Returns plugin
    	return plugin;
    }
    public static YamlConfiguration playerdata()
    {
    	// Returns yaml
    	return playerDataFile;
    }    
    
    public static YamlConfiguration loadYml(String sDirectory)
    {  
        YamlConfiguration yml = new YamlConfiguration();
        
        try
        {
            yml.load(sDirectory);
        }
        catch(FileNotFoundException e)
        {
            try
            {
                yml.save(sDirectory);//Create the file if it didn't exist
            }
            catch(Exception e2)
            {
            	e.printStackTrace();
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return yml;
    }

    public static boolean saveYml(YamlConfiguration ymlConfig, String sDirectory)
    {
        try
        {
        	ymlConfig.save(sDirectory);
            return true;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return false;
    }
}
