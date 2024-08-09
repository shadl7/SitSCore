package com.cwelth.banishedcore.proxy;

import com.cwelth.banishedcore.blocks.BanishedCoreBlocks;
import com.cwelth.banishedcore.blocks.Portal;
import com.cwelth.banishedcore.blocks.PortalBlock;
import com.cwelth.banishedcore.blocks.PortalCore;
import com.cwelth.banishedcore.items.PortalActivator;
import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class CommonProxy {
    public CommonProxy() {
    }

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register((new ItemBlock(BanishedCoreBlocks.portal)).setRegistryName(BanishedCoreBlocks.portal.getRegistryName()));
        event.getRegistry().register((new ItemBlock(BanishedCoreBlocks.portalBlock)).setRegistryName(BanishedCoreBlocks.portalBlock.getRegistryName()));
        event.getRegistry().register((new ItemBlock(BanishedCoreBlocks.portalCore)).setRegistryName(BanishedCoreBlocks.portalCore.getRegistryName()));
        event.getRegistry().register(new PortalActivator("emptyactivator"));
        event.getRegistry().register(new PortalActivator("netheractivator"));
        event.getRegistry().register(new PortalActivator("endactivator"));
        event.getRegistry().register(new PortalActivator("abyssalactivator"));
        event.getRegistry().register(new PortalActivator("betweenlandsactivator"));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new Portal());
        event.getRegistry().register(new PortalBlock());
        event.getRegistry().register(new PortalCore());
        GameRegistry.registerTileEntity(PortalCoreTE.class, new ResourceLocation("sitscore", "portalcorete"));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
    }

    @SubscribeEvent
    public static void handleOreDicts(OreDictionary.OreRegisterEvent event) {
    }

    public IAnimationStateMachine loadASM(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        return null;
    }
}
