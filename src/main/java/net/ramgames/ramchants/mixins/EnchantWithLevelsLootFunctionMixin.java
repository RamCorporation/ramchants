package net.ramgames.ramchants.mixins;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.ramgames.ramchants.RamChantUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantWithLevelsLootFunction.class)
public class EnchantWithLevelsLootFunctionMixin {

    @Shadow
    @Final
    private LootNumberProvider range;

    @Inject(method = "process", at = @At("HEAD"), cancellable = true)
    private void fixEnchantments(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(RamChantUtils.applyEnchantsToLootFunctionResult(range.nextInt(context), stack, context));
    }

}
