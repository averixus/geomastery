package com.jj.jjmod.tileentities;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockBeam.EnumAxis;
import com.jj.jjmod.blocks.BlockWall;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.packets.FloorUpdateClient;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

/** TileEntity for Beam block. */
public class TEBeam extends TileEntity {
    
    private EnumFacing facing;
    private EnumPartBeam part;
    private EnumFloor floor;
    private Item item;
    
    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartBeam part, Item item) {
        
        this.facing = facing;
        this.part = part;
        this.item = item;
        this.floor = EnumFloor.NONE;
    }
    
    /** Attempts to apply the given floor to this Beam block.
     * @return Whether the floor is successfully applied. */
    public boolean applyFloor(EnumFloor floor) {
        
        if (this.floor == EnumFloor.NONE && floor != EnumFloor.NONE) {
            
            this.floor = floor;
            this.sendFloorUpdate();
            return true;
        }
        
        return false;
    }
    
    /** Sets the floor of this Beam block to NONE. */
    public void removeFloor() {
        
        this.floor = EnumFloor.NONE;
        this.sendFloorUpdate();
    }
    
    /** Sends a packet to update the floor of this Beam block on the Client. */
    private void sendFloorUpdate() {
        
        if (!this.world.isRemote) {
            
            ModPackets.NETWORK
                    .sendToAll(new FloorUpdateClient(this.floor,this.pos));
        }
    }
    
    /** @return The EnumFacing state of this Beam block. */
    public EnumFacing getFacing() {
        
        return this.facing;
    }
    
    /** @return the EnumPartBeam state of this Beam block. */
    public EnumPartBeam getPart() {
        
        return this.part;
    }
    
    /** @return The EnumFloor state of this Beam block. */
    public EnumFloor getFloor() {
        
        return this.floor;
    }
    
    /** Checks whether the floor on this Beam block needs to
      * extend sideways in the given direction.
      * @return Whether or not the floor extends sideways. */
    public boolean hasSideConnection(EnumFacing facing) {
                
        if (this.facing == null || facing == this.facing || facing == this.facing.getOpposite() ||
                facing == EnumFacing.DOWN || facing == EnumFacing.UP) {
            
            return false;
        }
        
        Block block = this.world.getBlockState(this.pos.offset(facing))
                .getBlock();
        return block instanceof BlockWall;
    }
    
    /** Checks whether the floor on this Beam block needs to
     * extend forwards/backwards in the given direction.
     * @return Whether or not the floor extends end-ways. */
    public boolean hasEndConnection(EnumFacing facing) {
        
        if (this.facing == null || facing != this.facing && facing != this.facing.getOpposite()) {
            
            return false;
        }
        
        Block block = this.world.getBlockState(this.pos.offset(facing))
                .getBlock();
        return block instanceof BlockWall;
    }
    
    public Item getItem() {
        
        return this.item;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing.getHorizontalIndex());
        compound.setInteger("part", this.part.ordinal());
        compound.setInteger("floor", this.floor.ordinal());
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.facing = EnumFacing.getHorizontal(compound.getInteger("facing"));
        this.part = EnumPartBeam.values()[compound.getInteger("part")];
        this.floor = EnumFloor.values()[compound.getInteger("floor")];
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

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
    
    /** Enum defining parts of the whole Beam structure. */
    public enum EnumPartBeam implements IStringSerializable {
        
        BACK("start"), MIDDLE("middle"), FRONT("end");
        
        private String name;
        
        private EnumPartBeam(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return Whether this part drops a Beam Item. */
        public boolean shouldDrop() {

            return this == BACK;
        }
    }
    
    /** Enum defining types of floor a Beam block can have. */
    public enum EnumFloor implements IStringSerializable {
        
        NONE("none", () -> Items.AIR),
        POLE("pole", () -> ModItems.floorPole),
        WOOD("wood", () -> ModItems.floorWood);
        
        private String name;
        private Supplier<Item> item;
        
        private EnumFloor(String name, Supplier<Item> item) {
            
            this.name = name;
            this.item = item;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return The Ttem form of this Floor. */
        public Item getItem() {
            
            return this.item.get();
        }
    }
}
