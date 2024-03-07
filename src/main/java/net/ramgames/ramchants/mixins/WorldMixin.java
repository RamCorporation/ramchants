package net.ramgames.ramchants.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Shadow public abstract boolean breakBlock(BlockPos pos, boolean drop, @Nullable Entity breakingEntity, int maxUpdateDepth);

    @Inject(method = "breakBlock", at = @At("HEAD"))
    private void allowVoidingToVoid(BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        if(breakingEntity == null) return;
        if(!(breakingEntity instanceof LivingEntity livingEntity)) return;
        if(drop && EnchantmentHelper.getLevel(ModEnchantments.VOIDING, livingEntity.getMainHandStack()) > 0) {
            breakBlock(pos, false, breakingEntity, maxUpdateDepth);
            cir.cancel();
        }
    }

}
