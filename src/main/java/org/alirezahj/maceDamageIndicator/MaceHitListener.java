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
    private final MaceDamageIndicator plugin;

    public MaceHitListener(MaceDamageIndicator plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getConfig().getBoolean("enabled", true)) {
            return; // pretty self-explanatory
        }


        if (!(event.getDamager() instanceof Player player))
            return; // Damager ain't a player so no one to show the message to

        ItemStack weapon = player.getInventory().getItemInMainHand();
        if (weapon.getType() != Material.MACE)
            return; // This is a mace damage indicator, only for the MACE

        double damage = event.getFinalDamage();
        double minDamage = plugin.getConfig().getDouble("min-mace-damage", 6);

        if (damage < minDamage)
            return; // Damage is too low (will be configurable in the future)

        int decimals = plugin.getConfig().getInt("indicator-decimals", 2);
        boolean showViaActionbar = plugin.getConfig().getString("display-mode", "actionbar").equalsIgnoreCase("actionbar");

        Component message = Helpers.buildIndicatorMessage(damage, plugin);


        if (showViaActionbar)
            player.sendActionBar(message);

        else
            player.sendMessage(message);

        // Doesn't really matter if it's a smash attack or not, since
        // breach swapping is a thing
    }

}