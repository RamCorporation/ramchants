package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class CrumblingEnchantment extends AbstractLinkedCurseEnchantment {
    protected CrumblingEnchantment() {
        super(Enchantments.UNBREAKING, RamChantments.MAIN_HAND);
    }

    public static boolean shouldIncreaseDamage(ItemStack item, int level) {

        if (item.getItem() instanceof ArmorItem && Random.create().nextFloat() < 0.6F) {
            return false;
        } else {
            return Random.create().nextInt(level + 1) > 0;
        }
    }
}
