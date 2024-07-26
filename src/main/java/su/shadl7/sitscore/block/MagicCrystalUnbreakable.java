package su.shadl7.sitscore.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import su.shadl7.sitscore.Tags;
import su.shadl7.sitscore.tileentity.TileMagicCube;

public class MagicCrystalUnbreakable extends BlockContainer {
    public MagicCrystalUnbreakable() {
        super(Material.IRON);
        this.setRegistryName(new ResourceLocation(Tags.MOD_ID,"magic_crystal_unbreakable"));
        this.setTranslationKey("magic_crystal_unbreakable");
        this.setBlockUnbreakable();
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
}
