//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cwelth.banishedcore.gui;

import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
    public GUIHandler() {
    }

    @Nullable
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if (ID == 0) {
            PortalCoreTE te = (PortalCoreTE)world.getTileEntity(pos);
            return new PortalCoreContainer(player.inventory, te);
        } else {
            return null;
        }
    }

    @Nullable
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if (ID == 0) {
            PortalCoreTE te = (PortalCoreTE)world.getTileEntity(pos);
            return new PortalCoreGUI(te, new PortalCoreContainer(player.inventory, te), "textures/gui/portalcore.png", player);
        } else {
            return null;
        }
    }
}
