package me.mike1665.hubstuff;

import me.mike1665.coinapi.ApiEvent;
import me.mike1665.utils.UpdateEvent;
import me.mike1665.utils.UpdateType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UpdateScore implements Listener{
	
	public ApiEvent ae = new ApiEvent();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void updateScore(UpdateEvent e) {
		if (e.getType() == UpdateType.SEC) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				ae.updatescore(p);
			}
		}
	}

}
