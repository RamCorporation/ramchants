package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class DrowningEnchantment extends AbstractLinkedCurseEnchantment {
    protected DrowningEnchantment() {
        super(Enchantments.RESPIRATION, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }
}
