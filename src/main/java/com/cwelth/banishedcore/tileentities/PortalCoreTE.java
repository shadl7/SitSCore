package com.cwelth.banishedcore.tileentities;

import com.cwelth.banishedcore.blocks.BanishedCoreBlocks;
import com.cwelth.banishedcore.blocks.PortalBlock;
import com.cwelth.banishedcore.blocks.PortalBlock.PortalBlockStyle;
import com.cwelth.banishedcore.items.BanishedCoreItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class PortalCoreTE extends TileEntity {
    public ItemStackHandler itemStackHandler;
    public boolean isPortalActive = false;
    public boolean alwaysActive = false;
    public PortalBlock.PortalBlockStyle linkedPortalStyle;
    public BlockPos linkedPortalPosition;
    public boolean isPowered;

    public PortalCoreTE() {
        this.linkedPortalStyle = PortalBlockStyle.SELF;
        this.linkedPortalPosition = new BlockPos(0, 0, 0);
        this.isPowered = false;
        this.itemStackHandler = new ItemStackHandler(1) {
            protected void onContentsChanged(int slot) {
                PortalCoreTE.this.world.notifyBlockUpdate(PortalCoreTE.this.pos, PortalCoreTE.this.world.getBlockState(PortalCoreTE.this.pos), PortalCoreTE.this.world.getBlockState(PortalCoreTE.this.pos), 3);
                PortalCoreTE.this.markDirty();
            }
        };
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !this.isInvalid() && playerIn.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64.0;
    }

    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? true : super.hasCapability(capability, facing);
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) this.itemStackHandler : super.getCapability(capability, facing);
    }

    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(this.getPos(), 1, nbtTag);
    }

    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            this.itemStackHandler.deserializeNBT(compound.getCompoundTag("items"));
        }

        if (compound.hasKey("isPortalActive")) {
            this.isPortalActive = compound.getBoolean("isPortalActive");
        }

        if (compound.hasKey("alwaysActive")) {
            this.alwaysActive = compound.getBoolean("alwaysActive");
        }

        if (compound.hasKey("linkedPortalStyle")) {
            this.linkedPortalStyle = PortalBlockStyle.values()[compound.getInteger("linkedPortalStyle")];
        }

        if (compound.hasKey("linkedPortalPosition")) {
            this.linkedPortalPosition = NBTUtil.getPosFromTag(compound.getCompoundTag("linkedPortalPosition"));
        }

        if (compound.hasKey("isPowered")) {
            this.isPowered = compound.getBoolean("isPowered");
        }

    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("items", this.itemStackHandler.serializeNBT());
        compound.setBoolean("isPortalActive", this.isPortalActive);
        compound.setBoolean("alwaysActive", this.alwaysActive);
        compound.setInteger("linkedPortalStyle", this.linkedPortalStyle.ordinal());
        compound.setTag("linkedPortalPosition", NBTUtil.createPosTag(this.linkedPortalPosition));
        compound.setBoolean("isPowered", this.isPowered);
        return compound;
    }

    public boolean structureComplete() {
        World world = this.getWorld();
        BlockPos corePos = this.getPos();
        if (world.getBlockState(corePos.east(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.east(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(1).east(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(2).east(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(3).east(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(4).east(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.west(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.west(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(1).west(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(2).west(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(3).west(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(4).west(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.north(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.north(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(1).north(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(2).north(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(3).north(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(4).north(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.south(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.south(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(1).south(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(2).south(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(3).south(2)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else if (world.getBlockState(corePos.up(4).south(1)).getBlock() != BanishedCoreBlocks.portalBlock) {
            return false;
        } else {
            return world.getBlockState(corePos.up(4)).getBlock() == BanishedCoreBlocks.portalBlock;
        }
    }

    public void rebuildPortalStructure(PortalBlock.PortalBlockStyle style) {
        World world = this.getWorld();
        BlockPos corePos = this.getPos();
        if (world.getBlockState(corePos.east(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.east(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.east(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(1).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(1).east(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(2).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(2).east(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(3).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(3).east(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(4).east(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(4).east(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.west(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.west(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.west(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(1).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(1).west(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(2).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(2).west(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(3).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(3).west(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(4).west(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(4).west(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.north(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.north(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.north(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(1).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(1).north(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(2).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(2).north(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(3).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(3).north(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(4).north(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(4).north(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.south(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.south(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.south(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(1).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(1).south(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(2).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(2).south(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(3).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(3).south(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(4).south(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(4).south(1), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

        if (world.getBlockState(corePos.up(4)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockState(corePos.up(4), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, style));
        }

    }

    public void demolishPortalStructure() {
        World world = this.getWorld();
        BlockPos corePos = this.getPos();
        if (world.getBlockState(corePos.east(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.east(1));
        }

        if (world.getBlockState(corePos.east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.east(2));
        }

        if (world.getBlockState(corePos.up(1).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(1).east(2));
        }

        if (world.getBlockState(corePos.up(2).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(2).east(2));
        }

        if (world.getBlockState(corePos.up(3).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(3).east(2));
        }

        if (world.getBlockState(corePos.up(4).east(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(4).east(1));
        }

        if (world.getBlockState(corePos.west(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.west(1));
        }

        if (world.getBlockState(corePos.west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.west(2));
        }

        if (world.getBlockState(corePos.up(1).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(1).west(2));
        }

        if (world.getBlockState(corePos.up(2).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(2).west(2));
        }

        if (world.getBlockState(corePos.up(3).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(3).west(2));
        }

        if (world.getBlockState(corePos.up(4).west(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(4).west(1));
        }

        if (world.getBlockState(corePos.north(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.north(1));
        }

        if (world.getBlockState(corePos.north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.north(2));
        }

        if (world.getBlockState(corePos.up(1).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(1).north(2));
        }

        if (world.getBlockState(corePos.up(2).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(2).north(2));
        }

        if (world.getBlockState(corePos.up(3).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(3).north(2));
        }

        if (world.getBlockState(corePos.up(4).north(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(4).north(1));
        }

        if (world.getBlockState(corePos.south(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.south(1));
        }

        if (world.getBlockState(corePos.south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.south(2));
        }

        if (world.getBlockState(corePos.up(1).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(1).south(2));
        }

        if (world.getBlockState(corePos.up(2).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(2).south(2));
        }

        if (world.getBlockState(corePos.up(3).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(3).south(2));
        }

        if (world.getBlockState(corePos.up(4).south(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(4).south(1));
        }

        if (world.getBlockState(corePos.up(4)).getBlock() == BanishedCoreBlocks.portalBlock) {
            world.setBlockToAir(corePos.up(4));
        }

        if (world.getBlockState(corePos).getBlock() == BanishedCoreBlocks.portalCore) {
            world.setBlockToAir(corePos);
        }

    }

    public static BlockPos checkExistingPortal(World world, BlockPos initialPos) {
        BlockPos corePos = initialPos;
        BlockPos retCoords = null;
        if (world.getBlockState(corePos.east(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.east(1)).getBlock()).getPortalCore(corePos.east(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.east(2)).getBlock()).getPortalCore(corePos.east(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(1).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(1).east(2)).getBlock()).getPortalCore(corePos.up(1).east(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(2).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(2).east(2)).getBlock()).getPortalCore(corePos.up(2).east(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(3).east(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(3).east(2)).getBlock()).getPortalCore(corePos.up(3).east(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(4).east(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(4).east(1)).getBlock()).getPortalCore(corePos.up(4).east(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.west(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.west(1)).getBlock()).getPortalCore(corePos.west(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.west(2)).getBlock()).getPortalCore(corePos.west(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(1).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(1).west(2)).getBlock()).getPortalCore(corePos.up(1).west(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(2).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(2).west(2)).getBlock()).getPortalCore(corePos.up(2).west(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(3).west(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(3).west(2)).getBlock()).getPortalCore(corePos.up(3).west(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(4).west(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(4).west(1)).getBlock()).getPortalCore(corePos.up(4).west(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.north(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.north(1)).getBlock()).getPortalCore(corePos.north(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.north(2)).getBlock()).getPortalCore(corePos.north(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(1).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(1).north(2)).getBlock()).getPortalCore(corePos.up(1).north(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(2).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(2).north(2)).getBlock()).getPortalCore(corePos.up(2).north(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(3).north(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(3).north(2)).getBlock()).getPortalCore(corePos.up(3).north(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(4).north(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(4).north(1)).getBlock()).getPortalCore(corePos.up(4).north(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.south(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.south(1)).getBlock()).getPortalCore(corePos.south(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.south(2)).getBlock()).getPortalCore(corePos.south(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(1).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(1).south(2)).getBlock()).getPortalCore(corePos.up(1).south(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(2).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(2).south(2)).getBlock()).getPortalCore(corePos.up(2).south(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(3).south(2)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(3).south(2)).getBlock()).getPortalCore(corePos.up(3).south(2), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(4).south(1)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(4).south(1)).getBlock()).getPortalCore(corePos.up(4).south(1), world) : retCoords;
        }

        if (world.getBlockState(corePos.up(4)).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos.up(4)).getBlock()).getPortalCore(corePos.up(4), world) : retCoords;
        }

        if (world.getBlockState(corePos).getBlock() == BanishedCoreBlocks.portalBlock) {
            retCoords = retCoords == null ? ((PortalBlock)world.getBlockState(corePos).getBlock()).getPortalCore(corePos, world) : retCoords;
        }

        return retCoords;
    }

    public void activatePortal(PortalBlock.PortalBlockStyle style) {
        World world = this.getWorld();
        BlockPos corePos = this.getPos();
        if (this.structureComplete()) {
            this.rebuildPortalStructure(style);
            if (world.getBlockState(corePos.up(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(2)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(2), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(3)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(3), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(1).east(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(1).east(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(2).east(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(2).east(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(3).east(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(3).east(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(1).west(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(1).west(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(2).west(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(2).west(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(3).west(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(3).west(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(1).north(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(1).north(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(2).north(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(2).north(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(3).north(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(3).north(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(1).south(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(1).south(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(2).south(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(2).south(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            if (world.getBlockState(corePos.up(3).south(1)).getBlock() != BanishedCoreBlocks.portal) {
                world.setBlockState(corePos.up(3).south(1), BanishedCoreBlocks.portal.getDefaultState());
            }

            this.isPortalActive = true;
            this.markDirty();
        }
    }

    public void deactivatePortal(boolean demolishPortalFrame) {
        World world = this.getWorld();
        BlockPos corePos = this.getPos();
        this.rebuildPortalStructure(PortalBlockStyle.SELF);
        if (world.getBlockState(corePos.up(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(1));
        }

        if (world.getBlockState(corePos.up(2)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(2));
        }

        if (world.getBlockState(corePos.up(3)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(3));
        }

        if (world.getBlockState(corePos.up(1).east(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(1).east(1));
        }

        if (world.getBlockState(corePos.up(2).east(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(2).east(1));
        }

        if (world.getBlockState(corePos.up(3).east(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(3).east(1));
        }

        if (world.getBlockState(corePos.up(1).west(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(1).west(1));
        }

        if (world.getBlockState(corePos.up(2).west(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(2).west(1));
        }

        if (world.getBlockState(corePos.up(3).west(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(3).west(1));
        }

        if (world.getBlockState(corePos.up(1).north(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(1).north(1));
        }

        if (world.getBlockState(corePos.up(2).north(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(2).north(1));
        }

        if (world.getBlockState(corePos.up(3).north(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(3).north(1));
        }

        if (world.getBlockState(corePos.up(1).south(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(1).south(1));
        }

        if (world.getBlockState(corePos.up(2).south(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(2).south(1));
        }

        if (world.getBlockState(corePos.up(3).south(1)).getBlock() == BanishedCoreBlocks.portal) {
            world.setBlockToAir(corePos.up(3).south(1));
        }

        this.isPortalActive = false;
        if (demolishPortalFrame) {
            this.demolishPortalStructure();
        }

        this.linkedPortalPosition = new BlockPos(0, 0, 0);
        this.markDirty();
    }

    public PortalBlock.PortalBlockStyle getCoreStyle() {
        PortalBlock.PortalBlockStyle style = PortalBlockStyle.SELF;
        if (this.itemStackHandler.getStackInSlot(0).getItem() == BanishedCoreItems.activatorNether) {
            style = PortalBlockStyle.NETHER;
        }

        if (this.itemStackHandler.getStackInSlot(0).getItem() == BanishedCoreItems.activatorEnd) {
            style = PortalBlockStyle.THEEND;
        }

        if (this.itemStackHandler.getStackInSlot(0).getItem() == BanishedCoreItems.activatorAbyssal) {
            style = PortalBlockStyle.ABYSSAL;
        }

        if (this.itemStackHandler.getStackInSlot(0).getItem() == BanishedCoreItems.activatorBetweenlands) {
            style = PortalBlockStyle.BETWEENLANDS;
        }

        return style;
    }

    public static int getDimensionIDFromStyle(PortalBlock.PortalBlockStyle style) {
        if (style != PortalBlockStyle.SELF && style != PortalBlockStyle.ATUM) {
            if (style == PortalBlockStyle.NETHER) {
                return -1;
            } else if (style == PortalBlockStyle.THEEND) {
                return 1;
            } else if (style == PortalBlockStyle.ABYSSAL) {
                return 50;
            } else {
                return style == PortalBlockStyle.BETWEENLANDS ? 20 : 17;
            }
        } else {
            return 17;
        }
    }

    public static PortalBlock.PortalBlockStyle getStyleFormDimensionID(int dimension) {
        if (dimension == 17) {
            return PortalBlockStyle.ATUM;
        } else if (dimension == -1) {
            return PortalBlockStyle.NETHER;
        } else if (dimension == 1) {
            return PortalBlockStyle.THEEND;
        } else if (dimension == 50) {
            return PortalBlockStyle.ABYSSAL;
        } else {
            return dimension == 20 ? PortalBlockStyle.BETWEENLANDS : PortalBlockStyle.SELF;
        }
    }

    public void buildLinkedPortal(int dimension, BlockPos position) {
        MinecraftServer mc = this.getWorld().getMinecraftServer();
        World targetWorld = mc.getWorld(dimension);
        PortalCoreTE targetCore;
        if (targetWorld.getBlockState(position).getBlock() == BanishedCoreBlocks.portalCore) {
            targetCore = (PortalCoreTE)targetWorld.getTileEntity(position);
            if (targetCore.isPortalActive) {
                this.linkedPortalStyle = this.getCoreStyle();
                this.linkedPortalPosition = position;
                this.markDirty();
                return;
            }
        }

        int px;
        int py;
        for(px = 0; px < 5; ++px) {
            for(px = 0; px < 5; ++px) {
                for(py = 0; py < 5; ++py) {
                    targetWorld.setBlockToAir(position.up(py).east(2).south(2).west(px).north(px));
                }
            }
        }

        targetWorld.setBlockState(position, BanishedCoreBlocks.portalCore.getDefaultState());
        targetCore = (PortalCoreTE)targetWorld.getTileEntity(position);
        targetCore.linkedPortalPosition = this.getPos();
        targetCore.linkedPortalStyle = getStyleFormDimensionID(this.getWorld().provider.getDimension());
        targetCore.alwaysActive = true;

        for(px = 1; px <= 3; ++px) {
            if (px < 3) {
                targetWorld.setBlockState(position.east(px), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
                targetWorld.setBlockState(position.west(px), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
                targetWorld.setBlockState(position.south(px), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
                targetWorld.setBlockState(position.north(px), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
            }

            targetWorld.setBlockState(position.up(px).east(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
            targetWorld.setBlockState(position.up(px).west(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
            targetWorld.setBlockState(position.up(px).south(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
            targetWorld.setBlockState(position.up(px).north(2), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
        }

        targetWorld.setBlockState(position.up(4).east(), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
        targetWorld.setBlockState(position.up(4).west(), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
        targetWorld.setBlockState(position.up(4).south(), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
        targetWorld.setBlockState(position.up(4).north(), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));
        targetWorld.setBlockState(position.up(4), BanishedCoreBlocks.portalBlock.getDefaultState().withProperty(PortalBlock.STYLE, targetCore.linkedPortalStyle));

        for(px = 0; px < 5; ++px) {
            for(py = 0; py < 5; ++py) {
                targetWorld.setBlockState(position.down().east(2).south(2).west(px).north(py), Blocks.OBSIDIAN.getDefaultState());
            }
        }

        targetCore.activatePortal(targetCore.linkedPortalStyle);
        targetCore.markDirty();
        this.linkedPortalStyle = this.getCoreStyle();
        this.linkedPortalPosition = position;
        this.markDirty();
    }

    public BlockPos getTargetPortalPosition(PortalBlock.PortalBlockStyle style) {
        MinecraftServer mc = this.getWorld().getMinecraftServer();
        World world = mc.getWorld(getDimensionIDFromStyle(style));
        BlockPos possiblePortalCoords;
        if (style == PortalBlockStyle.NETHER) {
            double yCoord = (double)this.getPos().getY();
            if (yCoord > 70.0) {
                yCoord = 70.0;
            }

            possiblePortalCoords = new BlockPos(Math.floor((double)(this.getPos().getX() / 8)), yCoord, Math.floor((double)(this.getPos().getZ() / 8)));
        } else if (style == PortalBlockStyle.THEEND) {
            possiblePortalCoords = new BlockPos(100, 48, 0);
        } else {
            possiblePortalCoords = new BlockPos(Math.floor((double)this.getPos().getX()), Math.floor((double)this.getPos().getY()), Math.floor((double)this.getPos().getZ()));
            int lastY = possiblePortalCoords.getY();
            possiblePortalCoords = this.getWorld().getMinecraftServer().getWorld(getDimensionIDFromStyle(style)).getTopSolidOrLiquidBlock(possiblePortalCoords);
            if (possiblePortalCoords.getY() == -1) {
                possiblePortalCoords = new BlockPos(possiblePortalCoords.getX(), lastY, possiblePortalCoords.getZ());
            }
        }

        BlockPos existingPortalCoords = checkExistingPortal(world, possiblePortalCoords);
        if (existingPortalCoords != null) {
            PortalCoreTE targetPortal = (PortalCoreTE)world.getTileEntity(existingPortalCoords);
            if (targetPortal != null) {
                targetPortal.deactivatePortal(true);
            }

            return existingPortalCoords;
        } else {
            return possiblePortalCoords;
        }
    }

    public void redstoneSwitch(boolean isActive) {
        if (this.structureComplete()) {
            if (isActive) {
                if (!this.isPowered) {
                    this.isPowered = true;
                    if (!this.isPortalActive) {
                        PortalBlock.PortalBlockStyle style = this.getCoreStyle();
                        if (style != PortalBlockStyle.SELF) {
                            this.activatePortal(style);
                            if (this.linkedPortalPosition.getX() == 0 && this.linkedPortalPosition.getY() == 0 && this.linkedPortalPosition.getZ() == 0) {
                                this.buildLinkedPortal(getDimensionIDFromStyle(style), this.getTargetPortalPosition(style));
                            } else {
                                this.buildLinkedPortal(getDimensionIDFromStyle(style), this.linkedPortalPosition);
                            }

                        }
                    }
                }
            } else if (this.isPowered) {
                this.isPowered = false;
                if (this.isPortalActive) {
                    if (!this.alwaysActive) {
                        if (this.linkedPortalPosition.getX() != 0 || this.linkedPortalPosition.getY() != 0 || this.linkedPortalPosition.getZ() != 0) {
                            World targetWorld = this.getWorld().getMinecraftServer().getWorld(getDimensionIDFromStyle(this.linkedPortalStyle));
                            PortalCoreTE targetPortalTE = (PortalCoreTE)targetWorld.getTileEntity(this.linkedPortalPosition);
                            if (targetPortalTE != null) {
                                targetPortalTE.deactivatePortal(true);
                            }
                        }

                        this.deactivatePortal(false);
                    }

                }
            }
        }
    }
}
