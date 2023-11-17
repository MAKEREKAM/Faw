package kr.vanilage.main

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameStart : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.isOp) return false
        if (Bukkit.getOnlinePlayers().size != 2) return false
        Game.start(Bukkit.getOnlinePlayers().toList()[0], Bukkit.getOnlinePlayers().toList()[1])
        Bukkit.broadcast(Component.text("게임이 시작됩니다."))
        return false
    }
}