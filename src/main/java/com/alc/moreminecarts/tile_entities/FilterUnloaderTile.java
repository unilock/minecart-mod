package com.alc.moreminecarts.tile_entities;

import com.alc.moreminecarts.containers.FilterUnloaderContainer;
import com.alc.moreminecarts.entities.ChunkLoaderCartEntity;
import com.alc.moreminecarts.registry.MMTileEntities;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class FilterUnloaderTile extends AbstractCommonLoader implements WorldlyContainer, ExtendedScreenHandlerFactory {

    public final InventoryStorage storage = InventoryStorage.of(this, Direction.NORTH);

    public enum FilterType {
        allow_per_slot,
        allow_for_all,
        disallow_for_all;

        public int toInt() {
            switch(this) {
                case allow_per_slot:
                    return 0;
                case allow_for_all:
                    return 1;
                case disallow_for_all:
                    return 2;
            }
            return 3;
        }

        public static FilterUnloaderTile.FilterType next(FilterUnloaderTile.FilterType in) {
            switch(in) {
                case allow_per_slot:
                    return allow_for_all;
                case allow_for_all:
                    return disallow_for_all;
                case disallow_for_all:
                    return allow_per_slot;
            }
            return FilterUnloaderTile.FilterType.allow_per_slot;
        }

        public static FilterUnloaderTile.FilterType fromInt(int n) {
            if (n == 0) return FilterUnloaderTile.FilterType.allow_per_slot;
            else if (n == 1) return FilterUnloaderTile.FilterType.allow_for_all;
            else return FilterUnloaderTile.FilterType.disallow_for_all;
        }
    }

    public static int VALID_ITEM_SLOTS = 9;
    public static final int[] VALID_TAKE_SLOTS = new int[]{0,1,2,3,4,5,6,7,8};

    public FilterUnloaderTile(BlockPos pos, BlockState state) {
        super(MMTileEntities.FILTER_UNLOADER_TILE_ENTITY, pos, state);
        last_redstone_output = !redstone_output;
    }

    @Override
    public boolean getIsUnloader() {
        return true;
    }

    public void tick() {

        if (!level.isClientSide) {

            if (!isOnCooldown()) {
                List<AbstractMinecart> minecarts = getLoadableMinecartsInRange();
                float criteria_total = 0;
                for (AbstractMinecart minecart : minecarts) {

                    // No fluid or electric unloads

                    if (minecart instanceof AbstractMinecartContainer && !(minecart instanceof ChunkLoaderCartEntity)) {
                        criteria_total += doMinecartUnloads((AbstractMinecartContainer) minecart);
                    }

                }

                if (minecarts.size() == 0) criteria_total = 0;
                else criteria_total /= minecarts.size();

                if (comparator_output != ComparatorOutputType.cart_fullness)
                    criteria_total = (float) Math.floor(criteria_total);

                int new_comparator_output_value = (int) (criteria_total * 15);
                if (new_comparator_output_value != comparator_output_value || last_redstone_output != redstone_output) {
                    comparator_output_value = new_comparator_output_value;
                    last_redstone_output = redstone_output;
                    level.updateNeighbourForOutputSignal(getBlockPos(), this.getBlockState().getBlock());
                    level.updateNeighborsAt(getBlockPos(), this.getBlockState().getBlock());
                }

                if (changed_flag) {
                    this.setChanged();
                    changed_flag = false;
                }

            } else {
                decCooldown();
            }

        }
    }

    public float doMinecartUnloads(AbstractMinecartContainer minecart) {
        boolean changed = false;
        boolean all_empty = true;

        for (int i = 0; i < minecart.getContainerSize(); i++) {

            ItemStack unloadingStack = minecart.getItem(i);

            if (unloadingStack.isEmpty() || (leave_one_in_stack && unloadingStack.getCount() == 1)) continue;
            all_empty = false;

            for (int j = 0; j < VALID_ITEM_SLOTS; j++) {
                ItemStack add_to_stack = this.getItem(j);

                if (filterType == FilterType.allow_per_slot) {

                    ItemStack check_stack = this.getItem(j+VALID_ITEM_SLOTS);

                    if (!check_stack.isEmpty() && !itemsMatch(check_stack, unloadingStack)) {
                        continue;
                    }

                }
                else
                {
                    boolean matchesAny = false;

                    for (int k = VALID_ITEM_SLOTS; k < VALID_ITEM_SLOTS * 2; k++) {

                        ItemStack check_stack = this.getItem(k);

                        if (!check_stack.isEmpty() && itemsMatch(check_stack, unloadingStack)) {
                            matchesAny = true;
                            break;
                        }

                    }

                    if (matchesAny != (filterType == FilterType.allow_for_all)) continue;
                }

                boolean did_load = false;

                if (add_to_stack.isEmpty()) {
                    int true_count = unloadingStack.getCount() - (leave_one_in_stack? 1 : 0);
                    ItemStack new_stack = unloadingStack.copy();
                    int transfer_amount = Math.min(8, true_count);
                    new_stack.setCount(transfer_amount);
                    this.setItem(j, new_stack);
                    unloadingStack.shrink(transfer_amount);
                    did_load = true;
                }
                else if (canMergeItems(add_to_stack, unloadingStack)) {
                    int true_count = unloadingStack.getCount() - (leave_one_in_stack? 1 : 0);
                    int to_fill = add_to_stack.getMaxStackSize() - add_to_stack.getCount();
                    int transfer = Math.min(8, Math.min(true_count, to_fill));
                    unloadingStack.shrink(transfer);
                    add_to_stack.grow(transfer);
                    did_load = transfer > 0;
                }

                if (did_load) {
                    changed = true;
                    break;
                }

            }
        }

        if (changed) {
            resetCooldown();
            changed_flag = true;
        }

        if (comparator_output == ComparatorOutputType.done_loading) return changed? 0.0f : 1.0f;
        else if (comparator_output == ComparatorOutputType.cart_full) return all_empty? 1.0f : 0.0f;
        else if (minecart instanceof ChunkLoaderCartEntity && comparator_output == ComparatorOutputType.cart_fullness) {
            return ((ChunkLoaderCartEntity)minecart).getComparatorSignal() / 15.0f;
        }
        else {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(minecart) / 15.0f;
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("Filtered Unloader");
    }

    public static void doTick(Level level, BlockPos pos, BlockState state, FilterUnloaderTile ent) {
        ent.tick();
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack p_180462_2_, @Nullable Direction p_180462_3_) {
        return false;
    }

    @Override
    public int[] getSlotsForFace(Direction p_58363_) {
        return VALID_TAKE_SLOTS;
    }

    @Override
    public int getSlotCount() {
        return 18;
    }

    public boolean itemsMatch(ItemStack a, ItemStack b) {
        return ItemStack.isSameItemSameTags(a, b);
    }

    @Override
    public boolean canTakeItemThroughFace(int p_58392_, ItemStack p_58393_, Direction p_58394_) {
        return p_58392_ < VALID_ITEM_SLOTS;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        setChanged();
        return new FilterUnloaderContainer(containerId, inventory, this, this.dataAccess, getBlockPos());
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(getBlockPos());
    }
}
