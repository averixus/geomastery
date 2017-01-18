package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModBiomes;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.packets.TemperatureUpdateClient;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class DefaultCapTemperature implements ICapTemperature {
    
    public EntityPlayer player;
    public EnumTempIcon icon = EnumTempIcon.OK;
    public int timer = 0;
    protected int wetTimer = 0;
    
    public DefaultCapTemperature(EntityPlayer player) {
        
        this.player = player;
    }
    
    @Override
    public void update() {
        
        if (this.player.worldObj.isRemote) {
            
            return;
        }
        
        EnumTempIcon oldIcon = this.icon;
        float temp = 0;

        BlockPos playerPos = new BlockPos(this.player.posX,
                this.player.posY, this.player.posZ);
        Biome biome = this.player.worldObj.getBiomeForCoordsBody(playerPos);
        float biomeVar = ModBiomes.getTemp(biome);
        
        temp += biomeVar;

        float heightVar = 0;
        float belowSea = (float) (64 - this.player.posY);
        
        if (this.player.worldObj.getWorldType() != WorldType.FLAT &&
                belowSea != 0) {

            heightVar += belowSea / 12F;
        }
        
        temp += heightVar;

        float timeVar;
        long time = this.player.worldObj.getWorldTime();
        
        if ((time > 0 && time <= 3000) || (time > 12000 && time <= 18000)) {

            timeVar = 0;
            
        } else if (time > 6000 && time <= 1200) {

            timeVar = 1;
            
        } else {

            timeVar = -1;
            
        }
        
        if (biomeVar > 3 && time > 4000 && time <= 8000 &&
                !this.player.worldObj.canSeeSky(playerPos)) {

            timeVar += -1;
        }
        
        temp += timeVar;

        float waterVar = 0;
        
        if (this.player.isInWater() || this.player.isWet()) {
            
            waterVar = -3;
            this.wetTimer = 1200;
            
        } else if (this.wetTimer > 0) {
            
            waterVar = -1;
            this.wetTimer--;
        }

        temp += waterVar;  

        float clothesVar = 0;

        if (this.wetTimer == 0) {
        
            for (ItemStack stack : this.player.inventory.armorInventory) {

                if (stack != null && stack.getItem() != null &&
                        stack.getItem() instanceof ItemArmor) {
                    
                    ItemArmor armor = (ItemArmor) stack.getItem();
                    
                    if (armor.getArmorMaterial() == EquipMaterial.WOOL_APPAREL ||
                            armor.getArmorMaterial() ==
                            EquipMaterial.FUR_APPAREL) {

                        clothesVar += 0.5;
                        
                    } else {

                        clothesVar += 0.25;
                    }
                }
            }
        }
        
        temp += clothesVar;

        double fireVar = 0;
        
        for (int x = -10; x <= 10; x++) {
            
            for (int y = -10; y <= 10; y++) {
                
                for (int z = -10; z <= 10; z++) {
                    
                    double xPos = this.player.posX + x;
                    double yPos = this.player.posY + y;
                    double zPos = this.player.posZ + z;
                    
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    
                    Block block = this.player.worldObj
                            .getBlockState(pos).getBlock();
                    
                    boolean fireLit = false;
                    
                    if (block == ModBlocks.furnaceCampfire || 
                            block == ModBlocks.furnacePotfire ||
                            block == ModBlocks.furnaceClay ||
                            block == ModBlocks.furnaceStone) {
                        
                        TEFurnaceAbstract furnace =
                                (TEFurnaceAbstract) this.player.worldObj
                                .getTileEntity(pos);
                        
                        fireLit = furnace.isHeating();
                    }
                    
                    if (fireLit || block == Blocks.FIRE) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 4 - distance;
                        
                        fireVar = Math.max(fireVar, heat);
                        
                    } else if (block == ModBlocks.torchTallow ||
                            block == ModBlocks.torchTar) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 2 - distance;
                        
                        fireVar = Math.max(fireVar, heat);
                        
                    } else if (block == Blocks.LAVA ||
                            block == Blocks.FLOWING_LAVA) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 11 - distance;
                        
                        fireVar = Math.max(fireVar, heat);
                    }
                }
            }
        }
        
        temp += fireVar;

        if (temp < 0) {
            
            this.icon = EnumTempIcon.COLD;
            
        } else if (temp < 2.5) {
            
            this.icon = EnumTempIcon.COOL;
            
        } else if (temp < 5.5) {
            
            this.icon = EnumTempIcon.OK;
            
        } else if (temp < 8) {
            
            this.icon = EnumTempIcon.WARM;
            
        } else {
            
            this.icon = EnumTempIcon.HOT;
            
        }
        
        if ((this.icon == EnumTempIcon.HOT ||
                this.icon == EnumTempIcon.COLD) && this.timer == 0) {

            this.player.attackEntityFrom(DamageSource.generic, 1);
            this.timer = 200;
            
        } else if (this.timer != 0) {

            this.timer--;
        }
                
        if (oldIcon != this.icon) {

            this.sendMessage();
        }
    }
    
    @Override
    public ResourceLocation getIcon() {

        return this.icon.toRes();
    }
    
    @Override
    public void processMessage(int icon) {
        
        this.icon = EnumTempIcon.values()[icon];
    }
    
    @Override
    public void sendMessage() {
        
        if (this.player instanceof EntityPlayerMP) {

            ModPackets.INSTANCE.sendTo(new
                    TemperatureUpdateClient(this.icon.ordinal()),
                    (EntityPlayerMP) this.player);
            
        }
    }
    
    public enum EnumTempIcon {
        
        COLD("cold"), COOL("cool"), OK("ok"), WARM("warm"), HOT("hot");
        
        private String res;
        
        private EnumTempIcon(String res) {
            
            this.res = "jjmod:textures/gui/temp_" + res + ".png";
        }
        
        public ResourceLocation toRes() {
            
            return new ResourceLocation(this.res);
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("timer", this.timer);
        nbt.setInteger("icon", this.icon.ordinal());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        this.timer = nbt.getInteger("timer");
        this.icon = EnumTempIcon.values()[nbt.getInteger("icon")];
    }
}
