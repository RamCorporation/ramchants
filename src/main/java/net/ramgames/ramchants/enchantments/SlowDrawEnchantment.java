package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class SlowDrawEnchantment extends AbstractLinkedCurseEnchantment{
    protected SlowDrawEnchantment() {
        super(Enchantments.QUICK_CHARGE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
