/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.ArrayList;
import java.util.function.Supplier;
import jayavery.geomastery.blocks.BlockBeam;
import jayavery.geomastery.blocks.BlockBuilding;
import jayavery.geomastery.blocks.BlockDoor;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.tileentities.TEBeam;
import jayavery.geomastery.tileentities.TEBeam.EnumFloor;
import jayavery.geomastery.tileentities.TEBeam.EnumPartBeam;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Items which place blocks more complex than {@code ItemBlock}. */
public abstract class ItemBlockplacer extends ItemSimple {
    
    /** Sound type of using this item successfully. */
    protected final SoundType sound;
    
    public ItemBlockplacer(String name, int stackSize,
            CreativeTabs tab, SoundType sound) {
        
        super(name, stackSize, tab);
        this.sound = sound;
    }
    
    /** Attempts to place the blocks or structure for this item.
     * @return Whether placement was successful. */
    protected abstract boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing, ItemStack stack);
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos targetPos, EnumHand hand, EnumFacing targetSide,
            float x, float y, float z) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        int intFacing = MathHelper.floor(player.rotationYaw* 4.0F /
                360.0F + 0.5D) & 3;
        EnumFacing placeFacing = EnumFacing.getHorizontal(intFacing);
        
        if (this.place(world, targetPos, targetSide, placeFacing, stack)) {
            
            world.playSound(player, targetPos, this.sound.getPlaceSound(),
                    SoundCategory.BLOCKS, (this.sound.getVolume() + 1F) / 2F,
                    this.sound.getPitch() * 0.8F);
            
            if (!player.capabilities.isCreativeMode) {
                
                stack.shrink(1);
                ContainerInventory.updateHand(player, hand);
            }
            
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }
    
    public static class Multipart <E extends Enum<E> & IMultipart>
            extends ItemBlockplacer {
        
        /** Part of the structure placed at the target block. */
        private final E placePart;
        
        public Multipart(String name, E placePart, SoundType sound) {
            
            super(name, 1, CreativeTabs.DECORATIONS, sound);
            this.placePart = placePart;
        }
        
        @Override
        protected boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing,
                ItemStack stack) {
            
            return this.placePart.buildStructure(world,
                    targetPos.offset(targetSide), placeFacing);
        }
    }
    
    public static class Doubling <B extends BlockBuilding & IDoublingBlock>
            extends ItemBlockplacer {
        
        public final Supplier<B> single;
        public final Supplier<B> duble;
        
        public Doubling(String name, int stackSize, SoundType sound,
                Supplier<B> single, Supplier<B> duble) {
            
            super(name, stackSize, CreativeTabs.BUILDING_BLOCKS, sound);
            this.single = single;
            this.duble = duble; 
        }
        
        @Override
        public boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing,
                ItemStack stack) {
            
            IBlockState targetState = world.getBlockState(targetPos);
            Block targetBlock = targetState.getBlock();
            
            B single = this.single.get();
            B duble = this.duble.get();

            if (targetBlock == single && single
                    .shouldDouble(targetState.getActualState(world, targetPos),
                    targetSide) && duble.isValid(world, targetPos)) {
                
                world.setBlockState(targetPos, duble.getDefaultState());
           
            } else {
                
                targetPos = targetPos.offset(targetSide);
                targetState = world.getBlockState(targetPos);
                targetBlock = targetState.getBlock();
                
                if (!targetBlock.isReplaceable(world, targetPos) ||
                        !single.isValid(world, targetPos)) {
                    
                    return false;
                }
                
                world.setBlockState(targetPos, single.getDefaultState());
            }
            
            return true;
        }
    }
    
    public static class Heaping extends ItemBlockplacer {
        
        private final Supplier<Block> block;
        
        public Heaping(String name, SoundType sound, Supplier<Block> block) {
            
            super(name, 1, CreativeTabs.BUILDING_BLOCKS, sound);
            this.block = block;
        }
        
        @Override
        public boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing,
                ItemStack stack) {
            
            BlockPos posPlace = targetPos.offset(targetSide);
            Block blockTarget = world.getBlockState(posPlace).getBlock();
            
            if (!blockTarget.isReplaceable(world, posPlace)) {
                
                return false;
            }
            
            BlockPos posBelow = posPlace.down();
            IBlockState stateBelow = world.getBlockState(posBelow);
            
            if (!BlockWeight.getWeight(stateBelow.getBlock())
                    .canSupport(BlockWeight.MEDIUM)) {
                
                return false;
            }
            
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
                BlockPos posOffset = posBelow.offset(facing);
                IBlockState stateOffset = world.getBlockState(posOffset);
                
                if (!BlockWeight.getWeight(stateOffset.getBlock())
                        .canSupport(BlockWeight.MEDIUM)) {
                    
                    return false;
                }
            }
            
            world.setBlockState(posPlace, this.block.get().getDefaultState());
            return true;
        }
    }
    
    public static class Door extends ItemBlockplacer {
        
        /** The door block of this item. */
        private Supplier<BlockDoor> block;
        
        public Door(Supplier<BlockDoor> block, String name) {
            
            super(name, 1, CreativeTabs.DECORATIONS, SoundType.WOOD);
            this.block = block;
        }

        @Override
        protected boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing,
                ItemStack stack) {

            BlockPos bottomPos = targetPos.offset(targetSide);
            Block bottomBlock = world.getBlockState(bottomPos).getBlock();
            BlockPos topPos = bottomPos.up();
            Block topBlock = world.getBlockState(topPos).getBlock();
                    
            if (!bottomBlock.isReplaceable(world, bottomPos) ||
                    !topBlock.isReplaceable(world, topPos) ||
                    !this.block.get().isValid(world, bottomPos)) {
            
                return false;
            }
            
            IBlockState placeState = this.block.get().getDefaultState()
                    .withProperty(BlockDoor.FACING, placeFacing)
                    .withProperty(BlockDoor.OPEN, false)
                    .withProperty(BlockDoor.TOP, false);
            world.setBlockState(bottomPos, placeState);
            world.setBlockState(topPos, placeState
                    .withProperty(BlockDoor.TOP, true));
            
            return true;
        }
    }
    
    public static class Beam extends ItemBlockplacer {
        
        /** Minimum length this item's beam structure can span. */
        private int minLength;
        /** Maximm length this item's beam structure can span. */
        public int maxLength;
        /** Beam block of this item's structure. */
        private Supplier<BlockBeam> block;

        public Beam(String name, Supplier<BlockBeam> block,
                int minLength, int maxLength) {
            
            super(name, 1, CreativeTabs.BUILDING_BLOCKS, SoundType.WOOD);
            this.minLength = minLength;
            this.maxLength = maxLength;
            this.block = block;
        }
        
        /** Builds a beam structure. */
        @Override
        protected boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing,
                ItemStack stack) {
                      
            // Get positions
            BlockPos posBack = targetPos.offset(targetSide);
            BlockPos posMiddle = posBack.offset(targetSide);
            ArrayList<BlockPos> middles = new ArrayList<BlockPos>();
            int length = 2;
            
            while (length <= this.maxLength &&
                    world.getBlockState(posMiddle).getBlock()
                    .isReplaceable(world, posMiddle) &&
                    world.getBlockState(posMiddle.offset(targetSide)).getBlock()
                    .isReplaceable(world, posMiddle.offset(targetSide))) {

                middles.add(posMiddle);
                posMiddle = posMiddle.offset(targetSide);
                length++;
            }
            
            BlockPos posFront = posMiddle;
            
            Block frontEnd = world.getBlockState(posFront.offset(targetSide))
                    .getBlock();
            Block backEnd = world.getBlockState(targetPos).getBlock();
            
            // Check validity of supports and length
            
            BlockBeam block = this.block.get();
            
            boolean frontValid = BlockWeight.getWeight(frontEnd)
                    .canSupport(block.getWeight());
            boolean backValid = BlockWeight.getWeight(backEnd)
                    .canSupport(block.getWeight());
            
            if (length < this.minLength || length > this.maxLength ||
                    !frontValid || !backValid) {

                return false;
            }   
            
            // Check ends replaceable
            IBlockState stateBack = world.getBlockState(posBack);
            Block blockBack = stateBack.getBlock();
            boolean replaceableBack = blockBack.isReplaceable(world, posBack);
            
            IBlockState stateFront = world.getBlockState(posFront);
            Block blockFront = stateFront.getBlock();
            boolean replaceableFront = blockFront.isReplaceable(world, posFront);
            
            if (!replaceableBack || !replaceableFront) {

                return false;
            }
            
            // Place blocks
            IBlockState state = block.getDefaultState();
            
            world.setBlockState(posBack, state);
            world.setBlockState(posFront, state);
            
            for (BlockPos mid : middles) {
                
                world.setBlockState(mid, state);
            }
            
            // Apply TE states
            ((TEBeam) world.getTileEntity(posBack))
                    .setState(targetSide, EnumPartBeam.BACK, this);
            ((TEBeam) world.getTileEntity(posFront))
                    .setState(targetSide, EnumPartBeam.FRONT, this);
            
            for (BlockPos mid : middles) {
                
                ((TEBeam) world.getTileEntity(mid))
                        .setState(targetSide, EnumPartBeam.MIDDLE, this);
            }
            
            return true;
        }
    }
    
    public static class Floor extends ItemBlockplacer {
        
        /** This item's floor type. */
        private EnumFloor floor;

        public Floor(int stackSize, EnumFloor floor) {
            
            super("floor_" + floor.getName(), stackSize,
                    CreativeTabs.DECORATIONS, SoundType.WOOD);
            this.floor = floor;
        }
        
        /** Attempts to apply this floor to the targested block. */
        @Override
        protected boolean place(World world, BlockPos targetPos,
                EnumFacing targetSide, EnumFacing placeFacing,
                ItemStack stack) {
            
            Block block = world.getBlockState(targetPos).getBlock();
            TileEntity tileEntity = world.getTileEntity(targetPos);
            
            if (!(block instanceof BlockBeam) ||
                    !(tileEntity instanceof TEBeam)) {

                return false;
            }
            
            TEBeam tileBeam = (TEBeam) tileEntity;
            
            if (tileBeam.getFloor() != EnumFloor.NONE) {

                return false;
            }
            
            return tileBeam.applyFloor(this.floor);
        }
    }
}
