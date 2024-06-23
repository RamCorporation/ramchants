package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.EnchantedBookItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    @ModifyReturnValue(method = "isEnchantable", at = @At("RETURN"))
    private boolean allowEnchanting(boolean original) {
        return true;
    }
}
