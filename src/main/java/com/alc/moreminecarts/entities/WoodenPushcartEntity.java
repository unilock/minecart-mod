package com.alc.moreminecarts.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("moreminecarts")
public class WoodenPushcartEntity extends AbstractPushcart {

    public static final Item wooden_pushcart = null;

    public WoodenPushcartEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public WoodenPushcartEntity(EntityType<?> type, World worldIn, double x, double y, double z) {
        super(type, worldIn, x, y, z);
    }

    @Override
    public void killMinecart(DamageSource source) {
        this.remove();
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack stack = new ItemStack(wooden_pushcart);
            if (this.hasCustomName()) {
                stack.setDisplayName(this.getCustomName());
            }
            this.entityDropItem(stack);
        }
    }

    public double getControlSpeed() { return 40; }
    public double getUphillSpeed() { return 4.8; }
    public double getBrakeSpeed() { return 0.25; }

    // Wooden cart speed is capped at half that of a normal cart
    @Override
    protected double getMaximumSpeed() {
        return 0.3D;
    }
}