package com.jj.jjmod.tileentities;

import com.jj.jjmod.utilities.IMultipart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

/** TileEntity for woodworking crafting block. */
public class TECraftingWoodworking extends TileEntity {

    /** EnumFacing of this woodworking block. */
    private EnumFacing facing;
    /** Part of this woodworking block. */
    private EnumPartWoodworking part;

    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartWoodworking part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this woodworking block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartWoodworking state of this woodworking block. */
    public EnumPartWoodworking getPart() {

        return this.part;
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
        this.part = EnumPartWoodworking.values()[compound.getInteger("part")];

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


    /** Enum defining parts of the whole woodworking structure. */
    public enum EnumPartWoodworking implements IStringSerializable, IMultipart {

        FM("fm"),
        FL("fl"),
        BL("bl"),
        BM("bm"),
        BR("br"),
        FR("fr");

        private final String name;

        private EnumPartWoodworking(String name) {

            this.name = name;
        }
        
        @Override
        public boolean shouldDrop() {
            
            return this == FM;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
