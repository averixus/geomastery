package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerBasket;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.util.ResourceLocation;

/** Gui for basket container. */
public class GuiBasket extends GuiContainerAbstract {
    
    /** Texture of this gui. */
    private final ResourceLocation texture;
    
    public GuiBasket(ContainerBasket container) {
        
        super(container, "Basket");
        this.texture = new ResourceLocation(Geomastery.MODID, 
                "textures/gui/basket_" +
                container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
