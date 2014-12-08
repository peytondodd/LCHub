package me.mike1665.gadgets;

import me.mike1665.Main.Main;

import org.bukkit.plugin.PluginManager;

public class GagdetManager {
	
	  public static void registerEvents(Main plugin)
	  {
	    PluginManager pm = plugin.getServer().getPluginManager();
	    pm.registerEvents(new BatBlaster(), plugin);
	    pm.registerEvents(new EnderDoge(), plugin);
	    pm.registerEvents(new FireWorks(), plugin);
	    pm.registerEvents(new KittyCannon(), plugin);
	    pm.registerEvents(new MeowBall(), plugin);
	    pm.registerEvents(new FunCreepers(), plugin);
	  }

}