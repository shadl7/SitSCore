package su.shadl7.sitscore.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;

public class GuiButtonPattern extends Gui {
    private final int selectedPattern;
    private ItemStack pattern;

    public GuiButtonPattern (int selectedPattern) {
        this.selectedPattern = selectedPattern;
        this.pattern = TinkerRegistry.getStencilTableCrafting().get(selectedPattern);
    }

    public void draw(Minecraft mc, int x, int y) {
        var itemRender = mc.getRenderItem();
        itemRender.renderItemIntoGUI(this.pattern, x, y);
    }

    public ItemStack getPattern() {
        return pattern;
    }
}
