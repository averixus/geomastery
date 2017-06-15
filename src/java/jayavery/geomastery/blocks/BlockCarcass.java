/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import jayavery.geomastery.capabilities.ICapDecay;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.items.ItemCarcassDecayable;
import jayavery.geomastery.items.ItemHuntingknife;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECarcass;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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
public abstract class BlockCarcass extends BlockBuilding {
    
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
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direcion) {
        
        return false;
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
            ICapDecay capDecay = drop.getCapability(GeoCaps.CAP_DECAY, null);
            capDecay.setBirthTime(birthTime);
            capDecay.setStageSize(stageSize);
            spawnAsEntity(world, pos, drop);
        }
    }
    
    /** Drops are handled using TE. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
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
    
    /** Check position and break if invalid. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.isValid(world, pos)) {

            TileEntity te = world.getTileEntity(pos);
            
            if (te instanceof TECarcass) {

                long birthTime = ((TECarcass) te).getBirthTime();
                int stageSize = ((TECarcass) te).getStageSize();
                ItemStack drop = new ItemStack(this.item.get(), 1);
                ICapDecay capDecay = drop
                        .getCapability(GeoCaps.CAP_DECAY, null);
                capDecay.setBirthTime(birthTime);
                capDecay.setStageSize(stageSize);
                spawnAsEntity(world, pos, drop);
            }
            
            world.setBlockToAir(pos);
        }
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
    
    public static class Chicken extends BlockCarcass {
        
        public Chicken() {
            
            super("carcass_chicken", 1F, 1, () -> GeoItems.CARCASS_CHICKEN);
        }
        
        @Override
        protected void spawnDrops(World world, BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.CHICKEN_RAW, 2);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            
            spawnAsEntity(world, pos, meat);
            spawnAsEntity(world, pos, new ItemStack(Items.BONE));
            spawnAsEntity(world, pos, new ItemStack(Items.FEATHER));
        }
    }
    
    public static class Sheep extends BlockCarcass {
        
        public Sheep() {
            
            super("carcass_sheep", 1F, 2, () -> GeoItems.CARCASS_SHEEP);
        }
        
        @Override
        protected void spawnDrops(World world, BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.MUTTON_RAW, 3);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            
            spawnAsEntity(world, pos, meat);
            spawnAsEntity(world, pos, new ItemStack(GeoItems.SKIN_SHEEP, 4));
            spawnAsEntity(world, pos, new ItemStack(Items.BONE, 3));
            spawnAsEntity(world, pos, new ItemStack(GeoItems.TALLOW));
            spawnAsEntity(world, pos, new ItemStack(GeoItems.WOOL, 3));
        }
    }
    
    public static class Cowpart extends BlockCarcass {

        public Cowpart() {
            
            super("carcass_cowpart", 2F, 2, () -> GeoItems.CARCASS_COWPART);
        }
        
        @Override
        protected void spawnDrops(World world, BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.BEEF_RAW, 5);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            
            spawnAsEntity(world, pos, meat);
            spawnAsEntity(world, pos, new ItemStack(GeoItems.SKIN_COW, 6));
            spawnAsEntity(world, pos, new ItemStack(Items.BONE, 5));
            spawnAsEntity(world, pos, new ItemStack(GeoItems.TALLOW));
        }
    }
    
    public static class Pig extends BlockCarcass {

        public Pig() {
            
            super("carcass_pig", 1F, 2, () -> GeoItems.CARCASS_PIG);
        }
        
        @Override
        protected void spawnDrops(World world, BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.PORK_RAW, 4);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            
            spawnAsEntity(world, pos, meat);
            spawnAsEntity(world, pos, new ItemStack(GeoItems.SKIN_PIG, 5));
            spawnAsEntity(world, pos, new ItemStack(Items.BONE, 4));
            spawnAsEntity(world, pos, new ItemStack(GeoItems.TALLOW));
        }
    }
    
    public static class Rabbit extends BlockCarcass {

        public Rabbit() {
            
            super("carcass_rabbit", 1F, 2, () -> GeoItems.CARCASS_RABBIT);
        }
        
        @Override
        protected void spawnDrops(World world, BlockPos pos, long age) {
            
            ItemStack meat = new ItemStack(GeoItems.RABBIT_RAW);
            meat.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(age);
            
            spawnAsEntity(world, pos, meat);
            spawnAsEntity(world, pos, new ItemStack(Items.RABBIT_HIDE));
            spawnAsEntity(world, pos, new ItemStack(Items.BONE));
        }
    }
}
