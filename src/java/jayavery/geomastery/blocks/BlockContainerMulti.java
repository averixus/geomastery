/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.main.GuiHandler.GuiList;
import jayavery.geomastery.tileentities.TECraftingAbstract;
import jayavery.geomastery.tileentities.TECraftingArmourer;
import jayavery.geomastery.tileentities.TECraftingArmourer.EPartArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker;
import jayavery.geomastery.tileentities.TECraftingCandlemaker.EPartCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge;
import jayavery.geomastery.tileentities.TECraftingForge.EPartForge;
import jayavery.geomastery.tileentities.TECraftingMason;
import jayavery.geomastery.tileentities.TECraftingMason.EPartMason;
import jayavery.geomastery.tileentities.TECraftingTextiles;
import jayavery.geomastery.tileentities.TECraftingTextiles.EPartTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking;
import jayavery.geomastery.tileentities.TECraftingWoodworking.EPartWoodworking;
import jayavery.geomastery.tileentities.TEFurnaceClay;
import jayavery.geomastery.tileentities.TEFurnaceClay.EPartClay;
import jayavery.geomastery.tileentities.TEFurnaceStone;
import jayavery.geomastery.tileentities.TEFurnaceStone.EPartStone;
import jayavery.geomastery.tileentities.TEMultiAbstract;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.IItemStorage;
import jayavery.geomastery.utilities.IMultipart;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Abstract superclass for crafting blocks with multipart TileEntities. */
public abstract class BlockContainerMulti<E extends Enum<E> & IMultipart> extends BlockContainerAbstract<ItemPlacing.Building> {
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    /** Supplier for this block's tileentity. */
    private final Supplier<? extends TileEntity> te;
    /** Part of the structure placed at the target block. */
    protected final E placePart;
        
    public BlockContainerMulti(String name, BlockMaterial material,
            float hardness, Supplier<? extends TileEntity> te,
            int ordinal, E placePart) {
        
        super(name, material, hardness, ordinal);
        this.te = te;
        this.placePart = placePart;
    }
    
