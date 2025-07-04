package com.moddedhealth.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HealthDisplayConfig {
    
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    public static final ForgeConfigSpec.BooleanValue ENABLED;
    public static final ForgeConfigSpec.DoubleValue MAX_DISTANCE;
    public static final ForgeConfigSpec.BooleanValue SHOW_PLAYERS;
    public static final ForgeConfigSpec.BooleanValue SHOW_HOSTILE_MOBS;
    public static final ForgeConfigSpec.BooleanValue SHOW_PASSIVE_MOBS;
    public static final ForgeConfigSpec.BooleanValue SHOW_PERCENTAGE;
    
    static {
        BUILDER.push("Health Display Settings");
        
        ENABLED = BUILDER
                .comment("Enable/disable health display above entities")
                .define("enabled", true);
        
        MAX_DISTANCE = BUILDER
                .comment("Maximum distance to show health (in blocks)")
                .defineInRange("maxDistance", 64.0, 1.0, 256.0);
        
        SHOW_PLAYERS = BUILDER
                .comment("Show health above other players")
                .define("showPlayers", false);
        
        SHOW_HOSTILE_MOBS = BUILDER
                .comment("Show health above hostile mobs")
                .define("showHostileMobs", true);
        
        SHOW_PASSIVE_MOBS = BUILDER
                .comment("Show health above passive mobs")
                .define("showPassiveMobs", true);
        
        SHOW_PERCENTAGE = BUILDER
                .comment("Show health as percentage instead of numbers")
                .define("showPercentage", false);
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}