package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.init.ModRecipes;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

/** TileEntity for stone furnace blocks. */
public class TEFurnaceStone extends TEFurnaceAbstract {

    /** This block's horizontal facing. */
    private EnumFacing facing;
    /** This block's part in the structure. */
    private EnumPartStone part;

    public TEFurnaceStone() {

        super(ModRecipes.STONE, 6);
    }

    /** Sets this stone furnace block to the given information. */
    public void setState(EnumFacing facing, EnumPartStone part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this stone furnace block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartStone state of this stone furnace block. */
    public EnumPartStone getPart() {

        return this.part;
    }

    /** @return The position of the master
     * block for this stone furnace structure. */
    public BlockPos getMaster() {

        switch (this.part) {

            case BL: {

                return this.pos;
            }
            
            case BM: {

                return this.pos.offset(this.facing.rotateY().getOpposite());
            }
            
            case BR: {

                return this.pos.offset(this.facing.rotateY().getOpposite(), 2);
            }
            
            case TL: {

                return this.pos.down();
            }
            
            case TM: {

                return this.pos.offset(this.facing.rotateY()
                        .getOpposite()).down();
            }
            
            case TR: {

                return this.pos.offset(this.facing.rotateY()
                        .getOpposite(), 2).down();
            }
            
            default: {

                return this.pos;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing.getHorizontalIndex());
        compound.setInteger("part", this.part.ordinal());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.facing = EnumFacing.getHorizontal(compound.getInteger("facing"));
        this.part = EnumPartStone.values()[compound.getInteger("part")];
    }
    
    /** Required to update rendering on the Client. */
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    /** Required to update rendering on the Client. */
   @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

   /** Required to update rendering on the Client. */
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }

    /** Enum defining parts of the whole stone furnace structure. */
    public enum EnumPartStone implements IStringSerializable, IMultipart {

        BL("bl"),
        BM("bm"),
        BR("br"),
        TL("tl"),
        TM("tm"),
        TR("tr");

        private final String name;

        private EnumPartStone(String name) {

            this.name = name;
        }
        
        @Override
        public boolean shouldDrop() {
            
            return this == BL;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
