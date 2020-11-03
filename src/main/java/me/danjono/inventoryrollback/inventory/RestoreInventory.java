package me.danjono.inventoryrollback.inventory;

import me.danjono.inventoryrollback.InventoryRollback;
import me.danjono.inventoryrollback.config.MessageData;
import me.danjono.inventoryrollback.util.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;

public class RestoreInventory {

    private final FileConfiguration playerData;
    private final long timestamp;

    public RestoreInventory(FileConfiguration playerData, long timestamp) {
        this.playerData = playerData;
        this.timestamp = timestamp;
    }

    //Credits to Dev_Richard (https://www.spigotmc.org/members/dev_richard.38792/)
    //https://gist.github.com/RichardB122/8958201b54d90afbc6f0
    public static void setTotalExperience(Player player, float xpFloat) {
        int xp = (int) xpFloat;
        int level;
        float experience;
        //Levels 0 through 15
        if (xp >= 0 && xp < 351) {
            //Calculate Everything
            int a = 1;
            int b = 6;
            int c = -xp;
            final double[] roots = MathUtils.roots(a, b, c);
            level = (int) roots[0];
            int xpForLevel = level * level + (6 * level);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (2 * level) + 7;
            experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);

            //Levels 16 through 30
        } else if (xp >= 352 && xp < 1507) {
            //Calculate Everything
            double a = 2.5;
            double b = -40.5;
            int c = -xp + 360;
            final double[] roots = MathUtils.roots(a, b, c);
            double dLevel = roots[0];
            level = (int) Math.floor(dLevel);
            int xpForLevel = (int) (2.5 * level * level - (40.5 * level) + 360);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (5 * level) - 38;
            experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);

            //Level 31 and greater
        } else {
            //Calculate Everything
            double a = 4.5;
            double b = -162.5;
            int c = -xp + 2220;
            final double[] roots = MathUtils.roots(a, b, c);
            double dLevel = roots[0];
            level = (int) Math.floor(dLevel);
            int xpForLevel = (int) (4.5 * level * level - (162.5 * level) + 2220);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (9 * level) - 158;
            experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);

        }
        //Set Everything
        player.setLevel(level);
        player.setExp(experience);
    }

    public static int getLevel(float floatXP) {
        int xp = (int) floatXP;

        //Levels 0 through 15
        if (xp >= 0 && xp < 351) {
            //Calculate Everything
            int a = 1;
            int b = 6;
            int c = -xp;
            return (int) Math.floor(MathUtils.roots(a, b, c)[0]);
            //Levels 16 through 30
        } else if (xp >= 352 && xp < 1507) {
            //Calculate Everything
            double a = 2.5;
            double b = -40.5;
            int c = -xp + 360;
            return (int) Math.floor(MathUtils.roots(a, b, c)[0]);
            //Level 31 and greater
        } else {
            //Calculate Everything
            double a = 4.5;
            double b = -162.5;
            int c = -xp + 2220;
            return (int) Math.floor(MathUtils.roots(a, b, c)[0]);
        }
    }

    public ItemStack[] retrieveArmour() {
        ItemStack[] inv = null;

        try {
            inv = stacksFromBase64(playerData.getString("data." + timestamp + ".armour"));

            if (inv.length == 0)
                inv = null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return inv;
    }

    public ItemStack[] retrieveEnderChestInventory() {
        ItemStack[] inv = null;

        try {
            inv = stacksFromBase64(playerData.getString("data." + timestamp + ".enderchest"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return inv;
    }

    private void logDeserializationError() {
        String packageVersion = playerData.getString("data." + timestamp + ".version");

        //Backup generated before InventoryRollback 1.3
        if (packageVersion == null) {
            InventoryRollback.logger.log(Level.SEVERE, ChatColor.stripColor(MessageData.pluginName)
                + "There was an error deserializing the material data. This is likely caused by a now incompatible material ID if the backup was originally generated on a different Minecraft server version.");
        }
        //Backup was not generated on the same server version
        else if (!packageVersion.equalsIgnoreCase(InventoryRollback.getPackageVersion())) {
            InventoryRollback.logger.log(Level.SEVERE, ChatColor.stripColor(MessageData.pluginName)
                + "There was an error deserializing the material data. The backup was generated on a "
                + packageVersion + " version server whereas you are now running a " + InventoryRollback
                .getPackageVersion()
                + " version server. It is likely a material ID inside the backup is no longer valid on this Minecraft server version and cannot be convereted.");
        }
        //Unknown error
        else {
            InventoryRollback.logger.log(Level.SEVERE, ChatColor.stripColor(MessageData.pluginName)
                + "There was an error deserializing the material data. Please upload the affected players backup file to Pastebin and send a link to it in the discussion page on Spigot for InventoryRollback detailing the problem as accurately as you can.");
        }
    }

    public ItemStack[] retrieveMainInventory() {

        try {
            return stacksFromBase64(playerData.getString("data." + timestamp + ".inventory"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getHunger() {
        return playerData.getInt("data." + timestamp + ".hunger");
    }

    public float getSaturation() {
        return (float) playerData.getDouble("data." + timestamp + ".saturation");
    }

    public float getXP() {
        return (float) playerData.getDouble("data." + timestamp + ".xp");
    }

    private ItemStack[] stacksFromBase64(String data) {
        if (data == null) {
            return new ItemStack[0];
        }

        ItemStack[] stacks;

        try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder
            .decodeLines(data)))) {
            stacks = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < stacks.length; i++) {
                try {
                    stacks[i] = (ItemStack) dataInput.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    logDeserializationError();
                    return new ItemStack[0];
                }
            }
            return stacks;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ItemStack[0];
        }
    }

    public double getHealth() {
        return playerData.getDouble("data." + timestamp + ".health");
    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = BigDecimal.valueOf(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);
        return bd.floatValue();
    }

}
