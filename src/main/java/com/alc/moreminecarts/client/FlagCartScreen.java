package com.alc.moreminecarts.client;

import com.alc.moreminecarts.containers.FlagCartContainer;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

@Environment(EnvType.CLIENT)
public class FlagCartScreen extends AbstractContainerScreen<FlagCartContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/programmable_cart.png");
    private static final Component TITLE = Component.translatable("gui.moreminecarts.flag_cart.title");

    private final List<SimpleButton> buttons = Lists.newArrayList();

    public FlagCartScreen(FlagCartContainer container, Inventory inv, Component titleIn) {
        super(container, inv, TITLE);
    }

    private void addButton(SimpleButton p_169617_) {
        this.addRenderableWidget(p_169617_);
        this.buttons.add(p_169617_);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new LeftButton(leftPos + 46, topPos + 19));
        this.addButton(new MinusButton(leftPos + 68, topPos + 19));
        this.addButton(new PlusButton(leftPos + 90, topPos + 19));
        this.addButton(new RightButton(leftPos + 112, topPos + 19));
    }

    @Override
    public void render(GuiGraphics p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

        for (AbstractButton button : buttons) {
            button.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        }

        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void renderBg(GuiGraphics matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShaderTexture(0, display);
        matrix.blit(display, leftPos, topPos, 0, 0, 176, 166);

        // Slot disclusion renders
        for (int i = 0; i < menu.getDiscludedSlots(); i++) {
            matrix.blit(display, leftPos + 151 - (18*i), topPos + 41, 176, 36, 18, 18);
        }

        // Selected slot render
        int s = menu.getSelectedSlot();
        matrix.blit(display, leftPos + 4 + (18*s), topPos + 38, 194, 36, 24, 24);
    }

    @Environment(EnvType.CLIENT)
    abstract class SimpleButton extends MMButton {

        protected SimpleButton(int x, int y) { super(x, y); }

        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShaderTexture(0, display);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            boolean mouse_on = isDragging() && this.isHovered;

            if (mouse_on) {
                renderSelected(matrix, xPos,yPos);
            }
        }

        public abstract void renderSelected(GuiGraphics matrix, int x, int y);

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

        }
    }

    class LeftButton extends SimpleButton {
        protected LeftButton(int x, int y) { super(x, y); }
        @Override
        public void renderSelected(GuiGraphics matrix, int x, int y) {
            matrix.blit(display, xPos,yPos, 194, 18, 18, 18);
        }
        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.FlagCartPacket packet = new MoreMinecartsPacketHandler.FlagCartPacket(false, true);
            ClientPlayNetworking.send(packet);
        }

    }

    class RightButton extends SimpleButton {
        protected RightButton(int x, int y) { super(x, y); }
        @Override
        public void renderSelected(GuiGraphics matrix, int x, int y) {
            matrix.blit(display, xPos,yPos, 230, 18, 18, 18);
        }
        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.FlagCartPacket packet = new MoreMinecartsPacketHandler.FlagCartPacket(true, true);
            ClientPlayNetworking.send(packet);
        }
    }

    class MinusButton extends SimpleButton {
        protected MinusButton(int x, int y) { super(x, y); }
        @Override
        public void renderSelected(GuiGraphics matrix, int x, int y) {
            matrix.blit(display, xPos,yPos, 230, 0, 18, 18);
        }
        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.FlagCartPacket packet = new MoreMinecartsPacketHandler.FlagCartPacket(true, false);
            ClientPlayNetworking.send(packet);
        }
    }

    class PlusButton extends SimpleButton {
        protected PlusButton(int x, int y) { super(x, y); }
        @Override
        public void renderSelected(GuiGraphics matrix, int x, int y) {
            matrix.blit(display, xPos,yPos, 194, 0, 18, 18);
        }
        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.FlagCartPacket packet = new MoreMinecartsPacketHandler.FlagCartPacket(false, false);
            ClientPlayNetworking.send(packet);
        }
    }

}
