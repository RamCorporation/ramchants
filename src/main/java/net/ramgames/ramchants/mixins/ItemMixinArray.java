package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.RamChants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Item.class, FishingRodItem.class, TridentItem.class, BookItem.class, RangedWeaponItem.class, })
public class ItemMixinArray {

    @ModifyReturnValue(method = "getEnchantability", at = @At("RETURN"))
    public int getEnchantability(int original) {
        Identifier id = Registries.ITEM.getId((Item)(Object)this);
        if(RamChants.hasCustomEnchantability(id)) return RamChants.getEnchantability(id);
        return original;
    }
}
