package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class FlimsyEnchantment extends AbstractLinkedCurseEnchantment {
    protected FlimsyEnchantment() {
        super(Enchantments.POWER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
