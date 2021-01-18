package com.example.examplemod.items;

import com.example.examplemod.entities.CouplerEntity;
import com.example.examplemod.entities.IronPushcartEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Predicate;

@ObjectHolder("examplemod")
public class CouplerItem extends Item {
    public static final EntityType<CouplerEntity> coupler = null;

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TAG_COUPLED_UUID_1 = "coupled_UUID_1";
    private static final String TAG_COUPLED_UUID_2 = "coupled_UUID_2";

    public CouplerItem(Properties properties) {
        super(properties);
    }


    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        CompoundNBT tag = stack.getOrCreateTag();
        if (!tag.hasUniqueId(TAG_COUPLED_UUID_1) && tag.hasUniqueId(TAG_COUPLED_UUID_2) && entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            player.playSound(SoundEvents.BLOCK_CHAIN_PLACE, 0.7F, 1.0F);
            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
            clearCoupler(stack);
            return;
        }

        if (! (worldIn instanceof  ServerWorld)) return;

        ServerWorld world = (ServerWorld)worldIn;

        if (tag.hasUniqueId(TAG_COUPLED_UUID_1) && tag.hasUniqueId(TAG_COUPLED_UUID_2)){
            UUID uuid1 = tag.getUniqueId(TAG_COUPLED_UUID_1);
            Entity ent1 = world.getEntityByUuid(uuid1);
            UUID uuid2 = tag.getUniqueId(TAG_COUPLED_UUID_2);
            Entity ent2 = world.getEntityByUuid(uuid2);

            if (ent1 != null && ent2 != null && ent1 != ent2) {

                double distance = ent1.getDistance(ent2);
                if (distance < 2.5) {

                    Vector3d center_pos = new Vector3d(
                            (ent1.getPosX() + ent2.getPosX())/2,
                            (ent1.getPosY() + ent2.getPosY())/2,
                            (ent1.getPosZ() + ent2.getPosZ())/2);

                    List<CouplerEntity> list = worldIn.getEntitiesWithinAABB(coupler,
                            new AxisAlignedBB(center_pos.x + 0.5, center_pos.y + 0.5, center_pos.z + 0.5,
                                            center_pos.x - 0.5, center_pos.y - 0.5, center_pos.z - 0.5), (entity) -> true);

                    boolean is_duplicate = false;
                    for (CouplerEntity ent : list) {
                        if ((ent.getFirstVehicle() == ent1 && ent.getSecondVehicle() == ent2)
                            || (ent.getSecondVehicle() == ent1 && ent.getFirstVehicle() == ent2)) {
                            is_duplicate = true;
                            break;
                        }
                    }

                    if (!is_duplicate) {
                        CouplerEntity coupler_ent = new CouplerEntity(coupler, worldIn, ent1, ent2);
                        worldIn.addEntity(coupler_ent);
                        tag.remove(TAG_COUPLED_UUID_1);
                        return;
                    }
                }
            }

            clearCoupler(stack);
        }

    }

    public static void hookIn(PlayerEntity player, World worldIn, ItemStack used, Entity vehicle) {
        CompoundNBT tag = used.getOrCreateTag();
        //player.playSound(SoundEvents.BLOCK_CHAIN_PLACE, 0.7F, 1.0F);
        if (tag.hasUniqueId(TAG_COUPLED_UUID_2)){}
        if (tag.hasUniqueId(TAG_COUPLED_UUID_1)) {
            UUID uuid = vehicle.getUniqueID();
            tag.putUniqueId(TAG_COUPLED_UUID_2, uuid);
        }
        else {
            UUID uuid = vehicle.getUniqueID();
            tag.putUniqueId(TAG_COUPLED_UUID_1, uuid);
            tag.putInt("CustomModelData", 1);
        }
    }

    public static void clearCoupler(ItemStack used) {
        CompoundNBT tag = used.getOrCreateTag();
        tag.remove(TAG_COUPLED_UUID_1);
        tag.remove(TAG_COUPLED_UUID_2);
        tag.putInt("CustomModelData", 0);
    }
}