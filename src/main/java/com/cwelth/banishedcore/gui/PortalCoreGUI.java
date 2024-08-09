package com.cwelth.banishedcore.gui;

import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PortalCoreGUI extends GuiContainer {
    public static final int WIDTH = 174;
    public static final int HEIGHT = 186;
    PortalCoreTE te;
    private static ResourceLocation background;

    public PortalCoreGUI(PortalCoreTE tileEntity, PortalCoreContainer container, String bg, EntityPlayer player) {
        super(container);
        this.te = tileEntity;
        background = new ResourceLocation("sitscore", bg);
        this.xSize = 174;
        this.ySize = 186;
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if (!this.te.structureComplete()) {
            this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format("portalcore.nostructure", new Object[0]), this.guiLeft + 87, this.guiTop + 92, -1);
        }

        if (this.te.isPortalActive) {
            this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format("portalcore.active", new Object[0]), this.guiLeft + 87, this.guiTop + 92, -1);
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
