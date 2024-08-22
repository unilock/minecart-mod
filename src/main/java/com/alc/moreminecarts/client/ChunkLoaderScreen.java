package com.alc.moreminecarts.client;

import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.containers.ChunkLoaderContainer;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;

import java.util.List;

import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public class ChunkLoaderScreen extends AbstractContainerScreen<ChunkLoaderContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/chunk_loader_gui.png");
    private static final ITextComponent TITLE = new TranslationTextComponent("gui.moreminecarts.chunk_loader.title");
    private static final ITextComponent ON_LABEL = new TranslationTextComponent("gui.moreminecarts.chunk_loader.on");
    private static final ITextComponent OFF_LABEL = new TranslationTextComponent("gui.moreminecarts.chunk_loader.off");
    private static final ITextComponent INFO_LABEL = new TranslationTextComponent("gui.moreminecarts.chunk_loader.info");

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
        this.addButton(new ChunkLoaderInfoButton(leftPos + 2, topPos + 3));
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
        matrix.drawString(font, minutes_left + " minutes left", leftPos + 29, topPos + 55, 4210752, false);

    }

    // Taken from BeaconScreen, for tooltip rendering.
    @Override
    protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrix, getTitle(), (float)this.titleLabelX + 12, (float)this.titleLabelY, 4210752);
        this.font.draw(matrix, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);

        Iterator var4 = this.buttons.iterator();

        while(var4.hasNext()) {
            Widget lvt_5_1_ = (Widget)var4.next();
            if (lvt_5_1_.isHovered()) {
                lvt_5_1_.renderToolTip(matrix, p_230451_2_ - this.leftPos, p_230451_3_ - this.topPos);
                break;
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
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
                    this.blit(matrix, x,y, 176, 0, 18, 18);
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
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

        }

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(menu.isEnabled()? ON_LABEL: OFF_LABEL));
        }
    }

    @OnlyIn(Dist.CLIENT)
    class ChunkLoaderInfoButton extends AbstractButton {

        protected ChunkLoaderInfoButton(int x, int y) {
            super(x, y, 18, 18, StringTextComponent.EMPTY);
            this.setTooltip(Tooltip.create(INFO_LABEL));
        }

        public void renderButton(MatrixStack matrix, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
            minecraft.getTextureManager().bind(display);

            if (isHovered()) {
                this.blit(matrix, x,y, 176, 36, 18, 18);
            }
            else {
                // Render nothing. This is already on the backdrop.
            }
        }

        @Override
        public void onPress() {

        }
    }
}
