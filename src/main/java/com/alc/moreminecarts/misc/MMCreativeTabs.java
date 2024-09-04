package com.alc.moreminecarts.misc;


import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.registry.MMItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MMCreativeTabs {

    public static final ResourceLocation BASE_TAB_ID = new ResourceLocation(MMConstants.modid, "base");

    private static class DisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
            output.acceptAll(
                    MMItems.ITEMS
            );
//            output.accept(new ItemStack( MMItems.ARITHMETIC_RAIL_ITEM));
        }
    }

    public static final CreativeModeTab tab = register("more_minecarts_tab",
           FabricItemGroup.builder()
                   .icon(() -> new ItemStack(MMItems.PISTON_PUSHCART_ITEM))
                   .title(Component.translatable("More Minecarts and Rails"))
                   .displayItems(new DisplayItemsGenerator())
                   .build());

    public static void register() {
    }

    private static CreativeModeTab register(String path, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MMConstants.id(path), tab);
    }

}
