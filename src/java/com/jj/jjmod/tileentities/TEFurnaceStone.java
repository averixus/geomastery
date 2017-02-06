package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

public class TEFurnaceStone extends TEFurnaceAbstract {

    private int facing;
    private int part;

    public TEFurnaceStone() {

        super(ModRecipes.STONE);
    }

    public void setState(int facing, int part) {

        this.facing = facing;
        this.part = part;
    }

    public EnumFacing getFacing() {

        return EnumFacing.getHorizontal(this.facing);
    }

    public EnumPartStone getPart() {

        return EnumPartStone.values()[this.part];
    }

    public BlockPos getMaster() {

        EnumFacing enumFacing = EnumFacing.getHorizontal(this.facing);

        switch (this.part) {

            case 0: {

                return this.pos;
            }
            
            case 1: {

                return this.pos.offset(enumFacing.rotateY().getOpposite());
            }
            
            case 2: {

                return this.pos.offset(enumFacing.rotateY().getOpposite(), 2);
            }
            
            case 3: {

                return this.pos.down();
            }
            
            case 4: {

                return this.pos.offset(enumFacing.rotateY()
                        .getOpposite()).down();
            }
            
            case 5: {

                return this.pos.offset(enumFacing.rotateY()
                        .getOpposite(), 2).down();
            }
            
            default: {

                return null;
            }
        }
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

    @Override
    public int getCookTime(ItemStack stack) {

        // CONFIG furnaceStone item cook times

        return 300;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {

        return new ContainerFurnace(player, this.world, this);
    }

    public enum EnumPartStone implements IStringSerializable {

        BL("bl"),
        BM("bm"),
        BR("br"),
        TL("tl"),
        TM("tm"),
        TR("tr");

        private final String NAME;

        private EnumPartStone(String name) {

            this.NAME = name;
        }

        @Override
        public String toString() {

            return this.NAME;
        }

        @Override
        public String getName() {

            return this.NAME;
        }
    }
}
