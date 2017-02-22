package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.capabilities.ICapDecay;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.items.ItemCarcassDecayable;
import com.jj.jjmod.items.ItemHuntingknife;
import com.jj.jjmod.items.ItemJj;
import com.jj.jjmod.tileentities.TECarcass;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for carcass blocks. */
public abstract class BlockCarcass extends BlockNew
        implements ITileEntityProvider {
    
    /** Shelf life of this carcass in days. */
    protected int shelfLife;
    /** Supplier for the dropped item. */
    protected Supplier<ItemCarcassDecayable> item;

    public BlockCarcass(String name, float hardness, int shelfLife,
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
}
