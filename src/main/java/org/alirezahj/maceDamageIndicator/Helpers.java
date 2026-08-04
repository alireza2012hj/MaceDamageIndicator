package org.alirezahj.maceDamageIndicator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Helpers {
    private static List<DamageTier> tiers = new ArrayList<>();
    private static NamedTextColor defaultColor = null;

    public static void loadDamageTiers(MaceDamageIndicator plugin) {
        tiers.clear();

        List<Map<?, ?>> rawTiers = plugin.getConfig().getMapList("damage-tiers");
        for (Map<?, ?> entry : rawTiers) {
            double max = ((Number) entry.get("max")).doubleValue();
            String colorName = (String) entry.get("color");
            NamedTextColor color = NamedTextColor.NAMES.value(colorName.toLowerCase());

            if (color != null) {
                tiers.add(new DamageTier(max, color));
            }
        }

        tiers.sort(Comparator.comparingDouble(t -> t.max)); // ensure ascending order regardless of yaml order

        String defaultColorName = plugin.getConfig().getString("default-color", "WHITE");
        NamedTextColor parsedDefault = NamedTextColor.NAMES.value(defaultColorName.toLowerCase());
        defaultColor  = (parsedDefault != null) ? parsedDefault : NamedTextColor.WHITE;
    }



    public static NamedTextColor getDamageColor(double damage) {
        for (DamageTier tier : tiers) {
            if (damage < tier.max)
                return tier.color;
        }

        return defaultColor;
    }


    public static String formatDamage(double damage, int decimals) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(Math.max(0, decimals)));
        return df.format(damage);
    }



    public static Component buildIndicatorMessage(double damage, MaceDamageIndicator plugin) {
        String template = plugin.getConfig().getString("indicator-message", "Mace Damage >> {damage}!");
        String colorName = plugin.getConfig().getString("message-color", "BLUE");
        boolean bold = plugin.getConfig().getBoolean("message-bold", true);

        NamedTextColor messageColor = NamedTextColor.NAMES.value(colorName.toLowerCase());
        if (messageColor == null) {
            messageColor = NamedTextColor.BLUE;
        }

        String[] parts = template.split("\\{damage}", 2); // limit=2: only split on the first occurrence
        String prefix = parts[0];
        String suffix = (parts.length > 1) ? parts[1] : "";

        String formattedDamage = String.format("%.2f", damage);
        NamedTextColor damageColor = getDamageColor(damage); // your existing tier lookup

        return Component.text(prefix)
                .color(messageColor)
                .decoration(TextDecoration.BOLD, bold)
                .append(
                        Component.text(formattedDamage)
                                .color(damageColor)
                                .decoration(TextDecoration.BOLD, false)
                )
                .append(
                        Component.text(suffix)
                                .color(messageColor)
                                .decoration(TextDecoration.BOLD, bold)
                );
    }

}
