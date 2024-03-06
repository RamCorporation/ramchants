package net.ramgames.ramchants.items.tooltip;

import net.minecraft.client.item.TooltipData;

public record EnchantabilityToolTipData(int enchantability, int usedEnchantability, boolean enchantingScreen) implements TooltipData {

}
