package de.aeksora.minemmo.util;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.enchantment.FortifyStrength;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class EnchantmentRegisterer {

    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);
    public static final UUID FORTIFY_STRENGTH_UUID = UUID.randomUUID();

    public static Enchantment FORTIFY_STRENGTH = new FortifyStrength();

    public static void register() {
        Registry.register(Registries.ENCHANTMENT, new Identifier("minemmo", "fortify_strength"), FORTIFY_STRENGTH);

        registerTooltipEvent();
    }

    private static void registerTooltipEvent() {
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, modifierMultimap) -> {
            if (slot != EquipmentSlot.MAINHAND) {
                return;
            }

            int level = EnchantmentHelper.getLevel(FORTIFY_STRENGTH, stack);
            if (level <= 0) {
                return;
            }

            // multiplier of damage based on the enchantment
            double multiplier = FortifyStrength.PERCENTAGES[level] / 100.0;

            // Get vanilla attack damage of the weapon
            double baseDamage = 0.0;

            for (EntityAttributeModifier mod : modifierMultimap.get(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
                if (mod.getOperation() == EntityAttributeModifier.Operation.ADDITION) {
                    baseDamage += mod.getValue();
                }
            }

            PlayerEntity player = MinecraftClient.getInstance().player;
            double playerDamage = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
            baseDamage += playerDamage;

            double bonusDamage = baseDamage * multiplier;

            StringBuilder sb = new StringBuilder();
            sb.append("Recieved following enchantment information:\n");
            sb.append("{");
            sb.append("    level: ").append(level).append(",\n");
            sb.append("    multiplier: ").append(multiplier).append(",\n");
            sb.append("    baseWeaponDamage: ").append(baseDamage).append(",\n");
            sb.append("    bonusDamage: ").append(bonusDamage).append(",\n");
            sb.append("}");

            LOGGER.info(sb.toString());

            modifierMultimap.put(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    new EntityAttributeModifier(
                            FORTIFY_STRENGTH_UUID,
                            "fortify_strength_bonus",
                            bonusDamage,
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );
        });
    }

}
