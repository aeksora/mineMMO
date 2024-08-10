package de.aeksora.minemmo.mixin;

import de.aeksora.minemmo.util.IEntityDataSaver;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MiningSpeedMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyMiningSpeedMultiplier(BlockState block, CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof PlayerEntity player) {
//            System.out.println(player.getName());

            float miningSpeed = ((IEntityDataSaver) player).getPersistentData().getFloat("miningSpeed");
            float miningSpeedUnmod = cir.getReturnValueF();
//            System.out.println("Unmodified mining speed: " + miningSpeedUnmod);
            float effectiveMiningSpeed = miningSpeedUnmod * miningSpeed;
//            System.out.println("Effective mining speed:" + effectiveMiningSpeed);
            cir.setReturnValue(effectiveMiningSpeed);
        }
    }
}

