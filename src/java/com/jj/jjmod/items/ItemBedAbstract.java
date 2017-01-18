package com.jj.jjmod.items;

import com.jj.jjmod.container.ContainerInventory;
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

public abstract class ItemBedAbstract extends ItemNew {

    public Block bedBlock;

    public ItemBedAbstract(String name, Block bedBlock) {

        super(name, 1, CreativeTabs.DECORATIONS);
        this.bedBlock = bedBlock;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos posFoot, EnumHand hand, EnumFacing facing,
            float x, float y, float z) {
        
        ItemStack stack = player.getActiveItemStack();

        if (world.isRemote) {

            return EnumActionResult.SUCCESS;

        } else if (facing != EnumFacing.UP) {

            return EnumActionResult.FAIL;

        } else {

            // Calculate positions
            IBlockState state = world.getBlockState(posFoot);
            Block block = state.getBlock();
            boolean footReplaceable = block.isReplaceable(world, posFoot);

            if (!footReplaceable) {
                
                posFoot = posFoot.up();
            }

            int i = MathHelper.floor_double(
                    (double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing enumFacing = EnumFacing.getHorizontal(i);
            BlockPos posHead = posFoot.offset(enumFacing);

            if (!player.canPlayerEdit(posFoot, facing, stack) || !player
                    .canPlayerEdit(posHead, facing, stack)) {
                
                return EnumActionResult.FAIL;
            }

            // Check positions are allowed
            boolean allowedFoot = footReplaceable ||
                    world.isAirBlock(posFoot);
            boolean allowedHead = world.isAirBlock(posHead) ||
                    world.getBlockState(posHead).getBlock()
                    .isReplaceable(world, posHead);

            // Check both blocks underneath are solid
            boolean solidFoot = world.getBlockState(posFoot.down())
                    .isSideSolid(world, posFoot.down(), EnumFacing.UP);
            boolean solidHead = world.getBlockState(posHead.down())
                    .isSideSolid(world, posHead.down(), EnumFacing.UP);

            if (!allowedFoot || !allowedHead || !solidFoot || !solidHead) {
                
                return EnumActionResult.FAIL;
            }

            // Place the bed
            int damage = stack.getItemDamage();
            placeBed(world, posFoot, posHead, enumFacing, damage);
            world.playSound((EntityPlayer) null, posFoot,
                    SoundType.CLOTH.getPlaceSound(), SoundCategory.BLOCKS,
                    (SoundType.CLOTH.getVolume() + 1.0F) / 2.0F,
                    SoundType.CLOTH.getPitch() * 0.8F);

            stack.func_190918_g(1);

            if (stack.func_190916_E() == 0) {

                stack = null;
            }

            ((ContainerInventory) player.inventoryContainer)
                    .sendUpdateHighlight();
            return EnumActionResult.SUCCESS;
        }
    }

    public abstract void placeBed(World world, BlockPos foot, BlockPos head,
            EnumFacing facing, int damage);
}
