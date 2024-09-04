package com.alc.moreminecarts.client;

import com.alc.moreminecarts.containers.MinecartUnLoaderContainer;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.alc.moreminecarts.tile_entities.AbstractCommonLoader;
import com.alc.moreminecarts.tile_entities.MinecartLoaderTile;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

@Environment(EnvType.CLIENT)
public class MinecartUnLoaderScreen extends AbstractContainerScreen<MinecartUnLoaderContainer> {
    private static final ResourceLocation display = new ResourceLocation("moreminecarts:textures/gui/loader_gui.png");
    public static final Component UNLOADER_TITLE = Component.translatable("gui.moreminecarts.unloader.title");
    public static final Component LOADER_TITLE = Component.translatable("gui.moreminecarts.loader.title");
    public static final Component OUTPUT_INACTIVITY = Component.translatable("gui.moreminecarts.unloader.output.inactivity");
    public static final Component OUTPUT_UNLOADER_FULL = Component.translatable("gui.moreminecarts.unloader.output.cart_full");
    public static final Component OUTPUT_LOADER_FULL = Component.translatable("gui.moreminecarts.loader.output.cart_full");
    public static final Component OUTPUT_FULLNESS = Component.translatable("gui.moreminecarts.unloader.output.cart_fullness");
    public static final Component ONLY_LOCKED_ON = Component.translatable("gui.moreminecarts.unloader.only_locked.on");
    public static final Component ONLY_LOCKED_OFF = Component.translatable("gui.moreminecarts.unloader.only_locked.off");
    public static final Component LEAVE_ONE_UNLOADER_ON = Component.translatable("gui.moreminecarts.unloader.leave_one.on");
    public static final Component LEAVE_ONE_UNLOADER_OFF = Component.translatable("gui.moreminecarts.unloader.leave_one.off");
    public static final Component LEAVE_ONE_LOADER_ON = Component.translatable("gui.moreminecarts.loader.leave_one.on");
    public static final Component LEAVE_ONE_LOADER_OFF = Component.translatable("gui.moreminecarts.loader.leave_one.off");
    public static final Component REDSTONE_ON = Component.translatable("gui.moreminecarts.unloader.redstone.on");
    public static final Component REDSTONE_OFF = Component.translatable("gui.moreminecarts.unloader.redstone.off");

    private final List<AbstractButton> buttons = Lists.newArrayList();

    public MinecartUnLoaderScreen(MinecartUnLoaderContainer container, Inventory inv, Component titleIn) {
        super(container, inv, container.getIsUnloader()? UNLOADER_TITLE : LOADER_TITLE);
    }

    @Override
    public Component getTitle() {
        return menu.getIsUnloader()? UNLOADER_TITLE : LOADER_TITLE;
    }

