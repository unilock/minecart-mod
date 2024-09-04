package com.alc.moreminecarts.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;


@Environment(EnvType.CLIENT)
public class MMButton extends AbstractButton {

    public int xPos;
    public int yPos;

    public MMButton(int x, int y) {
        super(x, y, 18, 18, CommonComponents.EMPTY);
        this.xPos = x;
        this.yPos = y;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public void onPress() {

    }
}
