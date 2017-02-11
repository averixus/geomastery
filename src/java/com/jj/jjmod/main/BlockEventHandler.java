package com.jj.jjmod.main;

import java.util.Arrays;
import com.jj.jjmod.blocks.BlockCarcass;
import com.jj.jjmod.blocks.BlockRock;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.items.ItemAxe;
import com.jj.jjmod.items.ItemHoe;
import com.jj.jjmod.items.ItemHuntingknife;
import com.jj.jjmod.items.ItemPickaxe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

/** Handler for block related events. */
public class BlockEventHandler {

    /** Alters equivalent to Block#neighborChanged behaviour
     * for vanilla blocks. */
    @SubscribeEvent
    public void notifyNeighbor(NeighborNotifyEvent event) {
        
        Block sourceBlock = event.getState().getBlock();
        World world = event.getWorld();
        BlockPos sourcePos = event.getPos();
        
        // Prevent Nether Portals from generating
        if (sourceBlock == Blocks.PORTAL) {

            world.setBlockToAir(sourcePos);
        }

        // Make dirt and stone fall
        for (EnumFacing facing : EnumFacing.VALUES) {
        
            BlockPos pos = sourcePos.offset(facing);
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            
            boolean shouldFall = false;
            IBlockState fallState = state;
            boolean airBelow = world.isAirBlock(pos.down());
            
            if (block instanceof BlockStone || block instanceof BlockRock ||
                    block instanceof BlockOre) {

                boolean airAround = false;
                
                for (EnumFacing direction : EnumFacing.HORIZONTALS) {
                    
                    if (world.isAirBlock(pos.offset(direction))) {
                        
                        airAround = true;
                        break;
                    }
                }
                
                if (block instanceof BlockStone) {

                    EnumType type = state.getValue(BlockStone.VARIANT);
                    
                    if (type == EnumType.GRANITE) {

                        shouldFall = false;
                        
                    } else if (type == EnumType.ANDESITE) {

                        shouldFall = airAround && airBelow ?
                                world.rand.nextFloat() < 0.33 : airBelow ?
                                world.rand.nextFloat() < 0.04F : false;
                        
                    } else if (type == EnumType.DIORITE) {

                        shouldFall = airAround && airBelow ?
                                world.rand.nextFloat() < 0.25 : airBelow ?
                                world.rand.nextFloat() < 0.03F : false;
                        
                    } else {

                        shouldFall = airAround && airBelow ?
                                world.rand.nextFloat() < 0.17F : airBelow ?
                                world.rand.nextFloat() < 0.02F : false;
                    }
                    
                    fallState = ModBlocks.rubble.getDefaultState();
                    
                } else {

                    shouldFall = airAround && airBelow ?
                            world.rand.nextFloat()< 0.17F : airBelow ?
                            world.rand.nextFloat() < 0.02F : false;
                }
                    
            
            } else if (block == Blocks.DIRT || block == Blocks.GRASS) {

                shouldFall = airBelow;
            }
            
            if (shouldFall) {

                if (!BlockFalling.fallInstantly &&
                        world.isAreaLoaded(pos.add(-32, -32, -32),
                        pos.add(32, 32, 32))) {
                    
                    if (!world.isRemote) {

                        world.setBlockState(pos, fallState);
                        EntityFallingBlock falling =
                                new EntityFallingBlock(world,
                                pos.getX() + 0.5D, pos.getY(),
                                pos.getZ() + 0.5D, fallState);
                        world.spawnEntity(falling);
                    }
                    
                } else {

                    world.setBlockToAir(pos);
                    BlockPos checkPos;

                    for (checkPos = pos.down(); world.isAirBlock(checkPos) &&
                            checkPos.getY() > 0; checkPos = checkPos.down()) {
                        ;
                    }

                    if (checkPos.getY() > 0) {

                        world.setBlockState(checkPos.up(), fallState, 0);
                    }
                }
            }
        }
    }
    
