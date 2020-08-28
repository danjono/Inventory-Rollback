package me.danjono.inventoryrollback.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.danjono.inventoryrollback.config.ConfigFile;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandsTab extends ConfigFile implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> cmdlist = new ArrayList<String>();
        if (args.length == 1) {
            if (sender.hasPermission("inventoryrollback.restore") && sender instanceof Player) {
                cmdlist.add("restore");
            }
            if (sender.hasPermission("inventoryrollback.forcebackup")) {
                cmdlist.add("forcebackup");
            }
            if (sender.hasPermission("InventoryRollback.enable")) {
                cmdlist.add("enable");
            }
            if (sender.hasPermission("InventoryRollback.disable")) {
                cmdlist.add("disable");
            }
            if (sender.hasPermission("InventoryRollback.reload")) {
                cmdlist.add("reload");
            }
        } else {
            return null;
        }
        return cmdlist;
    }
}
