package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHoe extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet( new Block[]
            {Blocks.DIRT, Blocks.GRASS});

    public ItemHoe(String name, ToolMaterial material) {

        super(2, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel("hoe", 1);
        this.efficiencyOnProperMaterial = 0.25F;
    }

    @SuppressWarnings("incomplete-switch")
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn,
            World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {

        if (!playerIn.canPlayerEdit(pos.offset(facing), facing,
                stack)) {

            return EnumActionResult.FAIL;

        } else {

            int hook = net.minecraftforge.event.ForgeEventFactory
                    .onHoeUse(stack, playerIn, worldIn, pos);

            if (hook != 0) {

                return hook > 0 ? EnumActionResult.SUCCESS
                        : EnumActionResult.FAIL;
            }

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {

                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {

                    this.setBlock(stack, playerIn, worldIn, pos,
                            Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT) {

                    switch ((BlockDirt.DirtType) iblockstate
                            .getValue(BlockDirt.VARIANT)) {

                        case DIRT: {
                            this.setBlock(stack, playerIn, worldIn, pos,
                                    Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        }
                        case COARSE_DIRT: {
                            this.setBlock(stack, playerIn, worldIn, pos,
                                    Blocks.DIRT.getDefaultState()
                                    .withProperty(BlockDirt.VARIANT,
                                    BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                        }
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }

    protected void setBlock(ItemStack stack, EntityPlayer player,
            World worldIn, BlockPos pos, IBlockState state) {

        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!worldIn.isRemote) {

            worldIn.setBlockState(pos, state, 11);
            stack.damageItem(1, player);
        }
    }
}