package com.lightcraftmc.effects;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

import com.lightcraftmc.extra.ExtraManager;
import com.lightcraftmc.hub.main.Main;
import com.lightcraftmc.particle.ParticleManager;

public class EffectManager
  implements Listener
{
  public static HashMap<Player, EffectType> effect3 = new HashMap();

  @EventHandler
  public void OnPlayerLeft(PlayerQuitEvent e)
  {
    Player p = e.getPlayer();
    if (hasEffect(p))
      removeEffect(p, false);
  }

  @EventHandler
  public void onLeave(PlayerKickEvent e)
  {
    Player p = e.getPlayer();

    if (hasEffect(p))
      removeEffect(p, false);
  }

  public static boolean hasEffect(Player p)
  {
    if (effect3.containsKey(p)) {
      return true;
    }
    return false;
  }

  public static void addEffect(Player p, EffectType type)
  {
    if (!effect3.containsKey(p)) {
      effect3.put(p, type);
    } else {
      removeEffect(p, false);
      effect3.put(p, type);
    }
  }

  public static EffectType getEffect(Player p)
  {
    return (EffectType)effect3.get(p);
  }

  public static void removeEffect(Player p, boolean message) {
    if (hasEffect(p)) {
      if (ParticleManager.hasCircleEffect(p)) {
        ParticleManager.removeCircleEffect(p);
      }
      effect3.remove(p);
      if (ExtraManager.hasExtraEffect(p)) {
        ExtraManager.removeExtraEffect(p);
      }

    }
    else if (message) {
      p.sendMessage("Particle Aready Removed");
    }
  }

  public static void registerEvents()
  {
    PluginManager pm = Bukkit.getServer().getPluginManager();
    pm.registerEvents(new EffectManager(), Main.getInstance());
  }

  public static enum EffectType
  {
    CircleEffect, FlameRing, GreenSpiral, Cloud, CloudSnow, CloudLight, Helix, Tornado, Vortex, FlameLilly, HourGlass, Shield, Fountain, GreenRing;
  }
}