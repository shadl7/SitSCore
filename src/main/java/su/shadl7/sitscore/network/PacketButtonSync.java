package su.shadl7.sitscore.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.shadl7.sitscore.SitSCoreMod;
import su.shadl7.sitscore.container.ContainerPartBuilderEx;

public class PacketButtonSync implements IMessage {
    public int selectedPattern;

    public PacketButtonSync() {
    }

    public PacketButtonSync(int selectedPattern) {
        this.selectedPattern = selectedPattern;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        selectedPattern = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(selectedPattern);
    }

    public static class HandlerServer implements IMessageHandler<PacketButtonSync, IMessage> {
        @Override
        public IMessage onMessage(PacketButtonSync message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            int patternIndex = message.selectedPattern;
            // Select pattern
            player.getServerWorld().addScheduledTask(() -> {
                if (player.openContainer instanceof ContainerPartBuilderEx container) {
                    container.getTile().setSelectedPattern(patternIndex);
                    container.syncPattern(patternIndex, player.world, container.getTile().getPos());
                }
            });
            return null;
        }
    }

    public static class HandlerClient implements IMessageHandler<PacketButtonSync, IMessage> {
        @Override
        public IMessage onMessage(PacketButtonSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().player.openContainer instanceof ContainerPartBuilderEx container)
                    container.getTile().setSelectedPattern(message.selectedPattern);
            });
            return null;
        }
    }
}
