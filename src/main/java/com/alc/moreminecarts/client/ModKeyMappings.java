package com.alc.moreminecarts.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class ModKeyMappings {

	// Jump key
	public static final KeyMapping PISTON_PUSHCART_UP = new PistonPushcartUpKey("Piston Pushcart Up", 32, "More Minecarts and Rails");

	// Left control key
	public static final KeyMapping PISTON_PUSHCART_DOWN = new PistonPushcartDownKey("Piston Pushcart Down", 341, "More Minecarts and Rails");

	public static void setupKeybindings() {
        KeyBindingHelper.registerKeyBinding(PISTON_PUSHCART_UP);
        KeyBindingHelper.registerKeyBinding(PISTON_PUSHCART_DOWN);
	}
}
