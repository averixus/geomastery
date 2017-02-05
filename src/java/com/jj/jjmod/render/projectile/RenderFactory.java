package com.jj.jjmod.render.projectile;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactory implements IRenderFactory<EntityArrow> {
    
    public static final RenderFactory ARROW_COPPER = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/arrow_copper.png"));
    public static final RenderFactory ARROW_BRONZE = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/arrow_bronze.png"));
    public static final RenderFactory ARROW_FLINT = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/arrow_flint.png"));
    public static final RenderFactory ARROW_STEEL = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/arrow_steel.png"));
    public static final RenderFactory ARROW_WOOD = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/arrow_wood.png"));
    public static final RenderFactory SPEAR_COPPER = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_copper.png"));
    public static final RenderFactory SPEAR_BRONZE = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_bronze.png"));
    public static final RenderFactory SPEAR_FLINT = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_flint.png"));
    public static final RenderFactory SPEAR_STEEL = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_steel.png"));
    public static final RenderFactory SPEAR_WOOD = new RenderFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_wood.png"));
    
    protected ResourceLocation texture;
    
    private RenderFactory(ResourceLocation texture) {
        
        this.texture = texture;
    }
    
    @Override
    public Render<? super EntityArrow> createRenderFor(
            RenderManager manager) {

        return new RenderProjectile(manager, this.texture);
    }
}
