package net.ramgames.ramchants.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLure(Lnet/minecraft/item/ItemStack;)I"))
    private int applyDeterrence(ItemStack stack) {
        int level = EnchantmentHelper.getLevel(RamChantments.DETERRENCE, stack);
        return level > 0 ? -level : EnchantmentHelper.getLure(stack);
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLuckOfTheSea(Lnet/minecraft/item/ItemStack;)I"))
    private int applySeasWoe(ItemStack stack) {
        int level = EnchantmentHelper.getLevel(RamChantments.SEAS_WOE, stack);
        return level > 0 ? -level : EnchantmentHelper.getLuckOfTheSea(stack);
    }

}
