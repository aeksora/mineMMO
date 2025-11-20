package de.aeksora.minemmo.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class QiAbsorption extends Enchantment {

    protected QiAbsorption(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }


}
