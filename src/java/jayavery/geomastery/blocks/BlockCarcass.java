/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.capabilities.ICapDecay;
import jayavery.geomastery.items.ItemCarcassDecayable;
import jayavery.geomastery.items.ItemHuntingknife;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECarcass;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Carcass blocks. */
public abstract class BlockCarcass extends BlockBuildingAbstract<ItemCarcassDecayable> {

    public BlockCarcass(String name, float hardness) {

        super(BlockMaterial.CARCASS, name, CreativeTabs.FOOD, hardness, 1);
        this.setHarvestLevel(EToolType.KNIFE.toString(), 1);
    }
    
    /** Spawns the knife harvest drops for this carcass. */
    protected abstract List<ItemStack> getItems(World world,
            BlockPos pos, long birthTime);
    /** @return The shelf life of this carcass in days. */
    public abstract int getShelfLife();
    
    @Override
    protected ItemCarcassDecayable createItem(int stackSize) {
        
        return new ItemCarcassDecayable(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direcion) {
        
        return false;
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos, EnumFacing targetSide,
            EnumFacing placeFacing, ItemStack stack, EntityPlayer player) {
    
        BlockPos placePos = targetPos.offset(targetSide);
        IBlockState setState = this.getDefaultState();
        
        if (this.isValid(world, placePos, stack, false, setState, player)) {
    
            ICapDecay decayCap = stack.getCapability(GeoCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());
            world.setBlockState(placePos, setState);
            ((TECarcass) world.getTileEntity(placePos))
                    .setData(decayCap.getBirthTime(), decayCap.getStageSize());
            return true;
        }
        
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack stack, EntityPlayer player) {
        
        if (te instanceof TECarcass && world instanceof World) {
            
            TECarcass carcass = (TECarcass) te;
            long birthTime = carcass.getBirthTime();
            int stageSize = carcass.getStageSize();
            
            if (stack.getItem() instanceof ItemHuntingknife) {
                
                return this.getItems((World) world, pos, birthTime);
                
            } else {
                
                ItemStack drop = new ItemStack(this.item, 1);
                ICapDecay capDecay = drop.getCapability(GeoCaps.CAP_DECAY,
                        null);
                capDecay.setBirthTime(birthTime);
                capDecay.setStageSize(stageSize);
                return Lists.newArrayList(drop);
            }
        }
        
        return Collections.emptyList();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState state) {

        return new TECarcass();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return EIGHT;
    }

    /** Chicken carcass. */
    public static class Chicken extends BlockCarcass {
        
        public Chicken() {
            
            super("carcass_chicken", 1F);
        }
        
        @Override
        protected List<ItemStack> getItems(World world,
                BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.CHICKEN_RAW, 2);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            return Lists.newArrayList(meat, new ItemStack(Items.BONE),
                    new ItemStack(Items.FEATHER));
        }
        
        @Override
        public int getShelfLife() {
            
            return 1;
        }
    }
    
    /** Sheep carcass. */
    public static class Sheep extends BlockCarcass {
        
        public Sheep() {
            
            super("carcass_sheep", 1F);
        }
        
        @Override
        protected List<ItemStack> getItems(World world,
                BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.MUTTON_RAW, 3);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            return Lists.newArrayList(meat,
                    new ItemStack(GeoItems.SKIN_SHEEP, 4),
                    new ItemStack(Items.BONE, 3),
                    new ItemStack(GeoItems.TALLOW),
                    new ItemStack(GeoItems.WOOL, 3));
        }
        
        @Override
        public int getShelfLife() {
            
            return 2;
        }
    }
    
    /** Cow carcass part. */
    public static class Cowpart extends BlockCarcass {

        public Cowpart() {
            
            super("carcass_cowpart", 2F);
        }
        
        @Override
        protected List<ItemStack> getItems(World world,
                BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.BEEF_RAW, 5);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            return Lists.newArrayList(meat, new ItemStack(GeoItems.SKIN_COW, 6),
                    new ItemStack(Items.BONE, 5),
                    new ItemStack(GeoItems.TALLOW));
        }
        
        @Override
        public int getShelfLife() {
            
            return 2;
        }
    }
    
    /** Pig carcass. */
    public static class Pig extends BlockCarcass {

        public Pig() {
            
            super("carcass_pig", 1F);
        }
        
        @Override
        protected List<ItemStack> getItems(World world,
                BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.PORK_RAW, 4);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            return Lists.newArrayList(meat, new ItemStack(GeoItems.SKIN_PIG, 5),
                    new ItemStack(Items.BONE, 4),
                    new ItemStack(GeoItems.TALLOW));
        }
        
        @Override
        public int getShelfLife() {
            
            return 2;
        }
    }
    
    /** Rabbit carcass. */
    public static class Rabbit extends BlockCarcass {

        public Rabbit() {
            
            super("carcass_rabbit", 1F);
        }
        
        @Override
        protected List<ItemStack> getItems(World world,
                BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.RABBIT_RAW);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            return Lists.newArrayList(meat, new ItemStack(Items.RABBIT_HIDE),
                    new ItemStack(Items.BONE));
        }
        
        @Override
        public int getShelfLife() {
            
            return 2;
        }
    }
}
