/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Random;
import jayavery.geomastery.worldgen.PopulateChunkRicelake;
import jayavery.geomastery.worldgen.WorldGenAntler;
import jayavery.geomastery.worldgen.WorldGenBeehive;
import jayavery.geomastery.worldgen.WorldGenCrop;
import jayavery.geomastery.worldgen.WorldGenPeat;
import jayavery.geomastery.worldgen.WorldGenStone;
import jayavery.geomastery.worldgen.WorldGenTar;
import jayavery.geomastery.worldgen.WorldGenTreeApple;
import jayavery.geomastery.worldgen.WorldGenTreeBanana;
import jayavery.geomastery.worldgen.WorldGenTreeOrange;
import jayavery.geomastery.worldgen.WorldGenTreePear;
import jayavery.geomastery.worldgen.WorldGenTreePineGiant;
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
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
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

        new WorldGenStone.Coal(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Diamond(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Emerald(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Gold(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Iron(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Lapis(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Redstone(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenStone.Copper(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Fireopal(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Ruby(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Sapphire(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Silver(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Tin(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Amethyst(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenStone.Salt(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Chalk(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenPeat(world, rand).generateChunk(xFromChunk, xFromChunk);
        new WorldGenTar(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenStone.Clay(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenAntler(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenCrop.Bean(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Tomato(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Berry(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Cotton(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Hemp(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Pepper(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Chickpea(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Beetroot(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Carrot(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Potato(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Wheat(world, rand).generateChunk(xFromChunk, zFromChunk);
        
        new WorldGenCrop.Pumpkin(world, rand).generateChunk(xFromChunk, zFromChunk);
        new WorldGenCrop.Melon(world, rand).generateChunk(xFromChunk, zFromChunk);
        
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

    /** Alters vanilla ore generation. */
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
    
    // TEST
    @SubscribeEvent
    public void saplingGrow(SaplingGrowTreeEvent event) {
        
        event.setResult(Result.DENY);
        World world = event.getWorld();
        Random rand = world.rand;
        BlockPos pos = event.getPos();
        
 //       switch (rand.nextInt(4)) {
            
   //         case 0:
          //      new WorldGenTreeOakSmall(world, rand, true).generateTree(pos);
     //           return;
 //           case 1:
          //      new WorldGenTreeOakMedium(world, rand, true).generateTree(pos);
  //              return;
//            case 2:
           //     new WorldGenTreeOakLarge(world, rand, true).generateTree(pos);  
 //               return;
 //           case 3:
        //        new WorldGenTreeOakGiant(world, rand, true).generateTree(pos);
     //           return;
//        }
        
        new WorldGenTreePineGiant(world, rand, true).generateTree(pos);
      //  new WorldGenTreeLarchSmall(world, rand, true).generateTree(pos);
    }
}
