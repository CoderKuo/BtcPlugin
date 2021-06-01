package com.dakuo

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.info

object BtcPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "com.dakuo.BtcPlugin",
        name = "BtcPlugin",
        version = "1.0-SNAPSHOT",
    ) {
        author("dakuo")
    }
) {
    override fun onEnable() {
        GlobalEventChannel.subscribeAlways<GroupMessageEvent> {event-> GroupMessageListener(event).monitor()}


        logger.info { "火币插件加载成功!" }
    }
}

