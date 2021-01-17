package me.danjono.inventoryrollback.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * A class which houses the help command
 * (/ir help)
 */

public class HelpCommand {
    public static void getHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.WHITE + "---------------- " + ChatColor.AQUA + ChatColor.BOLD + "Inventory Rollback" + ChatColor.WHITE + " ----------------");
        sender.sendMessage(ChatColor.AQUA + "/ir help" + ChatColor.WHITE + " - Lists all Inventory Restore commands.");

        if (sender.hasPermission("inventoryrollback.reload")) {
            sender.sendMessage(ChatColor.AQUA + "/ir reload" + ChatColor.WHITE + " - Reloads the plugin's configuration.");
        }

        if (sender.hasPermission("inventoryrollback.restore")) {
            sender.sendMessage(ChatColor.AQUA + "/ir restore <player>" + ChatColor.WHITE + " - Brings up inventory restore menu.");
        }

        if (sender.hasPermission("inventoryrollback.forcebackup")) {
            sender.sendMessage(ChatColor.AQUA + "/ir forcebackup <player>" + ChatColor.WHITE + " - Forces a backup of an online player's inventory.");
        }

        if (sender.hasPermission("inventoryrollback.enable")) {
            sender.sendMessage(ChatColor.AQUA + "/ir enable" + ChatColor.WHITE + " - Enables the plugin if previously disabled.");
        }

        if (sender.hasPermission("inventoryrollback.disable")) {
            sender.sendMessage(ChatColor.AQUA + "/ir disable" + ChatColor.WHITE + " - Disables the plugin.");
        }
    }
}
