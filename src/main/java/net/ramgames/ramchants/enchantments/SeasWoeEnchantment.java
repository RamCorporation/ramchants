package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class SeasWoeEnchantment extends AbstractLinkedCurseEnchantment {
    protected SeasWoeEnchantment() {
        super(Enchantments.LUCK_OF_THE_SEA, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
