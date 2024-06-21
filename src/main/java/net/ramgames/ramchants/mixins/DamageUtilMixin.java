package net.ramgames.ramchants.mixins;

import net.minecraft.entity.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DamageUtil.class)
public class DamageUtilMixin {

    @ModifyArg(method = "getInflictedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), index = 1)
    private static float allowNegativeProtectionToBeApplied(float value) {
        return -20F;
    }

}
