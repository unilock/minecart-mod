package com.alc.moreminecarts.client;

import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.containers.ChunkLoaderContainer;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ChunkLoaderScreen extends AbstractContainerScreen<ChunkLoaderContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/chunk_loader_gui.png");
    private final List<AbstractButton> buttons = Lists.newArrayList();

    public ChunkLoaderScreen(ChunkLoaderContainer container, Inventory inv, Component titleIn) {
        super(container, inv, new TranslatableComponent("Chunk Loader"));
    }

    private void addButton(AbstractButton p_169617_) {
        this.addWidget(p_169617_);
        this.buttons.add(p_169617_);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new ChunkLoaderButton(leftPos + 131, topPos + 14));
    }

    @Override
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

        for (AbstractButton button : buttons) {
            button.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        }

        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void renderBg(PoseStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, display);

        this.blit(matrix, leftPos, topPos, 0, 0, 176, 166);

        double log_progress = menu.getLogProgress();
        int progess = (int)Math.ceil(120 * log_progress);

        this.blit(matrix, leftPos + 28, topPos + 36, 0, 166, progess, 16);

        int minutes_left = menu.getTimeLeft();
        this.font.draw(matrix, minutes_left + " minutes left", leftPos + 29, topPos + 55, 4210752);

    }


    @OnlyIn(Dist.CLIENT)
    class ChunkLoaderButton extends AbstractButton {

        protected ChunkLoaderButton(int x, int y) {
            super(x, y, 18, 18, TextComponent.EMPTY);
        }

        public void renderToolTip(PoseStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
            ChunkLoaderScreen.this.renderTooltip(p_230443_1_,
                    new TranslatableComponent(menu.isEnabled()
                            ? "On"
                            : "Off"
                    ) , p_230443_2_, p_230443_3_);
        }

        @Override
        public void renderButton(PoseStack matrix, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, display);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            if (menu.isEnabled()) {
                if (isHovered) {
                    this.blit(matrix, x,y, 194, 18, 18, 18);
                }
                else {
                    this.blit(matrix, x, y, 176, 18, 18, 18);
                }
            }
            else {
                if (isHovered) {
                    this.blit(matrix, x,y, 176, 0, 18, 18);
                }
                else {
                    // Render nothing. This is already on the backdrop.
                }
            }

            if (this.isHoveredOrFocused()) {
                this.renderToolTip(matrix, p_230431_2_, p_230431_3_);
            }
        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(new MoreMinecartsPacketHandler.ChunkLoaderPacket(!menu.isEnabled()));
        }

        @Override
        public void updateNarration(NarrationElementOutput p_169152_) {

        }
    }
}