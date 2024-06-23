package net.ramgames.ramchants.items.tooltip;

import net.minecraft.item.tooltip.TooltipData;

public record EnchantabilityToolTipData(int enchantability, int usedEnchantability, boolean enchantingScreen, boolean sealed) implements TooltipData {

}
