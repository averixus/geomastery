package jayavery.geomastery.blocks;

import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.main.GuiHandler.GuiList;
import jayavery.geomastery.tileentities.TECraftingKnapping;
import jayavery.geomastery.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
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

/** Knapping crafting block. */
public class BlockCraftingKnapping extends BlockComplexAbstract {

    public BlockCraftingKnapping() {

        super("crafting_knapping", BlockMaterial.STONE_HANDHARVESTABLE,
                5F, null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
        
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TECraftingKnapping();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return FOUR;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this);
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state;
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
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {

        if (!world.isRemote) {
            
            player.openGui(Geomastery.instance, GuiList.KNAPPING.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
