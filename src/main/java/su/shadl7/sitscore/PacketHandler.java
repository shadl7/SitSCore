package su.shadl7.sitscore;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import su.shadl7.sitscore.network.PacketButtonSync;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);
    private static int id = 0;

    public static void registerPackets() {
        INSTANCE.registerMessage(PacketButtonSync.Handler.class, PacketButtonSync.class, id++, Side.SERVER);
    }
}
