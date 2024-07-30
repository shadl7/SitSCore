package su.shadl7.sitscore.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;
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
    protected void drawGradientRect(int left, int top, int right, int bottom, int color1, int color2) {}

    private boolean isPointInRegion(int left, int top, int right, int bottom, int pointX, int pointY) {
        return pointX >= left - 1 && pointX < right + 1 && pointY >= top - 1 && pointY < bottom + 1;
    }

    public void drawHoveredTooltip(int mouseX, int mouseY, ITooltipPainter painter) {
        for (int i = 0; i < buttons.size(); i++) {
            int renderTop = this.top - (int)((IScrollerHack) this).sitSCore$getScrollDistance();
            int slotTop = renderTop + i * this.slotHeight;
            int slotBottom = slotTop + this.slotHeight;
            if (slotBottom >= this.top && slotTop <= this.bottom) { // Check if button is outside selector
                int y1 = Math.max(this.top, slotTop), // If button over/under border reset coordinate to border
                        y2 = Math.min(this.bottom, slotBottom);
                if (this.isPointInRegion(this.left, y1,
                        SELECTOR_COLS * 16 + this.left, y2, mouseX, mouseY)) { // Check for all buttons in one row
                    for (int j = 0; j < buttons.get(i).size(); j++) {
                        int x1 = this.left + j * 16, // Calculate coordinates for every button in row
                                x2 = this.left + (j + 1) * 16;
                        if (this.isPointInRegion(x1, y1, x2, y2, mouseX, mouseY)) { // Check for button
                                    painter.drawTooltip(x1, y1, x2, y2, mouseX, mouseY, buttons.get(i).get(j).getPattern());
                        }
                    }
                }
            }
        }
    }
}
