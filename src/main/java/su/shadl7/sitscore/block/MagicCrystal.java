package su.shadl7.sitscore.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import su.shadl7.sitscore.EventRegistry;
import su.shadl7.sitscore.Tags;
import su.shadl7.sitscore.tileentity.TileMagicCube;

import java.util.Random;

public class MagicCrystal extends BlockContainer {
    public MagicCrystal() {
        super(Material.IRON);
        this.setRegistryName(new ResourceLocation(Tags.MOD_ID,"magic_crystal"));
        this.setTranslationKey("magic_crystal");
        this.setHardness(1.0F);
        this.setHarvestLevel("pickaxe", 3);
    }

    public int getLightValue(IBlockState state) {
        return 7;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMagicCube();
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return EventRegistry.magic_crystal_item;
    }
}
