package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;

public class PrickingEnchantment extends AbstractLinkedCurseEnchantment {
    protected PrickingEnchantment() {
        super(Enchantments.THORNS, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

}
