package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class CrumblingEnchantment extends Enchantment {
    protected CrumblingEnchantment() {
        super(Enchantments.UNBREAKING.getRarity(), Enchantments.UNBREAKING.target, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static boolean shouldIncreaseDamage(ItemStack item, int level) {

        if (item.getItem() instanceof ArmorItem && Random.create().nextFloat() < 0.6F) {
            return false;
        } else {
            return Random.create().nextInt(level + 1) > 0;
        }
    }

    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxLevel() {
        return Enchantments.UNBREAKING.getMaxLevel();
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if(other == Enchantments.UNBREAKING) return false;
        return super.canAccept(other);
    }
}
