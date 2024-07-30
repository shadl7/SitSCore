package su.shadl7.sitscore.gui;

import net.minecraft.item.ItemStack;

public interface ITooltipPainter {
    void drawTooltip(int x, int y, int x2, int y2, int mouseX, int mouseY, ItemStack pattern);
}
