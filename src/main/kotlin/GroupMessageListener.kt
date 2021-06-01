package com.dakuo

import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class GroupMessageListener(event: GroupMessageEvent) {

    var event = event;

    suspend fun monitor(){
        val message = event.message;
        val plainText = getPlainText(message);
        val split = plainText.split(" ");
        if (split[0].equals("你好")){
            event.group.sendMessage("你好");
            return;
        }

        if (split[0].equals("#hb")){
            if (split.size == 1){
                event.group.sendMessage(At(event.sender).plus("\n").plus("""+-----火币网查询-----+
                    |#hb [币名] <usdt|btc|husd> 查询当前市值
                """.trimMargin()));
                return;
            }else if (split.size == 2){
                val detail = HuobiService().getDetail(split[1] + "usdt")
                val f1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                if (detail != null) {
                    val data = detail.data.get(0)
                    val format = f1.format(detail.ts.toLong())
                    val upDown1 = getUpDown(data.close, data.open)
                    val toDouble = BigDecimal(upDown1*100).setScale(4, BigDecimal.ROUND_HALF_UP).toDouble()
                    val upDown = if (upDown1 > 0) "+$toDouble%" else "-$toDouble%"
                    if (detail.status == "ok") {
                        event.group.sendMessage(
                            At(event.sender).plus("\n").plus(
                                """
                                币种: ${split[1].uppercase()}/USDT
                                最新价格: ${data.close}(${upDown})
                                开盘价格: ${data.open}
                                日最高价: ${data.high}
                                日最低价: ${data.low}
                                日交易量: ${data.amount}
                                更新时间: $format
                            """.trimIndent()
                            )
                        )
                    }
                }
            }
        }
    }

    fun getPlainText(str:MessageChain):String{
        val content = str.content.toPlainText();
        return if(content.equals("")){
            "";
        }else{
            content.contentToString().trim();
        }
    }

    fun getUpDown(close: Double, open:Double): Double {
        return (close-open)/open
    }

}