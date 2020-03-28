package me.danjono.inventoryrollback.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.danjono.inventoryrollback.InventoryRollback;

public class MessageData {

    private File messagesFile;
    private FileConfiguration messages;
    private static String configurationFileName = "messages.yml";

    public MessageData() {
        generateMessagesFile();
    }

    private void generateMessagesFile() {
        getMessagesFile();
        if(!messagesFile.exists()) {
            InventoryRollback.getInstance().saveResource(configurationFileName, false);
            getMessagesFile();
        }
        getMessagesData();
    }

    private void getMessagesFile() {
        messagesFile = new File(InventoryRollback.getInstance().getDataFolder(), configurationFileName);
    }

    private void getMessagesData() {
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public boolean saveConfig() {
        try {
            messages.save(messagesFile);
        } catch (IOException e) {
            InventoryRollback.getInstance().getLogger().log(Level.SEVERE, "Could not save data to messages file", e);
            return false;
        }

        saveChanges = false;

        return true;
    }

    private static String pluginName = ChatColor.WHITE + "[" + ChatColor.AQUA + "InventoryRollback" + ChatColor.WHITE + "]" + ChatColor.RESET + " ";
    private static String noPermission;
    private static String error;
    private static String errorInventory = "You cannot access this backup due to an error. The backup was likely generated on another Minecraft server version and a Material ID has now changed.";

    private static String enabledMessage;
    private static String disabledMessage;
    private static String reloadMessage;
    private static String playerOnly;

    private static String noBackup;
    private static String notOnline;
    private static String forceSaved;
    private static String notForcedSaved;

    private static String mainInventoryRestored;
    private static String mainInventoryRestoredPlayer;
    private static String mainInventoryNotOnline;
    private static String mainInventoryButton;
    private static String enderChestRestored;
    private static String enderChestRestoredPlayer;
    private static String enderChestNotOnline;
    private static String enderChestButton;
    private static String healthRestored;
    private static String healthRestoredPlayer;
    private static String healthNotOnline;
    private static String healthButton;
    private static String hungerRestored;
    private static String hungerRestoredPlayer;
    private static String hungerNotOnline;
    private static String hungerButton;
    private static String experienceRestored;
    private static String experienceRestoredPlayer;
    private static String experienceNotOnline;
    private static String experienceButton;
    private static String experienceButtonLore;

    private static String deathLocationWorld;
    private static String deathLocationX;
    private static String deathLocationY;
    private static String deathLocationZ;
    private static String deathReason;
    private static String deathTime;
    private static String deathLocationTeleportTo;
    private static String deathLocationTeleport;
    private static String deathLocationInvalidWorld;

    private static String mainMenuButton;
    private static String nextPageButton;
    private static String previousPageButton;
    private static String backButton;

    public void setMessages() {
        setNoPermission(convertColourCodes((String) getDefaultValue("commands.no-permission", "&cYou do not have permission!")));
        setError(convertColourCodes((String) getDefaultValue("commands.error", "&cInvalid command")));
        setPluginEnabled(convertColourCodes((String) getDefaultValue("commands.enable", "&2The plugin has been enabled")));
        setPluginDisabled(convertColourCodes((String) getDefaultValue("commands.disable", "&2The plugin has been disabled")));
        setPluginReload(convertColourCodes((String) getDefaultValue("commands.reload", "&2The plugin has been reloaded successfully")));
        setPlayerOnlyError(convertColourCodes((String) getDefaultValue("commands.player-only", "&cCommand can only be run by a player")));

        setNoBackupError(convertColourCodes((String) getDefaultValue("backup.no-backup", "There is currently no backups for %NAME%")));
        setNotOnlineError(convertColourCodes((String) getDefaultValue("backup.not-online", "%NAME% is not currently online")));
        setForceBackup(convertColourCodes((String) getDefaultValue("backup.force-saved", "%NAME%'s inventory has been force saved")));
        setForceBackupError(convertColourCodes((String) getDefaultValue("backup.not-forced-saved", "There was an issue with saving %NAME%'s inventory")));

        setMainInventoryRestored(convertColourCodes((String) getDefaultValue("attribute-restore.main-inventory.restored", "%NAME%''s main inventory has been restored.")));
        setMainInventoryRestoredPlayer(convertColourCodes((String) getDefaultValue("attribute-restore.main-inventory.restored-player", "Your inventory has been restored by %NAME%.")));
        setMainInventoryNotOnline(convertColourCodes((String) getDefaultValue("attribute-restore.main-inventory.not-online", "%NAME% is not online to have their inventory restored.")));
        setMainInventoryButton(convertColourCodes((String) getDefaultValue("attribute-restore.main-inventory.button-name", "&dRestore Main Inventory")));
        
        setEnderChestRestored(convertColourCodes((String) getDefaultValue("attribute-restore.ender-chest.restored", "%NAME%'s ender chest has been restored.")));
        setEnderChestRestoredPlayer(convertColourCodes((String) getDefaultValue("attribute-restore.ender-chest.restored-player", "Your ender chest has been restored by %NAME%.")));
        setEnderChestNotOnline(convertColourCodes((String) getDefaultValue("attribute-restore.ender-chest.not-online", "%NAME% is not online to have their ender chest set.")));
        setEnderChestButton(convertColourCodes((String) getDefaultValue("attribute-restore.ender-chest.button-name", "&dRestore Ender Chest")));
        
        setHealthRestored(convertColourCodes((String) getDefaultValue("attribute-restore.health.restored", "%NAME%'s health has been restored.")));
        setHealthRestoredPlayer(convertColourCodes((String) getDefaultValue("attribute-restore.health.restored-player", "Your health has been restored by %NAME%.")));
        setHealthNotOnline(convertColourCodes((String) getDefaultValue("attribute-restore.health.not-online", "%NAME% is not online to have their health set.")));
        setHealthButton(convertColourCodes((String) getDefaultValue("attribute-restore.health.button-name", "&aRestore Health")));
        
        setHungerRestored(convertColourCodes((String) getDefaultValue("attribute-restore.hunger.restored", "%NAME%'s hunger has been restored.")));
        setHungerRestoredPlayer(convertColourCodes((String) getDefaultValue("attribute-restore.hunger.restored-player", "Your hunger has been restored by %NAME%.")));
        setHungerNotOnline(convertColourCodes((String) getDefaultValue("attribute-restore.hunger.not-online", "%NAME% is not online to have their hunger set.")));
        setHungerButton(convertColourCodes((String) getDefaultValue("attribute-restore.hunger.button-name", "&cRestore Food")));
        
        setExperienceRestored(convertColourCodes((String) getDefaultValue("attribute-restore.experience.restored", "%NAME%'s XP has been set to level %XP%.")));
        setExperienceRestoredPlayer(convertColourCodes((String) getDefaultValue("attribute-restore.experience.restored-player", "Your XP has been restored to level %XP% by %NAME%.")));
        setExperienceNotOnlinePlayer(convertColourCodes((String) getDefaultValue("attribute-restore.experience.not-online", "%NAME% is not online to have their XP set.")));
        setExperienceButton(convertColourCodes((String) getDefaultValue("attribute-restore.experience.button-name", "&2Restore Player XP")));
        setExperienceButtonLore(convertColourCodes((String) getDefaultValue("attribute-restore.experience.button-lore", "&rLevel %XP%")));

        setDeathLocationWorld(convertColourCodes((String) getDefaultValue("death-location.world", "&6World: &f%WORLD%")));
        setDeathLocationX(convertColourCodes((String) getDefaultValue("death-location.x", "&6X: &f%X%")));
        setDeathLocationY(convertColourCodes((String) getDefaultValue("death-location.y", "&6Y: &f%Y%")));
        setDeathLocationZ(convertColourCodes((String) getDefaultValue("death-location.z", "&6Z: &f%Z%")));
        setDeathReason(convertColourCodes((String) getDefaultValue("death-location.reason", "&6Death reason: &f%REASON%")));
        setDeathTime(convertColourCodes((String) getDefaultValue("death-location.time", "&6Time: &f%TIME%")));
        setDeathLocation(convertColourCodes((String) getDefaultValue("death-location.teleport-to", "&3Teleport to where this entry was logged.")));
        setDeathLocationTeleport(convertColourCodes((String) getDefaultValue("death-location.teleport", "You have been teleported to %LOCATION%")));
        setDeathLocationInvalidWorldError(convertColourCodes((String) getDefaultValue("death-location.invalid-world", "The world %WORLD% is not currently loaded on the server.")));
        
        setMainMenuButton(convertColourCodes((String) getDefaultValue("menu-buttons.main-menu", "&fMain Menu")));
        setNextPageButton(convertColourCodes((String) getDefaultValue("menu-buttons.next-page", "&fNext Page")));
        setPreviousPageButton(convertColourCodes((String) getDefaultValue("menu-buttons.previous-page", "&fPrevious Page")));
        setBackButton(convertColourCodes((String) getDefaultValue("menu-buttons.back-page", "&fBack")));

        if (saveChanges())
            saveConfig();
    }

    private static String nameVariable = "%NAME%";
    private static String xpVariable = "%XP%";

    public static void setNoPermission(String message) {
        noPermission = message;
    }

    public static void setError(String message) {
        error = message;
    }

    public static void setErrorInventory(String message) {
        errorInventory = message;
    }

    public static void setPluginEnabled(String message) {
        enabledMessage = message;
    }

    public static void setPluginDisabled(String message) {
        disabledMessage = message;
    }

    public static void setPluginReload(String message) {
        reloadMessage = message;
    }

    public static void setPlayerOnlyError(String message) {
        playerOnly = message;
    }

    public static void setNoBackupError(String message) {
        noBackup = message;
    }

    public static void setNotOnlineError(String message) {
        notOnline = message;
    }

    public static void setForceBackup(String message) {
        forceSaved = message;
    }

    public static void setForceBackupError(String message) {
        notForcedSaved = message;
    }

    public static void setMainInventoryRestored(String message) {
        mainInventoryRestored = message;
    }

    public static void setMainInventoryRestoredPlayer(String message) {
        mainInventoryRestoredPlayer = message;
    }

    public static void setMainInventoryNotOnline(String message) {
        mainInventoryNotOnline = message;
    }
    
    public static void setMainInventoryButton(String message) {
        mainInventoryButton = message;
    }

    public static void setEnderChestRestored(String message) {
        enderChestRestored = message;
    }

    public static void setEnderChestRestoredPlayer(String message) {
        enderChestRestoredPlayer = message;
    }

    public static void setEnderChestNotOnline(String message) {
        enderChestNotOnline = message;
    }
    
    public static void setEnderChestButton(String message) {
        enderChestButton = message;
    }

    public static void setHealthRestored(String message) {
        healthRestored = message;
    }

    public static void setHealthRestoredPlayer(String message) {
        healthRestoredPlayer = message;
    }

    public static void setHealthNotOnline(String message) {
        healthNotOnline = message;
    }
    
    public static void setHealthButton(String message) {
        healthButton = message;
    }

    public static void setHungerRestored(String message) {
        hungerRestored = message;
    }

    public static void setHungerRestoredPlayer(String message) {
        hungerRestoredPlayer = message;
    }

    public static void setHungerNotOnline(String message) {
        hungerNotOnline = message;
    }
    
    public static void setHungerButton(String message) {
        hungerButton = message;
    }

    public static void setExperienceRestored(String message) {
        experienceRestored = message;
    }

    public static void setExperienceRestoredPlayer(String message) {
        experienceRestoredPlayer = message;
    }

    public static void setExperienceNotOnlinePlayer(String message) {
        experienceNotOnline = message;
    }
    
    public static void setExperienceButton(String message) {
        experienceButton = message;
    }

    public static void setExperienceButtonLore(String message) {
        experienceButtonLore = message;
    }

    public static void setDeathLocationWorld(String message) {
        deathLocationWorld = message;
    }

    public static void setDeathLocationX(String message) {
        deathLocationX = message;
    }

    public static void setDeathLocationY(String message) {
        deathLocationY = message;
    }

    public static void setDeathLocationZ(String message) {
        deathLocationZ = message;
    }
    
    public static void setDeathReason(String message) {
        deathReason = message;
    }

    public static void setDeathTime(String message) {
        deathTime = message;
    }
    
    public static void setDeathLocation(String message) {
        deathLocationTeleportTo = message;
    }


    public static void setDeathLocationTeleport(String message) {
        deathLocationTeleport = message;
    }

    public static void setDeathLocationInvalidWorldError(String message) {
        deathLocationInvalidWorld = message;
    }

    public static void setMainMenuButton(String message) {
        mainMenuButton = message;
    }

    public static void setNextPageButton(String message) {
        nextPageButton = message;
    }

    public static void setPreviousPageButton(String message) {
        previousPageButton = message;
    }

    public static void setBackButton(String message) {
        backButton = message;
    }

    public static String getPluginName() {
        return pluginName;
    }

    public static String getNoPermission() {
        return noPermission;
    }

    public static String getError() {
        return error;
    }

    public static String getErrorInventory() {
        return errorInventory;
    }

    public static String getPluginEnabled() {
        return enabledMessage;
    }

    public static String getPluginDisabled() {
        return disabledMessage;
    }

    public static String getPluginReload() {
        return reloadMessage;
    }

    public static String getPlayerOnlyError() {
        return playerOnly;
    }

    public static String getNoBackupError(String name) {
        return noBackup.replaceAll(nameVariable, name);
    }

    public static String getNotOnlineError(String name) {
        return notOnline.replaceAll(nameVariable, name);
    }

    public static String getForceBackup(String name) {
        return forceSaved.replaceAll(nameVariable, name);
    }

    public static String getForceBackupError(String name) {
        return notForcedSaved.replaceAll(nameVariable, name);
    }
    
    public static String getMainInventoryRestored(String name) {
        return mainInventoryRestored.replaceAll(nameVariable, name);
    }

    public static String getMainInventoryRestoredPlayer(String name) {
        return mainInventoryRestoredPlayer.replaceAll(nameVariable, name);
    }

    public static String getMainInventoryNotOnline(String name) {
        return mainInventoryNotOnline.replaceAll(nameVariable, name);
    }
    
    public static String getMainInventoryRestoreButton() {
        return mainInventoryButton;
    }

    public static String getEnderChestRestored(String name) {
        return enderChestRestored.replaceAll(nameVariable, name);
    }

    public static String getEnderChestRestoredPlayer(String name) {
        return enderChestRestoredPlayer.replaceAll(nameVariable, name);
    }

    public static String getEnderChestNotOnline(String name) {
        return enderChestNotOnline.replaceAll(nameVariable, name);
    }
    
    public static String getEnderChestRestoreButton() {
        return enderChestButton;
    }

    public static String getHealthRestored(String name) {
        return healthRestored.replaceAll(nameVariable, name);
    }

    public static String getHealthRestoredPlayer(String name) {
        return healthRestoredPlayer.replaceAll(nameVariable, name);
    }

    public static String getHealthNotOnline(String name) {
        return healthNotOnline.replaceAll(nameVariable, name);
    }
    
    public static String getHealthRestoreButton() {
        return healthButton;
    }

    public static String getHungerRestored(String name) {
        return hungerRestored.replaceAll(nameVariable, name);
    }

    public static String getHungerRestoredPlayer(String name) {
        return hungerRestoredPlayer.replaceAll(nameVariable, name);
    }

    public static String getHungerNotOnline(String name) {
        return hungerNotOnline.replaceAll(nameVariable, name);
    }
    
    public static String getHungerRestoreButton() {
        return hungerButton;
    }

    public static String getExperienceRestored(String name, int xp) {
        return experienceRestored.replaceAll(nameVariable, name).replaceAll(xpVariable, xp + "");
    }

    public static String getExperienceRestoredPlayer(String name, int xp) {
        return experienceRestoredPlayer.replaceAll(nameVariable, name).replaceAll(xpVariable, xp + "");
    }

    public static String getExperienceNotOnlinePlayer(String name) {
        return experienceNotOnline.replaceAll(nameVariable, name);
    }
    
    public static String getExperienceRestoreButton() {
        return experienceButton;
    }
    
    public static String getExperienceRestoreLevel(int xp) {
        return experienceButtonLore.replaceAll(xpVariable, xp + "");
    }

    public static String getDeathLocationWorld(String world) {
        return deathLocationWorld.replace("%WORLD%", world);
    }

    public static String getDeathLocationX(Double x) {
        return deathLocationX.replace("%X%", Math.floor(x) + "");
    }

    public static String getDeathLocationY(Double y) {
        return deathLocationY.replace("%Y%", Math.floor(y) + "");
    }

    public static String getDeathLocationZ(Double z) {
        return deathLocationZ.replace("%Z%", Math.floor(z) + "");
    }

    public static String getDeathReason(String reason) {
        return deathReason.replace("%REASON%", reason);
    }

    public static String getDeathTime(String time) {
        return deathTime.replace("%TIME%", time);
    }
    
    public static String getDeathLocation() {
        return deathLocationTeleportTo;
    }
    
    public static String getDeathLocationTeleport(Location location) {
        return deathLocationTeleport.replace("%LOCATION%", "X:" + (int) location.getX() + " Y:" + (int) location.getY() + " Z:" + (int) location.getZ());
    }

    public static String getDeathLocationInvalidWorldError(String world) {
        return deathLocationInvalidWorld.replace("%WORLD%", world);
    }

    public static String getMainMenuButton() {
        return mainMenuButton;
    }

    public static String getNextPageButton() {
        return nextPageButton;
    }

    public static String getPreviousPageButton() {
        return previousPageButton;
    }

    public static String getBackButton() {
        return backButton;
    }
    
    private static String convertColourCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private boolean saveChanges = false;
    public Object getDefaultValue(String path, Object defaultValue) {
        Object obj = messages.get(path);

        if (obj == null) {
            obj = defaultValue;

            messages.set(path, defaultValue);
            saveChanges = true;
        }

        return obj;
    }

    private boolean saveChanges() {
        return saveChanges;
    }
}
