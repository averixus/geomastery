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

public class TEBeam extends TileEntity {
    
    protected EnumFacing facing;
    protected EnumPartBeam part;
    protected EnumFloor floor;
    protected Item item;
    
    public void setState(EnumFacing facing, EnumPartBeam part, Item item) {
        
        this.facing = facing;
        this.part = part;
        this.item = item;
        this.floor = EnumFloor.NONE;
    }
    
    public boolean applyFloor(EnumFloor floor) {
        
        if (this.floor == EnumFloor.NONE && floor != EnumFloor.NONE) {
            
            this.floor = floor;
            this.sendFloorUpdate();
            return true;
        }
        
        return false;
    }
    
    public void removeFloor() {
        
        this.floor = EnumFloor.NONE;
        this.sendFloorUpdate();
    }
    
    protected void sendFloorUpdate() {
        
        if (!this.world.isRemote) {
            
            ModPackets.INSTANCE
                    .sendToAll(new FloorUpdateClient(this.floor,this.pos));
        }
    }
    
    public EnumFacing getFacing() {
        
        return this.facing;
    }
    
    public EnumPartBeam getPart() {
        
        return this.part;
    }
    
    public EnumFloor getFloor() {
        
        return this.floor;
    }
    
    public boolean hasSideConnection(EnumFacing facing) {
                
        if (facing == this.facing || facing == this.facing.getOpposite() || facing == EnumFacing.DOWN || facing == EnumFacing.UP) {
            
            return false;
        }
        
        Block block = this.world.getBlockState(this.pos.offset(facing)).getBlock();
        return block instanceof BlockWall;
    }
    
    public boolean hasEndConnection(EnumFacing facing) {
        
        if (facing != this.facing && facing != this.facing.getOpposite()) {
            
            return false;
        }
        
        Block block = this.world.getBlockState(this.pos.offset(facing)).getBlock();
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
        
        public boolean shouldDrop() {
            System.out.println("should drop? " + this + " " + (this==BACK));
            return this == BACK;
        }
    }
    
    public enum EnumFloor implements IStringSerializable {
        
        NONE("none", () -> Items.AIR), POLE("pole", () -> ModItems.floorPole), WOOD("wood", () -> ModItems.floorWood);
        
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
        
        public Item getItem() {
            
            return this.item.get();
        }
    }
}
