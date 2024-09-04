package com.alc.moreminecarts.containers;

import com.alc.moreminecarts.misc.ChunkLoaderSlot;
import com.alc.moreminecarts.registry.MMContainers;
import com.alc.moreminecarts.tile_entities.ChunkLoaderTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChunkLoaderContainer extends AbstractContainerMenu {
    public static final int SLOT_COUNT = 1;
    private static final int INV_SLOT_START = 1;
    private static final int INV_SLOT_END = 28;
    private final Container inventory;
    private final ContainerData data;

    public ChunkLoaderContainer(int n, Level world, Inventory player_inventory, Player player_entity) {
        super(MMContainers.CHUNK_LOADER_CONTAINER, n);

        this.inventory = new SimpleContainer(1);
        this.data = new SimpleContainerData(4);

        CommonInitialization(player_inventory);
    }

    // For use with the entity chunk loaders.
    public ChunkLoaderContainer(int n, Level world, Container inventory, ContainerData data, Inventory player_inventory, Player player_entity) {
        super(MMContainers.CHUNK_LOADER_CONTAINER, n);

        this.inventory = inventory;
        this.data = data;

        CommonInitialization(player_inventory);
    }

    // For use with tile entity chunk loaders (server).
    public ChunkLoaderContainer(int n, Level world, BlockPos pos, Inventory player_inventory, Player player_entity) {
        super(MMContainers.CHUNK_LOADER_CONTAINER, n);

        ChunkLoaderTile tile = (ChunkLoaderTile) world.getBlockEntity(pos);

        this.inventory = tile;
        this.data = tile.dataAccess;

        CommonInitialization(player_inventory);
    }

    // For use with tile entity chunk loaders (client).
    public ChunkLoaderContainer(int p_38969_, Inventory p_38970_, Container p_38971_, ContainerData p_38972_) {
        super(MMContainers.CHUNK_LOADER_CONTAINER, p_38969_);

        this.inventory = p_38971_;
        this.data = p_38972_;

        CommonInitialization(p_38970_);
    }

    public void CommonInitialization(Inventory player_inventory) {

        checkContainerSize(inventory, 1);
        checkContainerDataCount(data, 2);

        this.addSlot(new ChunkLoaderSlot(inventory, 0, 80, 15){
            public void setChanged() {
                super.setChanged();
                ChunkLoaderContainer.this.slotsChanged(this.container);
            }});

        // player inventory slots, taken from the AbstractFurnaceContainer code.
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(player_inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(player_inventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    // Taken from the beacon container. No clue what this does really.
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (this.moveItemStackTo(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
                return ItemStack.EMPTY;
            } else if (index >= 1 && index < 28) {
                if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 28 && index < 37) {
                if (!this.moveItemStackTo(itemstack1, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public int getSize() {
        return 1;
    }

    public int getTimeLeft() {
        return Math.abs(this.data.get(0));
    }

    public double getLogProgress() {
        return (Math.log10( ((float)getTimeLeft()/ChunkLoaderTile.MAX_MINUTES)*9 + 1 ));
    }

    public boolean isEnabled() {
        return this.data.get(1) > 0;
    }

    public void setEnabled(boolean enabled) {
        this.setData(1, enabled? 1 : -1);
        this.broadcastChanges();
    }
}
