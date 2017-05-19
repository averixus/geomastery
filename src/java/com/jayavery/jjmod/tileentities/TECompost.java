package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.init.ModPackets;
import com.jayavery.jjmod.items.ItemEdible;
import com.jayavery.jjmod.packets.CompostPacketClient;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;

public class TECompost extends TEContainerAbstract implements ITickable {

    /** Total number of ticks to produce each output. */
    private static final int COMPOST_EACH = 6/*000*/;
    
    /** Ticks until next output is produced. */
    private int compostLeft = COMPOST_EACH;
    /** Balance of nitrogen (negative) to carbon (positive). */
    private int balance = 0;
    /** This compost heap's output stack size. */
    private int output = 0;
    /** This compost heap's input fullness. */
    private int input = 0;

    @Override
    public void dropItems() {

        this.dropItem(this.getOutput());
    }
    
    public static boolean isNitrogen(ItemStack stack) {
        
        Item item = stack.getItem();
        return item instanceof ItemEdible || item == ModItems.wool ||
                item == Items.BONE;
    }
    
    public static boolean isCarbon(ItemStack stack) {
        
        Item item = stack.getItem();
        return item == ModItems.leaves || item == Items.STICK ||
                item == ModItems.log || item == ModItems.pole ||
                item == ModItems.thicklog || item == Items.REEDS;
    }
    
    /** Adds this stack to affect the compost balance. */
    public void addInput(ItemStack stack) {
        
        int count = stack.getCount();
        this.input += count;
        
        if (isNitrogen(stack)) {
            
            this.balance -= count;
            
        } else if (isCarbon(stack)) {
            
            this.balance += count;
        }
    }
    
    /** Reduce the output stack size but the given amount. */
    public void reduceOutput(int amount) {
        
        this.output -= amount;
    }
    
    /** @return The ItemStack in the output slot. */
    public ItemStack getOutput() {
        
        int grade;
        int absBalance = Math.abs(this.balance);
        
        if (absBalance <= 2) {
            
            grade = 1;
            
        } else if (absBalance <= 12) {
            
            grade = 2;
            
        } else if (absBalance <= 27) {
            
            grade = 3;
            
        } else if (absBalance <= 47) {
            
            grade = 4;
            
        } else {
            
            grade = 5;
        }
        
        return new ItemStack(ModItems.compost, this.output, grade);
    }
    
    @Override
    public void update() {

        if (this.world.isRemote) {
            
            return;
        }
        
        if (this.output < 12 && this.input > 0) {
            
            this.compostLeft--;
            
            if (this.compostLeft <= 0) {
                
                this.compostLeft = COMPOST_EACH;
                this.output++;
                this.input--;
                this.markDirty();
                this.sendProgressPacket();
            }
            
        } else if (this.compostLeft < COMPOST_EACH) {
            
            this.compostLeft = COMPOST_EACH;
        }
    }
    
    /** Sets the given values, used for packets. */
    public void setValues(int input, int output, int balance) {
        
        this.input = input;
        this.output = output;
        this.balance = balance;
    }
    
    /** Sends a packet to update progress values on the client. */
    private void sendProgressPacket() {
        
        if (!this.world.isRemote) {
        
            ModPackets.NETWORK.sendToAll(new CompostPacketClient(this.input,
                    this.output, this.balance, this.pos));
        }
    }
    
    /** Required to update GUI on the client. */
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    /** Required to update GUI on the client. */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    /** Required to update GUI on the client. */
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        
        this.balance = compound.getInteger("balance");
        this.input = compound.getInteger("input");
        this.output = compound.getInteger("output");
        this.compostLeft = compound.getInteger("compostLeft");
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        
        compound.setInteger("balance", this.balance);
        compound.setInteger("input", this.input);
        compound.setInteger("output", this.output);
        compound.setInteger("compostLeft", this.compostLeft);
        
        return compound;
    }
}
