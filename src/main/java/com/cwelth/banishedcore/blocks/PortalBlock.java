package com.cwelth.banishedcore.blocks;

import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PortalBlock extends Block {
    public static final PropertyEnum<PortalBlockStyle> STYLE = PropertyEnum.create("style", PortalBlockStyle.class);

    public PortalBlock() {
        super(Material.IRON);
        this.setTranslationKey("banishedcore.portalblock");
        this.setRegistryName("portalblock");
        this.setHardness(2.0F);
        this.setResistance(6000000.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setDefaultState(this.getDefaultState().withProperty(STYLE, PortalBlock.PortalBlockStyle.SELF));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation("sitscore", "portalblock"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(this.getRegistryName(), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{STYLE});
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STYLE, PortalBlock.PortalBlockStyle.values()[meta]);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(STYLE).ordinal();
    }

    public BlockPos getPortalCore(BlockPos pos, World worldIn) {
        if (worldIn.getBlockState(pos.up()).getBlock() == this) {
            Block portal;
            if (worldIn.getBlockState(pos.east()).getBlock() == this) {
                portal = worldIn.getBlockState(pos.up().east()).getBlock();
                if (portal == BanishedCoreBlocks.portal) {
                    return pos.up().east();
                }
            }

            if (worldIn.getBlockState(pos.west()).getBlock() == this) {
                portal = worldIn.getBlockState(pos.up().west()).getBlock();
                if (portal == BanishedCoreBlocks.portal) {
                    return pos.up().west();
                }
            }

            if (worldIn.getBlockState(pos.south()).getBlock() == this) {
                portal = worldIn.getBlockState(pos.up().south()).getBlock();
                if (portal == BanishedCoreBlocks.portal) {
                    return pos.up().south();
                }
            }

            if (worldIn.getBlockState(pos.north()).getBlock() == this) {
                portal = worldIn.getBlockState(pos.up().north()).getBlock();
                if (portal == BanishedCoreBlocks.portal) {
                    return pos.up().north();
                }
            }
        }

        if (worldIn.getBlockState(pos.up()).getBlock() == BanishedCoreBlocks.portal) {
            return pos.up();
        } else if (worldIn.getBlockState(pos.down()).getBlock() == BanishedCoreBlocks.portal) {
            return pos.down();
        } else if (worldIn.getBlockState(pos.east()).getBlock() == BanishedCoreBlocks.portal) {
            return pos.east();
        } else if (worldIn.getBlockState(pos.west()).getBlock() == BanishedCoreBlocks.portal) {
            return pos.west();
        } else if (worldIn.getBlockState(pos.south()).getBlock() == BanishedCoreBlocks.portal) {
            return pos.south();
        } else {
            return worldIn.getBlockState(pos.north()).getBlock() == BanishedCoreBlocks.portal ? pos.north() : null;
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos portalCorePos = this.getPortalCore(pos, worldIn);
        if (portalCorePos != null) {
            Block portal = worldIn.getBlockState(portalCorePos).getBlock();
            PortalCoreTE portalCoreTE = (PortalCoreTE)worldIn.getTileEntity(((Portal)portal).findPortalCore(worldIn, portalCorePos));
            if (portalCoreTE != null) {
                portalCoreTE.deactivatePortal(false);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }

    public static enum PortalBlockStyle implements IStringSerializable {
        SELF,
        NETHER,
        THEEND,
        ABYSSAL,
        BETWEENLANDS,
        OVERWORLD,
        ATUM;

        private PortalBlockStyle() {
        }

        public String toString() {
            return this.getName();
        }

        public String getName() {
            if (this == SELF) {
                return "self";
            } else if (this == NETHER) {
                return "nether";
            } else if (this == THEEND) {
                return "theend";
            } else if (this == ABYSSAL) {
                return "abyssal";
            } else if (this == BETWEENLANDS) {
                return "betweenlands";
            } else if (this == OVERWORLD) {
                return "overworld";
            } else {
                return this == ATUM ? "atum" : "";
            }
        }
    }
}
