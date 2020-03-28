package me.danjono.inventoryrollback.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.danjono.inventoryrollback.config.ConfigData;
import me.danjono.inventoryrollback.data.LogType;
import me.danjono.inventoryrollback.inventory.SaveInventory;

public class EventLogs implements Listener {

	@EventHandler
	private void playerJoin(PlayerJoinEvent e) {
		if (!ConfigData.isEnabled()) return;

		Player player = e.getPlayer();
		if (player.hasPermission("inventoryrollback.joinsave")) {			
			new SaveInventory(e.getPlayer(), LogType.JOIN, null, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

	@EventHandler
	private void playerQuit(PlayerQuitEvent e) {
		if (!ConfigData.isEnabled()) return;

		Player player = e.getPlayer();

		if (player.hasPermission("inventoryrollback.leavesave")) {				
			new SaveInventory(e.getPlayer(), LogType.QUIT, null, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

	@EventHandler
	private void playerDeath(EntityDamageEvent e) {
		if (!ConfigData.isEnabled()) return;
		if (!(e.getEntity() instanceof Player)) return;

		Player player = (Player) e.getEntity();

		if (player.getHealth() - e.getDamage() <= 0 && player.hasPermission("inventoryrollback.deathsave")) {											
			new SaveInventory(player, LogType.DEATH, e.getCause(), player.getInventory(), player.getEnderChest()).createSave();
		}
	}

	@EventHandler
	private void playerChangeWorld(PlayerChangedWorldEvent e) {
		if (!ConfigData.isEnabled()) return;

		Player player = e.getPlayer();

		if (player.hasPermission("inventoryrollback.worldchangesave")) {				
			new SaveInventory(e.getPlayer(), LogType.WORLD_CHANGE, null, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

}
