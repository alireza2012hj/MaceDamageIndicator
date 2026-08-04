package org.alirezahj.maceDamageIndicator;

import net.kyori.adventure.text.format.NamedTextColor;

public class DamageTier {
    public final double max;
    public final NamedTextColor color;

    public DamageTier(double max, NamedTextColor color) {
        this.max = max;
        this.color = color;
    }
}
