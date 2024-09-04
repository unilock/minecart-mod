package com.alc.moreminecarts.misc;


import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.world.entity.player.Player;

// This allows players to break things at normal speeds while riding anything.
public class BreakSpeedEventReceiver {

    public static void register() {
        PlayerEvents.BREAK_SPEED.register(BreakSpeedEventReceiver::getBreakSpeed);
    }

    private static void getBreakSpeed(PlayerEvents.BreakSpeed event) {

        Player player = event.getEntity();
        if (player.getVehicle() != null && !player.onGround()) {
            // When a player is not grounded, the mining speed is always divided by 5.
            event.setNewSpeed(event.getOriginalSpeed() * 5);
        }

    }

}
