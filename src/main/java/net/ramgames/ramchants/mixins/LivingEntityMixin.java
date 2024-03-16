package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    @Shadow public abstract Random getRandom();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getProtectionAmount(Ljava/lang/Iterable;Lnet/minecraft/entity/damage/DamageSource;)I", shift = At.Shift.AFTER), cancellable = true)
    private void allowApplicationOfNegativeProtection(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
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
    private float applyAquaHaulToGVariable(float par1) {
        int i = EnchantmentHelper.getEquipmentLevel(RamChantments.AQUA_HAUL, (LivingEntity) (Object) this);
        if(i <= 0) return par1;
        return par1 / (i + 1);
    }

    @ModifyArgs(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private void applyAquaHaulToFVariable(Args args) {
        double i = EnchantmentHelper.getEquipmentLevel(RamChantments.AQUA_HAUL, (LivingEntity) (Object) this);
        if(i <= 0) return;
        i /= 12;
        args.set(0, ((double) args.get(0)) / (i + 1));
        args.set(2, ((double) args.get(0)) / (i + 1));
    }

    @ModifyReturnValue(method = "getNextAirUnderwater", at = @At("RETURN"))
    private int applyDrowning(int original) {
        int level = EnchantmentHelper.getEquipmentLevel(RamChantments.DROWNING, (LivingEntity)(Object)this);
        if(level <= 0) return original;
        if(this.random.nextInt(level + 1) > 0 && original != 0) original -= 1;
        return original;
    }

    @ModifyArg(method = "dropLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;JLjava/util/function/Consumer;)V"), index = 2)
    private Consumer<ItemStack> applyScarcity(Consumer<ItemStack> lootConsumer, @Local(argsOnly = true) DamageSource damageSource) {
        if(damageSource.getAttacker() == null) return lootConsumer;
        int level = EnchantmentHelper.getEquipmentLevel(RamChantments.SCARCITY, (LivingEntity) damageSource.getAttacker());
        if(level <= 0) return lootConsumer;
        if(this.getRandom().nextBetween(1, 100) > 10 + 15 * (level-1)) return lootConsumer;
        return (stack) -> {};
    }
}
