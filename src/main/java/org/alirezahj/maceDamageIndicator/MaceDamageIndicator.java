package org.alirezahj.maceDamageIndicator;

import org.bukkit.plugin.java.JavaPlugin;

public final class MaceDamageIndicator extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MaceHitListener(), this);

    }

    @Override
    public void onDisable() {
        System.out.println("MaceDamageIndicator Disabled!");
    }
}
