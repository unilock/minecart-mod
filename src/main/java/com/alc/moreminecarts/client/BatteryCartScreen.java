package com.alc.moreminecarts.client;

import com.alc.moreminecarts.containers.BatteryCartContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class BatteryCartScreen extends AbstractContainerScreen<BatteryCartContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/blank.png");
    private static final Component TITLE = Component.translatable("gui.moreminecarts.battery_cart.title");

    public BatteryCartScreen(BatteryCartContainer container, Inventory inv, Component titleIn) {
        super(container, inv, TITLE);
    }

    @Override
    public void render(GuiGraphics p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void renderBg(GuiGraphics matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShaderTexture(0, display);
        matrix.blit(display, leftPos, topPos, 0, 0, 176, 166);

        int energy = menu.getEnergy();
        matrix.drawString(font, energy+"/40,000 FE", leftPos + 8, topPos + 20, 4210752, false);
    }

}
