package net.ramgames.ramchants.mixins.client;

import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponMixin extends Item {

    public RangedWeaponMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

}
