package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;

import com.jj.jjmod.blocks.BlockCraftingForge;
import com.jj.jjmod.init.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

/** TileEntity for Forge crafting block. */
public class TECraftingForge extends TileEntity {

    private EnumFacing facing;
    private EnumPartForge part;

    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartForge part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this Forge block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartForge state of this Forge block. */
    public EnumPartForge getPart() {

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
        this.part = EnumPartForge.values()[compound.getInteger("part")];
    }

    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }

    /** Enum defining parts of the whole Forge structure. */
    public enum EnumPartForge implements IStringSerializable {

        FM("fm", true),
        FL("fl", false),
        BL("bl", false),
        BM("bm", false),
        BR("br", false),
        FR("fr", true);

        private String name;
        private boolean isFlat;

        private EnumPartForge(String name, boolean isFlat) {

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
