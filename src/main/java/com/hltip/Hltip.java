package com.hltip;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Objects;

public final class Hltip extends JavaPlugin {

    static Hltip main;


    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("§f[§bHltip§f] 启动成功");

        saveDefaultConfig();
        main =this;


        Objects.requireNonNull(Bukkit.getPluginCommand("htip")).setExecutor(new HlCommand());
        Bukkit.getPluginManager().registerEvents(new Hlsj(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§f[§bHltip§f] 关闭成功");
    }
}
