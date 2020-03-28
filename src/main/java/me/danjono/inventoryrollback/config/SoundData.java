package me.danjono.inventoryrollback.config;

import org.bukkit.Sound;

import me.danjono.inventoryrollback.InventoryRollback;
import me.danjono.inventoryrollback.InventoryRollback.VersionName;

public class SoundData extends ConfigData {
	
	private static Sound teleport;
	private static boolean teleportEnabled;

	private static Sound inventoryRestored;
	private static boolean inventoryRestoreEnabled;

	private static Sound foodRestored;
	private static boolean foodRestoredEnabled;

	private static Sound hungerRestored;
	private static boolean hungerRestoredEnabled;

	private static Sound experienceRestored;
	private static boolean experienceRestoredEnabled;

	public void setSounds() {	    
	    //If sounds are invalid they will be disabled.
		try {
			setTeleport(Sound.ENTITY_ENDERMAN_TELEPORT);
		} catch (NoSuchFieldError e) {
            if (InventoryRollback.getVersion().equals(VersionName.V1_8)) {
                setTeleport(Sound.valueOf("ENDERMAN_TELEPORT"));
            } else if (InventoryRollback.getVersion().equals(VersionName.V1_9_V1_12)) {
                setTeleport(Sound.valueOf("ENTITY_ENDERMEN_TELEPORT"));
            }
		}
		
		if (teleport != null)
		    setTeleportEnabled((boolean) getDefaultValue("sounds.teleport.enabled", true));
		
		try {
			setInvetoryRestored(Sound.ENTITY_ENDER_DRAGON_FLAP);
		} catch (NoSuchFieldError e) {
            if (InventoryRollback.getVersion().equals(VersionName.V1_8)) {
                setInvetoryRestored(Sound.valueOf("ENDERDRAGON_WINGS"));
            } else if (InventoryRollback.getVersion().equals(VersionName.V1_9_V1_12)) {
                setInvetoryRestored(Sound.valueOf("ENTITY_ENDERDRAGON_FLAP"));
            }
		}
		
		if (inventoryRestored != null)
		    setInventoryRestoredEnabled((boolean) getDefaultValue("sounds.inventory.enabled", true));

		try {
			setFoodRestored(Sound.ENTITY_GENERIC_EAT);
		} catch (NoSuchFieldError e) {
            if (InventoryRollback.getVersion().equals(VersionName.V1_8)) {
                setFoodRestored(Sound.valueOf("EAT"));
            } else if (InventoryRollback.getVersion().equals(VersionName.V1_9_V1_12)) {
                setFoodRestored(Sound.valueOf("ENTITY_GENERIC_EAT"));
            }
		}
		
		if (foodRestored != null)
		    setFoodRestoredEnabled((boolean) getDefaultValue("sounds.food.enabled", true));

		try {
			setHungerRestored(Sound.ENTITY_HORSE_EAT);
		} catch (NoSuchFieldError e) {
            if (InventoryRollback.getVersion().equals(VersionName.V1_8)) {
                setHungerRestored(Sound.valueOf("HORSE_IDLE"));
            } else if (InventoryRollback.getVersion().equals(VersionName.V1_9_V1_12)) {
                setHungerRestored(Sound.valueOf("ENTITY_HORSE_EAT"));
            }
		}
		
		if (hungerRestored != null)
		    setHungerRestoredEnabled((boolean) getDefaultValue("sounds.hunger.enabled", true));

		try {
			setExperienceSound(Sound.ENTITY_PLAYER_LEVELUP);
		} catch (NoSuchFieldError e) {
            if (InventoryRollback.getVersion().equals(VersionName.V1_8)) {
                setExperienceSound(Sound.valueOf("LEVEL_UP"));
            } else if (InventoryRollback.getVersion().equals(VersionName.V1_9_V1_12)) {
                setExperienceSound(Sound.valueOf("ENTITY_PLAYER_LEVELUP"));
            }
		}
		
		if (experienceRestored != null)
		    setExperienceRestoredEnabled((boolean) getDefaultValue("sounds.xp.enabled", true));
		
	}
	
	public static void setTeleport(Sound value) {
        teleport = value;
    }
    
    public static void setTeleportEnabled(boolean value) {
        teleportEnabled = value;
    }
    
    public static void setInvetoryRestored(Sound value) {
        inventoryRestored = value;
    }
    
    public static void setInventoryRestoredEnabled(boolean value) {
        inventoryRestoreEnabled = value;
    }
    
    public static void setFoodRestored(Sound value) {
        foodRestored = value;
    }
    
    public static void setFoodRestoredEnabled(boolean value) {
        foodRestoredEnabled = value;
    }
    
    public static void setHungerRestored(Sound value) {
        hungerRestored = value;
    }
    
    public static void setHungerRestoredEnabled(boolean value) {
        hungerRestoredEnabled = value;
    }
    
    public static void setExperienceSound(Sound value) {
        experienceRestored = value;
    }
    
    public static void setExperienceRestoredEnabled(boolean value) {
        experienceRestoredEnabled = value;
    }
	
	public static Sound getTeleport() {
	    return teleport;
	}
	
	public static boolean isTeleportEnabled() {
	    return teleportEnabled;
	}
	
	public static Sound getInventoryRestored() {
	    return inventoryRestored;
	}
	
	public static boolean isInventoryRestoreEnabled() {
	    return inventoryRestoreEnabled;
	}
	
	public static Sound getFoodRestored() {
	    return foodRestored;
	}
	
	public static boolean isFoodRestoredEnabled() {
        return foodRestoredEnabled;
    }
	
	public static Sound getHungerRestored() {
	    return hungerRestored;
	}
	
	public static boolean isHungerRestoredEnabled() {
        return hungerRestoredEnabled;
    }
	
	public static Sound getExperienceSound() {
	    return experienceRestored;
	}
	
	public static boolean isExperienceRestoredEnabled() {
        return experienceRestoredEnabled;
    }

}
