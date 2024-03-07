package net.ramgames.ramchants.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.ramgames.ramchants.RamChants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin extends Item {
    public EnchantedBookItemMixin(Settings settings) {
        super(settings);
    }

    @Unique
    public int getEnchantability() {
        return 5;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        if(stack.getNbt() == null) return true;
        return RamChants.totalEnchantmentsUsed(EnchantmentHelper.get(stack)) < getEnchantability();
    }

}
