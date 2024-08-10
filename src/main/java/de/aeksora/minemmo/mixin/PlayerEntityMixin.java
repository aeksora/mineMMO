package de.aeksora.minemmo.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyMiningSpeedMultiplier(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        System.out.println(player.getName());

        // TODO
        double miningSpeed = 1.0;
        float miningSpeedUnmod = cir.getReturnValueF();
        System.out.println("Unmodified mining speed: " + miningSpeedUnmod);
        float effectiveMiningSpeed = (float) (miningSpeedUnmod * miningSpeed);
        System.out.println("Effective mining speed:" + effectiveMiningSpeed);
        cir.setReturnValue(effectiveMiningSpeed);
    }
}

