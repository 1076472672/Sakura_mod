package cn.mcmod.sakura.world.biome;

import cn.mcmod.sakura.block.BlockLoader;
import cn.mcmod.sakura.world.gen.WorldGenMapleTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeMapleForest extends Biome {

    protected static final WorldGenerator RED_MAPLETREE = new WorldGenMapleTree(false,5, BlockLoader.MAPLE_LOG.getDefaultState(),BlockLoader.MAPLE_LEAVE_RED.getDefaultState(),false);
    protected static final WorldGenerator RED_MAPLETREE_SAP = new WorldGenMapleTree(false,5, BlockLoader.MAPLE_LOG.getDefaultState(),BlockLoader.MAPLE_LEAVE_RED.getDefaultState(),true);

    public BiomeMapleForest(BiomeProperties mapleForest) {
        super(mapleForest);
        this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {

        return (WorldGenAbstractTree) RED_MAPLETREE;

    }
}
