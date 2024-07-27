package su.shadl7.sitscore;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import su.shadl7.sitscore.worldgen.WorldGenStartStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class SitSCoreMod {

    public static Logger LOGGER = Logger.getLogger(Tags.MOD_ID);

    public static List<ItemStack> patterns;

    @Mod.EventHandler
    public void registrator(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenStartStruct(), 10);
    }

    @Mod.EventHandler
    public void postInit(FMLPreInitializationEvent event) {
        generatePatternCache();
    }

    private void generatePatternCache() {
        patterns = new ArrayList<>();
        var toolParts = TinkerRegistry.getPatternItems();
        var patternBaseItem = Item.REGISTRY.getObject(new ResourceLocation("tconstruct", "pattern"));
        for (var toolPart : toolParts) {
            var patternVariant = new ItemStack(patternBaseItem);
            patternVariant.setTagInfo("PartType", new NBTTagString(toolPart.getRegistryName().toString()));
            patterns.add(patternVariant);
        }
        patterns = ImmutableList.copyOf(patterns);
    }
}
