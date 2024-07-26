package su.shadl7.sitscore.worldgen;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenStartStruct implements IWorldGenerator {
    private static final ResourceLocation START = new ResourceLocation("sitscore:start_struct");
    @Override
    public void generate (Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), START);
    }
}

