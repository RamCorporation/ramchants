package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.ramgames.ramchants.RamChantsPersistentProjectileEntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {


    @ModifyReturnValue(method = "isOwner", at = @At("RETURN"))
    private boolean betrayOwner(boolean original) {
        if(!(((ProjectileEntity)(Object)this) instanceof TridentEntity trident)) return original;
        if(!(((RamChantsPersistentProjectileEntityAccess)trident).ramChants$getShouldBetray())) return original;
        return !original;
    }
}
