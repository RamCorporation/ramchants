package net.ramgames.ramchants.mixins.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
}
