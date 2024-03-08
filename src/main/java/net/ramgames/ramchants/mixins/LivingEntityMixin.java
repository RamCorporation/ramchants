package net.ramgames.ramchants.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getProtectionAmount(Ljava/lang/Iterable;Lnet/minecraft/entity/damage/DamageSource;)I", shift = At.Shift.AFTER), cancellable = true)
    public void allowApplicationOfNegativeProtection(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        int i = EnchantmentHelper.getProtectionAmount(this.getArmorItems(), source);
        if(i < 0) cir.setReturnValue(DamageUtil.getInflictedDamage(amount, (float)i));
    }

    @ModifyArg(method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V")
            )
    )
    public float applyAquaHaulToGVariable(float par1) {
        int i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.AQUA_HAUL, (LivingEntity) (Object) this);
        if(i <= 0) return par1;
        return par1 / (i + 1);
    }

    @ModifyArgs(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"))
    public void applyAquaHaulToFVariable(Args args) {
        double i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.AQUA_HAUL, (LivingEntity) (Object) this);
        if(i <= 0) return;
        i /= 12;
        args.set(0, ((double) args.get(0)) / (i + 1));
        args.set(2, ((double) args.get(0)) / (i + 1));
    }
}
