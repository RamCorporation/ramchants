package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.ramgames.ramchants.RamChantUtils;
import net.ramgames.ramchants.RamChants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.stream.Collectors;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin {

    @ModifyArg(method = "grind", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;set(Ljava/util/Map;Lnet/minecraft/item/ItemStack;)V"))
    private Map<Enchantment, Integer> allowCurseRemoval(Map<Enchantment, Integer> enchantments, @Local(argsOnly = true) ItemStack item) {
        return EnchantmentHelper.get(item).entrySet().stream().filter((entry) -> (RamChantUtils.getStackAccess(item).ramChants$isSealed() && RamChants.totalEnchantmentsUsed(EnchantmentHelper.get(item)) < RamChantUtils.getStackAccess(item).ramChants$enchantabilityWithGrinds()-1)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Inject(method = "grind", at = @At("RETURN"), cancellable = true)
    private void incrementTimesGrinded(ItemStack item, int damage, int amount, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = cir.getReturnValue();
        RamChantUtils.getStackAccess(stack).ramChants$IncrementTimesGrinded();
        cir.setReturnValue(stack);
    }



}
