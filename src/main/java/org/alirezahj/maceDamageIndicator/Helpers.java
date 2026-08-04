package org.alirezahj.maceDamageIndicator;

import net.kyori.adventure.text.format.NamedTextColor;

import java.text.DecimalFormat;

public class Helpers {

    public static NamedTextColor getDamageColor(double damage) {
        if (damage < 0)
            return NamedTextColor.BLACK; // Possible?

        else if (damage < 15)
            return NamedTextColor.GREEN;

        else if (damage < 20)
            return NamedTextColor.YELLOW;

        else if (damage < 30)
            return NamedTextColor.RED;

        else
            return NamedTextColor.DARK_RED;
    }


    public static String formatDamage(double damage, int decimals) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(Math.max(0, decimals)));
        return df.format(damage);
    }

}
