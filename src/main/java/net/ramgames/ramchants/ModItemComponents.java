package net.ramgames.ramchants;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class ModItemComponents {

    @SuppressWarnings("SpellCheckingInspection")
    public static final ComponentType<Integer> TIMES_GRINDED = register("times_grinded", (builder) -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));
    public static final ComponentType<Boolean> SEALED = register("enchantments_sealed", (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));


    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, RamChants.MOD_ID+':'+id, (builderOperator.apply(ComponentType.builder())).build());
    }

    public static void onInitialize() {

    }

}
