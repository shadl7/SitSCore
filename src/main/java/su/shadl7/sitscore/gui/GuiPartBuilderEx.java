package su.shadl7.sitscore.gui;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Mouse;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.Icons;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.IMaterialItem;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.tools.common.client.GuiTinkerStation;
import slimeknights.tconstruct.tools.common.client.module.GuiInfoPanel;
import slimeknights.tconstruct.tools.common.inventory.ContainerTinkerStation;
import su.shadl7.sitscore.Tags;
import su.shadl7.sitscore.container.ContainerPartBuilderEx;
import su.shadl7.sitscore.tileentity.TilePartBuilderEx;

@SideOnly(Side.CLIENT)
public class GuiPartBuilderEx extends GuiTinkerStation implements ITooltipPainter {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(Tags.MOD_ID,
            "textures/gui/part_table.png");

    public static final int SELECTOR_ROWS = 3,
                            SELECTOR_COLS = 5;
    protected GuiInfoPanel info;

    protected GuiPatternSelector selector;

    public GuiPartBuilderEx(InventoryPlayer playerInv, World world, BlockPos pos, TilePartBuilderEx tile) {
        super(world, pos, (ContainerTinkerStation) tile.createContainer(playerInv, world, pos));

        if(inventorySlots instanceof ContainerPartBuilderEx) {
            ContainerPartBuilderEx container = (ContainerPartBuilderEx) inventorySlots;
            info = new GuiInfoPanel(this, container);
            info.ySize = this.ySize;
            this.addModule(info);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        selector = new GuiPatternSelector(Minecraft.getMinecraft(),
                90, 19 + this.cornerY, SELECTOR_ROWS, 44 + cornerX, 16);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawBackground(BACKGROUND);

        // Draw inputs slot icons
        drawIconEmpty(container.getSlot(1), Icons.ICON_Ingot);
        drawIconEmpty(container.getSlot(2), Icons.ICON_Block);

        selector.drawScreen(mouseX, mouseY, partialTicks);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    @Override
    public void updateDisplay() {
        // check if we have an output
        ItemStack output = container.getSlot(0).getStack();
        if(!output.isEmpty()) {
            if(output.getItem() instanceof ToolPart) {
                ToolPart toolPart = (ToolPart) output.getItem();
                Material material = toolPart.getMaterial(output);
                // Material for the toolpart does not make sense, can't build anything out of it!
                if(!toolPart.canUseMaterial(material)) {
                    String materialName = material.getLocalizedNameColored() + TextFormatting.WHITE;
                    String error = I18n
                            .translateToLocalFormatted("gui.error.useless_tool_part", materialName, (new ItemStack(toolPart)).getDisplayName());
                    warning(error);
                }
                // Material is OK, display material properties
                else {
                    setDisplayForMaterial(material);
                }
            }
        }
        // no output, check input
        else {
            // is our input a material item?
            Material material = getMaterial(container.getSlot(1).getStack(), container.getSlot(2).getStack());
            if(material != null) {
                setDisplayForMaterial(material);
            }
            // no, display general usage information
            else {
                info.setCaption(container.getInventoryDisplayName());
                info.setText(I18n.translateToLocal("gui.partbuilder.info"));
            }
        }
    }

    @Override
    public void error(String message) {
        info.setCaption(I18n.translateToLocal("gui.error"));
        info.setText(message);
    }

    @Override
    public void warning(String message) {
        info.setCaption(I18n.translateToLocal("gui.warning"));
        info.setText(message);
    }

    protected void setDisplayForMaterial(Material material) {
        info.setCaption(material.getLocalizedNameColored());

        List<String> stats = Lists.newLinkedList();
        List<String> tips = Lists.newArrayList();
        for(IMaterialStats stat : material.getAllStats()) {
            List<String> info = stat.getLocalizedInfo();
            if(!info.isEmpty()) {
                stats.add(TextFormatting.UNDERLINE + stat.getLocalizedName());
                stats.addAll(info);
                stats.add(null);
                tips.add(null);
                tips.addAll(stat.getLocalizedDesc());
                tips.add(null);
            }
        }

        // Traits
        for(ITrait trait : material.getAllTraits()) {
            if(!trait.isHidden()) {
                stats.add(material.getTextColor() + trait.getLocalizedName());
                tips.add(material.getTextColor() + trait.getLocalizedDesc());
            }
        }

        if(!stats.isEmpty() && stats.get(stats.size() - 1) == null) {
            // last empty line
            stats.remove(stats.size() - 1);
            tips.remove(tips.size() - 1);
        }

        info.setText(stats, tips);
    }

    protected Material getMaterial(ItemStack... stacks) {
        for(ItemStack stack : stacks) {
            if(stack.isEmpty()) {
                continue;
            }
            // material-item?
            if(stack.getItem() instanceof IMaterialItem) {
                return ((IMaterialItem) stack.getItem()).getMaterial(stack);
            }
        }

        // regular item, check if it belongs to a material
        for(Material material : TinkerRegistry.getAllMaterials()) {
            if(material.matches(stacks).isPresent()) {
                return material;
            }
        }

        // no material found
        return null;
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        selector.drawHoveredTooltip(mouseX, mouseY, this);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        selector.onMouseClick(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        selector.actionPerformed(button);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        // Transform LWJGL mouse coordinates to Minecraft format (I don't know how it works but i found it in Minecraft code)
        selector.handleMouseInput(Mouse.getEventX() * this.width / this.mc.displayWidth,
                this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1);
    }

    @Override
    public void drawTooltip(int x, int y, int x2, int y2, int mouseX, int mouseY, ItemStack pattern) {
        this.renderToolTip(pattern, mouseX, mouseY);
        GlStateManager.colorMask(true, true, true, false);
        this.drawGradientRect(x, y, x2, y2, -2130706433, -2130706433); // Draw gray selecting rectangle on mouse hover
        GlStateManager.colorMask(true, true, true, true);
    }
}
