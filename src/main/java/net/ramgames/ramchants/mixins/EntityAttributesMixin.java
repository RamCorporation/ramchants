package net.ramgames.ramchants.mixins;

import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true)
@Mixin(EntityAttributes.class)
public abstract class EntityAttributesMixin {

    @ModifyArg(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/ClampedEntityAttribute;<init>(Ljava/lang/String;DDD)V", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=generic.water_movement_efficiency")
            ),
            index = 2
    )
    private static double allowNegativeWaterMovementEfficiency(double min) {
        return -1;
    }

    @ModifyArg(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/ClampedEntityAttribute;<init>(Ljava/lang/String;DDD)V", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=attribute.name.player.mining_efficiency")
            ),
            index = 2
    )
    private static double allowNegativeMiningSpeedEfficiency(double min) {
        return -1024;
    }
}