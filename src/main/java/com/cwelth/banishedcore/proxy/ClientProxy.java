package com.cwelth.banishedcore.proxy;

import com.cwelth.banishedcore.blocks.BanishedCoreBlocks;
import com.cwelth.banishedcore.items.BanishedCoreItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(
        modid = "sitscore",
        value = {Side.CLIENT}
)
public class ClientProxy extends CommonProxy {
    public ClientProxy() {
    }

    public void preInit(FMLPreInitializationEvent e) {
        OBJLoader.INSTANCE.addDomain("sitscore");
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BanishedCoreBlocks.initModels();
        BanishedCoreItems.initModels();
    }

    public void postInit(FMLPostInitializationEvent e) {
        BanishedCoreBlocks.initBlockItemModels();
        super.postInit(e);
    }

    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    public IAnimationStateMachine loadASM(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        return ModelLoaderRegistry.loadASM(location, parameters);
    }
}
