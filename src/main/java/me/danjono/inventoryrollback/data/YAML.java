package me.danjono.inventoryrollback.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import me.danjono.inventoryrollback.InventoryRollback;
import me.danjono.inventoryrollback.config.ConfigData;
import me.danjono.inventoryrollback.config.MessageData;
import me.danjono.inventoryrollback.gui.InventoryName;
import me.danjono.inventoryrollback.inventory.RestoreInventory;
import me.danjono.inventoryrollback.inventory.SaveInventory;

public class YAML {

    private UUID uuid;

    private File backupFolder;
    private File backupFile;
    private YamlConfiguration data;

    private String mainInventory;
    private String armour;
    private String enderChest;
    private float xp;
    private double health;
    private int hunger;
    private float saturation;
    private String world;
    private double x;
    private double y;
    private double z;
    private LogType logType;
    private String packageVersion;
    private String deathReason;

    private static String backup = "backups";

    public YAML(UUID uuid, LogType logType, Long timestamp) {
        this.uuid = uuid;
        this.logType = logType; 
        this.backupFolder = getBackupLocation();
        this.backupFile = new File (backupFolder, timestamp + ".yml");
        this.data = YamlConfiguration.loadConfiguration(backupFile);
    }

    public static void createStorageFolders() {        
        //Create folder for where player inventories will be saved
        File savesFolder = new File(ConfigData.getFolderLocation().getAbsoluteFile(), backup);
        if(!savesFolder.exists())
            savesFolder.mkdir();

        //Create folder for joins
        File joinsFolder = new File(savesFolder, "joins");
        if(!joinsFolder.exists())
            joinsFolder.mkdir();

        //Create folder for quits
        File quitsFolder = new File(savesFolder, "quits");
        if(!quitsFolder.exists())
            quitsFolder.mkdir();

        //Create folder for deaths
        File deathsFolder = new File(savesFolder, "deaths");
        if(!deathsFolder.exists())
            deathsFolder.mkdir();

        //Create folder for world changes
        File worldChangesFolder = new File(savesFolder, "worldChanges");
        if(!worldChangesFolder.exists())
            worldChangesFolder.mkdir();

        //Create folder for force saves
        File forceSavesFolder = new File(savesFolder, "force");
        if(!forceSavesFolder.exists())
            forceSavesFolder.mkdir();
    }

    private File getBackupLocation() {          
        File backupLocation = null;

        if (logType == LogType.JOIN) {
            backupLocation = new File(ConfigData.getFolderLocation(), backup + "/joins/" + uuid);
        } else if (logType == LogType.QUIT) {
            backupLocation = new File(ConfigData.getFolderLocation(), backup + "/quits/" + uuid);
        } else if (logType == LogType.DEATH) {
            backupLocation = new File(ConfigData.getFolderLocation(), backup + "/deaths/" + uuid);
        } else if (logType == LogType.WORLD_CHANGE) {
            backupLocation = new File(ConfigData.getFolderLocation(), backup + "/worldChanges/" + uuid);
        } else if (logType == LogType.FORCE) {
            backupLocation = new File(ConfigData.getFolderLocation(), backup + "/force/" + uuid);
        }

        return backupLocation;
    }

    public boolean doesBackupTypeExist() {
        return getAmountOfBackups() > 0;
    }

    public int getAmountOfBackups() {         
        if (!backupFolder.exists())
            return 0;

        return backupFolder.list().length;
    }

    public List<Long> getSelectedPageTimestamps(int pageNumber) {
        List<Long> allTimeStamps = new ArrayList<>();

        if (!backupFolder.exists())
            return allTimeStamps;

        for (File file : backupFolder.listFiles()) {
            if (file.isDirectory())
                continue;

            int pos = file.getName().lastIndexOf('.');
            String fileName = file.getName().substring(0, pos);

            if (!StringUtils.isNumeric(fileName))
                continue;

            allTimeStamps.add(Long.parseLong(fileName));
        }

        //Set timestamps in order
        Collections.sort(allTimeStamps, Collections.reverseOrder());

        //Number of backups that will be on the page
        int backups = InventoryName.ROLLBACK_LIST.getSize() - 9;

        //Return all timestamps if list if the same size or less than the page max size
        if (allTimeStamps.size() <= backups)
            return allTimeStamps;

        List<Long> requiredTimestamps = new ArrayList<>();
        for (int i = (backups * (pageNumber - 1)); i < ((backups * (pageNumber - 1)) + backups); i++) {            
            if (i < allTimeStamps.size()) {
                requiredTimestamps.add(allTimeStamps.get(i));
            } else {
                break;
            }
        }

        return requiredTimestamps;
    }

