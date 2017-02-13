package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;

import com.jj.jjmod.blocks.BlockCraftingForge;
import com.jj.jjmod.blocks.BlockCraftingWoodworking;
import com.jj.jjmod.init.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for Woodworking crafting block. */
public class TECraftingWoodworking extends TileEntity {

    private EnumFacing facing;
    private EnumPartWoodworking part;

    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartWoodworking part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this Woodworking block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartWoodworking state of this Woodworking block. */
    public EnumPartWoodworking getPart() {
        System.out.println("getting part " + this.part);
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


    /** Enum defining parts of the whole Woodworking structure. */
    public enum EnumPartWoodworking implements IStringSerializable {

        FM("fm", true),
        FL("fl", false),
        BL("bl", false),
        BM("bm", false),
        BR("br", false),
        FR("fr", true);

        private final String name;
        private final boolean isFlat;

        private EnumPartWoodworking(String name, boolean isFlat) {

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
