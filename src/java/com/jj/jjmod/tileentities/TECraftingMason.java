package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;

import com.jj.jjmod.blocks.BlockCraftingMason;
import com.jj.jjmod.init.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

/** TileEntity for Mason crafting block. */
public class TECraftingMason extends TileEntity {

    private EnumFacing facing;
    private EnumPartMason part;

    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartMason part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this Mason block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartMason state of this Mason block. */
    public EnumPartMason getPart() {

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
        this.part = EnumPartMason.values()[compound.getInteger("part")];
    }

    /** Require to update rendering on the Client. */
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    /** Require to update rendering on the Client. */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    /** Require to update rendering on the Client. */
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }

    /** Enum defining parts of the whole Mason structure. */
    public enum EnumPartMason implements IStringSerializable {

        FM("fm", false),
        FL("fl", false),
        BM("bm", false),
        BR("br", false),
        FR("fr", false);

        private final String name;
        private final boolean isFlat;

        private EnumPartMason(String name, boolean isFlat) {

            this.name = name;
            this.isFlat = isFlat;
        }

        @Override
        public String toString() {

            return this.name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        /** @return Whether this Part has the flat bounding box. */
        public boolean isFlat() {
            
            return this.isFlat;
        }
    }
}
