package kr.vanilage.main

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import java.util.*

class Game : Listener {
    companion object {
        lateinit var red : UUID
        lateinit var blue : UUID

        private val rd = Random()
        private val blockList = arrayOf(Material.OAK_LOG, Material.STONE, Material.IRON_ORE, Material.IRON_BLOCK, Material.FURNACE, Material.COAL_ORE, Material.COAL_BLOCK)
        private val oldRandomBlockList = Material.entries.toList()
        private val randomBlockList = ArrayList<Material>()

        fun start(red : Player, blue : Player) {
            red.playerListName(Component.text("§c${red.name}"))
            blue.playerListName(Component.text("§1${blue.name}"))

            this.red = red.uniqueId
            this.blue = blue.uniqueId

            val world = Bukkit.getWorld("world")

            red.teleport(Location(world, 0.0, 90.0, 6.0))
            blue.teleport(Location(world, 0.0, 90.0, -3.0))

            setRandomBlock()

            Bukkit.getScheduler().runTaskTimer(Main.instance, Runnable {
                generateRandomBlock()
            }, 5, 10)
        }

        private fun generateRandomBlock() {
            val x = rd.nextInt(-3, 4)
            val y = rd.nextInt(90, 99)
            val z = rd.nextInt(3, 10)

            if (rd.nextBoolean()) {
                Bukkit.getWorld("world")!!.getBlockAt(x, y, z).type = randomBlockList[rd.nextInt(randomBlockList.size)]
                Bukkit.getWorld("world")!!.getBlockAt(x, y, z - 10).type = randomBlockList[rd.nextInt(randomBlockList.size)]
            }

            else {
                Bukkit.getWorld("world")!!.getBlockAt(x, y, z).type = blockList[rd.nextInt(blockList.size)]
                Bukkit.getWorld("world")!!.getBlockAt(x, y, z - 10).type = blockList[rd.nextInt(blockList.size)]
            }
        }

        private fun setRandomBlock() {
            for (i in oldRandomBlockList) {
                if (i.isBlock && !i.name.contains("CONCRETE") && i.isSolid) randomBlockList.add(i)
            }
        }
    }

    @EventHandler
    fun onProjectileHit(e : ProjectileHitEvent) {
        if (e.hitBlock == null) return
        if (e.hitBlock!!.location.z != 1.0) return
        if (e.entity.type != EntityType.SNOWBALL) return
        val hitX = e.hitBlock!!.x
        val hitY = e.hitBlock!!.y
        for (x in hitX - 1..hitX + 1) {
            for (y in hitY - 1..hitY + 1) {
                val block = Location(Bukkit.getWorld("world")!!, x.toDouble(), y.toDouble(), 1.0).block
                if (block.type.name.contains("CONCRETE")) {
                    val shooter = e.entity.shooter as Player
                    if (shooter.uniqueId == red) {
                        block.type = Material.RED_CONCRETE
                    }

                    if (shooter.uniqueId == blue) {
                        block.type = Material.BLUE_CONCRETE
                    }
                }
            }
        }

        var redConcrete = 0
        var blueConcrete = 0
        var whiteConcrete = 0

        for (x in -4..4) {
            for (y in 90..98) {
                if (Location(Bukkit.getWorld("world"), x.toDouble(), y.toDouble(), 1.0).block.type == Material.RED_CONCRETE) {
                    redConcrete++
                }

                if (Location(Bukkit.getWorld("world"), x.toDouble(), y.toDouble(), 1.0).block.type == Material.BLUE_CONCRETE) {
                    blueConcrete++
                }

                if (Location(Bukkit.getWorld("world"), x.toDouble(), y.toDouble(), 1.0).block.type == Material.WHITE_CONCRETE) {
                    whiteConcrete++
                }
            }
        }

        if (whiteConcrete != 0) return
        if (redConcrete == 0) {
            Bukkit.broadcast(Component.text("§1BLUE 승리"))
        }
        if (blueConcrete == 0) {
            Bukkit.broadcast(Component.text("§cRED 승리"))
        }
    }
}