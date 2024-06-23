package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @ModifyReturnValue(method = "isEnchantable", at = @At("RETURN"))
    private boolean allowExtraEnchanting(boolean original) {
        return true;
    }
}
