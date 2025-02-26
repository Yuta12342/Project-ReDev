package net.roadkill.redev.core.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.roadkill.redev.ReDev;
import net.roadkill.redev.common.effect.SightEffect;
import net.roadkill.redev.common.effect.RootedEffect;

public final class EffectInit
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, ReDev.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> SIGHT = EFFECTS.register("sight", SightEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> ROOTED = EFFECTS.register("rooted", RootedEffect::new);
}
