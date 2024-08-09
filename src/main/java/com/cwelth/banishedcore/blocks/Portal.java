package com.cwelth.banishedcore.blocks;

import com.cwelth.banishedcore.blocks.PortalBlock.PortalBlockStyle;
import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import com.cwelth.banishedcore.util.TeleportHelper;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Portal extends Block {
    public Portal() {
        super(Material.PORTAL);
        this.setTranslationKey("banishedcore.portal");
        this.setRegistryName("portal");
        this.setBlockUnbreakable();
        this.setResistance(6000000.0F);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation("sitscore", "portal"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(this.getRegistryName(), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return Block.NULL_AABB;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }

    public int getLightValue(IBlockState state) {
        return 6;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return blockState != blockAccess.getBlockState(pos.offset(side));
    }

    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote && entityIn instanceof EntityPlayerMP) {
            BlockPos core = this.findPortalCore(worldIn, pos);
            if (core == null) {
                return;
            }

            PortalCoreTE te = (PortalCoreTE)worldIn.getTileEntity(core);
            MinecraftServer mc = entityIn.getEntityWorld().getMinecraftServer();
            BlockPos spawn = te.linkedPortalPosition.east(2).north(2).up();
            int dimId = PortalCoreTE.getDimensionIDFromStyle(te.linkedPortalStyle);
            entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0;
            TeleportHelper tele = new TeleportHelper(((EntityPlayerMP)entityIn).getServerWorld(), (double)spawn.getX() + 0.5, (double)spawn.getY(), (double)spawn.getZ() + 0.5, dimId);
            entityIn.changeDimension(dimId, tele);
            mc.getPlayerList().transferPlayerToDimension((EntityPlayerMP)entityIn, dimId, tele);
        }

    }

    public BlockPos findPortalCore(IBlockAccess worldIn, BlockPos pos) {
        PortalBlock.PortalBlockStyle style = PortalBlockStyle.SELF;
        BlockPos curPos = pos;

        Block biq;
        for(biq = worldIn.getBlockState(curPos.down()).getBlock(); biq == this; biq = worldIn.getBlockState(curPos.down()).getBlock()) {
            curPos = curPos.down();
        }

        if (worldIn.getBlockState(curPos.down()).getBlock() != BanishedCoreBlocks.portalCore) {
            biq = worldIn.getBlockState(curPos.east()).getBlock();
            if (biq == BanishedCoreBlocks.portal) {
                curPos = curPos.east();
            } else {
                biq = worldIn.getBlockState(curPos.west()).getBlock();
                if (biq == BanishedCoreBlocks.portal) {
                    curPos = curPos.west();
                } else {
                    biq = worldIn.getBlockState(curPos.south()).getBlock();
                    if (biq == BanishedCoreBlocks.portal) {
                        curPos = curPos.south();
                    } else {
                        biq = worldIn.getBlockState(curPos.north()).getBlock();
                        if (biq == BanishedCoreBlocks.portal) {
                            curPos = curPos.north();
                        }
                    }
                }
            }
        }

        PortalCoreTE te = (PortalCoreTE)worldIn.getTileEntity(curPos.down());
        return te != null ? curPos.down() : null;
    }

    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }
}
