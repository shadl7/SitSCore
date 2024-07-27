package su.shadl7.sitscore.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import toughasnails.api.TANCapabilities;
import toughasnails.api.stat.capability.ITemperature;
import toughasnails.api.stat.capability.IThirst;
import toughasnails.api.temperature.Temperature;

public class TileMagicCube extends TileEntity implements ITickable {
    public static final int MAX_SPREAD_DISTANCE = 50;
    private int updateTicks;
    private AxisAlignedBB maxSpreadBox;

    private void generateSpreadBox() {
        this.maxSpreadBox = new AxisAlignedBB(this.pos.getX(), this.pos.getY(), this.pos.getZ(),
                this.pos.getX(), this.pos.getY(), this.pos.getZ()).grow(MAX_SPREAD_DISTANCE);
    }

    public void update() {
        World world = this.getWorld();
        if (++this.updateTicks % 20 == 0) {

            if (this.maxSpreadBox == null) {
                generateSpreadBox();
            }

            for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, this.maxSpreadBox)) {
                ITemperature temperature = player.getCapability(TANCapabilities.TEMPERATURE, null);
                IThirst thirst = player.getCapability(TANCapabilities.THIRST, null);
                temperature.setTemperature(new Temperature(10));
                thirst.setThirst(20);
            }
        }
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
