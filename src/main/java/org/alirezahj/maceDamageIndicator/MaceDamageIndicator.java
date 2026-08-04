package org.alirezahj.maceDamageIndicator;

import org.bukkit.plugin.java.JavaPlugin;

public final class MaceDamageIndicator extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MaceHitListener(this), this);
        saveDefaultConfig();
        CommandManager.registerCommands(this);

        Helpers.loadDamageTiers(this); // load damage tiers from the config

        this.getLogger().info("MaceDamageIndicator Enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("MaceDamageIndicator Disabled!");
    }
}
