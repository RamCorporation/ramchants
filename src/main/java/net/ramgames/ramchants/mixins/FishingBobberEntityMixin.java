package net.ramgames.ramchants.mixins;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.BlockPos;
import net.ramgames.ramchants.RamChants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {

    @Shadow private int waitCountdown;

    @Redirect(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;II)V", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    private int removeMathMaxCall(int a, int b) {
        return b;
    }

    @Inject(method = "tickFishingLogic", at = @At("TAIL"))
    private void outputTimeLeft(BlockPos pos, CallbackInfo ci) {
        RamChants.LOGGER.info("wait time: "+this.waitCountdown);
    }

}