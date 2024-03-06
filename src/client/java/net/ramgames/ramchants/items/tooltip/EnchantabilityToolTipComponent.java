package net.ramgames.ramchants.items.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ramgames.ramchants.RamChants;
import org.joml.Matrix4f;

public class EnchantabilityToolTipComponent implements TooltipComponent {
    private final EnchantabilityToolTipData data;
    private final String text;

    public EnchantabilityToolTipComponent(EnchantabilityToolTipData tooltipData) {

    this.data = tooltipData;
    if(!data.enchantingScreen()) text = "";
    else if(data.enchantability()- data.usedEnchantability() == data.enchantability()) text = "Enchantability: "+data.enchantability();
    else text = "Enchantability: "+(data.enchantability()- data.usedEnchantability()) +"/"+data.enchantability();

    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(Text.literal(text).formatted(Formatting.DARK_PURPLE), x, y, 0, false, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 255);
        TooltipComponent.super.drawText(textRenderer, x, y, matrix, vertexConsumers);
    }

    @Override
    public int getHeight() {
        return data.enchantingScreen() ? 10 : 0;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return textRenderer.getWidth(text);
    }
}
