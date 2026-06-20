package com.nitronium.infomod.client;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ModConfig {
    
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec.IntValue POSITION_X;
    public static final ForgeConfigSpec.IntValue POSITION_Y;
    
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.push("overlay");
        builder.comment("Настройки положения информационного окна");
        
        POSITION_X = builder.defineInRange("positionX", 10, 0, Integer.MAX_VALUE);
        POSITION_Y = builder.defineInRange("positionY", 10, 0, Integer.MAX_VALUE);
        
        builder.pop();
        
        CLIENT_SPEC = builder.build();
    }
    
    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
    }
}
