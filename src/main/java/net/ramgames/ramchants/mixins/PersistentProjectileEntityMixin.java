package net.ramgames.ramchants.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.ramgames.ramchants.RamChantsPersistentProjectileEntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity implements RamChantsPersistentProjectileEntityAccess {

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void deflect();

    @Shadow public abstract void setVelocity(double x, double y, double z, float speed, float divergence);

    @Unique
    private boolean shouldDeflect = false;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;getOwner()Lnet/minecraft/entity/Entity;"), cancellable = true)
    private void preventCollision(CallbackInfo ci) {
        if(shouldDeflect) {
            shouldDeflect = false;
            setVelocity(getVelocity().multiply(-0.01,-0.01,-0.01));
            deflect();
            ci.cancel();
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("deflect", shouldDeflect);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    protected void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        shouldDeflect = nbt.getBoolean("deflect");
    }

    @Override
    public void RamChants$setShouldDeflect() {
        shouldDeflect = true;
    }

    @Override
    public boolean ramChants$getShouldBetray() {
        return false;
    }
}
