package su.shadl7.sitscore;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.shadl7.sitscore.worldgen.WorldGenStartStruct;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "required-after:mixinbooter")
public class SitSCoreMod {
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerPackets();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (Loader.isModLoaded("atum") && Loader.isModLoaded("toughasnails"))
            GameRegistry.registerWorldGenerator(new WorldGenStartStruct(), 10);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("tconstruct")) {
            var recipesRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
            recipesRegistry.remove(new ResourceLocation("tconstruct", "tools/table/stencil_table"));
            recipesRegistry.remove(new ResourceLocation("tconstruct", "tools/table/chest/pattern_simple"));
            recipesRegistry.remove(new ResourceLocation("tconstruct", "tools/table/chest/pattern"));
        }
    }
}
