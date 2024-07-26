package su.shadl7.sitscore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import su.shadl7.sitscore.worldgen.WorldGenStartStruct;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class SitSCoreMod {

    @Mod.EventHandler
    public void registrator(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenStartStruct(), 10);
    }
}