    /** @return The PropertyEnum for this multiblock's parts. */
    public abstract PropertyEnum<E> getPartProperty();
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        ItemPlacing.Building item = new ItemPlacing.Building(this, stackSize);
        item.setMaxDamage(TECraftingAbstract.MAX_WEATHERING);
        return item;
    }
    
    // Checking and placement handled by TEs
    @Override
    public boolean place(World world, BlockPos targetPos, EnumFacing targetSide,
            EnumFacing placeFacing, ItemStack stack, EntityPlayer player) {
        
        return this.placePart.buildStructure(world,
                targetPos.offset(targetSide), placeFacing, stack, player);
    }

    // Validity logic used by TEs
    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent) {
            
            if (!world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
                
                message(player, Lang.BUILDFAIL_OBSTACLE);
                return false;
            }
            
        } else {
            
            TileEntity tileEntity = world.getTileEntity(pos);
            
            if (!(tileEntity instanceof TEMultiAbstract<?>) ||
                    ((TEMultiAbstract<?>) tileEntity).shouldBreak()) {
                
                return false;
            }
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        TileEntity te = world.getTileEntity(pos);
        E part = te == null ? setState.getValue(this.getPartProperty()) :
                ((TEMultiAbstract<E>) te).getPart();
        
        if (part.needsSupport() &&
                !weightBelow.canSupport(this.getWeight(stateBelow))) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        return true;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        List<ItemStack> result = Lists.newArrayList();
        
        if (te instanceof TEMultiAbstract<?> &&
                ((TEMultiAbstract<?>) te).getPart() == this.placePart) {
    
            if ((te instanceof TECraftingAbstract) &&
                    ((TECraftingAbstract<?>) te).hasWeathering()) {
                
                result.add(new ItemStack(this.item, 1, this.item.getMaxDamage()
                        - ((TECraftingAbstract<?>) te).getWeathering()));
                
            } else {
            
                result.add(new ItemStack(this.item));
            }
        }
        
        if (te instanceof IItemStorage) {
            
            result.addAll(((IItemStorage) te).getDrops());
        }
        
        return result;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return this.te.get();
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

    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
            
            tooltip.add(I18n.format(Lang.BUILDTIP_MULTIPART));
            tooltip.add(I18n.format(EBlockWeight.NONE.requires()));
            tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
            tooltip.add(I18n.format(Lang.BUILDTIP_WEATHER));
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
    
    /** Stone furnace. */
    public static class Stone extends BlockContainerMulti<EPartStone> {
        
        public static final PropertyEnum<EPartStone> PART = PropertyEnum.create("part", EPartStone.class);
        
        public Stone() {
            
            super("furnace_stone", BlockMaterial.STONE_FURNITURE, 5F,
                    TEFurnaceStone::new, GuiList.STONE.ordinal(),
                    EPartStone.BM);
        }
        
        @Override
        public PropertyEnum<EPartStone> getPartProperty() {
            
            return PART;
        }
        
        @SideOnly(Side.CLIENT) @Override
        public void addInformation(ItemStack stack, EntityPlayer player,
                List<String> tooltip, boolean advanced) {
            
            if (GeoConfig.textVisual.buildTooltips) {
                
                tooltip.add(I18n.format(Lang.BUILDTIP_MULTIPART));
                tooltip.add(I18n.format(EBlockWeight.NONE.requires()));
                tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
            }
        }
    }
    
    /** Clay furnace. */
    public static class Clay extends BlockContainerMulti<EPartClay> {
        
        public static final PropertyEnum<EPartClay> PART = PropertyEnum.create("part", EPartClay.class);
        
        public Clay() {
            
            super("furnace_clay", BlockMaterial.STONE_FURNITURE, 5F,
                    TEFurnaceClay::new, GuiList.CLAY.ordinal(),
                    EPartClay.BL);
        }
        
        @Override
        public PropertyEnum<EPartClay> getPartProperty() {
            
            return PART;
        }
        
        @SideOnly(Side.CLIENT) @Override
        public void addInformation(ItemStack stack, EntityPlayer player,
                List<String> tooltip, boolean advanced) {
            
            if (GeoConfig.textVisual.buildTooltips) {
                
                tooltip.add(I18n.format(Lang.BUILDTIP_MULTIPART));
                tooltip.add(I18n.format(EBlockWeight.NONE.requires()));
                tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
            }
        }
    }
    
    /** Woodworking bench. */
    public static class Woodworking extends BlockContainerMulti<EPartWoodworking> {
        
        public static final PropertyEnum<EPartWoodworking> PART = PropertyEnum.create("part", EPartWoodworking.class);
        
        public Woodworking() {
            
            super("crafting_woodworking", BlockMaterial.WOOD_FURNITURE,
                    5F, TECraftingWoodworking::new,
                    GuiList.WOODWORKING.ordinal(), EPartWoodworking.FM);
        }
        
        @Override
        public PropertyEnum<EPartWoodworking> getPartProperty() {
            
            return PART;
        }
    }
    
    /** Textiles table. */
    public static class Textiles extends BlockContainerMulti<EPartTextiles> {
        
        public static final PropertyEnum<EPartTextiles> PART = PropertyEnum.create("part", EPartTextiles.class);
        
        public Textiles() {
            
            super("crafting_textiles", BlockMaterial.WOOD_FURNITURE, 5F,
                    TECraftingTextiles::new, GuiList.TEXTILES.ordinal(),
                    EPartTextiles.FRONT);
        }
        
        @Override
        public PropertyEnum<EPartTextiles> getPartProperty() {
            
            return PART;
        }
    }

    /** Mason's workshop. */
    public static class Mason extends BlockContainerMulti<EPartMason> {
        
        public static final PropertyEnum<EPartMason> PART = PropertyEnum.create("part", EPartMason.class);
        
        public Mason() {
            
            super("crafting_mason", BlockMaterial.STONE_FURNITURE, 5F,
                    TECraftingMason::new, GuiList.MASON.ordinal(),
                    EPartMason.FM);
        }
        
        @Override
        public PropertyEnum<EPartMason> getPartProperty() {
            
            return PART;
        }
    }
    
    /** Forge. */
    public static class Forge extends BlockContainerMulti<EPartForge> {
        
        public static final PropertyEnum<EPartForge> PART = PropertyEnum.create("part", EPartForge.class);
        
        public Forge() {
            
            super("crafting_forge", BlockMaterial.STONE_FURNITURE, 5F,
                    TECraftingForge::new, GuiList.FORGE.ordinal(),
                    EPartForge.FM);
        }
        
        @Override
        public PropertyEnum<EPartForge> getPartProperty() {
            
            return PART;
        }
    }
    
    /** Candlemaker's bench. */
    public static class Candlemaker extends BlockContainerMulti<EPartCandlemaker> {
        
        public static final PropertyEnum<EPartCandlemaker> PART = PropertyEnum.create("part", EPartCandlemaker.class);
        
        public Candlemaker() {
            
            super("crafting_candlemaker", BlockMaterial.WOOD_FURNITURE,
                    5F, TECraftingCandlemaker::new,
                    GuiList.CANDLEMAKER.ordinal(), EPartCandlemaker.FRONT);
        }
        
        @Override
        public PropertyEnum<EPartCandlemaker> getPartProperty() {
            
            return PART;
        }
    }
    
    /** Armourer. */
    public static class Armourer extends BlockContainerMulti<EPartArmourer> {
        
        public static final PropertyEnum<EPartArmourer> PART = PropertyEnum.create("part", EPartArmourer.class);
        
        public Armourer() {
            
            super("crafting_armourer", BlockMaterial.STONE_FURNITURE, 5F,
                    TECraftingArmourer::new, GuiList.ARMOURER.ordinal(),
                    EPartArmourer.M);
        }
        
        @Override
        public PropertyEnum<EPartArmourer> getPartProperty() {
            
            return PART;
        }
    }
}
