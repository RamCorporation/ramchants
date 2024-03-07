package net.ramgames.ramchants.mixins;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin extends Item {

    public ElytraItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

}
