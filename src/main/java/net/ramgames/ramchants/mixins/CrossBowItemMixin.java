package net.ramgames.ramchants.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Mixin(CrossbowItem.class)
public abstract class CrossBowItemMixin {

    @Shadow public abstract TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand);

    @Unique
    private static final HashMap<Entity, Iterator<Boolean>> shouldBeRealMap = new HashMap<>();

    @Inject(method = "getPullTime", at = @At("RETURN"), cancellable = true)
    private static void applySlowDraw(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int level = EnchantmentHelper.getLevel(ModEnchantments.SLOW_DRAW, stack);
        if(level > 0) cir.setReturnValue(25 + 5 * level);
    }

    @Inject(method = "shootAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;", shift = At.Shift.AFTER))
    private static void generateShouldBeReal(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence, CallbackInfo ci) {
        if(EnchantmentHelper.getLevel(ModEnchantments.INACCURACY, stack) <= 0) return;
        List<Boolean> list = new java.util.ArrayList<>(List.of(false, false, false));
        list.set(entity.getRandom().nextBetween(0,2), true);
        shouldBeRealMap.put(entity, list.iterator());
    }

    @Inject(method = "shoot", at = @At("HEAD"), cancellable = true)
    private static void cancelMultiShotArrow(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
        if(shouldBeRealMap.containsKey(shooter)) {
            boolean bl = shouldBeRealMap.get(shooter).next();
            System.out.println("will allow fire: "+bl);
            System.out.println("has next: "+ shouldBeRealMap.get(shooter).hasNext());
            if(!bl) ci.cancel();
        }
    }

    @Redirect(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private static boolean allowArrowPickUp(World instance, Entity entity) {
        Entity shooter;
        if (entity instanceof PersistentProjectileEntity projectileEntity) {
            shooter = projectileEntity.getOwner();
            if (shouldBeRealMap.containsKey(shooter)) projectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        } else if (entity instanceof FireworkRocketEntity fireworkRocket) {
            shooter = fireworkRocket.getOwner();
            if (shooter != null && !shouldBeRealMap.get(shooter).hasNext()) shouldBeRealMap.remove(shooter);
        }
        return instance.spawnEntity(entity);
    }
}
