package net.ramgames.ramchants.mixins.client;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.ramgames.ramchants.items.tooltip.EnchantabilityToolTipComponent;
import net.ramgames.ramchants.items.tooltip.EnchantabilityToolTipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {

    @Inject(method = "of(Lnet/minecraft/client/item/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;", at = @At("HEAD"), cancellable = true)
    private static void ofMixin(TooltipData data, CallbackInfoReturnable<TooltipComponent> info) {
        if (data instanceof EnchantabilityToolTipData) info.setReturnValue(new EnchantabilityToolTipComponent((EnchantabilityToolTipData) data));
    }

}
