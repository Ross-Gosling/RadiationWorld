package RadiationWorld;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import events.BedListener;
import events.DeathListener;
import events.JoinListener;
import events.KickListener;
import events.QuitListener;
import events.SpawnListener;
import functions.Radiation;

public class Main extends JavaPlugin 
{
	// Plugin for referencing in use
    private static Plugin plugin;
    public static String pluginName;
    
    @Override
    public void onEnable() 
    {
    	// Assigns plugin to variable
		plugin = this;
		pluginName = plugin.getDescription().getName();  
		    	
		// Creates a config
		plugin.saveDefaultConfig();
	    
		// Registers all event listeners
		Bukkit.getServer().getPluginManager().registerEvents(new BedListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new KickListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new SpawnListener(), plugin);

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
}
