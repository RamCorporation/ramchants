package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ramgames.ramchants.RamChantsPersistentProjectileEntityAccess;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity implements RamChantsPersistentProjectileEntityAccess {

    @Unique
    private boolean shouldBetray = false;

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> type, World world, ItemStack stack) {
        super(type, world, stack);
    }

    @ModifyReturnValue(method = "hasChanneling", at = @At(value = "RETURN"))
    private boolean allowElectricAttractionToSpawnLightning(boolean original) {
        return EnchantmentHelper.getLevel(RamChantments.ELECTRIC_ATTRACTION, this.getItemStack()) > 0 || original;
    }

    @ModifyExpressionValue(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getBlockPos()Lnet/minecraft/util/math/BlockPos;"))
    private BlockPos setLightningSpawnLocation(BlockPos original) {
        if(EnchantmentHelper.getLevel(RamChantments.ELECTRIC_ATTRACTION, this.getItemStack()) > 0 && this.getOwner() != null) return this.getOwner().getBlockPos();
        return original;
    }

    @ModifyReturnValue(method = "getDragInWater", at = @At("RETURN"))
    private float applyStagnation(float original) {
        ItemStack stack = this.getItemStack();
        int level = EnchantmentHelper.getLevel(RamChantments.STAGNATION, stack);
        if(level <= 0) return original;
        return Math.max(0f, 0.99f - (0.25f * level));
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    private void setShouldBetray(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci) {
        int level = EnchantmentHelper.getLevel(RamChantments.BETRAYAL, stack);
        if(level <= 0) return;
        if(world.getRandom().nextBetween(1,100) > 10 + 15 * (level-1)) return;
        shouldBetray = true;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("betray", shouldBetray);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        shouldBetray = nbt.getBoolean("betray");
    }

    @Override
    public boolean ramChants$getShouldBetray() {
        return shouldBetray;
    }
}
