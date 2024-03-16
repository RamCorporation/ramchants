package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {

    public TridentItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyReturnValue(method = "getEnchantability", at = @At("RETURN"))
    private int getEnchantability(int original) {
        return 8;
    }

}
