/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Random;
import jayavery.geomastery.worldgen.PopulateChunkRicelake;
import jayavery.geomastery.worldgen.WorldGenAmethyst;
import jayavery.geomastery.worldgen.WorldGenAntler;
import jayavery.geomastery.worldgen.WorldGenBean;
import jayavery.geomastery.worldgen.WorldGenBeehive;
import jayavery.geomastery.worldgen.WorldGenBeetroot;
import jayavery.geomastery.worldgen.WorldGenBerry;
import jayavery.geomastery.worldgen.WorldGenCarrot;
import jayavery.geomastery.worldgen.WorldGenChalk;
import jayavery.geomastery.worldgen.WorldGenChickpea;
import jayavery.geomastery.worldgen.WorldGenClay;
import jayavery.geomastery.worldgen.WorldGenCoal;
import jayavery.geomastery.worldgen.WorldGenCopper;
import jayavery.geomastery.worldgen.WorldGenCotton;
import jayavery.geomastery.worldgen.WorldGenDiamond;
import jayavery.geomastery.worldgen.WorldGenEmerald;
import jayavery.geomastery.worldgen.WorldGenFireopal;
import jayavery.geomastery.worldgen.WorldGenGold;
import jayavery.geomastery.worldgen.WorldGenHemp;
import jayavery.geomastery.worldgen.WorldGenIron;
import jayavery.geomastery.worldgen.WorldGenLapis;
import jayavery.geomastery.worldgen.WorldGenMelon;
import jayavery.geomastery.worldgen.WorldGenPeat;
import jayavery.geomastery.worldgen.WorldGenPepper;
import jayavery.geomastery.worldgen.WorldGenPotato;
import jayavery.geomastery.worldgen.WorldGenPumpkin;
import jayavery.geomastery.worldgen.WorldGenRedstone;
import jayavery.geomastery.worldgen.WorldGenRuby;
import jayavery.geomastery.worldgen.WorldGenSalt;
import jayavery.geomastery.worldgen.WorldGenSapphire;
import jayavery.geomastery.worldgen.WorldGenSilver;
import jayavery.geomastery.worldgen.WorldGenTar;
import jayavery.geomastery.worldgen.WorldGenTin;
import jayavery.geomastery.worldgen.WorldGenTomato;
import jayavery.geomastery.worldgen.WorldGenTreeApple;
import jayavery.geomastery.worldgen.WorldGenTreeBanana;
import jayavery.geomastery.worldgen.WorldGenTreeOrange;
import jayavery.geomastery.worldgen.WorldGenTreePear;
import jayavery.geomastery.worldgen.WorldGenWheat;
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

        if (world.provider.getDimension() == 0) {

            if (!(chunkGenerator instanceof ChunkProviderFlat)) {
                
                generateOverworldChunk(world, rand, chunkScaleX * 16,
                    chunkScaleZ * 16);
            }
        }
    }

    private void generateOverworldChunk(World world, Random rand,
            int xFromChunk, int zFromChunk) {

        new WorldGenCoal(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenDiamond(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenEmerald(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenGold(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenIron(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenLapis(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenRedstone(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenCopper(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenFireopal(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenRuby(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenSapphire(world, rand).generateChunk(xFromChunk, zFromChunk);
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
        
        new WorldGenTreeApple(world, rand, false).generateChunk(xFromChunk, zFromChunk);
        new WorldGenTreePear(world, rand, false).generateChunk(xFromChunk, zFromChunk);
        new WorldGenTreeOrange(world, rand, false).generateChunk(xFromChunk, zFromChunk);
        new WorldGenTreeBanana(world, rand, false).generateChunk(xFromChunk, zFromChunk);
        
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
    
    /** Alters vanilla structure generation. */
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
