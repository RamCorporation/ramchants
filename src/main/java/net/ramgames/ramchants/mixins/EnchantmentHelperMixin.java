package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.ramgames.ramchants.RamChantUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean allowEnchantedBookEnchanting(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isOf(Items.ENCHANTED_BOOK);
    }

    @WrapOperation(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int modifyEnchantabilityValue(Item instance, Operation<Integer> original, @Local(argsOnly = true) ItemStack stack) {
        return RamChantUtils.getRemainingEnchantability(stack);
    }
}
