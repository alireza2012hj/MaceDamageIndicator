package org.alirezahj.maceDamageIndicator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

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

        String defaultColorName = plugin.getConfig().getString("default-color", "DARK_RED");
        NamedTextColor parsedDefault = NamedTextColor.NAMES.value(defaultColorName.toLowerCase());
        defaultColor  = (parsedDefault != null) ? parsedDefault : NamedTextColor.DARK_AQUA;
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
        int decimals = plugin.getConfig().getInt("indicator-decimals", 2);

        String[] parts = template.split("\\{damage\\}", 2);
        String prefix = parts[0];
        String suffix = (parts.length > 1) ? parts[1] : "";

        String formattedDamage = formatDamage(damage, decimals);
        NamedTextColor damageColor = getDamageColor(damage);

        LegacyComponentSerializer legacy = LegacyComponentSerializer.legacySection();

        Component prefixComponent = legacy.deserialize(prefix);
        Component suffixComponent = legacy.deserialize(suffix);

        return prefixComponent
                .append(
                        Component.text(formattedDamage)
                                .color(damageColor)
                                .decoration(TextDecoration.BOLD, false)
                )
                .append(suffixComponent);
    }




    public static void setEnabledPersisted(boolean enabled, MaceDamageIndicator plugin) {
        try {
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            List<String> lines = Files.readAllLines(configFile.toPath());

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).matches("^enabled:\\s*(true|false).*")) {
                    lines.set(i, "enabled: " + enabled);
                    break;
                }
            }

            Files.write(configFile.toPath(), lines);
            plugin.getConfig().set("enabled", enabled); // keep in-memory config in sync too

            plugin.getLogger().info("MaceDamageIndicator " + (enabled? "Enabled!" : "Disabled!"));
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to persist enabled state: " + e.getMessage());
        }
    }

}
