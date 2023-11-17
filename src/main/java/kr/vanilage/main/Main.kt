package kr.vanilage.main

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getConsoleSender().sendMessage("Hello, World!")

        Bukkit.getPluginManager().registerEvents(BreakBlock(), this)
        Bukkit.getPluginManager().registerEvents(Game(), this)
        Bukkit.getPluginManager().registerEvents(Respawn(), this)

        Bukkit.getPluginCommand("gamestart")!!.setExecutor(GameStart())

        instance = this
    }

    companion object {
        lateinit var instance : Main
    }
}