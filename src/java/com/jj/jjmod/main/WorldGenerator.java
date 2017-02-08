package com.jj.jjmod.main;

import java.util.Random;
import com.jj.jjmod.worldgen.PopulateChunkRicelake;
import com.jj.jjmod.worldgen.WorldGenAmethyst;
import com.jj.jjmod.worldgen.WorldGenAntler;
import com.jj.jjmod.worldgen.WorldGenBean;
import com.jj.jjmod.worldgen.WorldGenBeehive;
import com.jj.jjmod.worldgen.WorldGenBeetroot;
import com.jj.jjmod.worldgen.WorldGenBerry;
import com.jj.jjmod.worldgen.WorldGenCarrot;
import com.jj.jjmod.worldgen.WorldGenChalk;
import com.jj.jjmod.worldgen.WorldGenChickpea;
import com.jj.jjmod.worldgen.WorldGenCoal;
import com.jj.jjmod.worldgen.WorldGenCopper;
import com.jj.jjmod.worldgen.WorldGenCotton;
import com.jj.jjmod.worldgen.WorldGenDiamond;
import com.jj.jjmod.worldgen.WorldGenEmerald;
import com.jj.jjmod.worldgen.WorldGenFireopal;
import com.jj.jjmod.worldgen.WorldGenGold;
import com.jj.jjmod.worldgen.WorldGenHemp;
import com.jj.jjmod.worldgen.WorldGenIron;
import com.jj.jjmod.worldgen.WorldGenLapis;
import com.jj.jjmod.worldgen.WorldGenMelon;
import com.jj.jjmod.worldgen.WorldGenPepper;
import com.jj.jjmod.worldgen.WorldGenPotato;
import com.jj.jjmod.worldgen.WorldGenPumpkin;
import com.jj.jjmod.worldgen.WorldGenRedstone;
import com.jj.jjmod.worldgen.WorldGenRuby;
import com.jj.jjmod.worldgen.WorldGenSalt;
import com.jj.jjmod.worldgen.WorldGenSapphire;
import com.jj.jjmod.worldgen.WorldGenSilver;
import com.jj.jjmod.worldgen.WorldGenTin;
import com.jj.jjmod.worldgen.WorldGenTomato;
import com.jj.jjmod.worldgen.WorldGenTreeApple;
import com.jj.jjmod.worldgen.WorldGenTreeBanana;
import com.jj.jjmod.worldgen.WorldGenTreeOrange;
import com.jj.jjmod.worldgen.WorldGenTreePear;
import com.jj.jjmod.worldgen.WorldGenWheat;
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
    
    @SubscribeEvent
    public void populate(PopulateChunkEvent.Populate event) {
        
        Random rand = event.getRand();        
        
        if (event.getType() == PopulateChunkEvent.Populate.EventType.LAKE &&
                rand.nextFloat() <= 0.1) {
            
            event.setResult(Result.DENY);
            new PopulateChunkRicelake(event.getWorld(), rand)
            .generateChunk(event.getChunkX(), event.getChunkZ());
            return;
        }
    }
    
    @SubscribeEvent
    public void cancelVillage(InitMapGenEvent event) {
        
        if (event.getType() == EventType.VILLAGE) {
            System.out.println("got village gen event");
            event.setNewGen(new MapGenVillage() {
                
                @Override
                public void generate(World world, int x, int z, ChunkPrimer primer) {}
            });
        }
    }
    
    @SubscribeEvent
    public void decorateBiome(DecorateBiomeEvent.Decorate event) {
        
        if (event.getType() == DecorateBiomeEvent.Decorate.EventType.PUMPKIN) {
            
            event.setResult(Result.DENY);
        }
    }

    @SubscribeEvent
    public void oreGenMinable(OreGenEvent.GenerateMinable event) {

        OreGenEvent.GenerateMinable.EventType type = event.getType();

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
