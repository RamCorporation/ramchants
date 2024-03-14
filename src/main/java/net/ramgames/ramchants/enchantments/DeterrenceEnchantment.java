package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class DeterrenceEnchantment extends AbstractLinkedCurseEnchantment {
    protected DeterrenceEnchantment() {
        super(Enchantments.LURE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}