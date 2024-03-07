package net.ramgames.ramchants.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item {

    public TridentItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getEnchantability() {
        return 8;
    }

}
