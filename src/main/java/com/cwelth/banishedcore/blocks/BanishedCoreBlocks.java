package com.cwelth.banishedcore.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BanishedCoreBlocks {
    @ObjectHolder("sitscore:portal")
    public static Portal portal;
    @ObjectHolder("sitscore:portalblock")
    public static PortalBlock portalBlock;
    @ObjectHolder("sitscore:portalcore")
    public static PortalCore portalCore;

    public BanishedCoreBlocks() {
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        portal.initModel();
        portalBlock.initModel();
        portalCore.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initBlockItemModels() {
        portal.initItemModel();
        portalBlock.initItemModel();
        portalCore.initItemModel();
    }
}
