package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponMixin extends Item {

    public RangedWeaponMixin(Settings settings) {
        super(settings);
    }

    @ModifyReturnValue(method = "getEnchantability", at = @At("RETURN"))
    private int getEnchantability(int original) {
        return 5;
    }

}
