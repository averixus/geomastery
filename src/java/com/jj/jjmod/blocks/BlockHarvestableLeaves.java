package com.jj.jjmod.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Harvestable fruit leaf block. */
public class BlockHarvestableLeaves extends BlockLeaves {
    
    public static final PropertyInteger AGE =
            PropertyInteger.create("age", 0, 7);
    
    /** Supplier for fruit item. */
    private Supplier<Item> itemRef;
    /** Chance of growth per update tick. */
    private float growthChance;
    
    public BlockHarvestableLeaves(String name, Supplier<Item> itemRef,
            float growthChance) {
        
        super();
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(AGE, 0));
        this.setTickRandomly(true);
        BlockNew.setupBlock(this, name, CreativeTabs.DECORATIONS,
                0.2F, ToolType.MACHETE);
        this.itemRef = itemRef;
        this.growthChance = growthChance;
        this.setGraphicsLevel(Minecraft.getMinecraft()
                .gameSettings.fancyGraphics);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return Items.AIR;        
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        super.updateTick(world, pos, state, rand);
        state = world.getBlockState(pos);
        
        if (state.getBlock() != this) {
            
            return;
        }
        
        if (rand.nextFloat() <= this.growthChance) {
            
            int oldAge = state.getValue(AGE);       
            int newAge = (oldAge + 1) > 7 ? 7 : (oldAge + 1);
            IBlockState newState = state.withProperty(AGE, newAge);
            world.setBlockState(pos, newState);  
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float hitX, float hitY, float hitZ) {
        
        int age = state.getValue(AGE);
        
        if (age != 7) {

            return false;
            
        } else {

            IBlockState newState = state.withProperty(AGE, 0);
            world.setBlockState(pos, newState);
            
            if (!world.isRemote) {
                
                for (EnumFacing facing : EnumFacing.VALUES) {
                    
                    if (world.isAirBlock(pos.offset(facing))) {
                        
                        pos = pos.offset(facing);
                        break;
                    }
                }
                
                world.spawnEntity(new EntityItem(world,
                        pos.getX(), pos.getY(), pos.getZ(),
                        new ItemStack(this.itemRef.get())));
            }  
            
            return true;
        }
    } 
    
    @Override
    protected BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[]
                {AGE, DECAYABLE, CHECK_DECAY});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(AGE, meta);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(AGE);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world,
            BlockPos pos, int fortune) {

        return null;
    }

    @Override
    public EnumType getWoodType(int meta) {

        return EnumType.OAK;
    }
}
