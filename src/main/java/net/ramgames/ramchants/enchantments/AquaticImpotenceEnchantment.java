package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityGroup;

public class AquaticImpotenceEnchantment extends AbstractLinkedCurseEnchantment {
    protected AquaticImpotenceEnchantment() {
        super(Enchantments.IMPALING, RamChantments.MAIN_HAND);
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return -Enchantments.IMPALING.getAttackDamage(level, group);
    }
}
