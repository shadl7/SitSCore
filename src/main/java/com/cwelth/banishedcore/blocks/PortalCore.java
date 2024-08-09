package com.cwelth.banishedcore.blocks;

import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.shadl7.sitscore.SitSCoreMod;

public class PortalCore extends Block {
    public PortalCore() {
        super(Material.IRON);
        this.setTranslationKey("banishedcore.portalcore");
        this.setRegistryName("portalcore");
        this.setHardness(2.0F);
        this.setResistance(6000000.0F);
        this.setHarvestLevel("pickaxe", 1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation("sitscore", "portalcore"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(this.getRegistryName(), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
    }

    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new PortalCoreTE();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && !playerIn.isSneaking()) {
            PortalCoreTE te = (PortalCoreTE)worldIn.getTileEntity(pos);
            if (te != null) {
                playerIn.openGui(SitSCoreMod.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
        }

        return true;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
            PortalCoreTE te = (PortalCoreTE)worldIn.getTileEntity(pos);
            if (worldIn.isBlockPowered(pos)) {
                te.redstoneSwitch(true);
            } else {
                te.redstoneSwitch(false);
            }
        }

    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            PortalCoreTE te = (PortalCoreTE)worldIn.getTileEntity(pos);
            if (te != null) {
                te.deactivatePortal(false);
            }

            InventoryHelper.spawnItemStack(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), te.itemStackHandler.getStackInSlot(0));
        }

        super.breakBlock(worldIn, pos, state);
    }

    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }
}
