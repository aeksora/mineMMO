package de.aeksora.minemmo.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

/**
 * Increases XP dropped by defeated enemies
 */
public class QiAbsorption extends Enchantment {

    private static final int[] POWER = {1, 12, 23, 34, 45};
    public static final int[] PERCENTAGES = {0, 11, 22, 40, 55, 75};

    public QiAbsorption() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    public int getMinPower(int level) {
        return POWER[level];
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 20;
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.isAcceptableItem(stack);
    }
}
