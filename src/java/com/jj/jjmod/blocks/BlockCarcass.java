package com.jj.jjmod.blocks;

import javax.annotation.Nullable;
import com.jj.jjmod.items.ItemHuntingknife;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCarcass extends BlockNew {
    
    protected static final AxisAlignedBB HALF_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    public final ItemStack[] drops;

    public BlockCarcass(String name, ItemStack[] drops, float hardness) {

        super(BlockMaterial.CARCASS, name, CreativeTabs.FOOD,
                hardness, ToolType.KNIFE);
        
        this.drops = drops;
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
        
        if (stack.getItem() instanceof ItemHuntingknife) {
            
            for (ItemStack drop : this.drops) {
                
                spawnAsEntity(world, pos, drop);
            }
            
        } else {
            
            spawnItem(world, pos, Item.getItemFromBlock(this));
        }
    }
}
