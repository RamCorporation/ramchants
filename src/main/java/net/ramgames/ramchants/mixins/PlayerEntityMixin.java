package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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

    @Inject(method = "applyEnchantmentCosts", at = @At("HEAD"), cancellable = true)
    public void adjustEnchantXPCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci) {
        this.addExperience(-5 * experienceLevels);
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
        if(this.isSubmergedIn(FluidTags.WATER) && EnchantmentHelper.getEquipmentLevel(ModEnchantments.VISCOSITY, (PlayerEntity)(Object) this) > 0) original /= 5.0F;
        int torpidity = EnchantmentHelper.getEquipmentLevel(ModEnchantments.TORPIDITY, ((PlayerEntity)(Object) this));
        if(torpidity > 0) original /= (2 * torpidity);
        return original;
    }
}
