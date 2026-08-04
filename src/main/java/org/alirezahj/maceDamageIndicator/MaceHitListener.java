package org.alirezahj.maceDamageIndicator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MaceHitListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))
            return; // Damager ain't a player so no one to show the message to

        ItemStack weapon = player.getInventory().getItemInMainHand();
        if (weapon.getType() != Material.MACE)
            return; // This is a mace damage indicator, only for the MACE

        double damage = event.getFinalDamage();
        if (damage < 6)
            return; // Damage is too low (will be configurable in the future)

        String formattedDamage = String.format("%.2f", damage);
        Component message = Component.text("Mace Damage ").color(NamedTextColor.BLUE).decorate(TextDecoration.BOLD)
                        .append( Component.text(">> ").color(NamedTextColor.GRAY) )
                                .append( Component.text(formattedDamage).color(Helpers.getDamageColor(damage)) );

        player.sendActionBar(message);

        // Doesn't really matter if it's a smash attack or not, since
        // breach swapping is a thing
    }

}