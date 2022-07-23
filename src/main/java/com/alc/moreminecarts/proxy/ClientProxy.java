package com.alc.moreminecarts.proxy;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.client.PistonPushcartDownKey;
import com.alc.moreminecarts.client.PistonPushcartUpKey;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid =  MMConstants.modid, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientProxy implements IProxy {

    @Override
    public Level getWorld() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isHoldingJump() {
        return Minecraft.getInstance().player.input.jumping;
    }

    @Override
    public boolean isHoldingRun() {
        return Minecraft.getInstance().player.input.shiftKeyDown;
    }

    @SubscribeEvent
    public void setupKeybindings(RegisterKeyMappingsEvent event) {
        // Jump key
        event.register(new PistonPushcartUpKey("Piston Pushcart Up", 32, "More Minecarts and Rails"));
        // Left control key
        event.register(new PistonPushcartDownKey("Piston Pushcart Down", 341, "More Minecarts and Rails"));
    }


}
