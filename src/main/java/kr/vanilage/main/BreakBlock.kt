package kr.vanilage.main

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BreakBlock : Listener {
    @EventHandler
    fun onBreak(e : BlockBreakEvent) {
        if (e.player.gameMode == GameMode.CREATIVE) return
        if (e.block.type.name.endsWith("_CONCRETE")) e.isCancelled = true
        if (e.block.type == Material.GLASS) e.isCancelled = true
    }
}