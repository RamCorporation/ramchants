package net.ramgames.ramchants.mixins;

import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {

    @Redirect(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;II)V", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    private int removeMathMaxCall(int a, int b) {
        return b;
    }

}