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
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.main.GuiHandler.GuiList;
import jayavery.geomastery.tileentities.TECraftingArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge;
import jayavery.geomastery.tileentities.TECraftingMason;
import jayavery.geomastery.tileentities.TECraftingSawpit;
import jayavery.geomastery.tileentities.TECraftingTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking;
import jayavery.geomastery.tileentities.TEFurnaceClay;
import jayavery.geomastery.tileentities.TEFurnaceStone;
import jayavery.geomastery.tileentities.TEMultiAbstract;
import jayavery.geomastery.tileentities.TECraftingArmourer.EnumPartArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker.EnumPartCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge.EnumPartForge;
import jayavery.geomastery.tileentities.TECraftingMason.EnumPartMason;
import jayavery.geomastery.tileentities.TECraftingSawpit.EnumPartSawpit;
import jayavery.geomastery.tileentities.TECraftingTextiles.EnumPartTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking.EnumPartWoodworking;
import jayavery.geomastery.tileentities.TEFurnaceClay.EnumPartClay;
import jayavery.geomastery.tileentities.TEFurnaceStone.EnumPartStone;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for crafting blocks with multipart TileEntities. */
public abstract class BlockMultiCrafting<E extends Enum<E> & IMultipart>
        extends BlockComplexAbstract {
    
    public static final PropertyDirection FACING = PropertyDirection
            .create("facing", EnumFacing.Plane.HORIZONTAL);
    
    /** Supplier for this block's tileentity. */
    private final Supplier<? extends TileEntity> te;
    /** Ordinal for the master block of this multipart. */
    private final int ordinal;
        
    public BlockMultiCrafting(String name, BlockMaterial material,
            float hardness, Supplier<? extends TileEntity> te, int ordinal) {
        
        super(name, material, hardness, null);
        this.te = te;
        this.ordinal = ordinal;
    }
    
    /** @return The PropertyEnum for this multiblock's parts. */
    protected abstract PropertyEnum<E> getPartProperty();
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return this.te.get();
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Geomastery.instance, this.ordinal, world, x, y, z);
        }
        
        return true;
    }
    
    /** Defines drops using TE information. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);
        spawnAsEntity(world, pos, ((TEMultiAbstract<?>) te).getDrop());
    }
    
    /** Overrides BlockBuilding default behaviour. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Collections.emptyList();
    }
    
    /** Validity logic handled by TEs. */
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        return super.isValid(world, pos) || world.getBlockState(pos).getBlock()
                == world.getBlockState(pos.down()).getBlock();
    }
    
    /** Overrides BlockBuilding default checks. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEMultiAbstract<?>)) {
            
            return;
        }
        
        TEMultiAbstract<?> tileCrafting = (TEMultiAbstract<?>) tileEntity;
        
        if (tileCrafting.shouldBreak()) {
            
            world.setBlockToAir(pos);
            spawnAsEntity(world, pos, tileCrafting.getDrop());
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEMultiAbstract<?>)) {
            
            return FULL_BLOCK_AABB;
        }
        
        TEMultiAbstract<?> tileCrafting = (TEMultiAbstract<?>) tileEntity;
        return tileCrafting.getBoundingBox();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEMultiAbstract<?>)) {
            
            return FULL_BLOCK_AABB;
        }
        
        TEMultiAbstract<?> tileCrafting = (TEMultiAbstract<?>) tileEntity;
        return tileCrafting.getCollisionBox();
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TEMultiAbstract<?>) {
            
            @SuppressWarnings("unchecked")
            TEMultiAbstract<E> tileCrafting = (TEMultiAbstract<E>) tileEntity;
            state = tileCrafting.applyActualState(state,
                    this.getPartProperty());
        }
        
        return state;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, this.getPartProperty());
    }
    
    public static class Stone extends BlockMultiCrafting<EnumPartStone> {
        
        public static final PropertyEnum<EnumPartStone> PART = PropertyEnum
                .<EnumPartStone>create("part", EnumPartStone.class);
        
        public Stone() {
            
            super("furnace_stone", BlockMaterial.STONE_HANDHARVESTABLE, 5F,
                    TEFurnaceStone::new, GuiList.STONE.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartStone> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Clay extends BlockMultiCrafting<EnumPartClay> {
        
        public static final PropertyEnum<EnumPartClay> PART = PropertyEnum
                .<EnumPartClay>create("part", EnumPartClay.class);
        
        public Clay() {
            
            super("furnace_clay", BlockMaterial.STONE_HANDHARVESTABLE, 5F,
                    TEFurnaceClay::new, GuiList.CLAY.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartClay> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Woodworking extends
            BlockMultiCrafting<EnumPartWoodworking> {
        
        public static final PropertyEnum<EnumPartWoodworking> PART =
                PropertyEnum.<EnumPartWoodworking>create("part",
                EnumPartWoodworking.class);
        
        public Woodworking() {
            
            super("crafting_woodworking", BlockMaterial.WOOD_HANDHARVESTABLE,
                    5F, TECraftingWoodworking::new,
                    GuiList.WOODWORKING.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartWoodworking> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Textiles extends BlockMultiCrafting<EnumPartTextiles> {
        
        public static final PropertyEnum<EnumPartTextiles> PART = PropertyEnum
                .<EnumPartTextiles>create("part", EnumPartTextiles.class);
        
        public Textiles() {
            
            super("crafting_textiles", BlockMaterial.WOOD_HANDHARVESTABLE, 5F,
                    TECraftingTextiles::new, GuiList.TEXTILES.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartTextiles> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Sawpit extends BlockMultiCrafting<EnumPartSawpit> {
        
        public static final PropertyEnum<EnumPartSawpit> PART = PropertyEnum
                .<EnumPartSawpit>create("part", EnumPartSawpit.class);
        
        public Sawpit() {
            
            super("crafting_sawpit", BlockMaterial.WOOD_HANDHARVESTABLE, 5F,
                    TECraftingSawpit::new, GuiList.SAWPIT.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartSawpit> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Mason extends BlockMultiCrafting<EnumPartMason> {
        
        public static final PropertyEnum<EnumPartMason> PART =
                PropertyEnum.<EnumPartMason>create("part", EnumPartMason.class);
        
        public Mason() {
            
            super("crafting_mason", BlockMaterial.STONE_HANDHARVESTABLE, 5F,
                    TECraftingMason::new, GuiList.MASON.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartMason> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Forge extends BlockMultiCrafting<EnumPartForge> {
        
        public static final PropertyEnum<EnumPartForge> PART = PropertyEnum
                .<EnumPartForge>create("part", EnumPartForge.class);
        
        public Forge() {
            
            super("crafting_forge", BlockMaterial.STONE_HANDHARVESTABLE, 5F,
                    TECraftingForge::new, GuiList.FORGE.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartForge> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Candlemaker extends
            BlockMultiCrafting<EnumPartCandlemaker> {
        
        public static final PropertyEnum<EnumPartCandlemaker> PART =
                PropertyEnum.<EnumPartCandlemaker>create("part",
                EnumPartCandlemaker.class);
        
        public Candlemaker() {
            
            super("crafting_candlemaker", BlockMaterial.WOOD_HANDHARVESTABLE,
                    5F, TECraftingCandlemaker::new,
                    GuiList.CANDLEMAKER.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartCandlemaker> getPartProperty() {
            
            return PART;
        }
    }
    
    public static class Armourer extends BlockMultiCrafting<EnumPartArmourer> {
        
        public static final PropertyEnum<EnumPartArmourer> PART = PropertyEnum
                .<EnumPartArmourer>create("part", EnumPartArmourer.class);
        
        public Armourer() {
            
            super("crafting_armourer", BlockMaterial.STONE_HANDHARVESTABLE, 5F,
                    TECraftingArmourer::new, GuiList.ARMOURER.ordinal());
        }
        
        @Override
        public PropertyEnum<EnumPartArmourer> getPartProperty() {
            
            return PART;
        }
    }
}
