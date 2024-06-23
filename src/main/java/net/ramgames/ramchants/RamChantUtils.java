package net.ramgames.ramchants;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public interface RamChantUtils {

    static List<EnchantmentLevelEntry> getFirstCompatible(Registry<Enchantment> enchantmentReg, Random random, ItemEnchantmentsComponent itemEnchants, List<EnchantmentLevelEntry> possibleEnchants) {
        mainForBlock:
        for(EnchantmentLevelEntry enchantment : possibleEnchants) {
            if(enchantment.enchantment.isIn(EnchantmentTags.CURSE)) continue;
            if(enchantment.enchantment.isIn(EnchantmentTags.TREASURE)) if(random.nextInt(4) == 1) continue;
            for(Object2IntMap.Entry<RegistryEntry<Enchantment>> combinable : itemEnchants.getEnchantmentEntries()) {
                AtomicReference<Enchantment> enchantAtomic = new AtomicReference<>();
                RegistryEntry<Enchantment> keyVal = combinable.getKey();
                keyVal.getKey().ifPresentOrElse(
                        (item) -> enchantAtomic.set(enchantmentReg.get(item)),
                        () -> RamChants.LOGGER.error("failed to find enchantment with id: {}", keyVal.getIdAsString())
                );
                if(enchantAtomic.get() == null) continue mainForBlock;
                Enchantment enchant = enchantAtomic.get();
                if(enchantment.enchantment.value().description() == enchant.description()) {
                    if(enchant.getMaxLevel() == itemEnchants.getLevel(enchantmentReg.getEntry(enchant))) continue mainForBlock;
                    else continue;
                }
                if(enchantment.enchantment.value().exclusiveSet().contains(keyVal)) continue mainForBlock;
            }
            return List.of(new EnchantmentLevelEntry(enchantment.enchantment, 1));
        }
        return List.of();
    }

    static boolean isStackSealed(ItemStack stack) {
        if(stack.get(ModItemComponents.SEALED) == null) return false;
        Boolean bool = stack.get(ModItemComponents.SEALED);
        return bool != null && bool; // had to do this, so the warning system would shut up.
    }

    static void setSealed(ItemStack stack, boolean val) {
        stack.set(ModItemComponents.SEALED, val);
    }

    static int getEnchantabilityWithGrinds(ItemStack stack) {
        return stack.getItem().getEnchantability() - timesGrinded(stack);
    }

    static void incrementGrinds(ItemStack stack) {
        stack.set(ModItemComponents.TIMES_GRINDED, timesGrinded(stack)+1);
        setSealed(stack, false);
    }

    @SuppressWarnings("SpellCheckingInspection")
    static int timesGrinded(ItemStack stack) {
        Integer grinds = stack.get(ModItemComponents.TIMES_GRINDED);
        if(grinds == null) return 0;
        return grinds;
    }

    static int usedEnchants(ItemStack stack) {
        AtomicInteger atomic = new AtomicInteger();
        ItemEnchantmentsComponent enchants = stack.getEnchantments();
        enchants.getEnchantments().forEach(entry -> atomic.getAndAdd(enchants.getLevel(entry)));
        return atomic.get();
    }

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
