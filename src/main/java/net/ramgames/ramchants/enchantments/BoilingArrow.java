package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class BoilingArrow extends AbstractLinkedCurseEnchantment {
    protected BoilingArrow() {
        super(Enchantments.FLAME, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
