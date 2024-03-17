package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.ramgames.ramchants.RamChantsPersistentProjectileEntityAccess;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CrossbowItem.class)
public abstract class CrossBowItemMixin {

    @Inject(method = "getPullTime", at = @At("RETURN"), cancellable = true)
    private static void applySlowDraw(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int level = EnchantmentHelper.getLevel(RamChantments.SLOW_DRAW, stack);
        if(level > 0) cir.setReturnValue(25 + 5 * level);
    }

    @Inject(method = "shootAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;getSoundPitches(Lnet/minecraft/util/math/random/Random;)[F", shift = At.Shift.BEFORE))
    private static void leaveSingleProjectileToBeFired(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence, CallbackInfo ci, @Local LocalRef<List<ItemStack>> list) {
        if(EnchantmentHelper.getLevel(RamChantments.INACCURACY, stack) <= 0) return;
        int random = entity.getRandom().nextBetween(0,2);
        List<ItemStack> newList = new java.util.ArrayList<>(List.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY));
        newList.set(random, list.get().get(random));
        list.set(newList);
    }


    @Inject(method = "loadProjectiles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"))
    private static void pretendToBeMultiShot(LivingEntity shooter, ItemStack crossbow, CallbackInfoReturnable<Boolean> cir, @Local(index = 3) LocalIntRef j) {
        int level = EnchantmentHelper.getLevel(RamChantments.INACCURACY, crossbow);
        if(level > 0) {
            j.set(3);
        }
    }

    @ModifyArg(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private static Entity allowArrowPickupAndDeflection(Entity par1, @Local(argsOnly = true, ordinal = 0) ItemStack crossbow, @Local(argsOnly = true) World world) {
        if(EnchantmentHelper.getLevel(RamChantments.INACCURACY, crossbow) > 0 && par1 instanceof PersistentProjectileEntity projectile) projectile.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        int deflection = EnchantmentHelper.getLevel(RamChantments.DEFLECTION, crossbow);
        if(deflection > 0 && world.getRandom().nextBetween(1,100) <= 20 * deflection && par1 instanceof PersistentProjectileEntity projectile) ((RamChantsPersistentProjectileEntityAccess)projectile).RamChants$setShouldDeflect();
        return par1;
    }
}
