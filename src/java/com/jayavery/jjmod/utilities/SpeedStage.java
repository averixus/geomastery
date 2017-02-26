package com.jayavery.jjmod.utilities;

import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

/** Enum defining stages of player speed and associated attribute modifiers. */
public enum SpeedStage {
    
    SPEED_2_3(new AttributeModifier(UUID
            .fromString("5bbed57e-d26e-4b30-97f2-a2978d408017"),
            "Speed 2.3", -0.465, 1).setSaved(true)),
    SPEED_2_8(new AttributeModifier(UUID
            .fromString("2ae1c668-7106-4ac5-afc6-0b13229ec80f"),
            "Speed 2.8", -0.349, 1).setSaved(true)),
    SPEED_3_3(new AttributeModifier(UUID
            .fromString("da5357d3-6b7a-403d-8461-b9e40ccde30c"),
            "Speed 3.3", -0.233, 1).setSaved(true)),
    SPEED_3_8(new AttributeModifier(UUID
            .fromString("8cae5c5d-8a29-473b-bba0-5a633b1b7e91"),
            "Speed 3.8", -0.116, 1).setSaved(true));
        
    /** Attribute modifier for this speed stage. */
    private AttributeModifier modifier;
    
    private SpeedStage(AttributeModifier modifier) {
        
        this.modifier = modifier;
    }
    
    /** Apply the given modifier and remove all others. */
    public static void apply(IAttributeInstance movement, SpeedStage newStage) {
        
        for (SpeedStage stage : values()) {
            
            if (movement.hasModifier(stage.modifier)) {
                
                movement.removeModifier(stage.modifier);
            }
        }
        
        if (newStage != null) {
        
            movement.applyModifier(newStage.modifier);
        }
    }
}
