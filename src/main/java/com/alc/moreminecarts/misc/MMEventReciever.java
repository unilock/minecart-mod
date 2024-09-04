package com.alc.moreminecarts.misc;


import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.entities.PistonPushcartEntity;
import com.alc.moreminecarts.items.CouplerItem;
import com.alc.moreminecarts.registry.MMItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class MMEventReciever {

    public static void register() {
        UseEntityCallback.EVENT.register(MMEventReciever::onInteractEntity);
    }

    private static InteractionResult onInteractEntity(Player player, Level world, InteractionHand hand, Entity entity, EntityHitResult hitResult) {

        InteractionResult result = InteractionResult.PASS;

        if (entity instanceof PistonPushcartEntity) {
            MoreMinecartsMod.LOGGER.log(org.apache.logging.log4j.Level.WARN, "piston pushcart interact");
        }

        ItemStack using = player.getItemInHand(hand);

        InteractionHand other_hand = hand == InteractionHand.MAIN_HAND? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack using_secondary = player.getItemInHand(other_hand);

        Item couplerItem = MMItems.COUPLER_ITEM;

        // We check both hands, but only use one, since this function gets called once for each hand.
        if (using.getItem() == couplerItem || using_secondary.getItem() == couplerItem) {
            result = InteractionResult.CONSUME;
            if (world.isClientSide()) return result;

            if (using.getItem() == couplerItem) {
                if (entity instanceof AbstractMinecart
                    || entity instanceof Boat
                    || entity instanceof Mob
                    || entity instanceof EnderDragon){

                    player.playSound(SoundEvents.CHAIN_PLACE, 0.9F, 1.0F);
                    CouplerItem.hookIn(player, world, using, entity);
                }
                else {
                    CouplerItem.clearCoupler(using);
                }
            }
        }

//        Item hsUpgradeItem = MMItems.HIGH_SPEED_UPGRADE_ITEM;
//
//
//        if (using.getItem() == hsUpgradeItem || using_secondary.getItem() == hsUpgradeItem) {
//            result = InteractionResult.CONSUME;
//
//            if (world.isClientSide()) return result;
//
//            if (using.getItem() == hsUpgradeItem && entity instanceof AbstractMinecart
//                && !(entity instanceof HSMinecartEntities.IHSCart)) {
//                boolean success = HSMinecartEntities.upgradeMinecart((AbstractMinecart) entity);
//                if (!player.isCreative() && success) using.shrink(1);
//            }
//        }
//
//        // To prevent entering a high speed cart immediately after upgrading it.
//        if ( (entity instanceof HSMinecartEntities.HSMinecart || entity instanceof HSMinecartEntities.HSPushcart)
//                && entity.tickCount < 10) {
//            result = InteractionResult.CONSUME;
//        }

        return result;

    }

}
