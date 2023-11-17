package kr.vanilage.main

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class Respawn : Listener {
    @EventHandler
    fun onRespawn(e : PlayerRespawnEvent) {
        if (e.player.uniqueId == Game.red) {
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
                val spawnLocation = Location(Bukkit.getWorld("world"), 0.0, 90.0, 6.0)
                spawnLocation.block.type = Material.AIR
                e.player.teleport(spawnLocation)
            }, 1)
        }

        if (e.player.uniqueId == Game.blue) {
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
                val spawnLocation = Location(Bukkit.getWorld("world"), 0.0, 90.0, -3.0)
                spawnLocation.block.type = Material.AIR
                e.player.teleport(spawnLocation)
            }, 1)
        }
    }
}