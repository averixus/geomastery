package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.utilities.IMultipart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

/** TileEntity for clay furnace. */
public class TEFurnaceClay extends TEFurnaceAbstract {

    /** This block's horizontal facing. */
    private EnumFacing facing;
    /** This block's part in the structure. */
    private EnumPartClay part;

    public TEFurnaceClay() {

        super(ModRecipes.CLAY, 4);
    }
    
    /** Sets this clay furnace to the given state information. */
    public void setState(EnumFacing facing, EnumPartClay part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this clay furnace block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartClay state of this clay furnace block. */
    public EnumPartClay getPart() {

        return this.part;
    }

    /** @return The position of the master block of this structure. */
    public BlockPos getMaster() {
        
        switch (this.part) {

            case BL: {

                return this.pos;
            }
            
            case BR: {

                return this.pos.offset(this.facing.rotateY().getOpposite());
            }
            
            case TL: {

                return this.pos.down();
            }
            
            case TR: {

                return this.pos.offset(this.facing.rotateY()
                        .getOpposite()).down();
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
        this.part = EnumPartClay.values()[compound.getInteger("part")];
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

    /** Enum defining parts of the clay furnace structure. */
    public enum EnumPartClay implements IStringSerializable, IMultipart {

        BL("bl"),
        BR("br"),
        TL("tl"),
        TR("tr");

        private final String name;

        private EnumPartClay(String name) {

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
