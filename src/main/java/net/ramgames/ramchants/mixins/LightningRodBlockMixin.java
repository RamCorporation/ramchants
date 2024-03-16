package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockMixin {

    @ModifyExpressionValue(method = "onProjectileHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/BlockHitResult;getBlockPos()Lnet/minecraft/util/math/BlockPos;"))
    private BlockPos allowElectricAttraction(BlockPos original, @Local(argsOnly = true) ProjectileEntity projectile) {
        if(!(projectile instanceof TridentEntity trident)) return original;
        if(trident.getOwner() == null) return original;
        return trident.getOwner().getBlockPos();
    }
}
