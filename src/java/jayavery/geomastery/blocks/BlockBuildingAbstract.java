/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Abstract superclass for item-placed weighted building blocks. */
public abstract class BlockBuildingAbstract<I extends ItemPlacing>
        extends BlockNew {

    /** This block's associated ItemBuilding instance. */
    protected final I item;
    
    public BlockBuildingAbstract(Material material, String name,
            CreativeTabs tab, float hardness, int stackSize) {
        
        super(material, name, tab, hardness, null);
        this.item = this.createItem(stackSize);
    }
    
    /** @return A new ItemPlacing instance for this block.*/
    protected abstract I createItem(int stackSize);
    /** @return This block state's effective weight. */
    public abstract EBlockWeight getWeight(IBlockState state); 
    
    /** @return This block's ItemPlacing instance. */
    public I getItem() {
        
        return this.item;
    }
    
    /** Attempts to place this block or structure. Default implementation
     * places default blockstate at targeted side of position if valid. */
    public boolean place(World world, BlockPos targetPos, EnumFacing targetSide,
            EnumFacing placeFacing, ItemStack stack, EntityPlayer player) {
        
        BlockPos placePos = targetPos.offset(targetSide);
        IBlockState setState = this.getDefaultState();
        
        if (this.isValid(world, placePos, stack, false, setState, player)) {
            
            world.setBlockState(placePos, setState);
            return true;
        }
        
        return false;
    }

    /** @return Whether this position is valid for this block. Default
     * implementation requires the block below to be same weight or heavier.
     * @param alreadyPresent if false, ignores multipart checking.
     * @param checkState the state being checked for this position. */
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent && !world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        
        if (!weightBelow.canSupport(this.getWeight(stateBelow))) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        return true;
    }
    
    
    /** @return Whether walls connect to this block from the given direction.
     * Default is always true. */
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return true;
    }
    
    /** Adds this block's build reqs to the tooltip if config. Default
     * implementation uses getWeight for requires and supports. */
    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.buildTooltips) {
        
            tooltip.add(I18n.format(this.getWeight(this.getDefaultState()).requires()));
            tooltip.add(I18n.format(this.getWeight(this.getDefaultState()).supports()));
        }
    }
    
    /** Override to delay removal until harvestBlock so getDrops can use TE. */
    @Override
    public final boolean removedByPlayer(IBlockState state, World world,
            BlockPos pos, EntityPlayer player, boolean willHarvest) {
        
        if (willHarvest) {
            
            return true;
        }
        
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
    /** Remove block after getDrops has used TE.
     * Normally adds exhaustion and drops items after block is already air.
     * This version uses custom doHarvest to get drops from player's tool.
     * Override to set result of breaking to something other than air.
     * Overrides must call doHarvest. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        
        world.setBlockToAir(pos);
        this.doHarvest(world, pos, state, player, te, tool);
    }
    
    /** Does standard harvesting behaviour, exhaustion and drops.
     * Does not change any blocks. */
    public final void doHarvest(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, TileEntity te, ItemStack tool) {
        
        player.addExhaustion(0.005F);

        for (ItemStack stack : this.getDrops(world, pos, state, 0,
                te, tool, player)) {
            
            spawnAsEntity(world, pos, stack);
        }
    }
    
    /** @return List of drops using te, tool, and player if present.
     * Default implementation is one of the ItemPlacing.
     * Called with null player when the block is destroyed not harvested. */
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        return Lists.newArrayList(new ItemStack(this.item));
    }
    
    /** Overriden to call the tool-sensitive version in harvestBlock. */
    @Override
    public final List<ItemStack> getDrops(IBlockAccess world,
            BlockPos pos, IBlockState state, int fortune) {
        
        return this.getDrops(world, pos, state, fortune,
                world.getTileEntity(pos), null, null);
    }
    
    /* Checks whether this block is still in a valid position, breaks if not. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.isValid(world, pos, null, true, state, null)) {
            
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    /** Translates and sends the message to the player if config and nonnull. */
    protected static void message(EntityPlayer player, String unlocalised) {
        
        if (player != null && GeoConfig.buildMessages) {
            
            player.sendMessage(new TextComponentTranslation(unlocalised));
        }
    }
}
