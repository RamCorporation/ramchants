package net.ramgames.ramchants.mixins;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin extends Item {

    public ElytraItemMixin(Settings settings) {
        super(settings);
    }

    @Unique
    public int getEnchantability() {
        return 5;
    }

}