    public void purgeExcessSaves(int deleteAmount) {
        List<Long> timeSaved = new ArrayList<>();

        for (File file : backupFolder.listFiles()) {
            if (file.isDirectory())
                continue;

            int pos = file.getName().lastIndexOf('.');
            String fileName = file.getName().substring(0, pos);

            if (!StringUtils.isNumeric(fileName))
                continue;

            timeSaved.add(Long.parseLong(fileName));
        }

        for (int i = 0; i < deleteAmount; i++) {
            Long deleteTimestamp = Collections.min(timeSaved);
            timeSaved.remove(deleteTimestamp);
            try {
                Files.delete(new File (backupFolder, deleteTimestamp + ".yml").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMainInventory(ItemStack[] items) {
        this.mainInventory = SaveInventory.toBase64(items);
    }

    public void setArmour(ItemStack[] items) {
        this.armour = SaveInventory.toBase64(items);
    }

    public void setEnderChest(ItemStack[] items) {
        this.enderChest = SaveInventory.toBase64(items);
    }

    public void setXP(float xp) {
        this.xp = xp;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setFoodLevel(int foodLevel) {
        this.hunger = foodLevel;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public void setVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public void setDeathReason(String deathReason) {
        this.deathReason = deathReason;
    }

    public ItemStack[] getMainInventory() {
        String base64 = data.getString("inventory");
        return RestoreInventory.getInventoryItems(getVersion(), base64);
    }

    public ItemStack[] getArmour() {
        String base64 = data.getString("armour");
        return RestoreInventory.getInventoryItems(getVersion(), base64);
    }

    public ItemStack[] getEnderChest() {
        String base64 = data.getString("enderchest");
        return RestoreInventory.getInventoryItems(getVersion(), base64);
    }

    public float getXP() {
        return Float.parseFloat(data.getString("xp"));
    }

    public double getHealth() {
        return data.getDouble("health");
    }

    public int getFoodLevel() {
        return data.getInt("hunger");
    }

    public float getSaturation() {
        return Float.parseFloat(data.getString("saturation"));
    }

    public String getWorld() {
        return data.getString("location.world");
    }

    public double getX() {
        return data.getDouble("location.x");
    }

    public double getY() {
        return data.getDouble("location.y");
    }

    public double getZ() {
        return data.getDouble("location.z");
    }

    public LogType getSaveType() {
        LogType logType = null;

        try {
            logType = LogType.valueOf(data.getString("logType").toUpperCase());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return logType;
    }

    public String getVersion() {
        return data.getString("version");
    }

    public String getDeathReason() {
        return data.getString("deathReason");
    }

    public void saveData() {
        data.set("inventory", mainInventory);
        data.set("armour", armour);
        data.set("enderchest", enderChest);
        data.set("xp", xp);
        data.set("health", health);
        data.set("hunger", hunger);
        data.set("saturation", saturation);
        data.set("location.world", world);
        data.set("location.x", x);
        data.set("location.y", y);
        data.set("location.z", z);
        data.set("logType", logType.name());
        data.set("version", packageVersion);
        data.set("deathReason", deathReason);

        try {
            data.save(backupFile);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static String getBackupFolderName() {
        return backup;
    }

    public static void convertOldBackupData() {
        List<File> backupLocations = new ArrayList<>();
        backupLocations.add(new File(ConfigData.getFolderLocation(), "saves/deaths"));
        backupLocations.add(new File(ConfigData.getFolderLocation(), "saves/joins"));
        backupLocations.add(new File(ConfigData.getFolderLocation(), "saves/quits"));
        backupLocations.add(new File(ConfigData.getFolderLocation(), "saves/worldChanges"));
        backupLocations.add(new File(ConfigData.getFolderLocation(), "saves/force"));

        List<LogType> logTypeFiles = new ArrayList<>();
        int logTypeNumber = 0;
        logTypeFiles.add(LogType.DEATH);
        logTypeFiles.add(LogType.JOIN);
        logTypeFiles.add(LogType.QUIT);
        logTypeFiles.add(LogType.WORLD_CHANGE);
        logTypeFiles.add(LogType.FORCE);

        for (File backupFolders : backupLocations) {
            if (!backupFolders.exists()) {
                InventoryRollback.getPluginLogger().log(Level.WARNING, () -> MessageData.getPluginName() + "Backup folder does not exist at " + backupFolders.getAbsolutePath());
                logTypeNumber++;
                continue;
            }

            List<File> backupFiles = new ArrayList<>();

            //Add all YAML files to list
            for (File file : backupFolders.listFiles()) {
                if (file.isFile() && file.getName().substring(file.getName().indexOf('.')).equals(".yml")) {
                    backupFiles.add(file);
                }
            }

            LogType log = logTypeFiles.get(logTypeNumber);
            InventoryRollback.getPluginLogger().log(Level.INFO, () -> MessageData.getPluginName() + "Converting the backup location " + log.name());

            for (File backup : backupFiles) {
                YamlConfiguration data = new YamlConfiguration();
                
                try {
                    data.load(backup);
                } catch (InvalidConfigurationException | IOException e) {
                    InventoryRollback.getPluginLogger().log(Level.WARNING, () -> MessageData.getPluginName() + "Error converting backup file at " + backup.getAbsolutePath() + " - Invalid YAML format possibly from corruption.");
                    continue;
                }

                for (String time : data.getConfigurationSection("data").getKeys(false)) {
                    try {
                        Long timestamp = Long.parseLong(time);
                        UUID uuid = UUID.fromString(backup.getName().substring(0, backup.getName().length() - 4)); 

                        YAML yaml = new YAML(uuid, log, timestamp);

                        String packageVersion = data.getString("data." + timestamp + ".version");

                        yaml.setMainInventory(RestoreInventory.getInventoryItems(packageVersion, data.getString("data." + timestamp + ".inventory")));
                        yaml.setArmour(RestoreInventory.getInventoryItems(packageVersion, data.getString("data." + timestamp + ".armour")));
                        yaml.setEnderChest(RestoreInventory.getInventoryItems(packageVersion, data.getString("data." + timestamp + ".enderchest")));                    
                        yaml.setXP(Float.parseFloat(data.getString("data." + timestamp + ".xp")));
                        yaml.setHealth(data.getDouble("data." + timestamp + ".health"));
                        yaml.setFoodLevel(data.getInt("data." + timestamp + ".hunger"));
                        yaml.setSaturation(Float.parseFloat(data.getString("data." + timestamp + ".saturation")));
                        yaml.setWorld(data.getString("data." + timestamp + ".location.world"));
                        yaml.setX(data.getDouble("data." + timestamp + ".location.x"));
                        yaml.setY(data.getDouble("data." + timestamp + ".location.y"));
                        yaml.setZ(data.getDouble("data." + timestamp + ".location.z"));

                        String lt = data.getString("data." + timestamp + ".logType");
                        LogType logType = null;
                        if (lt.equalsIgnoreCase("WORLDCHANGE")) {
                            logType = LogType.WORLD_CHANGE;
                        } else {
                            logType = LogType.valueOf(lt);
                        }
                        yaml.setLogType(logType);

                        yaml.setVersion(packageVersion);
                        yaml.setDeathReason(data.getString("data." + timestamp + ".deathReason"));

                        yaml.saveData();
                    } catch (Exception e) {
                        InventoryRollback.getPluginLogger().log(Level.WARNING, () -> MessageData.getPluginName() + "Error converting backup file at " + backup.getAbsolutePath() + " on timestamp " + time);
                    }
                }
            }

            logTypeNumber++;

        }

        InventoryRollback.getPluginLogger().log(Level.INFO, () -> MessageData.getPluginName() + "Conversion completed!");
    }

}
