package net.ramgames.ramchants.mixins;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.ramgames.ramchants.RamChantUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantRandomlyLootFunction.class)
public class EnchantRandomlyLootFunctionMixin {

    @Inject(method = "process", at = @At("HEAD"), cancellable = true)
    private void fixEnchantments(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(RamChantUtils.applyEnchantsToLootFunctionResult(context.getRandom().nextBetween(1,30), stack, context));
    }

}
