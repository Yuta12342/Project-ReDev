package net.roadkill.redev.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;

public class RootedEffect extends MobEffect {
    public RootedEffect() {
        super(MobEffectCategory.HARMFUL, 0x4CAF50); // Green color
    }

            @Override
            public boolean applyEffectTick(ServerLevel s, LivingEntity entity, int amplifier) {
                if (entity instanceof Player player) {
                    player.setSprinting(false);
                    double maxSpeed = 0.1D - (0.02D * amplifier); // Cap speed based on amplifier
                    if (player.getDeltaMovement().lengthSqr() > maxSpeed * maxSpeed) {
                        player.setDeltaMovement(player.getDeltaMovement().normalize().scale(maxSpeed));
                    }
                } else {
                    double maxSpeed = 0.1D - (0.02D * amplifier); // Cap speed based on amplifier
                    if (entity.getDeltaMovement().lengthSqr() > maxSpeed * maxSpeed) {
                        entity.setDeltaMovement(entity.getDeltaMovement().normalize().scale(maxSpeed));
                    }
                }
                return super.applyEffectTick(s, entity, amplifier);
            }

            @Override
            public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


}


