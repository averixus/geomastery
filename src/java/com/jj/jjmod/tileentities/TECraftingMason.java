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

public class TECraftingMason extends TileEntity {

    private int facing;
    private int part;

    public void setState(int facing, EnumPartMason part) {

        this.facing = facing;
        this.part = part.ordinal();
    }

    public EnumFacing getFacing() {

        return EnumFacing.getHorizontal(this.facing);
    }

    public EnumPartMason getPart() {

        return EnumPartMason.values()[this.part];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing);
        compound.setInteger("part", this.part);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.facing = compound.getInteger("facing");
        this.part = compound.getInteger("part");
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

    public enum EnumPartMason implements IStringSerializable {

        FM("fm", false),
        FL("fl", false),
        BM("bm", false),
        BR("br", false),
        FR("fr", false);

        private final String NAME;
        private final boolean isFlat;

        private EnumPartMason(String name, boolean isFlat) {

            this.NAME = name;
            this.isFlat = isFlat;
        }

        @Override
        public String toString() {

            return this.NAME;
        }

        @Override
        public String getName() {

            return this.NAME;
        }
        
        public boolean isFlat() {
            
            return this.isFlat;
        }
    }
}
