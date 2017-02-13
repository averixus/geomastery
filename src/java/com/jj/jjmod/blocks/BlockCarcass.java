package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.items.ItemCarcassDecayable;
import com.jj.jjmod.items.ItemHuntingknife;
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

/** Abstract superclass for Carcass blocks. */
public abstract class BlockCarcass extends BlockNew
        implements ITileEntityProvider {
    
    protected static final AxisAlignedBB HALF_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    
    /** Shelf life of this Carcass in days. */
    private int shelfLife;
    /** Supplier for the dropped item. */
    protected Supplier<ItemCarcassDecayable> item;

    public BlockCarcass(String name, float hardness, int shelfLife,
            Supplier<ItemCarcassDecayable> item) {

        super(BlockMaterial.CARCASS, name, null, hardness, ToolType.KNIFE);
        this.shelfLife = shelfLife;
        this.item = item;
    }
    
    /** @return The shelf life of this Carcass in days. */
    public int getShelfLife() {
        
        return this.shelfLife;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return HALF_BOUNDS;
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);
        int age = ((TECarcass) te).getAge();
        
        if (stack.getItem() instanceof ItemHuntingknife) {
            
            this.spawnDrops(world, pos, age);
            
        } else {

            ItemStack drop = new ItemStack(this.item.get());
            drop.getCapability(ModCapabilities.CAP_DECAY, null).setAge(age);
            spawnAsEntity(world, pos, drop);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        TECarcass tileEntity = new TECarcass();
        tileEntity.setShelfLife(this.shelfLife);
        return tileEntity;
    }
    
    /** Spawns the knife harvest drops for this Carcass. */
    protected abstract void spawnDrops(World world, BlockPos pos, int age);
}
