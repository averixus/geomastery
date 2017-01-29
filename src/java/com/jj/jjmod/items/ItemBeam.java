package com.jj.jjmod.items;

import java.util.ArrayList;
import com.jj.jjmod.blocks.BlockWall;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.tileentities.TEBeam;
import com.jj.jjmod.tileentities.TEBeam.EnumPartBeam;
import com.jj.jjmod.utilities.IBuildingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBeam extends ItemNew {
    
    public int minLength;
    public int maxLength;

    public ItemBeam(String name, int minLength, int maxLength) {
        
        super(name, 1, CreativeTabs.BUILDING_BLOCKS);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
                
        if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
            
            return EnumActionResult.FAIL;
        }
      
        // Get positions
        BlockPos posBack = pos.offset(side);
        System.out.println("pos back is " + posBack);
        ArrayList<BlockPos> middles = new ArrayList<BlockPos>();
        BlockPos posMiddle = posBack.offset(side);
        int length = 2;
        
        while (length <= this.maxLength &&
                world.getBlockState(posMiddle).getBlock()
                .isReplaceable(world, posMiddle) &&
                world.getBlockState(posMiddle.offset(side)).getBlock()
                .isReplaceable(world, posMiddle.offset(side))) {
            System.out.println("adding middle " + posMiddle);
            middles.add(posMiddle);
            posMiddle = posMiddle.offset(side);
            length++;
        }
        
        BlockPos posFront = posMiddle;
        
        Block frontEnd = world.getBlockState(posFront.offset(side)).getBlock();
        Block backEnd = world.getBlockState(pos).getBlock();
        System.out.println("pos front is " + posFront);
        
        // Check validity
        boolean frontValid = ModBlocks.HEAVY.contains(frontEnd);
        boolean backValid = ModBlocks.HEAVY.contains(backEnd);
        
        if (frontEnd instanceof IBuildingBlock) {
            
            IBuildingBlock frontBuilding = (IBuildingBlock) frontEnd;
            frontValid = frontBuilding.supportsBeam();
        }
        
        if (backEnd instanceof IBuildingBlock) {
            
            IBuildingBlock backBuilding = (IBuildingBlock) backEnd;
            backValid = backBuilding.supportsBeam();
        }
        if (length > this.maxLength || !frontValid || !backValid) {
            
            System.out.println("length " + length + " too long OR");
            System.out.println("block " + frontEnd + " at " + posFront.offset(side) + " is not wall or fence");
            return EnumActionResult.FAIL;
        }
        
        
        // Check ends replaceable
        IBlockState stateBack = world.getBlockState(posBack);
        Block blockBack = stateBack.getBlock();
        boolean replaceableBack = blockBack.isReplaceable(world, posBack);
        
        IBlockState stateFront = world.getBlockState(posFront);
        Block blockFront = stateFront.getBlock();
        boolean replaceableFront = blockFront.isReplaceable(world, posFront);
        
        if (!replaceableBack || !replaceableFront) {
            System.out.println("start or end not replaceable");
            return EnumActionResult.FAIL;
        }
        
        // Place blocks
        IBlockState state = ModBlocks.beam.getDefaultState();
        
        world.setBlockState(posBack, state);
        world.setBlockState(posFront, state);
        
        for (BlockPos mid : middles) {
            
            world.setBlockState(mid, state);
        }
        
        // Apply TEs
        ((TEBeam) world.getTileEntity(posBack)).setState(side, EnumPartBeam.BACK, this);
        ((TEBeam) world.getTileEntity(posFront)).setState(side, EnumPartBeam.FRONT, this);
        
        for (BlockPos mid : middles) {
            
            ((TEBeam) world.getTileEntity(mid)).setState(side, EnumPartBeam.MIDDLE, this);
        }
        
        // Use item
        world.playSound(null, posBack, SoundType.WOOD.getPlaceSound(),
                SoundCategory.BLOCKS,
                SoundType.WOOD.getVolume() + 1.0F / 2.0F,
                SoundType.WOOD.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            System.out.println("using item, before " + stack);
            stack.func_190918_g(1);
            System.out.println("after " + stack);
            ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
        }
        
        return EnumActionResult.SUCCESS;
    }
}
