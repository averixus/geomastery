/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.projectile;

import jayavery.geomastery.main.Geomastery;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/** Factories for spear rendering. */
public class RenderSpearFactory implements IRenderFactory<EntityArrow> {

    /** Factory for copper spear renderers. */
    public static final RenderSpearFactory SPEAR_COPPER = new RenderSpearFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/spear_copper.png"));
    /** Factory for bronze spear renderers. */
    public static final RenderSpearFactory SPEAR_BRONZE = new RenderSpearFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/spear_bronze.png"));
    /** Factory for flint spear renderers. */
    public static final RenderSpearFactory SPEAR_FLINT = new RenderSpearFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/spear_flint.png"));
    /** Factory for steel spear renderers. */
    public static final RenderSpearFactory SPEAR_STEEL = new RenderSpearFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/spear_steel.png"));
    /** Factory for wood spear renderers. */
    public static final RenderSpearFactory SPEAR_WOOD = new RenderSpearFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/spear_wood.png"));
    
    /** Texture for this render. */
    private ResourceLocation texture;
    
    private RenderSpearFactory(ResourceLocation texture) {
        
        this.texture = texture;
    }
    
    @Override
    public Render<? super EntityArrow> createRenderFor(
            RenderManager manager) {

        return new RenderSpear(manager, this.texture);
    }
}
