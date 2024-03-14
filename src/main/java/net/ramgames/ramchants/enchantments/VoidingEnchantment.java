package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class VoidingEnchantment extends AbstractLinkedCurseEnchantment {
    protected VoidingEnchantment() {
        super(Enchantments.SILK_TOUCH, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
