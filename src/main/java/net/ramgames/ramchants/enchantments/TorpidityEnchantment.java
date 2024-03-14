package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class TorpidityEnchantment extends AbstractLinkedCurseEnchantment {
    protected TorpidityEnchantment() {
        super(Enchantments.EFFICIENCY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
