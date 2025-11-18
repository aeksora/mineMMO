package de.aeksora.minemmo.enchantment;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public class FortifyStrength extends DamageEnchantment {

    public static final int[] PERCENTAGES = {0, 2, 4, 6, 8, 10};

    public FortifyStrength() {
        super(Rarity.UNCOMMON, 0, EquipmentSlot.MAINHAND);
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {

        // Damage handled in mixin
        return 0f;
    }
}
