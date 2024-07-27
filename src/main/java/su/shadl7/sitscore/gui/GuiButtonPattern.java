package su.shadl7.sitscore.gui;

import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.util.ResourceLocation;

public class GuiButtonPattern extends GuiButtonImage {
    public GuiButtonPattern (int buttonId, int x, int y, int width, int height,
                            int textureOffestX, int textureOffestY, int yDiffText, ResourceLocation resource) {
        super(buttonId, x, y, width, height, textureOffestX, textureOffestY, yDiffText, resource);
    }
}
