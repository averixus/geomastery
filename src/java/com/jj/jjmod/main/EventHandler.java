package com.jj.jjmod.main;

import com.jj.jjmod.blocks.BlockBedAbstract;
import com.jj.jjmod.blocks.BlockBedAbstract.EnumPartBed;
import com.jj.jjmod.blocks.BlockBedBreakable;
import com.jj.jjmod.blocks.BlockCarcass;
import com.jj.jjmod.capabilities.CapFoodstats;
import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.CapTemperature;
import com.jj.jjmod.capabilities.DefaultCapFoodstats;
import com.jj.jjmod.capabilities.DefaultCapInventory;
import com.jj.jjmod.capabilities.DefaultCapTemperature;
import com.jj.jjmod.capabilities.ProviderCapFoodstats;
import com.jj.jjmod.capabilities.ProviderCapInventory;
import com.jj.jjmod.capabilities.ProviderCapTemperature;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.items.ItemAxe;
import com.jj.jjmod.items.ItemHoe;
import com.jj.jjmod.items.ItemHuntingknife;
import com.jj.jjmod.items.ItemPickaxe;
import com.jj.jjmod.items.ItemShield;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.utilities.FoodStatsPartial;
import com.jj.jjmod.utilities.FoodStatsWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EventHandler {
    
    /** ------------------------ GUI & INPUT EVENTS ------------------------ */
    
    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        
        if (event.getType() == ElementType.HOTBAR) {
        
            EnumHandSide hand = player.getPrimaryHand();
            ResourceLocation icon = player.getCapability(CapTemperature
                    .CAP_TEMPERATURE, null).getIcon();
            
            int width = event.getResolution().getScaledWidth() / 2;
            width = hand == EnumHandSide.LEFT ? width - 114 : width + 96;
            int height = event.getResolution().getScaledHeight() - 21;
            
            mc.getTextureManager().bindTexture(icon);
            Gui.drawModalRectWithCustomSizedTexture(width, height,
                    0, 0, 18, 18, 18, 18);
        }
        
        if (event.getType() == ElementType.FOOD) {

            GlStateManager.enableBlend();
            int left = event.getResolution().getScaledWidth() / 2 + 91;
            int top = event.getResolution().getScaledHeight() - 39;
            Gui gui = new Gui();
            Minecraft.getMinecraft().getTextureManager()
            .bindTexture(new ResourceLocation("textures/gui/icons.png"));
            DefaultCapFoodstats food = (DefaultCapFoodstats) player
                    .getCapability(CapFoodstats.CAP_FOODSTATS, null);
            
            FoodStatsPartial carbs = food.carbs;
            int carbsHunger = carbs.getFoodLevel();
            
            for (int i = 0; i < 10; i++) {
                
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                int icon = 16;
                
                gui.drawTexturedModalRect(x, y, 16, 27, 9, 9);
                
                if (idx < carbsHunger) {
                    
                    gui.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
                    
                } else if (idx == carbsHunger) {
                    
                    gui.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
                }
            }
            
            FoodStatsPartial fruitveg = food.fruitveg;
            int fruitvegHunger = fruitveg.getFoodLevel();
            top -= 10;
            
            for (int i = 0; i < 10; i++) {
                
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                int icon = 16;
                
                gui.drawTexturedModalRect(x, y, 16, 27, 9, 9);
                
                if (idx < fruitvegHunger) {
                    
                    gui.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
                    
                } else if (idx == fruitvegHunger) {
                    
                    gui.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
                }
            }
            
            FoodStatsPartial protein = food.protein;
            int proteinHunger = protein.getFoodLevel();
            top -= 10;
            
            for (int i = 0; i < 10; i++) {
                
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                int icon = 16;
                
                gui.drawTexturedModalRect(x, y, 16, 27, 9, 9);
                
                if (idx < proteinHunger) {
                    
                    gui.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
                    
                } else if (idx == proteinHunger) {
                    
                    gui.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
                }
            }
            
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (event.getGui() instanceof
                net.minecraft.client.gui.inventory.GuiInventory &&
                player.inventoryContainer instanceof ContainerInventory) {

            event.setGui(new com.jj.jjmod.gui
                    .GuiInventory((ContainerInventory)
                    player.inventoryContainer));
        }
    }
    
    @SubscribeEvent
    public void keyInput(KeyInputEvent event) {

        Minecraft mc = Minecraft.getMinecraft();
        
        if (!mc.thePlayer.isSpectator() &&
                !mc.thePlayer.capabilities.isCreativeMode &&
                mc.gameSettings.keyBindSwapHands.isPressed()) {
           
            ContainerInventory inv =
                    (ContainerInventory) mc.thePlayer.inventoryContainer;
            
            if (mc.thePlayer.inventory.offHandInventory.get(0) != null &&
                    ModBlocks.OFFHAND_ONLY.contains(mc.thePlayer.inventory
                    .offHandInventory.get(0).getItem())) {
                
                return;
            }

            inv.swapHands();
            inv.sendUpdateOffhand();
        }
    }
    
    /** -------------------------- BLOCK EVENTS ---------------------------- */
    
    @SubscribeEvent
    public void notifyNeighbor(NeighborNotifyEvent event) {
        
        if (event.getState().getBlock() == Blocks.PORTAL) {

            event.getWorld().setBlockToAir(event.getPos());
        }
    }
    
    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event) {
        
        Block block = event.getState().getBlock();
        EntityPlayer player = event.getPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        
        if ((block == Blocks.DIRT || block == Blocks.GRASS) &&
                stack != null && stack.getItem() instanceof ItemHoe) {
            
            event.getWorld().setBlockState(event.getPos(),
                    Blocks.FARMLAND.getDefaultState());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void harvestDrops(HarvestDropsEvent event) {

        World world = event.getWorld();
        Block block = event.getState().getBlock();
        ItemStack stack = event.getHarvester() == null ? null :
                event.getHarvester().getHeldItemMainhand();
        
        if (block instanceof BlockCarcass) {

            BlockCarcass carcass = (BlockCarcass) block;

            if ((stack == null) ||
                    !(stack.getItem() instanceof ItemHuntingknife)) {

                // No change to drops

            } else {

                event.getDrops().clear();
                event.getDrops().addAll(carcass.DROPS);
            }

            return;
        }   

        // CONFIG vanilla block harvest drops

        if (block instanceof BlockLeaves) {

            if (world.rand.nextInt(4) == 0) {

                event.getDrops().add(new ItemStack(ModItems.leafpile));
            }

            if (world.rand.nextInt(8) == 0) {

                event.getDrops().add(new ItemStack(Items.STICK));
            }
        }

        if (block instanceof BlockLog) {

            event.getDrops().clear();
            int rand = world.rand.nextInt(3);

            switch (rand) {

                case 0: {
                    
                    event.getDrops().add(new ItemStack(ModItems.log));
                    break;
                }
                
                case 1: {
                    
                    event.getDrops().add(new ItemStack(ModItems.pole));
                    break;
                }
                
                case 2: {
                    
                    event.getDrops().add(new ItemStack(ModItems.thicklog));
                    break;
                }
            }
        }

        if (block instanceof BlockDirt || block instanceof BlockGrass) {

            event.getDrops().clear();

            if (world.rand.nextInt(10) == 0) {

                event.getDrops().add(new ItemStack(Items.FLINT));
            }

            event.getDrops().add(new ItemStack(ModItems.dirt, 4));
        }

        if (block instanceof BlockStone) {

            event.getDrops().clear();
            event.getDrops().add(new ItemStack(ModItems.stoneLoose, 4));
        }
    }
    
    @SubscribeEvent
    public void playerBreakSpeed(PlayerEvent.BreakSpeed event) {

        Block block = event.getState().getBlock();
        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        String toolRequired = block.getHarvestTool(event.getState());
        
        if (((toolRequired != null && toolRequired.equals("axe") &&
                (stack == null || !(stack.getItem() instanceof ItemAxe))) ||
                (toolRequired != null && toolRequired.equals("pickaxe") &&
                (stack == null || !(stack.getItem() instanceof ItemPickaxe))))) {
            
            event.setCanceled(true);
        }
    }
    
    /** --------------------------- PLAYER EVENTS -------------------------- */
    
    @SubscribeEvent
    public void playerJoin(EntityJoinWorldEvent event) {
       
        
        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();
            
            player.getCapability(CapTemperature.CAP_TEMPERATURE, null)
                    .sendMessage();
            player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                    .sendMessage();
        }
    }
    
    @SubscribeEvent
    public void playerCapabilities(AttachCapabilitiesEvent<Entity> event) {

        if (!(event.getObject() instanceof EntityPlayer)) {

            return;
        }

        EntityPlayer player = (EntityPlayer) event.getObject();
        
        if (!(player.hasCapability(CapInventory.CAP_INVENTORY, null))) {

            event.addCapability(CapInventory.ID,
                    new ProviderCapInventory(new DefaultCapInventory(player)));
        }
        
        if (!(player.hasCapability(CapTemperature.CAP_TEMPERATURE, null))) {
            
            event.addCapability(CapTemperature.ID,
                    new ProviderCapTemperature(new
                    DefaultCapTemperature(player)));
        }
        
        if (!(player.hasCapability(CapFoodstats.CAP_FOODSTATS, null))) {
            
            event.addCapability(CapFoodstats.ID, new ProviderCapFoodstats(new
                    DefaultCapFoodstats(player)));
        }
    }

    @SubscribeEvent
    public void playerTick(PlayerTickEvent event) {

        EntityPlayer player = event.player;        
        player.getCapability(CapTemperature.CAP_TEMPERATURE, null).update();
                
        if (player.inventoryContainer instanceof ContainerPlayer &&
                !player.capabilities.isCreativeMode) {
            
            player.inventoryContainer =
                    new ContainerInventory(player, player.worldObj);
            player.openContainer = player.inventoryContainer;
            
        } else if (player.inventoryContainer instanceof ContainerInventory &&
                player.capabilities.isCreativeMode) {
            
            player.inventoryContainer = new ContainerPlayer(player.inventory,
                    !player.worldObj.isRemote, player);
            player.openContainer = player.inventoryContainer;

        }

        if (!(player.getFoodStats() instanceof FoodStatsWrapper)) {
            
            ReflectionHelper.setPrivateValue(EntityPlayer.class, player,
                    new FoodStatsWrapper(player), "foodStats");
        }
    }
    
    @SubscribeEvent
    public void playerItemPickup(EntityItemPickupEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        
        if (player.capabilities.isCreativeMode) {
            
            return;
        }
        
        int remaining = event.getItem().getEntityItem().func_190916_E();
        Item item = event.getItem().getEntityItem().getItem();
        ItemStack stack = event.getItem().getEntityItem();
        
        if (ModBlocks.OFFHAND_ONLY.contains(item)) {
            
            ItemStack inSlot = player.inventory.offHandInventory.get(0);
            
            if (inSlot == null) {
                
                player.inventory.offHandInventory.set(0, stack);
                ((ContainerInventory) player.inventoryContainer)
                        .sendUpdateOffhand();
                remaining = 0;
            }
            
        } else {

            remaining = ((DefaultCapInventory) player
                    .getCapability(CapInventory.CAP_INVENTORY, null))
                    .add(stack);
        }

        if (remaining == 0) {

            event.getItem().setDead();

        } else {
            
            stack.func_190920_e(remaining);
            event.getItem().setEntityItemStack(stack);
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public void itemToss(ItemTossEvent event) {
        
        if (!event.getPlayer().capabilities.isCreativeMode) {

            ((ContainerInventory) event.getPlayer().inventoryContainer)
                    .sendUpdateHighlight();
        }
    }

    @SubscribeEvent
    public void playerWakeUp(PlayerWakeUpEvent event) {

        BlockPos pos = new BlockPos(event.getEntityPlayer());
        World world = event.getEntityPlayer().worldObj;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof BlockBedAbstract)) {

            return;
        }

        EnumPartBed part = state.getValue(BlockBedAbstract.PART);
        EnumFacing facing = state.getValue(BlockHorizontal.FACING);

        BlockPos posFoot;
        BlockPos posHead;

        if (part == EnumPartBed.FOOT) {

            posFoot = pos;
            posHead = pos.offset(facing);

        } else {

            posHead = pos;
            posFoot = pos.offset(facing.getOpposite());
        }

        if (block == ModBlocks.bedLeaf) {

            world.setBlockToAir(posFoot);
            world.setBlockToAir(posHead);
        }

        if (block instanceof BlockBedBreakable) {

            TileEntity tileEntity = world.getTileEntity(posFoot);

            if (tileEntity instanceof TEBed) {

                TEBed tileBed = (TEBed) tileEntity;
                tileBed.addUse();

                if (tileBed.isBroken()) {

                    world.setBlockToAir(posFoot);
                    world.setBlockToAir(posHead);
                    world.removeTileEntity(posFoot);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void playerAttacked(LivingAttackEvent event) {
        
        if (!(event.getEntity() instanceof EntityPlayer)) {
            
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntity();
        DamageSource source = event.getSource();
        
        if (!source.isUnblockable() && player.isActiveItemStackBlocking()) {
            
            System.out.println("can block");
            Vec3d sourceVec = source.getDamageLocation();

            if (sourceVec != null) {
                
                Vec3d playerVec = player.getLook(1.0F);
                Vec3d attackVec = sourceVec.subtractReverse(new
                        Vec3d(player.posX, player.posY,
                        player.posZ)).normalize();
                attackVec = new Vec3d(attackVec.xCoord,
                        0.0D, attackVec.zCoord);

                if (attackVec.dotProduct(playerVec) < 0.0D &&
                        event.getAmount() >= 3 &&
                        player.getActiveItemStack().getItem()
                        instanceof ItemShield) {
                    
                    EnumHand hand = player.getActiveHand();
                    player.getActiveItemStack().damageItem(1 +
                            MathHelper.floor_float(event.getAmount()), player);
                    
                    if (player.getActiveItemStack().func_190926_b()) {
                        
                        if (hand == EnumHand.MAIN_HAND) {
                            
                            player.setItemStackToSlot(
                                    EntityEquipmentSlot.MAINHAND,
                                    ItemStack.field_190927_a);
                            
                        } else {
                            
                            player.setItemStackToSlot(
                                    EntityEquipmentSlot.OFFHAND,
                                    ItemStack.field_190927_a);
                        }
                    }
                    
                    if (hand == EnumHand.MAIN_HAND) {
                        
                        ((ContainerInventory) player.inventoryContainer)
                                .sendUpdateHighlight();
                    
                    } else {
                        
                        ((ContainerInventory) player.inventoryContainer)
                            .sendUpdateOffhand();
                    }
                }
            }
        }
    }
    
    /** ----------------------- GENERAL ENTITY EVENTS ---------------------- */

    @SubscribeEvent
    public void livingDrops(LivingDropsEvent event) {

        Entity entity = event.getEntity();

        if (entity.worldObj.isRemote) {

            return;
        }

        if (entity instanceof EntityPig) {

            event.getDrops().clear();
            entity.entityDropItem(new ItemStack(ModBlocks.carcassPig), 0);

        } else if (entity instanceof EntityCow) {

            event.getDrops().clear();
            entity.entityDropItem(new ItemStack(ModBlocks.carcassCowpart), 0);
            
        } else if (entity instanceof EntitySheep) {

            event.getDrops().clear();
            entity.entityDropItem(new ItemStack(ModBlocks.carcassSheep), 0);

        } else if (entity instanceof EntityChicken) {

            event.getDrops().clear();
            entity.entityDropItem(new ItemStack(ModBlocks.carcassChicken), 0);

        } else if (entity instanceof EntityRabbit) {

            event.getDrops().clear();
            entity.entityDropItem(new ItemStack(ModBlocks.carcassRabbit), 0);
        }
    }
}