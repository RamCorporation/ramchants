package net.ramgames.ramchants;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.math.random.Random;
import net.ramgames.ramchants.api.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RamChantUtils {

    static RamChantsItemStackAccess getStackAccess(ItemStack stack) {
        return (RamChantsItemStackAccess) (FabricItemStack)stack;
    }

    /*static List<EnchantmentLevelEntry> getFirstCompatible(Random random, Map<Enchantment, Integer> itemEnchants, List<EnchantmentLevelEntry> possibleEnchants) {
        mainForBlock:
        for(EnchantmentLevelEntry enchantment : possibleEnchants) {
            if(enchantment.enchantment.isCursed()) continue;
            if(enchantment.enchantment.isTreasure()) if(random.nextInt(4) == 1) continue;
            for(Enchantment combinable : itemEnchants.keySet()) {
                if (enchantment.enchantment == combinable) {
                    if(combinable.getMaxLevel() == itemEnchants.get(combinable)) continue mainForBlock;
                    else continue;
                }
                if (!enchantment.enchantment.canCombine(combinable)) continue mainForBlock;
            }
            return List.of(new EnchantmentLevelEntry(enchantment.enchantment, 1));
        }
        return List.of();
    }*/

    /*static Enchantment determineIfApplyCurse(Random random, Enchantment enchantment, int level) {
        if(!Resources.LINKED_CURSES.contains(EnchantmentHelper.getEnchantmentId(enchantment))) return enchantment;
        if(random.nextBetween(1, level+1) != 1) return enchantment;
        return Resources.LINKED_CURSES.query(EnchantmentHelper.getEnchantmentId(enchantment));
    }*/

    /*static ItemStack applyEnchantsToLootFunctionResult(int level, ItemStack stack, LootContext context) {
        int iterations = context.getRandom().nextBetween(1,maxEnchantmentLevelsAllowed(level));
        for(int i = 0; i < iterations; i++) {
            List<EnchantmentLevelEntry> enchantments = EnchantmentHelper.generateEnchantments(context.getRandom(), stack.getItem() == Items.ENCHANTED_BOOK ? new ItemStack(Items.BOOK) : stack, level, level >= 30);
            List<EnchantmentLevelEntry> compatibleEnchant = RamChantUtils.getFirstCompatible(context.getRandom(), EnchantmentHelper.get(stack), enchantments);
            if (!compatibleEnchant.isEmpty()) {
                if (stack.getItem() == Items.BOOK) {
                    ItemStack newStack = new ItemStack(Items.ENCHANTED_BOOK);
                    newStack.setNbt(stack.getNbt());
                    stack = newStack;
                }
                EnchantmentLevelEntry enchantment = compatibleEnchant.get(0);
                EnchantmentLevelEntry finalEdition = new EnchantmentLevelEntry(RamChantUtils.determineIfApplyCurse(context.getRandom(), enchantment.enchantment, level), enchantment.level);
                stack.addEnchantment(finalEdition.enchantment, finalEdition.level);
            }
        }
        RamChantUtils.getStackAccess(stack).ramChants$setSealed(true);
        return stack;
    }*/

    static int maxEnchantmentLevelsAllowed(int enchantingLevel) {
        return (int) Math.ceil(-5 * Math.pow(0.5, enchantingLevel/10d)+5);
    }

    static int totalEnchantmentsUsed(Map<Enchantment, Integer> enchantments) {
        int cost = 0;
        for(Enchantment enchantment : enchantments.keySet()) cost += enchantments.get(enchantment);
        return cost;
    }

    static Map<Enchantment, Integer> combineStackEnchantments(Map<Enchantment, Integer> stack1, Map<Enchantment, Integer> stack2) {
        HashMap<Enchantment, Integer> combinedMap = new HashMap<>();
        for(Enchantment enchantment : Set.copyOf(stack1.keySet())) {
            if(stack2.containsKey(enchantment)) {
                int level = Math.min(enchantment.getMaxLevel(), stack1.get(enchantment)+stack2.get(enchantment));
                combinedMap.put(enchantment, level);
                stack1.remove(enchantment);
                stack2.remove(enchantment);
            }
        }
        combinedMap.putAll(stack1);
        combinedMap.putAll(stack2);
        return combinedMap;
    }
}