    /** Adjusts equivalent to Block#updateTick for vanilla blocks. */
    @SubscribeEvent
    public void cropGrow(CropGrowEvent.Pre event) {
        
        // Sugarcane -> adjust odds
        if (event.getState().getBlock() == Blocks.REEDS) {
            
            if (event.getWorld().rand.nextFloat() > 0.4) {
                
                event.setResult(Result.DENY);
            }
        }
    }
    
    /** Adjusts block breaking speed. */
    @SubscribeEvent
    public void playerBreakSpeed(PlayerEvent.BreakSpeed event) {

        Block block = event.getState().getBlock();
        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        String toolRequired = block.getHarvestTool(event.getState());
        
        // Make axe and pickaxe blocks unbreakable without tool
        if (("axe".equals(toolRequired) &&
                !(stack.getItem() instanceof ItemAxe)) ||
                ("pickaxe".equals(toolRequired) &&
                !(stack.getItem() instanceof ItemPickaxe))) {
            
            event.setCanceled(true);
        }
    }
    
    /** Changes behaviour of blocks when broken by player. */
    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event) {
        
        Block block = event.getState().getBlock();
        EntityPlayer player = event.getPlayer();
        ItemStack stack = player.getHeldItem(player.getActiveHand());
        
        // Dirt broken with hoe turns to farmland
        if ((block == Blocks.DIRT || block == Blocks.GRASS) &&
                stack.getItem() instanceof ItemHoe) {
            
            event.getWorld().setBlockState(event.getPos(),
                    Blocks.FARMLAND.getDefaultState());
            event.setCanceled(true);
        }
    }

    /** Alters equivalent to Block#getDrops for vanilla blocks. */
    @SubscribeEvent
    public void harvestDrops(HarvestDropsEvent event) {

        World world = event.getWorld();
        Block block = event.getState().getBlock();

        if (block instanceof BlockLeaves) {

            event.getDrops().add(new ItemStack(ModItems.leaves));

            if (world.rand.nextInt(8) == 0) {

                event.getDrops().add(new ItemStack(Items.STICK));
            }
        }
        
        if (block == Blocks.TALLGRASS || block == Blocks.DOUBLE_PLANT) {

            event.getDrops().replaceAll((Object) -> ItemStack.EMPTY);
        }

        if (block instanceof BlockLog) {

            event.getDrops().clear();
            int rand = world.rand.nextInt(3);
            event.getDrops().add(new ItemStack(rand == 0 ?
                    ModItems.log : rand == 1 ?
                    ModItems.pole : ModItems.thicklog));
        }

        if (block instanceof BlockDirt || block instanceof BlockGrass) {

            event.getDrops().clear();
            
            if (world.rand.nextInt(10) == 0) {

                event.getDrops().add(new ItemStack(Items.FLINT));
            }

            event.getDrops().add(new ItemStack(ModItems.dirt, 4));
        }
        
        if (block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Items.REDSTONE,
                    world.rand.nextInt(4) + 1));
        }
        
        if (block == Blocks.LAPIS_ORE) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Items.DYE,
                    world.rand.nextInt(5) + 1,
                    EnumDyeColor.BLUE.getMetadata()));
        }
        
        if (block == Blocks.DIAMOND_ORE) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Items.DIAMOND));
        }
        
        if (block == Blocks.GOLD_ORE) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(ModItems.oreGold));
        }
        
        if (block == Blocks.EMERALD_ORE) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Items.EMERALD));
        }
        
        if (block == Blocks.IRON_ORE) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(ModItems.oreIron));
        }

        if (block instanceof BlockStone) {

            event.getDrops().clear();
            event.getDrops().add(new ItemStack(ModItems.stoneRough, 4));
        }
        
        if (block == Blocks.SAND) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(ModItems.sand, 4));
        }
        
        if (block == Blocks.GRAVEL) {
            
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Items.FLINT));
        }
    }
}
