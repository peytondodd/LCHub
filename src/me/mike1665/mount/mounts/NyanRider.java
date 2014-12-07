package me.mike1665.mount.mounts;

import java.util.Iterator;
import java.util.UUID;

import me.mike1665.Main.Main;
import me.mike1665.coinapi.LcCoinsAPI;
import me.mike1665.mount.MountManager;
import me.mike1665.utils.UtilityBlock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.arrayprolc.strings.MessageType;
import com.arrayprolc.strings.StringManager;

public class NyanRider implements Listener {

	private static Main plugin;

	public static void initialize(Main plugin) {
		NyanRider.plugin = plugin;
	}

	public void playAngelRider(Player p) {
		UUID pn = p.getPlayer().getUniqueId();
		boolean check = plugin.getConfig().getBoolean(pn + ".NyanMount");
		if (!check && LcCoinsAPI.hasEnough(p, 12000)) {
			LcCoinsAPI.takePoints(p, 12000);
			plugin.getConfig().set(pn + ".NyanMount", true);
			plugin.saveFile();
			p.sendMessage(StringManager.getPrefix(MessageType.INFO)
					+ ChatColor.GREEN + "" + ChatColor.BOLD
					+ "Mount Purchased!");
			p.sendMessage(StringManager.getPrefix(MessageType.INFO)
					+ ChatColor.AQUA
					+ "Note: Click on your mount again to spawn your new mount! ");

		} else if (check) {
			if (Bukkit.getWorld("world2") != null) {
				World w = p.getWorld();
				double x = plugin.getConfig().getDouble("mount.x");
				double y = plugin.getConfig().getDouble("mount.y");
				double z = plugin.getConfig().getDouble("mount.z");
				Location mountspawnloc = new Location(w, x, y, z);

				MountManager.removeCurrentPet(p, false);

				Horse horse = (Horse) p.getWorld().spawn(mountspawnloc,
						Horse.class);
				Entity entity = horse;
				Horse entityHorse = (Horse) entity;
				entityHorse.getInventory().setSaddle(
						new ItemStack(Material.SADDLE));
				horse.setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD
						+ p.getPlayer().getName() + ChatColor.RESET
						+ "'s Horse");
				horse.setCustomNameVisible(true);
				horse.setOwner(p);
				horse.setVariant(Horse.Variant.DONKEY);
				horse.setAdult();
				horse.setPassenger(p);

				horse.setMetadata("nyanrider", new FixedMetadataValue(
						Main.schedule, "nyanrider"));
				MountManager.pet.put(p.getUniqueId(), horse);
			} else {
				p.sendMessage(StringManager.getPrefix(MessageType.ERROR)
						+ ChatColor.DARK_RED
						+ "You cannot spawn mounts outside of the Hub world! ");
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if ((event.getFrom().getBlockX() != event.getTo().getBlockX())
				|| (event.getFrom().getBlockZ() != event.getTo().getBlockZ())) {
			Player p = event.getPlayer();
			Entity e = p.getVehicle();

			if ((e instanceof Horse)) {
				if (e.hasMetadata("nyanrider")) {
					byte color = 14;
					double r = Math.random();
					if (r > 0.8D)
						color = 2;
					else if (r > 0.6D)
						color = 4;
					else if (r > 0.2D)
						color = 5;
					else if (r > 0.4D)
						color = 6;
					else if (r > 0.8D)
						color = 10;
					else if (r > 0.1D) {
						color = 1;
					}

					Iterator localIterator = UtilityBlock
							.getInRadius(
									((Horse) MountManager.pet.get(p
											.getUniqueId())).getLocation(),
									2.5D, true).keySet().iterator();

					while (localIterator.hasNext()) {
						Block block = (Block) localIterator.next();
						if (UtilityBlock.solid(block)) {
							if (!UtilityBlock.blockToRestore.contains(block))
								UtilityBlock.setBlockToRestore(block, 35,
										color, 2L, true, false, false);
						}
					}
				}
			}
		}
	}
}