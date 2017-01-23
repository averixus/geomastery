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

public class TECraftingForge extends TileEntity {

    protected int facing;
    protected int part;

    public void setState(int facing, EnumPartForge part) {

        this.facing = facing;
        this.part = part.ordinal();
    }

    public EnumFacing getFacing() {

        return EnumFacing.getHorizontal(this.facing);
    }

    public EnumPartForge getPart() {

        return EnumPartForge.values()[this.part];
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

    public enum EnumPartForge implements IStringSerializable {

        FM("fm", true),
        FL("fl", false),
        BL("bl", false),
        BM("bm", false),
        BR("br", false),
        FR("fr", true);

        private final String name;
        private final boolean isFlat;

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
        
        public boolean isFlat() {
            
            return this.isFlat;
        }
    }
}
