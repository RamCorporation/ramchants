package net.ramgames.ramchants.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockMixin {

    @Shadow public abstract void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile);

    @Inject(method = "onProjectileHit", at = @At("HEAD"), cancellable = true)
    public void allowElectricAttraction(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
        if (world.isThundering() && projectile instanceof TridentEntity && ((TridentEntity)projectile).hasChanneling()) {
            if(EnchantmentHelper.getLevel(RamChantments.ELECTRIC_ATTRACTION, ((TridentEntity) projectile).getItemStack()) > 0 && projectile.getOwner() != null) {
                if(hit.getBlockPos() == projectile.getOwner().getBlockPos()) {
                    return;
                }
                hit = new BlockHitResult(projectile.getOwner().getPos(), hit.getSide(), projectile.getOwner().getBlockPos(), hit.isInsideBlock());
                this.onProjectileHit(world, state, hit, projectile);
                ci.cancel();
            }
        }
    }

}
