package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class InaccuracyEnchantment extends AbstractLinkedCurseEnchantment{
    protected InaccuracyEnchantment() {
        super(Enchantments.MULTISHOT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
