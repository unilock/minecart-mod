package com.alc.moreminecarts.client;

import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.entities.PistonPushcartEntity;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class PistonPushcartUpKey extends KeyMapping {


    public PistonPushcartUpKey(String description, int default_key, String category) {
        super(description, default_key, category);
    }

    @Override
    public void setDown(boolean pressed) {
        super.setDown(pressed);

        if (Minecraft.getInstance().getConnection() == null) return;

        Player player = MoreMinecartsMod.PROXY.getPlayer();
        if (player.getRootVehicle() instanceof PistonPushcartEntity) {
            MoreMinecartsPacketHandler.PistonPushcartPacket packet = new MoreMinecartsPacketHandler.PistonPushcartPacket(true, pressed);
            ClientPlayNetworking.send(packet);
        }

    }
}
