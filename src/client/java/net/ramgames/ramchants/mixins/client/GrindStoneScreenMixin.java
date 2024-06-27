package net.ramgames.ramchants.mixins.client;

import net.minecraft.client.gui.screen.ingame.GrindstoneScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.text.Text;
import net.ramgames.ramchants.GrindstoneScreenDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GrindstoneScreen.class)
public abstract class GrindStoneScreenMixin extends HandledScreen<GrindstoneScreenHandler> implements GrindstoneScreenDuck {


    public GrindStoneScreenMixin(GrindstoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Unique
    @Override
    public ItemStack ramchants$getStack(int index) {
        return this.handler.getSlot(index).getStack();
    }
}