    private void addButton(AbstractButton p_169617_) {
        this.addRenderableWidget(p_169617_);
        this.buttons.add(p_169617_);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new OutputTypeButton(leftPos + 46, topPos + 19));
        this.addButton(new OnlyLockedButton(leftPos + 68, topPos + 19));
        this.addButton(new ComparatorOutputButton(leftPos + 90, topPos + 19));
        this.addButton(new LeaveOneInStackButton(leftPos + 112, topPos + 19));
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

//        String contents_text = "";
//        FluidStack fluid_stack = menu.getFluids();
//        if (fluid_stack == null || fluid_stack.isEmpty()) {
//            contents_text += "0/2,000 mB, ";
//        }
//        else {
//            contents_text += fluid_stack.getAmount() + "/2,000 mB " + fluid_stack.getDisplayName().getString() + ", ";
//        }
//
//        int energy_amount = menu.getEnergy();
//        contents_text += energy_amount + "/2,000 RF";
//
//        matrix.drawString(font, contents_text, leftPos + 7, topPos + 62, 4210752, false);

    }

    // Taken from BeaconScreen, for tooltip rendering.
    @Override
    protected void renderLabels(GuiGraphics matrix, int p_230451_2_, int p_230451_3_) {
        matrix.drawString(font, getTitle(), this.titleLabelX, this.titleLabelY, 4210752, false);
        matrix.drawString(font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Environment(EnvType.CLIENT)
    class ComparatorOutputButton extends MMButton {

        AbstractCommonLoader.ComparatorOutputType oldValue;

        protected ComparatorOutputButton(int x, int y) {
            super(x, y);
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
            MoreMinecartsPacketHandler.MinecartLoaderPacket oldPacket = menu.getCurrentPacket();
            MoreMinecartsPacketHandler.MinecartLoaderPacket newPacket = new MoreMinecartsPacketHandler.MinecartLoaderPacket(oldPacket.is_unloader(), oldPacket.locked_minecarts_only(), oldPacket.leave_one_item_in_stack(), MinecartLoaderTile.ComparatorOutputType.next(oldPacket.output_type()), oldPacket.redstone_output(), oldPacket.filterType());
            ClientPlayNetworking.send(newPacket);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            Component text;
            switch (menu.getComparatorOutputType()) {
                case done_loading:
                    text = OUTPUT_INACTIVITY;
                    break;
                case cart_full:
                    if (menu.getIsUnloader()) text = OUTPUT_UNLOADER_FULL;
                    else text = OUTPUT_LOADER_FULL;
                    break;
                case cart_fullness:
                    text = OUTPUT_FULLNESS;
                    break;
                default:
                    text = Component.literal("ERROR");
            }

            this.setTooltip(Tooltip.create(text));
        }
    }

    @Environment(EnvType.CLIENT)
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
            MoreMinecartsPacketHandler.MinecartLoaderPacket oldPacket = menu.getCurrentPacket();
            MoreMinecartsPacketHandler.MinecartLoaderPacket newPacket = new MoreMinecartsPacketHandler.MinecartLoaderPacket(oldPacket.is_unloader(), !oldPacket.locked_minecarts_only(), oldPacket.leave_one_item_in_stack(), oldPacket.output_type(), oldPacket.redstone_output(), oldPacket.filterType());
            ClientPlayNetworking.send(newPacket);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(
                    menu.getLockedMinecartsOnly()? ONLY_LOCKED_ON : ONLY_LOCKED_OFF
            ));
        }
    }

    @Environment(EnvType.CLIENT)
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
            MoreMinecartsPacketHandler.MinecartLoaderPacket oldPacket = menu.getCurrentPacket();
            MoreMinecartsPacketHandler.MinecartLoaderPacket newPacket = new MoreMinecartsPacketHandler.MinecartLoaderPacket(oldPacket.is_unloader(), oldPacket.locked_minecarts_only(), !oldPacket.leave_one_item_in_stack(), oldPacket.output_type(), oldPacket.redstone_output(), oldPacket.filterType());
            ClientPlayNetworking.send(newPacket);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(
                    menu.getIsUnloader()
                            ? (menu.getLeaveOneInStack()
                            ? LEAVE_ONE_UNLOADER_ON
                            : LEAVE_ONE_UNLOADER_OFF)
                            : (menu.getLeaveOneInStack()
                            ? LEAVE_ONE_LOADER_ON
                            : LEAVE_ONE_LOADER_OFF)
            ));
        }
    }

    @Environment(EnvType.CLIENT)
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

        @Override
        public void onPress() {
            MoreMinecartsPacketHandler.MinecartLoaderPacket oldPacket = menu.getCurrentPacket();
            MoreMinecartsPacketHandler.MinecartLoaderPacket newPacket = new MoreMinecartsPacketHandler.MinecartLoaderPacket(oldPacket.is_unloader(), oldPacket.locked_minecarts_only(), oldPacket.leave_one_item_in_stack(), oldPacket.output_type(), !oldPacket.redstone_output(), oldPacket.filterType());
            ClientPlayNetworking.send(newPacket);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {}

        public void UpdateTooltip() {
            this.setTooltip(Tooltip.create(menu.getRedstoneOutput() ? REDSTONE_ON : REDSTONE_OFF));
        }
    }

}
