package net.ramgames.ramchants;


public interface RamChantsItemStackAccess {

    boolean ramChants$isSealed();
    void ramChants$setSealed(boolean bl);
    int ramChants$timesGrinded();

    void ramChants$IncrementTimesGrinded();
    int ramChants$enchantabilityWithGrinds();
}
