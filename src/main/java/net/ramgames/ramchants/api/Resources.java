package net.ramgames.ramchants.api;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Set;

public interface Resources {

    RamChantsResource<Integer> ENCHANTABILITY = new RamChantsResource<>();
    RamChantsResource<Enchantment> LINKED_CURSES = new RamChantsResource<>();

    class RamChantsResource<T> {
        private final HashMap<Identifier, T> storage = new HashMap<>();

        private RamChantsResource() {}

        protected void register(Identifier identifier, T item) {
            storage.put(identifier, item);
        }
        public boolean contains(Identifier identifier) {
            return storage.containsKey(identifier);
        }
        protected void flush() {
            Set.copyOf(storage.keySet()).forEach(storage::remove);
        }
        public T query(Identifier identifier) {
            return storage.get(identifier);
        }
    }
}
