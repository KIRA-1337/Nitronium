package com.nitronium.infomod.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import java.util.ArrayList;
import java.util.List;
import com.nitronium.infomod.InfoMod;

public class InfoOverlay {
    
    private static int posX = 10;
    private static int posY = 10;
    
    public static void setPos(int x, int y) {
        posX = x;
        posY = y;
    }
    
    public static int getPosX() {
        return posX;
    }
    
    public static int getPosY() {
        return posY;
    }
    
    public static final IGuiOverlay INFO_OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }
        
        Player player = mc.player;
        Font font = mc.font;
        
        // Получаем настройки из конфига
        posX = ModConfig.POSITION_X.get();
        posY = ModConfig.POSITION_Y.get();
        
        List<String> infoLines = new ArrayList<>();
        
        // Координаты
        int x = (int) Math.floor(player.getX());
        int y = (int) Math.floor(player.getY());
        int z = (int) Math.floor(player.getZ());
        infoLines.add(String.format("X: %d Y: %d Z: %d", x, y, z));
        
        // Скорость
        double speed = Math.sqrt(player.getDeltaMovement().x * player.getDeltaMovement().x + 
                                  player.getDeltaMovement().z * player.getDeltaMovement().z);
        infoLines.add(String.format("Speed: %.2f", speed));
        
        // Сид мира
        long seed = mc.level.getSeed();
        infoLines.add("Seed: " + seed);
        
        // Ник игрока
        infoLines.add("Player: " + player.getName().getString());
        
        // Отрисовка фона
        int lineHeight = font.lineHeight + 2;
        int maxWidth = 0;
        for (String line : infoLines) {
            int width = font.width(line);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        
        int bgWidth = maxWidth + 8;
        int bgHeight = infoLines.size() * lineHeight + 4;
        
        guiGraphics.fill(posX - 2, posY - 2, posX + bgWidth, posY + bgHeight, 0x80000000);
        
        // Отрисовка текста
        for (int i = 0; i < infoLines.size(); i++) {
            guiGraphics.drawString(font, infoLines.get(i), posX + 4, posY + 2 + i * lineHeight, 0xFFFFFF);
        }
    };
    
    public static void register() {
        Minecraft.getInstance().submitAsync(() -> {
            net.minecraftforge.client.gui.overlay.ForgeGuiHelper.registerOverlay(
                ResourceLocation.fromNamespaceAndPath(InfoMod.MOD_ID, "info"),
                INFO_OVERLAY
            );
        });
    }
}
