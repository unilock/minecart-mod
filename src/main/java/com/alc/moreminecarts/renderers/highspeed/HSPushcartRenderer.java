package com.alc.moreminecarts.renderers.highspeed;

import com.alc.moreminecarts.renderers.FixedBlockMinecart;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.ResourceLocation;

public class HSPushcartRenderer extends FixedBlockMinecart {
    public HSPushcartRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractMinecartEntity entity) {
        return new ResourceLocation("moreminecarts:textures/entity/high_speed_pushcart.png");
    }
}
