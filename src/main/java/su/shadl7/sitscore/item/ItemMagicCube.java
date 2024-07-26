package su.shadl7.sitscore.item;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import su.shadl7.sitscore.Tags;
import su.shadl7.sitscore.block.MagicCrystal;

public class ItemMagicCube extends Item {
    public ItemMagicCube() {
        this.setRegistryName(Tags.MOD_ID, "magic_cube");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        worldIn.setBlockState(pos.add(facing.getDirectionVec()), new BlockStateContainer(new MagicCrystal()).getBaseState());
        return EnumActionResult.SUCCESS;
    }
}
