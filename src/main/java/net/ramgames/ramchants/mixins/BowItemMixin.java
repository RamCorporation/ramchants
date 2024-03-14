package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.ramgames.ramchants.RamChants;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(print = true)
@Mixin(BowItem.class)
public abstract class BowItemMixin {

    @SuppressWarnings("LocalMayBeArgsOnly")
    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private boolean applyGhostQuiver(World instance, Entity entity,  @Local(ordinal = 0) ItemStack itemStack) {
        if(EnchantmentHelper.getLevel(ModEnchantments.GHOST_QUIVER, itemStack) > 0 && instance.random.nextBetween(0,3) == 3) {
            for(PlayerEntity player : instance.getPlayers()) ((ServerPlayerEntity)player).networkHandler.sendPacket(new ParticleS2CPacket(ParticleTypes.END_ROD, true, entity.getX(), entity.getY(), entity.getZ(), 0.05f, 0.05f, 0.05f, 0.05f, 3));
            return false;
        }
        return instance.spawnEntity(entity);
    }

    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setDamage(D)V", shift = At.Shift.AFTER))
    private void applyFlimsy(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci, @Local LocalRef<PersistentProjectileEntity> projectileEntity) {
        int level = EnchantmentHelper.getLevel(ModEnchantments.FLIMSY, stack);
        if(level <= 0) return;
        PersistentProjectileEntity projectile = projectileEntity.get();
        RamChants.LOGGER.info("damage was: "+projectile.getDamage());
        projectile.setDamage(projectileEntity.get().getDamage() / (1 + level));
        RamChants.LOGGER.info("damage is: "+projectile.getDamage());
        projectileEntity.set(projectile);

    }
}
