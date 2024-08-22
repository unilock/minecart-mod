package com.alc.moreminecarts.client;

import com.alc.moreminecarts.containers.FilterUnloaderContainer;
import com.alc.moreminecarts.containers.MinecartUnLoaderContainer;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.alc.moreminecarts.tile_entities.AbstractCommonLoader;
import com.alc.moreminecarts.tile_entities.FilterUnloaderTile;
import com.alc.moreminecarts.tile_entities.MinecartLoaderTile;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Filter;

@OnlyIn(Dist.CLIENT)
public class FilterUnloaderScreen extends AbstractContainerScreen<FilterUnloaderContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/filter_loader_gui.png");
    private static final Component TITLE = Component.translatable("gui.moreminecarts.filter_unloader.title");
    private static final Component FILTER_ALLOW_PER_SLOT = Component.translatable("gui.moreminecarts.filter_unloader.filter.allow_per_slot");
    private static final Component FILTER_ALLOW_FOR_ALL = Component.translatable("gui.moreminecarts.filter_unloader.filter.allow_for_all");
    private static final Component FILTER_DISALLOW_FOR_ALL = Component.translatable("gui.moreminecarts.filter_unloader.filter.disallow_for_all");

    private final List<AbstractButton> buttons = Lists.newArrayList();

    public FilterUnloaderScreen(FilterUnloaderContainer container, Inventory inv, Component titleIn) {
        super(container, inv, TITLE);
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    private void addButton(AbstractButton p_169617_) {
        this.addRenderableWidget(p_169617_);
        this.buttons.add(p_169617_);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new OutputTypeButton(leftPos + 46, topPos + 6));
        this.addButton(new OnlyLockedButton(leftPos + 68, topPos + 6));
        this.addButton(new ComparatorOutputButton(leftPos + 90, topPos + 6));
        this.addButton(new LeaveOneInStackButton(leftPos + 112, topPos + 6));
        this.addButton(new FilterTypeButton(leftPos + 7, topPos + 6));
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
        matrix.blit(display, leftPos, topPos - 14, 0, 0, 176, 180);

    }

    // Taken from BeaconScreen, for tooltip rendering.
    @Override
    protected void renderLabels(GuiGraphics matrix, int p_230451_2_, int p_230451_3_) {
        matrix.drawString(font, getTitle(), this.titleLabelX, this.titleLabelY - 14, 4210752, false);
        matrix.drawString(font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @OnlyIn(Dist.CLIENT)
    class ComparatorOutputButton extends MMButton {

        AbstractCommonLoader.ComparatorOutputType oldValue;

        protected ComparatorOutputButton(int x, int y) {
            super(x,y);
            UpdateTooltip();
        }

        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShaderTexture(0, display);

            boolean mouse_on = isDragging() && this.isHovered;

            switch (menu.getComparatorOutputType()) {
                case done_loading:
                    if (mouse_on) {
                        matrix.blit(display, xPos,yPos, 176+18, 18, 18, 18);
                    }
                    else {
                        matrix.blit(display, xPos,yPos, 176, 18, 18, 18);
                    }
                    break;
                case cart_full:
                    if (mouse_on) {
                        if (menu.getIsUnloader()) matrix.blit(display, xPos,yPos, 176+18+36, 0, 18, 18);
                        else matrix.blit(display, xPos,yPos, 176+18, 0, 18, 18);
                    }
                    else {
                        if (menu.getIsUnloader()) matrix.blit(display, xPos,yPos, 176+36, 0, 18, 18);
                        //matrix.blit(display, x, y, 176, 0, 18, 18);
                    }
                    break;
                case cart_fullness:
                    if (mouse_on) {
                        matrix.blit(display, xPos,yPos, 176+18, 36, 18, 18);
                    }
                    else {
                        matrix.blit(display, xPos,yPos, 176, 36, 18, 18);
                    }
                    break;
                default:
            }

            var newValue = menu.getComparatorOutputType();
            if (newValue != oldValue) {
                oldValue = newValue;
                UpdateTooltip();
            }
        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.MinecartLoaderPacket packet = menu.getCurrentPacket();
            packet.output_type = MinecartLoaderTile.ComparatorOutputType.next(packet.output_type);
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            Component text;

            switch (menu.getComparatorOutputType()) {
                case done_loading:
                    text = MinecartUnLoaderScreen.OUTPUT_INACTIVITY;
                    break;
                case cart_full:
                    if (menu.getIsUnloader()) text = MinecartUnLoaderScreen.OUTPUT_UNLOADER_FULL;
                    else text = MinecartUnLoaderScreen.OUTPUT_LOADER_FULL;
                    break;
                case cart_fullness:
                    text = MinecartUnLoaderScreen.OUTPUT_FULLNESS;
                    break;
                default:
                    text = Component.literal("ERROR");
            }

            this.setTooltip(Tooltip.create(text));
        }
    }

    @OnlyIn(Dist.CLIENT)
    class FilterTypeButton extends MMButton {

        FilterUnloaderTile.FilterType oldValue;

        protected FilterTypeButton(int x, int y) {
            super(x, y);
            UpdateTooltip();
        }

        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShaderTexture(0, display);

            boolean mouse_on = isDragging() && this.isHovered;

            switch (menu.getFilterType()) {
                case allow_for_all:
                    if (mouse_on) {
                        matrix.blit(display, xPos,yPos, 176+18, 126, 18, 18);
                    }
                    else {
                        matrix.blit(display, xPos,yPos, 176, 126, 18, 18);
                    }
                    break;
                case disallow_for_all:
                    if (mouse_on) {
                        matrix.blit(display, xPos,yPos, 176+18, 126+18, 18, 18);
                    }
                    else {
                        matrix.blit(display, xPos,yPos, 176, 126+18, 18, 18);
                    }
                    break;
                case allow_per_slot:
                    if (mouse_on) {
                        matrix.blit(display, xPos,yPos, 176+18, 126+36, 18, 18);
                    }
                    else {
                        matrix.blit(display, xPos,yPos, 176, 126+36, 18, 18);
                    }
                    break;
                default:
            }

            FilterUnloaderTile.FilterType newValue = menu.getFilterType();
            if (newValue != oldValue) {
                oldValue = newValue;
                UpdateTooltip();
            }

        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.MinecartLoaderPacket packet = menu.getCurrentPacket();
            packet.filterType = FilterUnloaderTile.FilterType.next(packet.filterType);
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            Component text;

            switch (menu.getFilterType()) {
                case allow_per_slot:
                    text = FILTER_ALLOW_PER_SLOT;
                    break;
                case allow_for_all:
                    text = FILTER_ALLOW_FOR_ALL;
                    break;
                case disallow_for_all:
                    text = FILTER_DISALLOW_FOR_ALL;
                    break;
                default:
                    text = Component.literal("ERROR");
            }

            this.setTooltip(Tooltip.create(text));
        }
    }

    @OnlyIn(Dist.CLIENT)
    class OnlyLockedButton extends MMButton {

        boolean oldValue;

        protected OnlyLockedButton(int x, int y) {
            super(x, y);
            UpdateTooltip();
        }

        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShaderTexture(0, display);

            boolean mouse_on = isDragging() && this.isHovered;

            if (menu.getLockedMinecartsOnly()) {
                if (mouse_on) {
                    if (menu.getIsUnloader()) matrix.blit(display, xPos,yPos, 176+18+36, 108, 18, 18);
                    else matrix.blit(display, xPos,yPos, 176+18, 108, 18, 18);
                }
                else {
                    if (menu.getIsUnloader()) matrix.blit(display, xPos,yPos, 176+36, 108, 18, 18);
                    else matrix.blit(display, xPos,yPos, 176, 108, 18, 18);
                }
            }
            else {
                if (mouse_on) {
                    matrix.blit(display, xPos,yPos, 176+18, 108-18, 18, 18);
                }
                else {
                    // Render nothing. This is already on the backdrop.
                }
            }

            boolean newValue = menu.getLockedMinecartsOnly();
            if (newValue != oldValue) {
                oldValue = newValue;
                UpdateTooltip();
            }
        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.MinecartLoaderPacket packet = menu.getCurrentPacket();
            packet.locked_minecarts_only = !packet.locked_minecarts_only;
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(menu.getLockedMinecartsOnly()? MinecartUnLoaderScreen.ONLY_LOCKED_ON : MinecartUnLoaderScreen.ONLY_LOCKED_OFF));
        }
    }

    @OnlyIn(Dist.CLIENT)
    class LeaveOneInStackButton extends MMButton {

        boolean oldValue;

        protected LeaveOneInStackButton(int x, int y) {
            super(x, y);
            UpdateTooltip();
        }

        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShaderTexture(0, display);

            boolean mouse_on = isDragging() && this.isHovered;

            if (menu.getLeaveOneInStack()) {
                if (mouse_on) {
                    if (menu.getIsUnloader()) matrix.blit(display, xPos,yPos, 176+18+36, 72, 18, 18);
                    else matrix.blit(display, xPos,yPos, 176+18, 72, 18, 18);
                }
                else {
                    if (menu.getIsUnloader()) matrix.blit(display, xPos, yPos, 176+36, 72, 18, 18);
                    else matrix.blit(display, xPos,yPos, 176, 72, 18, 18);
                }
            }
            else {
                if (mouse_on) {
                    matrix.blit(display, xPos,yPos, 176+18, 72-18, 18, 18);
                }
                else {
                    // Render nothing. This is already on the backdrop.
                }
            }

            boolean newValue = menu.getLeaveOneInStack();
            if (newValue != oldValue) {
                oldValue = newValue;
                UpdateTooltip();
            }

        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.MinecartLoaderPacket packet = menu.getCurrentPacket();
            packet.leave_one_item_in_stack = !packet.leave_one_item_in_stack;
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(
                    menu.getIsUnloader()
                        ? (menu.getLeaveOneInStack()
                        ? MinecartUnLoaderScreen.LEAVE_ONE_UNLOADER_ON
                        : MinecartUnLoaderScreen.LEAVE_ONE_UNLOADER_OFF)
                        : (menu.getLeaveOneInStack()
                        ? MinecartUnLoaderScreen.LEAVE_ONE_LOADER_ON
                        : MinecartUnLoaderScreen.LEAVE_ONE_LOADER_OFF)
            ));
        }
    }

    @OnlyIn(Dist.CLIENT)
    class OutputTypeButton extends MMButton {

        boolean oldValue;

        protected OutputTypeButton(int x, int y) {
            super(x, y);
            UpdateTooltip();
        }

        public void renderWidget(GuiGraphics matrix, int x, int y, float p_230431_4_) {
            RenderSystem.setShaderTexture(0, display);

            boolean mouse_on = isDragging() && this.isHovered;

            if (menu.getRedstoneOutput()) {
                if (mouse_on) {
                    matrix.blit(display, xPos,yPos, 212+18, 36, 18, 18);
                }
                else {
                    matrix.blit(display, xPos,yPos, 212, 36, 18, 18);
                }
            }
            else {
                if (mouse_on) {
                    matrix.blit(display, xPos,yPos, 230, 18, 18, 18);
                }
                else {
                    // Render nothing. This is already on the backdrop.
                }
            }

            boolean newValue = menu.getRedstoneOutput();
            if (newValue != oldValue) {
                oldValue = newValue;
                UpdateTooltip();
            }

        }

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(menu.getRedstoneOutput() ? MinecartUnLoaderScreen.REDSTONE_ON : MinecartUnLoaderScreen.REDSTONE_OFF));
        }

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.MinecartLoaderPacket packet = menu.getCurrentPacket();
            packet.redstone_output = !packet.redstone_output;
            MoreMinecartsPacketHandler.INSTANCE.sendToServer(packet);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}
    }

}
