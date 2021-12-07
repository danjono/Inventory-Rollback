# Why does this fork exist?
Danjono, the plugin's original dev, seems to have disappeared off the face of the earth. I intend to maintain this fork in their absence. I will be manually merging pull requests (which I believe are of benefit to the plugin) from the original repo.

[![Build Status](https://ci.sidpatchy.com/buildStatus/icon?job=InventoryRollback-Continued&style=flat-square)](https://ci.sidpatchy.com/job/InventoryRollback-Continued/)
[![License](https://img.shields.io/github/license/Sidpatchy/Inventory-Rollback?style=flat-square)](https://github.com/Sidpatchy/Inventory-Rollback/blob/master/LICENSE)

# 1.18 Support
Experimental builds supporting 1.18 can be found on my Jenkins: https://ci.sidpatchy.com/job/InventoryRollback-Continued/

The experimental builds require NBTAPI v2.9.0+: https://www.spigotmc.org/resources/nbt-api.7939/

![](https://i.imgur.com/zRdah4N.png)
# Inventory Rollback

### Minecraft Bukkit Plugin - Tested with versions 1.8.8 - 1.17.1

This plugin will log a players' inventory, health, hunger, experience, and ender chest during certain events. Perfect if someone loses their gear because of an admin mishap or if a bad plugin accidentally wipes a players data for example! These logged events include:-  

-   Player death
-   Player joining the server
-   Player disconnecting from the server
-   Player changing worlds

Staff with the required permission can open a GUI and select the required backup for the player. They can then click and drag the items the player requires off the GUI so they can pick them up. Clicking on the other icons enables you to restore the other attributes if required directly to the player.  
  
By default, it will log 50 deaths and 10 joins, disconnects, world changes and force saves each per player before the old data is purged to save space. These values can be changed in the config.  
  
**If upgrading a current server from before 1.13 you will need to delete all your backup data due to the changes with materials in the newest versions.**

## Commands
/ir restore %**PLAYERNAME**% - Opens a GUI to select the backup you require.  
/ir forcebackup %**PLAYERNAME**% - Forces a backup for an online player.  

## Permissions

inventoryrollback.restore - Allows access to */ir restore* (Default: OP)  
inventoryrollback.forcebackup - Allows access to */ir forcebackup* (Default: OP)  

inventoryrollback.deathsave - Saves inventory on a player death. (Default: All)  
inventoryrollback.joinsave - Saves inventory on joining the server. (Default: All)  
inventoryrollback.leavesave - Saves inventory on leaving the server. (Default: All)  
inventoryrollback.worldchangesave  - Saves inventory when changing to a different world. (Default: All)  

## Spigot Link
[https://www.spigotmc.org/resources/inventory-rollback-continued.93436/](https://www.spigotmc.org/resources/inventory-rollback-continued.93436/)
