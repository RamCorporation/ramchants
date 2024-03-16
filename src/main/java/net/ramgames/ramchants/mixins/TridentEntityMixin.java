package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

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

}
