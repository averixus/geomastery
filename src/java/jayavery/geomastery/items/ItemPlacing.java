/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.List;
import jayavery.geomastery.blocks.BlockBuildingAbstract;
import jayavery.geomastery.main.GeoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Advanced replacement for ItemBlock. */
public abstract class ItemPlacing extends Item {

    /** Constructor using non-owned block. */
    public ItemPlacing(String name, int stackSize, CreativeTabs tab) {
        
        ItemSimple.setupItem(this, name, stackSize, tab);
    }
    
    /** Constructor using owned block. */
    public ItemPlacing(Block block, int stackSize, CreativeTabs tab) {
        
        this.setRegistryName(block.getRegistryName());
        this.setUnlocalizedName(block.getRegistryName().toString());
        this.setMaxStackSize(stackSize);
        this.setCreativeTab(tab);
    }
    
    /** Attempt to build the structure.
     * @return Whether it was built successfully. */
    protected abstract boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player);
    /** @return The sound to be made when the structure is built. */
    protected abstract SoundType getSoundType();
    /** Adds the block's building information to tooltip. */
    @Override @SideOnly(Side.CLIENT)
    public abstract void addInformation(ItemStack stack, World world,
            List<String> tooltip, ITooltipFlag advanced);

    // Tries to place the structure, play sound, use item
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos target, EnumHand hand, EnumFacing targetSide,
            float x, float y, float z) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        EnumFacing placeFacing = player.getHorizontalFacing();
                
        if (player.canPlayerEdit(target, placeFacing, stack) && this
                .place(world, target, targetSide, placeFacing, stack, player)) {
            
            SoundType sound = this.getSoundType();
            world.playSound(player, target, sound.getPlaceSound(),
                    SoundCategory.BLOCKS, (sound.getVolume() + 1F) / 2F,
                    sound.getPitch() * 0.8F);
            
            if (!player.capabilities.isCreativeMode) {
                
                stack.shrink(1);
            } 
            
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
    
    /** Translates and sends the message to the player if config and nonnull. */
    protected static void message(EntityPlayer player, String unlocalised) {
        
        if (player != null && GeoConfig.textVisual.buildMessages) {
            
            player.sendMessage(new TextComponentTranslation(unlocalised));
        }
    }
    
    /** Implementation using a BlockBuildingAbstract for all logic. */
    public static class Building extends ItemPlacing {
        
        /** This item's building block. */
        protected final BlockBuildingAbstract<? extends ItemPlacing> block;

        public Building(BlockBuildingAbstract<? extends ItemPlacing> block,
                int stackSize) {
            
            super(block, stackSize, CreativeTabs.BUILDING_BLOCKS);
            this.block = block;
        }

        @Override
        protected boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing, ItemStack stack,
                EntityPlayer player) {

            return this.block.place(world, targetPos, targetSide,
                    placeFacing, stack, player);
        }

        @Override
        protected SoundType getSoundType() {

            return this.block.getSoundType();
        }

        @Override
        public void addInformation(ItemStack stack, World world,
                List<String> tooltip, ITooltipFlag advanced) {

            this.block.addInformation(stack, world, tooltip, advanced);
        }
    }
}
