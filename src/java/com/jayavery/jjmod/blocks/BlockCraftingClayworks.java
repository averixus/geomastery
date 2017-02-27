package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IMultipart;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Clayworks crafting block. */
public class BlockCraftingClayworks extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartClayworks> PART = PropertyEnum
            .<EnumPartClayworks>create("part", EnumPartClayworks.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingClayworks() {

        super("crafting_clayworks", BlockMaterial.WOOD_HANDHARVESTABLE,
                5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (state.getValue(PART).shouldDrop()) {

            spawnAsEntity(world, pos,
                    new ItemStack(ModItems.craftingClayworks));
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return null;
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        EnumFacing facing = state.getValue(FACING);
        EnumPartClayworks part = state.getValue(PART);
        boolean broken = false;

        switch (part) {

            case FR: {

                broken = world.getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;
                break;
            }

            case FL: {

                broken = world.getBlockState(pos.offset(facing))
                        .getBlock() != this;
                break;
            }

            case BL: {

                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }

            case BR: {

                broken = world.getBlockState(pos.offset(facing.getOpposite()))
                        .getBlock() != this;
                break;
            }
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos,
                        new ItemStack(ModItems.craftingClayworks));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        EnumPartClayworks enumPart = state.getValue(PART);
        
        switch (enumPart) {
            
            case BR:
            case BL:
            case FL:
                return TWELVE;
            
            case FR: 
                return CENTRE_FOUR;
            
            default: 
                return FULL_BLOCK_AABB;
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        EnumPartClayworks enumPart = state.getValue(PART);
        return enumPart == EnumPartClayworks.BL ||
                enumPart == EnumPartClayworks.BR ? TWELVE : NULL_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();
        i = i | (4 * state.getValue(PART).ordinal());

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing facing = EnumFacing.getHorizontal(meta);
        IBlockState state = this.getDefaultState();
        int partOrdinal = meta / 4;

        state = state.withProperty(FACING, facing);
        state = state.withProperty(PART,
                EnumPartClayworks.values()[partOrdinal]);

        return state;
    }

    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {

        if (!world.isRemote) {
            
            player.openGui(Main.instance, GuiList.CLAYWORKS.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }

    /** Enum defining parts of the whole Clayworks structure. */
    public enum EnumPartClayworks implements IStringSerializable, IMultipart {

        FR("fr"),
        FL("fl"),
        BL("bl"),
        BR("br");

        private final String name;

        private EnumPartClayworks(String name) {

            this.name = name;
        }
        
        @Override
        public boolean shouldDrop() {
            
            return this == FR;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
