package com.jayavery.jjmod.main;

import java.util.Random;
import com.jayavery.jjmod.worldgen.PopulateChunkRicelake;
import com.jayavery.jjmod.worldgen.WorldGenAmethyst;
import com.jayavery.jjmod.worldgen.WorldGenAntler;
import com.jayavery.jjmod.worldgen.WorldGenBean;
import com.jayavery.jjmod.worldgen.WorldGenBeehive;
import com.jayavery.jjmod.worldgen.WorldGenBeetroot;
import com.jayavery.jjmod.worldgen.WorldGenBerry;
import com.jayavery.jjmod.worldgen.WorldGenCarrot;
import com.jayavery.jjmod.worldgen.WorldGenChalk;
import com.jayavery.jjmod.worldgen.WorldGenChickpea;
import com.jayavery.jjmod.worldgen.WorldGenClay;
import com.jayavery.jjmod.worldgen.WorldGenCoal;
import com.jayavery.jjmod.worldgen.WorldGenCopper;
import com.jayavery.jjmod.worldgen.WorldGenCotton;
import com.jayavery.jjmod.worldgen.WorldGenDiamond;
import com.jayavery.jjmod.worldgen.WorldGenEmerald;
import com.jayavery.jjmod.worldgen.WorldGenFireopal;
import com.jayavery.jjmod.worldgen.WorldGenGold;
import com.jayavery.jjmod.worldgen.WorldGenHemp;
import com.jayavery.jjmod.worldgen.WorldGenIron;
import com.jayavery.jjmod.worldgen.WorldGenLapis;
import com.jayavery.jjmod.worldgen.WorldGenMelon;
import com.jayavery.jjmod.worldgen.WorldGenPeat;
import com.jayavery.jjmod.worldgen.WorldGenPepper;
import com.jayavery.jjmod.worldgen.WorldGenPotato;
import com.jayavery.jjmod.worldgen.WorldGenPumpkin;
import com.jayavery.jjmod.worldgen.WorldGenRedstone;
import com.jayavery.jjmod.worldgen.WorldGenRuby;
import com.jayavery.jjmod.worldgen.WorldGenSalt;
import com.jayavery.jjmod.worldgen.WorldGenSapphire;
import com.jayavery.jjmod.worldgen.WorldGenSilver;
import com.jayavery.jjmod.worldgen.WorldGenTar;
import com.jayavery.jjmod.worldgen.WorldGenTin;
import com.jayavery.jjmod.worldgen.WorldGenTomato;
import com.jayavery.jjmod.worldgen.WorldGenTreeApple;
import com.jayavery.jjmod.worldgen.WorldGenTreeBanana;
import com.jayavery.jjmod.worldgen.WorldGenTreeOrange;
import com.jayavery.jjmod.worldgen.WorldGenTreePear;
import com.jayavery.jjmod.worldgen.WorldGenWheat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Handler for world generation and related events. */
public class WorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkScaleX, int chunkScaleZ,
            World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {

        switch (world.provider.getDimension()) {

            case -1: {

                generateNetherChunk(world, rand, chunkScaleX * 16,
                        chunkScaleZ * 16);
                break;
            }

            case 0: {

                if (!(chunkGenerator instanceof ChunkProviderFlat)) {
                    
                    generateOverworldChunk(world, rand, chunkScaleX * 16,
                        chunkScaleZ * 16);
                }
                
                break;
            }

            case 1: {

                generateEndChunk(world, rand, chunkScaleX * 16,
                        chunkScaleZ * 16);
                break;
            }
        }
    }

    private void generateNetherChunk(World world, Random rand, int xFromChunk,
            int zFromChunk) {

    }

    private void generateOverworldChunk(World world, Random rand,
            int xFromChunk, int zFromChunk) {

        new WorldGenCoal(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenDiamond(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenEmerald(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenGold(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenIron(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenLapis(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenRedstone(world, rand)
                .generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenCopper(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenFireopal(world, rand)
                .generateChunk(xFromChunk, zFromChunk);
        new WorldGenRuby(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenSapphire(world, rand)
                .generateChunk(xFromChunk, zFromChunk);
        new WorldGenSilver(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenTin(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenAmethyst(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenSalt(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenChalk(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenPeat(world, rand).generateChunk(xFromChunk, xFromChunk);
        new WorldGenTar(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenClay(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenAntler(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenBean(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenTomato(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenBerry(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCotton(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenHemp(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenPepper(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenChickpea(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenBeetroot(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCarrot(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenPotato(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenWheat(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenPumpkin(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenMelon(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenBeehive(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenTreeApple(world, rand, false)
                .generateChunk(xFromChunk, zFromChunk);
        new WorldGenTreePear(world, rand, false)
                .generateChunk(xFromChunk, zFromChunk);
        new WorldGenTreeOrange(world, rand, false)
                .generateChunk(xFromChunk, zFromChunk);
        new WorldGenTreeBanana(world, rand, false)
                .generateChunk(xFromChunk, zFromChunk);
        
    }

    private void generateEndChunk(World world, Random rand, int x, int z) {

    }
    
    /** Alters vanilla chunk populating. */
    @SubscribeEvent
    public void populate(PopulateChunkEvent.Populate event) {
        
        Random rand = event.getRand();        
        
        // Chance of generating rice lake
        if (event.getType() == PopulateChunkEvent.Populate.EventType.LAKE &&
                rand.nextFloat() <= 0.1) {
            
            event.setResult(Result.DENY);
            new PopulateChunkRicelake(event.getWorld(), rand)
                    .generateChunk(event.getChunkX(), event.getChunkZ());
            return;
        }
    }
    
    /** Intercepts structure generation. */
    @SubscribeEvent
    public void cancelVillage(InitMapGenEvent event) {
        
        // Cancel any village generation
        if (event.getType() == EventType.VILLAGE) {

            event.setNewGen(new MapGenVillage() {
                
                @Override
                public void generate(World world, int x,
                        int z, ChunkPrimer primer) {}
            });
        }
    }
    
    /** Alters vanilla chunk decorating. */
    @SubscribeEvent
    public void decorateBiome(DecorateBiomeEvent.Decorate event) {
        
        // Cancel vanilla pumpkin and melon generation
        if (event.getType() == DecorateBiomeEvent.Decorate.EventType.PUMPKIN) {
            
            event.setResult(Result.DENY);
        }
    }

    /* Alters vanilla ore generation. */
    @SubscribeEvent
    public void oreGenMinable(OreGenEvent.GenerateMinable event) {

        OreGenEvent.GenerateMinable.EventType type = event.getType();

        // Cancel vanilla ores to be overriden
        if (type == OreGenEvent.GenerateMinable.EventType.COAL ||
                type == OreGenEvent.GenerateMinable.EventType.DIAMOND ||
                type == OreGenEvent.GenerateMinable.EventType.EMERALD ||
                type == OreGenEvent.GenerateMinable.EventType.GOLD ||
                type == OreGenEvent.GenerateMinable.EventType.IRON ||
                type == OreGenEvent.GenerateMinable.EventType.LAPIS ||
                type == OreGenEvent.GenerateMinable.EventType.REDSTONE) {

            event.setResult(Event.Result.DENY);
        }
    }
}
