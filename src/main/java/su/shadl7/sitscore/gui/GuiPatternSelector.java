package su.shadl7.sitscore.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.client.config.GuiUtils;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.util.ArrayList;
import java.util.List;

import static su.shadl7.sitscore.gui.GuiPartBuilderEx.SELECTOR_COLS;

public class GuiPatternSelector extends GuiScrollingList {
    private final List<List<GuiButtonPattern>> buttons;

    public GuiPatternSelector(Minecraft client, int width,
                              int top, int rows, int left, int entryHeight) {
        super(client, width, 0, top, top + entryHeight * rows, left, entryHeight, 0, 0);
        this.setHeaderInfo(false, 0);
        buttons = new ArrayList<>();
        var patterns = TinkerRegistry.getStencilTableCrafting();
        // Selector rows (subtract if last row is regular because division round down)
        var rowsAll = patterns.size() / SELECTOR_COLS -
                (patterns.size() % SELECTOR_COLS == 0 ? 1 : 0) + 1;
        for (int i = 0; i < rowsAll; i++) {
            buttons.add(new ArrayList<>());
            for (int j = 0; j < patterns.size() % SELECTOR_COLS + 2 // Maximum on row - SELECTOR_COLS
                    && patterns.size() > SELECTOR_COLS * i + j; j++) { // Check if last row has fewer patterns
                buttons.get(i).add(new GuiButtonPattern(SELECTOR_COLS * i + j));
            }
        }
    }

    @Override
    protected int getSize() {
        return buttons.size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {

    }

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        var buttonsInRow = buttons.get(slotIdx);
        for (int i = 0; i < buttonsInRow.size(); i++) {
            buttonsInRow.get(i).draw(Minecraft.getMinecraft(), this.left + 16 * i, slotTop);
        }
    }

    @Override
    protected void drawGradientRect(int left, int top, int right, int bottom, int color1, int color2)
    {
        //GuiUtils.drawGradientRect(0, left, top, right, bottom, color1, color2);
    }
}
