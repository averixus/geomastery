package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockProperties;

/** Abstract superclass for bed items. */
public abstract class ItemBedAbstract extends ItemJj {

    /** The bed block this item places. */
    protected Block bedBlock;

    public ItemBedAbstract(String name, Block bedBlock) {

        super(name, 1, CreativeTabs.DECORATIONS);
        this.bedBlock = bedBlock;
    }
    
    /** Place this item's bed block and TE with damage if needed. */
    protected abstract void placeBed(World world, BlockPos foot, BlockPos head,
            EnumFacing facing, int usesLeft);

    /** Builds a bed structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
        
        if (side != EnumFacing.UP) {

            return EnumActionResult.FAIL;
        }

        // Calculate positions
        BlockPos posFoot = pos.offset(side);
        IBlockState state = world.getBlockState(posFoot);
        Block block = state.getBlock();

        int intFacing = MathHelper.floor((player.rotationYaw * 4.0F / 360.0F)
                + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos posHead = posFoot.offset(enumFacing);

        // Check positions are allowed
        boolean footReplaceable = block.isReplaceable(world, posFoot);
        boolean headReplaceable = world.getBlockState(posHead).getBlock()
                .isReplaceable(world, posHead);

        boolean solidFoot = world.getBlockState(posFoot.down())
                .isSideSolid(world, posFoot.down(), EnumFacing.UP);
        boolean solidHead = world.getBlockState(posHead.down())
                .isSideSolid(world, posHead.down(), EnumFacing.UP);

        if (!footReplaceable || !headReplaceable || !solidFoot || !solidHead) {
            
            return EnumActionResult.FAIL;
        }

        // Place the bed
        int usesLeft = stack.getMaxDamage() - stack.getItemDamage();
        placeBed(world, posFoot, posHead, enumFacing, usesLeft);
        
        // Use item
        world.playSound((EntityPlayer) null, posFoot,
                SoundType.CLOTH.getPlaceSound(), SoundCategory.BLOCKS,
                (SoundType.CLOTH.getVolume() + 1.0F) / 2.0F,
                SoundType.CLOTH.getPitch() * 0.8F);

        if (!player.capabilities.isCreativeMode) {
        
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
