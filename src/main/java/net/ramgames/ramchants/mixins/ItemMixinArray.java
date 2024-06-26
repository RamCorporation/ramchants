package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.api.Resources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Item.class, FishingRodItem.class, TridentItem.class, BookItem.class, RangedWeaponItem.class})
public abstract class ItemMixinArray {

    @SuppressWarnings("UnreachableCode")
    @ModifyReturnValue(method = "getEnchantability", at = @At("RETURN"))
    public int getEnchantability(int original) {
        Identifier id = Registries.ITEM.getId((Item)(Object)this);
        if(Resources.ENCHANTABILITY.contains(id)) return Resources.ENCHANTABILITY.query(id);
        return original;
    }
}
