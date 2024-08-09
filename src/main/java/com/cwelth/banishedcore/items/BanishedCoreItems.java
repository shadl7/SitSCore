package com.cwelth.banishedcore.items;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BanishedCoreItems {
    @ObjectHolder("sitscore:emptyactivator")
    public static PortalActivator activatorEmpty;
    @ObjectHolder("sitscore:netheractivator")
    public static PortalActivator activatorNether;
    @ObjectHolder("sitscore:endactivator")
    public static PortalActivator activatorEnd;
    @ObjectHolder("sitscore:abyssalactivator")
    public static PortalActivator activatorAbyssal;
    @ObjectHolder("sitscore:betweenlandsactivator")
    public static PortalActivator activatorBetweenlands;

    public BanishedCoreItems() {
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        activatorEmpty.initModel();
        activatorNether.initModel();
        activatorEnd.initModel();
        activatorAbyssal.initModel();
        activatorBetweenlands.initModel();
    }
}
