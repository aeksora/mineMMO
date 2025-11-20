package de.aeksora.minemmo.mixin;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.enchantment.FortifyStrength;
import de.aeksora.minemmo.functions.EnchantmentRegisterer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public class FortifyStrengthMixin {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true
    )
    private void applyEnchantmentDamage(Entity target, CallbackInfo ci, float baseDamage) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.world.isClient) {
            return;
        }

        ItemStack stack = player.getMainHandStack();
        int level = EnchantmentHelper.getLevel(EnchantmentRegisterer.FORTIFY_STRENGTH, stack);

        if (level <= 0) {
            return;
        }

        double damageMultiplier = 1.0 + FortifyStrength.PERCENTAGES[level] / 100.0;

        float totalDamage = baseDamage * (float) damageMultiplier;

        StringBuilder sb = new StringBuilder();
        sb.append("Recieved following damage information:\n");
        sb.append("{");
        sb.append("    level: ").append(level).append(",\n");
        sb.append("    multiplier: ").append(damageMultiplier).append(",\n");
        sb.append("    baseWeaponDamage: ").append(baseDamage).append(",\n");
        sb.append("    totalDamage: ").append(totalDamage).append(",\n");
        sb.append("}");

        LOGGER.info(sb.toString());

        target.damage(player.getDamageSources().playerAttack(player), totalDamage);
        ci.cancel();
    }

}
