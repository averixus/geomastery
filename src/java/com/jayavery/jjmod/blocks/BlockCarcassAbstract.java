package com.jayavery.jjmod.blocks;

import java.util.function.Supplier;
import com.jayavery.jjmod.capabilities.ICapDecay;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.items.ItemCarcassDecayable;
import com.jayavery.jjmod.items.ItemHuntingknife;
import com.jayavery.jjmod.tileentities.TECarcass;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import com.sun.istack.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for carcass blocks. */
public abstract class BlockCarcassAbstract extends BlockNew
        implements ITileEntityProvider {
    
    /** Shelf life of this carcass in days. */
    protected int shelfLife;
    /** Supplier for the dropped item. */
    protected Supplier<ItemCarcassDecayable> item;

    public BlockCarcassAbstract(String name, float hardness, int shelfLife,
            Supplier<ItemCarcassDecayable> item) {

        super(BlockMaterial.CARCASS, name, null, hardness, ToolType.KNIFE);
        this.shelfLife = shelfLife;
        this.item = item;
    }
    
    /** Spawns the knife harvest drops for this carcass. */
    protected abstract void spawnDrops(World world,
            BlockPos pos, long birthTime);
    
    /** @return The shelf life of this carcass in days. */
    public int getShelfLife() {
        
        return this.shelfLife;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return EIGHT;
    }
    
    /** Breaks this block and drops carcass item or
     * food items according to harvesting tool. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);
        long birthTime = ((TECarcass) te).getBirthTime();
        int stageSize = ((TECarcass) te).getStageSize();
        
        if (stack.getItem() instanceof ItemHuntingknife) {
            
            this.spawnDrops(world, pos, birthTime);
            
        } else {

            ItemStack drop = new ItemStack(this.item.get(), 1);
            ICapDecay capDecay = drop.getCapability(ModCaps.CAP_DECAY, null);
            capDecay.setBirthTime(birthTime);
            capDecay.setStageSize(stageSize);
            spawnAsEntity(world, pos, drop);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TECarcass();
    }
    
    /** Check position and break if invalid. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {

            TileEntity te = world.getTileEntity(pos);
            
            if (te instanceof TECarcass) {

                long birthTime = ((TECarcass) te).getBirthTime();
                int stageSize = ((TECarcass) te).getStageSize();
                ItemStack drop = new ItemStack(this.item.get(), 1);
                ICapDecay capDecay = drop
                        .getCapability(ModCaps.CAP_DECAY, null);
                capDecay.setBirthTime(birthTime);
                capDecay.setStageSize(stageSize);
                spawnAsEntity(world, pos, drop);
            }
            
            world.setBlockToAir(pos);
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {

        Block block = world.getBlockState(pos.down()).getBlock();
        
        if (ModBlocks.LIGHT.contains(block) ||
                ModBlocks.HEAVY.contains(block)) {

            return true;
        }
        
        if (block instanceof IBuildingBlock) {
            
            IBuildingBlock builtBlock = (IBuildingBlock) block;
            
            if (builtBlock.isHeavy() || builtBlock.isLight()) {

                return true;
            }
        }

        return false;
    }
}
