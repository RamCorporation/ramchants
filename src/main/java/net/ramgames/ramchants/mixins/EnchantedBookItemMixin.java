package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.ramgames.ramchants.RamChants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin extends Item {
    public EnchantedBookItemMixin(Settings settings) {
        super(settings);
    }

    @Unique
    public int getEnchantability() {
        return 5;
    }
    @ModifyReturnValue(method = "isEnchantable", at = @At("RETURN"))
    private boolean changeIsEnchantable(boolean original, @Local(argsOnly = true) ItemStack stack) {
        if(stack.getNbt() == null) return true;
        return RamChants.totalEnchantmentsUsed(EnchantmentHelper.get(stack)) < getEnchantability();
    }

}
