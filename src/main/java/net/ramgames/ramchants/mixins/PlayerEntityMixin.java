package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {


    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract void addExperience(int experience);

    @Shadow public int experienceLevel;

    @Shadow public float experienceProgress;

    @Shadow public int totalExperience;

    @Shadow protected int enchantmentTableSeed;

    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Unique
    private Vec3d lastVec3d;

    @Inject(method = "applyEnchantmentCosts", at = @At("HEAD"), cancellable = true)
    public void adjustEnchantXPCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci) {
        this.addExperience(-(5 + 2 * experienceLevels));
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experienceProgress = 0.0F;
            this.totalExperience = 0;
        }
        this.enchantmentTableSeed = this.random.nextInt();
        ci.cancel();
    }

    /* CURSE STUFF */

    @ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("RETURN"))
    private float changeBreakingSpeed(float original) {
        if(this.isSubmergedIn(FluidTags.WATER) && EnchantmentHelper.getEquipmentLevel(RamChantments.VISCOSITY, (PlayerEntity)(Object) this) > 0) original /= 5.0F;
        int torpidity = EnchantmentHelper.getEquipmentLevel(RamChantments.TORPIDITY, ((PlayerEntity)(Object) this));
        if(torpidity > 0) original /= (2 * torpidity);
        return original;
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I"))
    private void applyMoltenHandle(Entity target, CallbackInfo ci) {
        int level = EnchantmentHelper.getEquipmentLevel(RamChantments.MOLTEN_HANDLE, (PlayerEntity)(Object)this);
        if(level > 0) this.setOnFireFor(4 * level);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void applyPricking(CallbackInfo ci) {
        if(this.lastVec3d == this.getPos()) return;
        this.lastVec3d = this.getPos();
        int level = EnchantmentHelper.getEquipmentLevel(RamChantments.PRICKING, (LivingEntity) (Object)this);
        if(level <= 0) return;
        if(this.random.nextBetween(1, (int)(100000/(Math.pow(10, level)))) == 1) damage(this.getDamageSources().thorns((LivingEntity)(Object)this), 2);
    }
}
