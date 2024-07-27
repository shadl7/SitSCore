package su.shadl7.sitscore;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.shadl7.sitscore.block.MagicCrystal;
import su.shadl7.sitscore.block.MagicCrystalUnbreakable;
import su.shadl7.sitscore.tileentity.TileMagicCube;
import su.shadl7.sitscore.tileentity.TilePartBuilderEx;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class EventRegistry {
    public static final Block magic_crystal = new MagicCrystal();
    public static final Block magic_crystal_ub = new MagicCrystalUnbreakable();
    public static final Item magic_crystal_item = new ItemBlock(magic_crystal).setRegistryName(magic_crystal.getRegistryName());
    public static final Item magic_crystal_item_ub = new ItemBlock(magic_crystal_ub).setRegistryName(magic_crystal_ub.getRegistryName());
    @SubscribeEvent
    public static void registrationBlock(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileMagicCube.class, new ResourceLocation("magic_crystal"));
        GameRegistry.registerTileEntity(TilePartBuilderEx.class, new ResourceLocation("tilepartbuilderex"));
        event.getRegistry().register(magic_crystal);
        event.getRegistry().register(magic_crystal_ub);
    }

    @SubscribeEvent
    public static void registrationItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(magic_crystal_item);
        event.getRegistry().register(magic_crystal_item_ub);
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegistryModel(ModelRegistryEvent event) {
        ModelResourceLocation mrl = new ModelResourceLocation(magic_crystal_item.getRegistryName(), "inventory");
        ModelBakery.registerItemVariants(magic_crystal_item, mrl);
        ModelLoader.setCustomModelResourceLocation(magic_crystal_item, 0, mrl);
        ModelResourceLocation mrl2 = new ModelResourceLocation(magic_crystal_item_ub.getRegistryName(), "inventory");
        ModelBakery.registerItemVariants(magic_crystal_item_ub, mrl2);
        ModelLoader.setCustomModelResourceLocation(magic_crystal_item_ub, 0, mrl2);
    }
}
