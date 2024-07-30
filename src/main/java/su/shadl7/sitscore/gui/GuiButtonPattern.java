package su.shadl7.sitscore.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import slimeknights.tconstruct.library.TinkerRegistry;

public class GuiButtonPattern extends Gui {
    private final int selectedPattern;

    public GuiButtonPattern (int selectedPattern) {
        this.selectedPattern = selectedPattern;
    }

    public void draw(Minecraft mc, int x, int y) {
        var pattern = TinkerRegistry.getStencilTableCrafting().get(selectedPattern);
        var itemRender = mc.getRenderItem();
        itemRender.renderItemIntoGUI(pattern, x, y);

    }

    public boolean isClicked(int mouseX, int mouseY, int x, int y, int height) {
        return false;
    }
}
