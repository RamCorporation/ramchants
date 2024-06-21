package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentLevelBasedValue.LevelsSquared.class)
public class LevelsSquaredMixin {

    @Shadow @Final private float added;

    @WrapOperation(method = "getValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;square(I)I"))
    private int allowNegativeResultFromLevelsSquared(int n, Operation<Integer> original) {
        int val = original.call(n);
        System.out.println("outing: "+(this.added < 0 ? -val : val));
        return this.added < 0 ? -val : val;
    }
}
