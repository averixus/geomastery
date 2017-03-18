package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TEBox;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Box block. */
public class BlockBox extends BlockComplexAbstract {

    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.25,0,0.25,0.75,0.56,0.75);
    
    public BlockBox() {
        
        super("box", BlockMaterial.WOOD_HANDHARVESTABLE, 5, null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return Item.getItemFromBlock(this);
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {

        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.BOX.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        return new TEBox();
    }
    
    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int data) {
        
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false :
            tileentity.receiveClientEvent(id, data);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess source, BlockPos pos) {
        
        return BOX;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[0]);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {}
}
