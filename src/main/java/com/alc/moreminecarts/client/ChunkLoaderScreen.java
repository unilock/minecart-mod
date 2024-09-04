package com.alc.moreminecarts.client;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.containers.ChunkLoaderContainer;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ChunkLoaderScreen extends AbstractContainerScreen<ChunkLoaderContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/chunk_loader_gui.png");
    private static final Component TITLE = Component.translatable("gui.moreminecarts.chunk_loader.title");
    private static final Component ON_LABEL = Component.translatable("gui.moreminecarts.chunk_loader.on");
    private static final Component OFF_LABEL = Component.translatable("gui.moreminecarts.chunk_loader.off");
    private static final Component INFO_LABEL = Component.translatable("gui.moreminecarts.chunk_loader.info");
    private static final Component MINUTES_LEFT = Component.translatable("gui.moreminecarts.chunk_loader.minutes_left");

    private final List<AbstractButton> buttons = Lists.newArrayList();

    public ChunkLoaderScreen(ChunkLoaderContainer container, Inventory inv, Component titleIn) {
        super(container, inv, TITLE);
    }

    private void addButton(AbstractButton p_169617_) {
        this.addRenderableWidget(p_169617_);
        this.buttons.add(p_169617_);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new ChunkLoaderButton(leftPos + 99, topPos + 14));
        this.addButton(new ChunkLoaderInfoButton(leftPos + 3, topPos + 4));
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
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, display);

        matrix.blit(display, leftPos, topPos, 0, 0, 176, 166);

        double log_progress = menu.getLogProgress();
        int progess = (int)Math.ceil(120 * log_progress);

        matrix.blit(display, leftPos + 28, topPos + 36, 0, 166, progess, 16);

        int minutes_left = menu.getTimeLeft();
        matrix.drawString(font, minutes_left + " " + MINUTES_LEFT.getString(), leftPos + 29, topPos + 55, 4210752, false);

    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, this.title, this.titleLabelX+13, this.titleLabelY, 4210752, false);
        p_281635_.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Environment(EnvType.CLIENT)
    class ChunkLoaderButton extends MMButton {

        boolean oldValue;

        protected ChunkLoaderButton(int x, int y) {
            super(x, y);
            UpdateTooltip();
        }

        @Override
        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, display);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            if (menu.isEnabled()) {
                if (isHovered() && isDragging()) {
                    matrix.blit(display, xPos,yPos, 194, 18, 18, 18);
                }
                else {
                    matrix.blit(display, xPos,yPos, 176, 18, 18, 18);
                }
            }
            else {
                if (isHovered() && isDragging()) {
                    matrix.blit(display, xPos,yPos, 176, 0, 18, 18);
                }
                else {
                    // Render nothing. This is already on the backdrop.
                }
            }

            var newValue = menu.isEnabled();
            if (newValue != oldValue) {
                oldValue = newValue;
                UpdateTooltip();
            }
        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.ChunkLoaderPacket packet = new MoreMinecartsPacketHandler.ChunkLoaderPacket(!menu.isEnabled());
            ClientPlayNetworking.send(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

        }

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(menu.isEnabled()? ON_LABEL: OFF_LABEL));
        }
    }

    @Environment(EnvType.CLIENT)
    class ChunkLoaderInfoButton extends MMButton {

        protected ChunkLoaderInfoButton(int x, int y) {
            super(x, y);
            UpdateTooltip();
        }

        @Override
        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, display);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            if (isHovered()) {
                matrix.blit(display, xPos,yPos, 176, 36, 18, 18);
            }
            else {
                // Render nothing. This is already on the backdrop.
            }
        }

        @Override
        public void onPress() {

        }

        public void UpdateTooltip() {
            List<? extends String> configMessageLines = MMConstants.CONFIG_CHUNK_LOADER_MESSAGE.get();
            if (!configMessageLines.isEmpty()) {
                this.setTooltip(Tooltip.create(Component.translatable(String.join("\n", configMessageLines))));
            }
            else this.setTooltip(Tooltip.create(INFO_LABEL));
        }
    }
}
