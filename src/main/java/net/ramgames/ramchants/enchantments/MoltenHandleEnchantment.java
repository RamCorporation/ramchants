package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class MoltenHandleEnchantment extends AbstractLinkedCurseEnchantment {
    protected MoltenHandleEnchantment() {
        super(Enchantments.FIRE_ASPECT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
