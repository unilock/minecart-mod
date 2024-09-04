package com.alc.moreminecarts.proxy;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.containers.ChunkLoaderContainer;
import com.alc.moreminecarts.containers.FilterUnloaderContainer;
import com.alc.moreminecarts.containers.FlagCartContainer;
import com.alc.moreminecarts.containers.MinecartUnLoaderContainer;
import com.alc.moreminecarts.entities.CouplerEntity;
import com.alc.moreminecarts.entities.PistonPushcartEntity;
import com.alc.moreminecarts.tile_entities.AbstractCommonLoader;
import com.alc.moreminecarts.tile_entities.FilterUnloaderTile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MoreMinecartsPacketHandler {
    @Environment(EnvType.CLIENT)
    public static void initClient() {

        // For syncing the coupler to the server -> client
        ClientPlayNetworking.registerGlobalReceiver(CouplePacket.TYPE, CouplePacket::handle);
    }

    public static void initServer() {

        // For changing the chunk loader client -> server
        ServerPlayNetworking.registerGlobalReceiver(ChunkLoaderPacket.TYPE, ChunkLoaderPacket::handle);

        // For sending the piston pushcart inputs client -> server
        ServerPlayNetworking.registerGlobalReceiver(PistonPushcartPacket.TYPE, PistonPushcartPacket::handle);

        // For sending long-range player interactions client -> server
        ServerPlayNetworking.registerGlobalReceiver(ExtendedInteractPacket.TYPE, ExtendedInteractPacket::handle);

        // For changing the minecart loader and unloader client -> server
        ServerPlayNetworking.registerGlobalReceiver(MinecartLoaderPacket.TYPE, MinecartLoaderPacket::handle);

        // For changing the flag cart via GUI client -> server
        ServerPlayNetworking.registerGlobalReceiver(FlagCartPacket.TYPE, FlagCartPacket::handle);
    }

    // Currently unused.
    @Environment(EnvType.CLIENT)
    public record CouplePacket(int coupler_id, int v1, int v2) implements FabricPacket {
        public static final PacketType<CouplePacket> TYPE = PacketType.create(MMConstants.id("couple"), CouplePacket::new);

        public CouplePacket(FriendlyByteBuf buf) {
            this(buf.readInt(), buf.readInt(), buf.readInt());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.coupler_id);
            buf.writeInt(this.v1);
            buf.writeInt(this.v2);
        }

        public static void handle(CouplePacket packet, LocalPlayer player, PacketSender responseSender) {

            MoreMinecartsMod.LOGGER.log(org.apache.logging.log4j.Level.WARN, "HERE!!!");

            Minecraft.getInstance().execute(() -> {

                Level world = MoreMinecartsMod.PROXY.getWorld();

                Entity ent = world.getEntity(packet.coupler_id);
                if (ent != null && ent instanceof CouplerEntity) {
                    CouplerEntity coupler_ent = (CouplerEntity) ent;
                    coupler_ent.vehicle1_id = packet.v1;
                    coupler_ent.vehicle2_id = packet.v2;
                    MoreMinecartsMod.LOGGER.log(org.apache.logging.log4j.Level.WARN, "HERE2!!!");
                }
            });
        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }
    }

    public record ChunkLoaderPacket(boolean set_enabled) implements FabricPacket {
        public static final PacketType<ChunkLoaderPacket> TYPE = PacketType.create(MMConstants.id("chunk_loader"), ChunkLoaderPacket::new);

        public ChunkLoaderPacket(FriendlyByteBuf buf) {
            this(buf.readBoolean());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(this.set_enabled);
        }

        public static void handle(ChunkLoaderPacket packet, ServerPlayer sender, PacketSender responseSender) {
            //LogManager.getLogger().info("HERE!!!");
            sender.getServer().execute(() -> {
                if (sender.containerMenu instanceof ChunkLoaderContainer) {
                    ((ChunkLoaderContainer)sender.containerMenu).setEnabled(packet.set_enabled);
                }
            });
        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }
    }

    public record PistonPushcartPacket(boolean is_up_key, boolean now_down) implements FabricPacket {
        public static final PacketType<PistonPushcartPacket> TYPE = PacketType.create(MMConstants.id("piston_pushcart"), PistonPushcartPacket::new);

        public PistonPushcartPacket(FriendlyByteBuf buf) {
            this(buf.readBoolean(), buf.readBoolean());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(this.is_up_key);
            buf.writeBoolean(this.now_down);
        }

        public static void handle(PistonPushcartPacket packet, ServerPlayer sender, PacketSender responseSender) {

            sender.getServer().execute(() -> {
                if (sender.getRootVehicle() instanceof PistonPushcartEntity) {
                    ((PistonPushcartEntity)sender.getRootVehicle()).setElevating(packet.is_up_key, packet.now_down);
                }
            });

        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }
    }

    public enum FakeInteraction {
        INTERACTION
    }

    public record ExtendedInteractPacket(int entityId, FakeInteraction interaction, InteractionHand hand, boolean isShiftKeyDown) implements FabricPacket {
        public static final PacketType<ExtendedInteractPacket> TYPE = PacketType.create(MMConstants.id("extended_interact"), ExtendedInteractPacket::new);

        public ExtendedInteractPacket(FriendlyByteBuf buf) {
            this(buf.readVarInt(), buf.readEnum(FakeInteraction.class), buf.readEnum(InteractionHand.class), buf.readBoolean());
        }

        public ExtendedInteractPacket(Entity entity, boolean isShiftKeyDown, InteractionHand hand) {
            this(entity.getId(), FakeInteraction.INTERACTION, hand, isShiftKeyDown);
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeVarInt(entityId);
            buf.writeEnum(interaction);
            buf.writeEnum(hand);
            buf.writeBoolean(isShiftKeyDown);
        }

        public static void handle(ExtendedInteractPacket packet, ServerPlayer sender, PacketSender responseSender) {

            // MoreMinecartsMod.LOGGER.log(org.apache.logging.log4j.Level.WARN, "PISTON PUSHCART INTERACT 3");
            sender.getServer().execute(() -> {
                handleInteract(new ServerboundInteractPacket(packet.entityId, packet.isShiftKeyDown, new ServerboundInteractPacket.InteractionAction(packet.hand)), sender, sender.connection.connection);
            });
        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }
    }

    // TAKEN FROM SERVERNETPLAYHANDLER
    // Works identically to normal interaction, except only when the distance is too far to be considered by
    // the vanilla failsafe. New max distance is 100.
    public static void handleInteract(ServerboundInteractPacket p_9866_, ServerPlayer player, Connection connection) {
        // hopefully not important?
        //PacketUtils.ensureRunningOnSameThread(p_9866_, this, player.getLevel());

        ServerLevel serverlevel = player.serverLevel();
        final Entity entity = p_9866_.getTarget(serverlevel);
        player.resetLastActionTime();
        player.setShiftKeyDown(p_9866_.isUsingSecondaryAction());
        if (entity != null) {
            if (!serverlevel.getWorldBorder().isWithinBounds(entity.blockPosition())) {
                return;
            }

            double d0 = 36.0D;
            if (player.distanceToSqr(entity) >= 36.0D && player.distanceToSqr(entity) < 100) {
                MoreMinecartsMod.LOGGER.log(org.apache.logging.log4j.Level.WARN, "PISTON PUSHCART INTERACT 4");
                p_9866_.dispatch(new ServerboundInteractPacket.Handler() {
                    private void performInteraction(InteractionHand p_143679_, EntityInteraction p_143680_) {
                        ItemStack itemstack = player.getItemInHand(p_143679_).copy();
                        InteractionResult interactionresult = p_143680_.run(player, entity, p_143679_);
                        if (UseEntityCallback.EVENT.invoker().interact(player, player.getCommandSenderWorld(), p_143679_, entity, null) != InteractionResult.PASS) return; // TODO: Perhaps hitResult shouldn't be null?
                        if (interactionresult.consumesAction()) {
                            CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(player, itemstack, entity);
                            if (interactionresult.shouldSwing()) {
                                player.swing(p_143679_, true);
                            }
                        }

                    }

                    public void onInteraction(InteractionHand p_143677_) {
                        this.performInteraction(p_143677_, Player::interactOn);
                    }

                    public void onInteraction(InteractionHand p_143682_, Vec3 p_143683_) {
                        this.performInteraction(p_143682_, (p_143686_, p_143687_, p_143688_) -> {
                            return p_143687_.interactAt(p_143686_, p_143683_, p_143688_);
                        });
                    }

                    public void onAttack() {
                        if (!(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb) && !(entity instanceof AbstractArrow) && entity != player) {
                            player.attack(entity);
                        } else {
                            disconnect(Component.translatable("multiplayer.disconnect.invalid_entity_attacked"), connection);
                            //ServerGamePacketListenerImpl.LOGGER.warn("Player {} tried to attack an invalid entity", (Object)ServerGamePacketListenerImpl.this.player.getName().getString());
                        }
                    }
                });
            }
        }
    }

    public static void disconnect(Component comp, Connection connection) {
        connection.send(new ClientboundDisconnectPacket(comp), new ConnectionPacketListener(comp, connection));
        connection.setReadOnly();
        connection.handleDisconnection();
    }

    public static class ConnectionPacketListener implements PacketSendListener {

        Component comp;
        Connection connection;

        public ConnectionPacketListener(Component comp, Connection connection) {
            this.comp = comp;
            this.connection = connection;
        }

        @Override
        public void onSuccess() {
            connection.disconnect(comp);
            PacketSendListener.super.onSuccess();
        }

    }

    @FunctionalInterface
    interface EntityInteraction {
        InteractionResult run(ServerPlayer p_143695_, Entity p_143696_, InteractionHand p_143697_);
    }


    public record MinecartLoaderPacket(boolean is_unloader, boolean locked_minecarts_only, boolean leave_one_item_in_stack, AbstractCommonLoader.ComparatorOutputType output_type, boolean redstone_output, FilterUnloaderTile.FilterType filterType) implements FabricPacket {
        public static final PacketType<MinecartLoaderPacket> TYPE = PacketType.create(MMConstants.id("minecart_loader"), MinecartLoaderPacket::new);

        public MinecartLoaderPacket(FriendlyByteBuf buf){
            this(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readEnum(AbstractCommonLoader.ComparatorOutputType.class), buf.readBoolean(), buf.readEnum(FilterUnloaderTile.FilterType.class));
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(this.is_unloader);
            buf.writeBoolean(this.locked_minecarts_only);
            buf.writeBoolean(this.leave_one_item_in_stack);
            buf.writeEnum(this.output_type);
            buf.writeBoolean(this.redstone_output);
            buf.writeEnum(this.filterType);
        }

        public static void handle(MinecartLoaderPacket packet, ServerPlayer sender, PacketSender responseSender) {

            sender.getServer().execute(() -> {
                if (sender.containerMenu instanceof MinecartUnLoaderContainer container) {
                    container.setOptions(packet.locked_minecarts_only, packet.leave_one_item_in_stack, packet.output_type, packet.redstone_output, packet.filterType);
                }
                else if (sender.containerMenu instanceof FilterUnloaderContainer container) {
                    container.setOptions(packet.locked_minecarts_only, packet.leave_one_item_in_stack, packet.output_type, packet.redstone_output, packet.filterType);
                }
            });

        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }
    }

    public record FlagCartPacket(boolean is_decrement, boolean is_disclude) implements FabricPacket {
        public static final PacketType<FlagCartPacket> TYPE = PacketType.create(MMConstants.id("flag_cart"), FlagCartPacket::new);

        public FlagCartPacket(FriendlyByteBuf buf) {
            this(buf.readBoolean(), buf.readBoolean());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeBoolean(this.is_decrement);
            buf.writeBoolean(this.is_disclude);
        }

        public static void handle(FlagCartPacket packet, ServerPlayer sender, PacketSender responseSender) {

            sender.getServer().execute(() -> {
                if (sender.containerMenu instanceof FlagCartContainer) {
                    ((FlagCartContainer)sender.containerMenu).changeSelection(packet.is_decrement, packet.is_disclude);
                }
            });

        }

        @Override
        public PacketType<?> getType() {
            return TYPE;
        }
    }
}


