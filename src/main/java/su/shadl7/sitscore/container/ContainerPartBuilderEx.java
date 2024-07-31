package su.shadl7.sitscore.container;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;

import slimeknights.mantle.inventory.IContainerCraftingCustom;
import slimeknights.mantle.inventory.SlotCraftingCustom;
import slimeknights.mantle.inventory.SlotOut;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.ListUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.shared.inventory.InventoryCraftingPersistent;
import slimeknights.tconstruct.tools.common.block.BlockToolTable;
import slimeknights.tconstruct.tools.common.inventory.ContainerPatternChest;
import slimeknights.tconstruct.tools.common.inventory.ContainerTinkerStation;
import slimeknights.tconstruct.tools.common.inventory.SlotStencil;
import slimeknights.tconstruct.tools.common.tileentity.TilePatternChest;
import su.shadl7.sitscore.PacketHandler;
import su.shadl7.sitscore.gui.GuiPartBuilderEx;
import su.shadl7.sitscore.network.PacketButtonSync;
import su.shadl7.sitscore.tileentity.TilePartBuilderEx;

public class ContainerPartBuilderEx extends ContainerTinkerStation<TilePartBuilderEx> implements IContainerCraftingCustom {

    public IInventory craftResult;

    private final Slot input1;
    private final Slot input2;

    private final boolean partCrafter;
    private final EntityPlayer player;

    public ContainerPartBuilderEx(InventoryPlayer playerInventory, TilePartBuilderEx tile) {
        super(tile);

        InventoryCraftingPersistent craftMatrix = new InventoryCraftingPersistent(this, tile, 1, 3);
        this.craftResult = new InventoryCraftResult();
        this.player = playerInventory.player;

        // output slots
        this.addSlotToContainer(new SlotCraftingCustom(this, playerInventory.player, craftMatrix, craftResult, 0, 153, 35));

        // material slots
        this.addSlotToContainer(input1 = new Slot(craftMatrix, 0, 8, 24));
        this.addSlotToContainer(input2 = new Slot(craftMatrix, 1, 8, 46));

        // crafting station also present?
        boolean hasCraftingStation = false;
        for(Pair<BlockPos, IBlockState> pair : tinkerStationBlocks) {
            if(!pair.getRight().getProperties().containsKey(BlockToolTable.TABLES)) {
                continue;
            }

            BlockToolTable.TableTypes type = pair.getRight().getValue(BlockToolTable.TABLES);
            if(type != null) {
                if(type == BlockToolTable.TableTypes.CraftingStation) {
                    hasCraftingStation = true;
                }
            }
        }

        // are we a PartCrafter?
        partCrafter = hasCraftingStation;

        this.addPlayerInventory(playerInventory, 8, 84);

        onCraftMatrixChanged(playerInventory);
    }

    public boolean isPartCrafter() {
        return partCrafter;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        updateResult();
    }

    // Sets the result in the output slot depending on the input!
    public void updateResult() {
        // no pattern -> no output
        if(getTile().getSelectedPattern() == -1 || (!input1.getHasStack() && !input2.getHasStack())) {
            craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
            updateGUI();
        }
        else {
            Throwable throwable = null;
            NonNullList<ItemStack> toolPart;
            try {
                var pattern = TinkerRegistry.getStencilTableCrafting().get(getTile().getSelectedPattern());
                toolPart = ToolBuilder.tryBuildToolPart(pattern, ListUtil.getListFrom(input1.getStack(), input2.getStack()), false);
                if(toolPart != null && !toolPart.get(0).isEmpty()) {
                    TinkerCraftingEvent.ToolPartCraftingEvent.fireEvent(toolPart.get(0), player);
                }
            } catch(TinkerGuiException e) {
                toolPart = null;
                throwable = e;
            }


            // got output?
            if(toolPart != null) {
                craftResult.setInventorySlotContents(0, toolPart.get(0));
            }
            else {
                craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
            }

            if(throwable != null) {
                error(throwable.getMessage());
            }
            else {
                updateGUI();
            }
        }
    }

    @Override
    public void onCrafting(EntityPlayer player, ItemStack output, IInventory craftMatrix) {
        NonNullList<ItemStack> toolPart = NonNullList.create();
        try {
            toolPart = ToolBuilder.tryBuildToolPart(TinkerRegistry.getStencilTableCrafting().get(getTile().getSelectedPattern()),
                    ListUtil.getListFrom(input1.getStack(), input2.getStack()), true);
        } catch(TinkerGuiException e) {
            // don't need any user information at this stage
        }
        if (toolPart == null) {
            // undefined :I
            return;
        }

        updateResult();
    }

    @Override
    public boolean canMergeSlot(ItemStack itemstack, Slot slot) {
        // prevents that doubleclicking on an item pulls the same out of the crafting slot
        return slot.inventory != this.craftResult && super.canMergeSlot(itemstack, slot);
    }

    @Override
    public String getInventoryDisplayName() {
        if (partCrafter)
            return Util.translate("gui.partcrafter.name");

        return super.getInventoryDisplayName();
    }

    // Server side -> client side pattern select sync
    public void syncPattern(int selectedPattern, World world, BlockPos tile) {
        for (var player : world.playerEntities) {
            var playerMP = (EntityPlayerMP) player;
            if (player.openContainer instanceof ContainerPartBuilderEx container)
                if (container.getTile().getPos() == tile)
                    PacketHandler.INSTANCE.sendTo(new PacketButtonSync(selectedPattern), playerMP);
        }
    }
}
