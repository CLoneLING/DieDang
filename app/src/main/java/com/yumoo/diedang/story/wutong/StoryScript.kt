package com.yumoo.diedang.story.wutong

import com.yumoo.diedang.model.Scene
import com.yumoo.diedang.model.Option
import com.yumoo.diedang.model.GameState

fun buildScenes(): List<Scene> {
    val scenes = mutableListOf<Scene>()

    fun opt(text: String, target: String, condition: (GameState) -> Boolean = { true }, onSelect: (GameState) -> Unit = {}): Option {
        return Option(text, target, condition, onSelect)
    }

    // ============================================================
    // 第一章：信件的降临
    // ============================================================
    scenes.add(Scene(
        id = "start",
        text = """
            七月的午后，蝉鸣像一把钝锯，反复拉扯着空气。
            出租屋的窗边，风扇吱呀作响，吹出的风也是热的。桌上放着一封没有署名的信，牛皮纸信封，上面只有一行字：“故人旧事，与君相关。”
            楼下传来收废品的三轮车铃声，叮叮当当，由远及近。
            窗外，对面楼顶的隔热层反射着刺目的白光。
        """.trimIndent(),
        options = listOf(
            opt("✉️ 拆开信看看", "read_letter"),
            opt("🚬 到阳台抽根烟", "balcony_smoke"),
            opt("💻 打开电脑，假装没看见", "ignore_letter"),
            opt("📞 先给母亲打个电话", "call_mother_early"),
            opt("📖 翻翻今天的报纸", "newspaper_today"),
            opt("🍜 下楼吃碗面再回来", "noodle_break")
        )
    ))

    scenes.add(Scene(
        id = "newspaper_today",
        text = """
            拿起桌上的晚报，头版是本地新闻，关于旧城改造的报道。
            翻到中缝，一则寻人启事引起了注意：“寻找七年前事故目击者，知情者请联系……”后面的电话号码被撕掉了。
            只剩一个残存的“魏”字。
            这会是巧合吗？
        """.trimIndent(),
        options = listOf(
            opt("✉️ 拆开信看看", "read_letter"),
            opt("📞 给母亲打电话", "call_mother_early"),
            opt("🚬 去阳台抽根烟", "balcony_smoke"),
            opt("🔍 去报社打听", "newspaper_office") 
        )
    ))

    scenes.add(Scene(
        id = "noodle_break",
        text = """
            下楼走进常去的那家面馆，老板娘正在擦桌子。
            “老样子？”她问。
            点点头，坐在角落。面条端上来，热气蒸腾，模糊了视线。
            旁边桌两个老头在聊天，其中一个说：“听说‘梧桐树’那边最近有人打听七年前的事。”
            另一个压低声音：“嘘，别乱说。”
            筷子停在半空。
        """.trimIndent(),
        options = listOf(
            opt("✉️ 回去拆信", "read_letter"),
            opt("🚶 直接去‘梧桐树’看看", "visit_teahouse_area"),
            opt("📞 给母亲打电话", "call_mother_early"),
            opt("💬 询问老板娘", "noodle_ask")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_early",
        text = """
            拨通了母亲的号码。
            电话响了好几声才接，她的声音带着午后的困倦：“喂？怎么这时候打电话？”
            “收到一封信，没署名，说什么故人旧事……”
            那头沉默了一会儿，然后说：“你爸的事，本想等你再大些才告诉你。既然信都来了……你去看看也好，但记住，别太当真。”
            没等再问，就挂了电话。
            听筒里只剩忙音，窗外的蝉鸣突然变得更响了。
        """.trimIndent(),
        options = listOf(
            opt("✉️ 还是拆开信看看", "read_letter"),
            opt("🚬 去阳台透透气", "balcony_smoke"),
            opt("📅 直接决定周六赴约", "confirm_meeting"),
            opt("📞 再打一次，追问细节", "call_mother_again")
        )
    ))

    scenes.add(Scene(
        id = "balcony_smoke",
        text = """
            站在阳台上，点燃一支烟。烟雾被热风吹散，消失在暮色里。
            楼下，水果摊的老板娘正在收摊，她的小女儿坐在一旁写作业，铅笔在本子上沙沙作响。
            对面的住户亮了灯，一个女人在厨房里忙碌，油烟机的轰鸣声隔着街道传过来。
            烟灰在指尖积聚，然后被风带走。
            深吸一口，把烟头在栏杆上摁灭。
        """.trimIndent(),
        options = listOf(
            opt("✉️ 回去拆信", "read_letter"),
            opt("📱 给朋友阿坤打个电话", "call_akun"),
            opt("💤 回去睡觉", "sleep_rest"),
            opt("🚶 下楼走走", "balcony_walk")
        )
    ))

    scenes.add(Scene(
        id = "ignore_letter",
        text = """
            把信扔到桌上，打开了电脑。屏幕的光映在脸上，有些刺眼。
            登录游戏角色，站在主城里，周围是各种夸张的坐骑和特效。
            但什么也看不进去。那封信就在身后，像一块石头压在心上。
            关掉游戏，看着空荡荡的桌面发呆。
            天色渐渐暗下来了。
        """.trimIndent(),
        options = listOf(
            opt("✉️ 最终还是拿起信", "read_letter"),
            opt("🚬 去阳台抽根烟", "balcony_smoke"),
            opt("🍜 下楼吃碗面", "noodle_break"),
            opt("🎮 再打一局游戏", "game_frustration")
        )
    ))

    scenes.add(Scene(
        id = "read_letter",
        text = """
            撕开封口，里面掉出一张泛黄的照片。
            照片上，父亲和一个陌生男人站在一辆老式桑塔纳前，车牌照是外地的。父亲穿着那件他最喜欢的藏青色夹克，笑得比记忆里任何时候都开朗。
            信是打印的，只有几行字：
            “你父亲曾替人顶罪，这是那晚的借条复印件。若想了解真相，周六下午三点，‘梧桐树’茶馆见。”
            借条上，借款金额是三十万，借款人的签名被涂黑了，但担保人的位置，签着父亲的名字。
            手指划过照片边缘，感到一丝潮湿的凉意。
        """.trimIndent(),
        options = listOf(
            opt("📞 打电话给母亲", "call_mother"),
            opt("🔍 仔细看照片里的车牌", "plate_number"),
            opt("🗑️ 把信和照片烧掉", "burn_letter"),
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("📖 去图书馆查旧报纸", "go_library"),
            opt("📞 联系发信人", "reply_letter"),
            opt("📦 翻找父亲旧物", "old_stuff")
        )
    ))

    scenes.add(Scene(
        id = "reply_letter",
        text = """
            信上没有留下联系方式，只写了“周六见”。
            但可以试着去邮局查一下寄信地址。信封上的邮戳是省城中心支局。
            也许能调出监控？虽然希望渺茫。
            决定明天一早就去省城。
        """.trimIndent(),
        options = listOf(
            opt("🚶 现在就出发", "post_office_early"),
            opt("📅 先赴周六之约", "confirm_meeting"),
            opt("📞 告诉母亲", "call_mother"),
            opt("📝 先写一封回信", "write_reply")
        )
    ))

    scenes.add(Scene(
        id = "post_office_early",
        text = """
            坐末班车赶到省城，邮政所早已关门。
            在附近找了家小旅馆住下，房间潮湿，墙上有水渍。
            躺在床上，看着天花板上旋转的风扇，久久不能入睡。
            明天，或许能揭开一角。
        """.trimIndent(),
        options = listOf(
            opt("🌙 第二天一早去邮政所", "post_office_morning"),
            opt("🔙 回家，等周六", "confirm_meeting"),
            opt("📞 给阿坤打电话", "call_akun_late")
        )
    ))

    scenes.add(Scene(
        id = "post_office_morning",
        text = """
            清晨的邮政所刚开门，工作人员打着哈欠。
            说明来意，对方表示监控保存期只有三个月，七年前的早已覆盖。
            走出邮政所，阳光刺眼。
            巷口有个修鞋摊，师傅正低头干活，身旁收音机里放着早间新闻。
            走过去，想顺便补一下鞋跟。
        """.trimIndent(),
        options = listOf(
            opt("👞 坐下来修鞋，聊聊天", "shoemaker_talk"),
            opt("📅 回市里准备周六", "confirm_meeting"),
            opt("📖 在附近旧书店逛逛", "post_office_bookshop")
        )
    ))

    scenes.add(Scene(
        id = "shoemaker_talk",
        text = """
            修鞋师傅是个健谈的老人，一边钉鞋一边说：“你打听那封信？我倒是见过一个穿风衣的男人，连续几天都在这一带转悠。”
            “他什么样？”
            “瘦高个，戴眼镜，看起来像个文化人。他好像在等什么人。”
            心头一动：“您还记得他长什么样吗？”
            “不太记得了，不过他手里总拿着一把黑伞，晴雨天都不离手。”
        """.trimIndent(),
        options = listOf(
            opt("🔍 在附近找找有没有类似的人", "search_man"),
            opt("📅 先回去，周六再说", "confirm_meeting"),
            opt("🔎 去文具店买把黑伞试探", "buy_black_umbrella")
        )
    ))

    scenes.add(Scene(
        id = "search_man",
        text = """
            在邮政所周边走了两圈，没有见到拿黑伞的人。
            正打算放弃，却在一条小巷的垃圾桶旁发现了一把折断的黑伞。
            伞柄上缠着一根红绳，红绳末端系着一张小纸条，上面写着一个地址：幸福路17号。
            又是幸福路。
            那里，是阿珍的地址。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去幸福路17号", "go_to_azhen"),
            opt("📅 先回市里，周六再探", "confirm_meeting"),
            opt("🔍 在巷子里再搜搜", "search_lane_again")
        )
    ))

    scenes.add(Scene(
        id = "go_library",
        text = """
            带着照片和信，坐公交去了市图书馆。
            阅览室里很安静，只有翻书声和空调的低鸣。在微缩胶片区找到七年前的本地报纸，一页一页翻过去。
            终于，在“社会新闻”版找到一篇简短的报道：“省城郊区发生重大交通事故，一死两伤，肇事司机逃逸。”
            报道里没有提及任何姓名，只附了一张模糊的现场照片。
            但照片背景里有一棵特别的树——和“梧桐树”茶馆门口那棵一模一样。
        """.trimIndent(),
        options = listOf(
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("📞 打电话给母亲", "call_mother"),
            opt("🔍 再仔细看看照片背景", "library_photo_detail"),
            opt("📰 查阅其他日期的报纸", "library_other_dates")
        )
    ))

    scenes.add(Scene(
        id = "library_photo_detail",
        text = """
            拿出放大镜，仔细看那张现场照片。
            在那棵梧桐树旁边，还停着一辆黑色轿车，隐约能看见车牌的后几位——和父亲照片里那辆桑塔纳的车牌一样。
            原来，事故就发生在茶馆附近。
            这绝对不是巧合。合上报纸，心里有了决断。
        """.trimIndent(),
        options = listOf(
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🚶 去茶馆附近转转", "visit_teahouse_area"),
            opt("📞 联系魏律师", "contact_lawyer")
        )
    ))

    scenes.add(Scene(
        id = "visit_teahouse_area",
        text = """
            提前去了“梧桐树”茶馆所在的巷子。下午的巷子很安静，几个老人在树荫下下棋。
            走到那棵梧桐树下，抬头看。树干上有一个陈旧的刻痕，像是两个字母的缩写。
            蹲下身，在树根处发现了一个被泥土半掩的铁盒。打开，里面是一张折好的纸条，上面写着：“你若看到这个，说明你已经开始寻找了。小心，有人不想让真相浮出水面。”
            字迹和父亲的很像。
        """.trimIndent(),
        options = listOf(
            opt("📱 把铁盒和纸条收起来", "confirm_meeting"),
            opt("🕵️ 四处问问附近的老人", "ask_neighbors"),
            opt("🔙 先回家，准备周六赴约", "confirm_meeting"),
            opt("🔍 检查铁盒底部", "check_iron_box")
        )
    ))

    scenes.add(Scene(
        id = "ask_neighbors",
        text = """
            走到下棋的老人旁边，客气地问：“大爷，您认识这附近一个姓林的师傅吗？大概七年前常来这儿。”
            一个戴草帽的老人抬起头，打量了一眼：“你是他什么人？”
    “他是我爸。”
            老人摘下草帽，扇了扇风：“你爸是个好人，可惜……当年那事之后，他就很少来了。你要想知道什么，去找巷尾的修鞋匠老周，他知道的比我多。”
            他指了指巷子深处。
        """.trimIndent(),
        options = listOf(
            opt("👞 去找修鞋匠老周", "shoemaker_zhou"),
            opt("📅 先回家，周六再来", "confirm_meeting"),
            opt("📝 记下老人的话", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "shoemaker_zhou",
        text = """
            修鞋摊在巷子尽头，一把大伞下，一个戴着老花镜的师傅正在钉鞋底。
            说明来意，他放下锤子，沉默了很久。
            “你爸当年替人顶罪，是因为他欠那人一条命。”老周摘下眼镜，擦了擦，“你爸年轻时跑运输，在一条山路上出了事故，是孟建国把他从车里拖出来的。从那以后，你爸就认准了，这条命是孟建国的。”
            “所以当孟建国撞了人，你爸二话不说就顶了。”
            老周说完，又拿起锤子，继续钉鞋底。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约，问清楚细节", "confirm_meeting"),
            opt("📞 打电话给母亲，核实这些", "call_mother"),
            opt("📖 去查阅当年事故档案", "police_archive")
        )
    ))

    scenes.add(Scene(
        id = "call_mother",
        text = """
            电话响了很久才接通。母亲的声音像隔着一层雾：“喂？”
            “妈，爸以前是不是有个朋友，开桑塔纳的？”
            那头沉默了很久。能听到呼吸声变重。“你……是不是拿到什么东西了？”
            “一封信。”
            又是一阵沉默。然后她说：“你爸不是好人，也不是坏人。他只是……太想当个‘好人’了。别查了，儿子，有些事不知道比知道好。”
            她挂了电话。
            风扇还在转，吹起桌上的借条一角。
        """.trimIndent(),
        options = listOf(
            opt("🔍 还是看看照片上的车牌", "plate_number"),
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🚬 去阳台冷静一下", "balcony_smoke"),
            opt("📖 翻开父亲的日记", "find_diary")
        )
    ))

    scenes.add(Scene(
        id = "plate_number",
        text = """
            把照片翻过来，背面用圆珠笔写了一行小字：“江A·T3421”。
            是省城的牌照。
            打开电脑，在搜索引擎里输入这个车牌。跳出来的结果很旧，是一条七年前的新闻：“省城郊区发生重大交通事故，一死两伤，肇事司机逃逸。”
            新闻配图里，有一辆被撞毁的黑色轿车，依稀能辨认出车牌的后几位。
            心跳漏了一拍。
        """.trimIndent(),
        options = listOf(
            opt("📰 深挖这条新闻", "news_deep_dive"),
            opt("📞 联系发信人", "reply_letter"),
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🚶 去事故现场看看", "visit_accident_site"),
            opt("📂 去派出所查档案", "police_archive"),
            opt("🔍 查一下这个车牌的车主", "check_plate_owner")
        )
    ))

    scenes.add(Scene(
        id = "police_archive",
        text = """
            走进辖区派出所，说明来意。值班民警翻了翻旧档案，找到一份事故记录。
            记录上除了基本描述，还附有一份手写笔录——署名是一个叫“刘德顺”的目击者，地址在城郊的老职工宿舍。
            民警说这个人当年提供了不少细节，但后来没再联系。
            抄下地址，道谢离开。阳光刺得睁不开眼。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去找刘德顺", "witness_house"),
            opt("📅 先赴茶馆之约", "confirm_meeting"),
            opt("📞 给魏律师打电话", "contact_lawyer")
        )
    ))

    scenes.add(Scene(
        id = "witness_house",
        text = """
            城郊的老职工宿舍，红砖墙，爬山虎几乎盖住了半边窗户。
            敲响三楼的铁门，过了好一会儿，一个拄着拐杖的老人打开门。他眯着眼看了好一会儿：“找谁？”
            “刘大爷，我是七年前那起车祸相关人的家属，想向您了解一些情况。”
            他沉默片刻，侧身让进门：“进来吧，屋里坐。”
            屋里光线昏暗，桌上放着一副老花镜和一本摊开的《水浒传》。
        """.trimIndent(),
        options = listOf(
            opt("🤝 坐下细谈", "witness_story"),
            opt("🔍 先看看屋里的陈设", "witness_room"),
            opt("📸 拍照作为证据", "witness_photo")
        )
    ))

    scenes.add(Scene(
        id = "witness_room",
        text = """
            目光扫过房间：墙上挂着一幅黑白遗照，是一位中年妇女；墙角堆着几捆旧报纸；窗台上放着一盆干枯的茉莉。
            茶几上有一个搪瓷缸，茶渍深褐。除此之外，再无他物。
            收回目光，在刘德顺对面坐下。
        """.trimIndent(),
        options = listOf(
            opt("🤝 请刘大爷讲讲当年的事", "witness_story")
        )
    ))

    scenes.add(Scene(
        id = "witness_story",
        text = """
            刘大爷端起搪瓷缸，喝了一口茶，缓缓开口：“那天晚上，我正好在路边等末班车。一辆黑色轿车飞快开过，撞倒两个人，停都没停。”
            “我记下了车牌，但第二天去派出所报案时，发现那辆车已经被注销了。”他摇摇头，“后来听说有人顶了罪，但我心里清楚，开车的不是顶罪的那个人。”
            “那您看清司机了吗？”问。
            “太暗，看不清脸，但那人戴着一顶鸭舌帽，帽檐压得很低。”他指了指自己的帽子，“就是这个样式。”
            那顶灰色鸭舌帽，和茶馆里孟小军戴的一模一样。
        """.trimIndent(),
        options = listOf(
            opt("❓ 您还记得别的细节吗？", "witness_more"),
            opt("📅 谢过刘大爷，去赴约", "confirm_meeting"),
            opt("📞 打电话给魏律师", "contact_lawyer"),
            opt("📝 记下鸭舌帽的特征", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "witness_more",
        text = """
            “还有一点，”刘大爷压低声音，“那辆车在经过我身边时，车窗开着一道缝，我听到里面有人吵架。一个说‘开慢点’，另一个说‘别管我’。”
            “像是两个人，声音都很急。”
            这跟魏律师说的“你父亲约孟建国出去”能对上——车上应该有两个人，孟建国和父亲。
            那么，开车的是谁？
            谢过刘大爷，走出楼道，天已经有些发暗。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约，问魏律师更多", "confirm_meeting"),
            opt("🚶 再去废品站找何叔", "go_he_direct"),
            opt("📞 打电话给孙师傅", "call_sun") 
        )
    ))

    scenes.add(Scene(
        id = "news_deep_dive",
        text = """
            点开那篇新闻的全文。报道很简略，只说“肇事车辆逃逸，警方正在追查”，七年前的网络信息还不像现在这样铺天盖地，线索在这里断了。
            但评论区里，有一条七年前的匿名留言：“我知道是谁开的车，但我不敢说。”
            留言者的头像，是一棵梧桐树。
        """.trimIndent(),
        options = listOf(
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🖥️ 尝试追踪那个匿名用户", "trace_anon"),
            opt("📞 联系网站管理员", "contact_webmaster") 
        )
    ))

    scenes.add(Scene(
        id = "trace_anon",
        text = """
            点进那个匿名用户的页面，一片空白，只注册了那一个账号，发表过那一条评论。
            线索彻底断了。
            靠在椅背上，盯着天花板。吊扇的扇叶上积了一层灰，其中一片的末端有一道淡淡的划痕——那是父亲有一次喝醉了，用晾衣杆敲的。
            他很少喝酒，只有那一次，喝得烂醉。
            那晚之后，他就经常出差了。
        """.trimIndent(),
        options = listOf(
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("📞 打电话给父亲的老同事", "call_old_colleague"),
            opt("🔍 检查那道划痕", "check_scratch") 
        )
    ))

    scenes.add(Scene(
        id = "call_old_colleague",
        text = """
            在通讯录里翻了很久，找到“赵叔叔”的名字。他是父亲在厂里的工友，和父亲关系最好。
            电话接通，赵叔叔的声音很洪亮：“喂？是小林啊！好久没联系了！”
            寒暄几句后，问：“赵叔，我爸以前，是不是认识开桑塔纳的人？”
            电话那头突然安静了。背景音里，麻将声也停了。
            “小林，”赵叔的声音变得很慢，“有些事，你爸不让我说。我只能告诉你，你爸这辈子，栽就栽在‘义气’两个字上。你要是想知道，就去找一个姓魏的律师，他以前帮你们家办过事。”
            他给了一个手机号，然后匆忙挂了电话。
        """.trimIndent(),
        options = listOf(
            opt("📱 联系魏律师", "contact_lawyer"),
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("📖 翻翻父亲留下的旧物", "old_stuff")
        )
    ))

    scenes.add(Scene(
        id = "old_stuff",
        text = """
            翻出床底下那个落满灰尘的纸箱，里面装着父亲留下的东西。
            一套旧电工工具，几本泛黄的机械制图手册，还有一个铁皮盒子。
            盒子没有锁，里面是一叠票据和一封信。
            票据都是些维修收据，日期很乱。
            信是父亲写的，但没有寄出。上面只有一句话：
            “儿子，如果有一天你看到了这封信，说明我已经不在你身边了。记住，人这一辈子，最难的不是选择，而是承受选择之后的结果。”
            信纸的右下角，有一个淡淡的咖啡渍。
        """.trimIndent(),
        options = listOf(
            opt("📖 再翻翻箱底有没有别的东西", "box_bottom"),
            opt("📖 发现一本旧日记", "find_diary"),
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🚶 出去走走", "walk_outside"),
            opt("📝 把信抄下来", "copy_letter") 
        )
    ))

    scenes.add(Scene(
        id = "box_bottom",
        text = """
            翻到箱底，摸到一块硬硬的东西，是一枚旧的工牌。
            塑料封套已经发黄，上面是父亲的证件照，他还年轻，头发很密。单位名称是“省城第三建筑公司”。
            从来没听父亲提起过他在省城工作过。
            工牌的背面，用透明胶带粘着一张名片，上面印着：“魏国栋 律师”。
            下面是他的座机号，区号是省城的。
            原来，他们早就认识。
        """.trimIndent(),
        options = listOf(
            opt("📱 联系魏律师", "contact_lawyer"),
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🚶 出去走走", "walk_outside"),
            opt("📖 看看工牌的日期", "check_badge_date") 
        )
    ))

    scenes.add(Scene(
        id = "find_diary",
        text = """
            在铁盒的夹层里，摸到一本牛皮封面的笔记本，边缘已经磨损。
            翻开第一页，是父亲的字迹：“一九九八年，春。”
            里面断断续续记录了父亲的年轻岁月，其中几页提到了一个叫“阿珍”的女人。
            “阿珍的丈夫因病去世，留下她和三岁的儿子。今天帮她修了屋顶，她硬塞给我一篮鸡蛋。”
            后面又写：“阿珍说想去省城找活干，我帮不了她太多，只能借她一点钱。”
            最后一页夹着一张泛黄的地址：城北，幸福路17号。
        """.trimIndent(),
        options = listOf(
            opt("📖 继续读日记", "diary_content"),
            opt("📞 问母亲是否知道阿珍", "call_mother_azhen"),
            opt("📅 先处理眼前的事，回头再说", "confirm_meeting"),
            opt("🚶 立即去城北找阿珍", "go_to_azhen") 
        )
    ))

    scenes.add(Scene(
        id = "diary_content",
        text = """
            一页页翻下去，日记里反复出现“阿珍”这个名字。
            有一次父亲写道：“阿珍的儿子生病，我送他们去医院，垫了医药费。阿珍哭得很厉害，说不知道怎么还。”
            还有一次：“阿珍找到一份缝纫工的工作，很开心。她说等攒够钱就还我，我说不急。”
            最后一篇日记的日期，正是父亲出事的前一天：“阿珍今天来电话，说她儿子考上大学了。我答应周末去看他们。”
            但那个周末，父亲再也没有回来。
            合上日记，窗外的风突然变得很凉。
        """.trimIndent(),
        options = listOf(
            opt("🚶 决定去城北找阿珍", "go_to_azhen"),
            opt("📞 先打电话问问母亲", "call_mother_azhen"),
            opt("📅 先处理信的事，再去城北", "confirm_meeting"),
            opt("📝 把日记里关于阿珍的内容摘抄下来", "copy_diary") 
        )
    ))

    scenes.add(Scene(
        id = "call_mother_azhen",
        text = """
            拨通母亲的电话，直接问：“妈，你认识一个叫阿珍的女人吗？爸以前帮过她。”
            电话那头沉默了更久，然后母亲叹了口气：“阿珍……她是你爸的一个旧识，你爸帮过她很多。后来她搬走了，你爸还偶尔提起她。如果你能找到她，替我问声好吧。”
            没有更多解释，但语气里没有怨怼，反而有一丝怀念。
            挂了电话，决定去城北看看。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去城北幸福路17号", "go_to_azhen"),
            opt("📅 先赴茶馆之约", "confirm_meeting"),
            opt("📞 再问母亲关于阿珍的事", "call_mother_azhen_more") 
        )
    ))

    scenes.add(Scene(
        id = "go_to_azhen",
        text = """
            城北的老街区，巷子比城西更窄，墙根长满青苔。
            幸福路17号是一栋老式居民楼，楼道昏暗，墙皮剥落。
            敲了敲三楼的铁门，开门的是一个头发花白的女人，眉眼之间依稀能看出年轻时的轮廓。
            “您找谁？”她的声音很温和。
            “您是阿珍阿姨吗？我是林建国的儿子。”
            她的眼神瞬间变了，嘴唇微微颤抖：“你……你是建国的孩子？快进来！”
        """.trimIndent(),
        options = listOf(
            opt("🚶 进屋坐下", "azhen_home"),
            opt("🔍 先看看门口的环境", "azhen_doorway"),
            opt("📸 拍下门的照片", "azhen_photo") 
        )
    ))

    scenes.add(Scene(
        id = "azhen_doorway",
        text = """
            门框上贴着一副褪色的春联，门边放着一双男式旧皮鞋，擦得很干净。
            门内传来饭菜的香味，还有收音机里京剧的唱腔。
            收回目光，跟着阿珍进了屋。
        """.trimIndent(),
        options = listOf(
            opt("🚶 进屋坐下", "azhen_home")
        )
    ))

    scenes.add(Scene(
        id = "azhen_home",
        text = """
            屋里不大，但收拾得整洁。墙上挂着几幅十字绣，其中一幅是“常回家看看”。
            阿珍倒了杯茶，双手端过来：“你爸……他走了这些年，我一直想去看看他，又怕打扰你们。”
            “阿姨，我爸在日记里提到您很多次。他说您儿子考上大学了？”
            她眼圈红了：“是啊，志强现在在省城工作，上个月刚结婚。你爸要是还在，看到这一幕该多好。”
            她从柜子里拿出一张照片，是志强的结婚照，旁边的座位空着，上面放着一束白花——那是留给父亲的。
        """.trimIndent(),
        options = listOf(
            opt("🤝 安慰阿珍", "azhen_comfort"),
            opt("❓ 阿姨，您还记得我爸最后一次来找您吗？", "azhen_last_meeting"),
            opt("📱 留下联系方式，答应常来", "azhen_contact"),
            opt("📸 拍下照片", "azhen_take_photo") 
        )
    ))

    scenes.add(Scene(
        id = "azhen_comfort",
        text = """
            “您别难过，我爸一直惦记着您和志强。”轻声说。
            阿珍擦了擦眼角：“你爸是个好人，当年要不是他，我和志强根本撑不过来。我一直想报答他，可他什么都没要。”
            她握住了手：“孩子，你要是有什么需要帮忙的，尽管开口。”
            屋里很安静，窗外传来麻雀的叫声。
        """.trimIndent(),
        options = listOf(
            opt("❓ 阿姨，您能说说我爸最后一次来的时候吗？", "azhen_last_meeting"),
            opt("📱 留下联系方式", "azhen_contact"),
            opt("📖 看看阿珍保留的旧物", "azhen_keepsakes") 
        )
    ))

    scenes.add(Scene(
        id = "azhen_last_meeting",
        text = """
            “那天下午，你爸突然来了，神色很疲惫。”阿珍回忆道，“他说他可能要出一趟远门，让我照顾好自己。还给了我一个信封，说如果以后有事，可以打开看。”
            “我当时没多想，后来他走了，我才发现信封里是一张存单，五千块钱。我知道他是怕我日子难过……”
            阿珍低下头，“那之后，他就再也没来过。直到听到他的消息……”
            一滴泪落在茶杯里。
        """.trimIndent(),
        options = listOf(
            opt("🤝 握住阿珍的手", "azhen_comfort"),
            opt("📱 留下联系方式，以后多联系", "azhen_contact"),
            opt("📖 问阿珍是否还保留着那个信封", "azhen_envelope") 
        )
    ))

    scenes.add(Scene(
        id = "azhen_contact",
        text = """
            写下手机号和住址，递给她：“阿姨，以后有什么事，随时找我。”
            她郑重地收好，然后从里屋拿出一个布包：“这是你爸当年落在我这的一件旧毛衣，我一直想还给他，可……”
            接过布包，打开，是一件深灰色的毛衣，针脚细密，袖口处绣着一个“林”字。
            毛衣里还有一张泛黄的纸，上面是父亲的字：“阿珍，天冷多穿衣。”
            鼻子一酸，把毛衣紧紧抱在怀里。
        """.trimIndent(),
        options = listOf(
            opt("🔙 离开阿珍家，继续处理信的事", "confirm_meeting"),
            opt("🔙 回家，把毛衣收好", "go_home_early"),
            opt("🏠 以后常来阿珍家", "ending_azhen"),
            opt("📞 给母亲打电话，告诉她见到阿珍了", "call_mother_after_azhen") 
        )
    ))

    scenes.add(Scene(
        id = "walk_outside",
        text = """
            出了门，漫无目的地走在街上。晚风带着白天的余热，吹在脸上。
            路边一家旧书店还开着门，昏黄的灯光从玻璃窗透出来。
            推门进去，书香味扑鼻而来。老板是个戴圆框眼镜的老人，正拿着一本《围城》在翻阅。
            在角落的书架上随意翻看，手指拂过书脊，突然在某一本旧书的封套里摸到一张硬纸片。
            抽出来一看，是一张名片，上面印着：“孟建国 个体运输”。
            下面是一串手机号，但已经停机。
        """.trimIndent(),
        options = listOf(
            opt("📞 试着拨打那个号码", "call_meng"),
            opt("📖 买下那本书，回去再看看", "buy_book"),
            opt("🚶 继续往前走", "continue_walk"),
            opt("📱 给朋友阿坤打电话聊聊天", "call_akun"),
            opt("📖 在书店里多翻翻其他书", "bookstore_explore") 
        )
    ))

    scenes.add(Scene(
        id = "call_meng",
        text = """
            拿出手机，拨了那个号码。
            听筒里传来机械的女声：“您好，您拨打的号码是空号。”
            果然，已经过去七年了。
            把名片收好，付钱买下了那本书。
            走出书店，夜色已经完全降临，路灯把街道照得半明半暗。
        """.trimIndent(),
        options = listOf(
            opt("🚶 继续往前走", "continue_walk"),
            opt("🔙 回家", "go_home_early"),
            opt("📱 给阿坤发消息", "call_akun") 
        )
    ))

    scenes.add(Scene(
        id = "buy_book",
        text = """
            付了钱，把那本旧书拿在手里。书的封面已经磨损，但内页还完整。
            翻到扉页，上面用钢笔写着：“赠给老友——孟建国，1998年春。”
            原来这是孟建国送给父亲的书。
            翻了几页，在书的后半部分，夹着一张纸片，上面是父亲的字迹：“老孟，对不住。”
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家，好好想想", "go_home_early"),
            opt("🚶 继续走走", "continue_walk"),
            opt("📖 仔细看看书里有没有别的笔记", "book_notes") 
        )
    ))

    scenes.add(Scene(
        id = "continue_walk",
        text = """
            走在空旷的街道上，偶尔有一辆出租车经过，带起一阵风。
            路过一家还在营业的小饭馆，里面传出碰杯和说笑的声音。
            在门口站了一会儿，最终没有进去。
            再往前走，就是一片住宅区，很多窗户亮着灯，温暖而遥远。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_early"),
            opt("🚶 走到巷子尽头看看", "lane_end"),
            opt("🍜 进饭馆坐坐", "restaurant_evening") 
        )
    ))

    scenes.add(Scene(
        id = "lane_end",
        text = """
            巷子尽头是一个小广场，几个老人还在跳广场舞，音乐声不大。
            广场边有一个公共电话亭，玻璃上贴满了小广告。
            走进电话亭，里面有一股尿骚味。墙上有用油性笔写的一串数字，下面写着“孟建国  有急事找”。
            不知道这是不是真的，但还是记了下来。
            离开电话亭，决定回家。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_early"),
            opt("📞 拨打那个号码", "call_phone_booth_number") 
        )
    ))

    scenes.add(Scene(
        id = "call_akun",
        text = """
            拨通阿坤的号码，响了几声他接了：“喂？林哥，咋了？”
            “没事，就问问你最近怎么样。”
            “我啊，刚从南京回来，拉了一趟货。对了，我上周在路上碰见一个人，自称是你爸以前的朋友，姓孙，说想找你聊聊。我帮你留了他电话。”
            阿坤发过来一个号码。
            又多了一条线索。道了谢，挂掉电话，决定先联系这个姓孙的人。
        """.trimIndent(),
        options = listOf(
            opt("📞 拨打孙师傅的电话", "call_sun"),
            opt("🚶 继续散步", "continue_walk"),
            opt("📱 给阿坤回个消息", "call_akun_again") 
        )
    ))

    scenes.add(Scene(
        id = "call_sun",
        text = """
            拨通孙师傅的电话，那头传来一个沙哑的声音：“喂？”
            “孙师傅您好，我是林建国的儿子，听阿坤说您找我？”
            “哦，你爸的故人。”他咳了两声，“我当年和你爸一起跑过长途，知道一些关于孟建国的事。你要是想听，就来城西的‘老孙大排档’，我晚上都在。”
            挂了电话，决定晚上去一趟。
        """.trimIndent(),
        options = listOf(
            opt("🍺 晚上去大排档找孙师傅", "sun_dinner"),
            opt("📅 先忙正事，改天再去", "confirm_meeting"),
            opt("📞 先打电话问清楚", "sun_phone_again") 
        )
    ))

    scenes.add(Scene(
        id = "sun_dinner",
        text = """
            晚上八点，城西大排档人声鼎沸。孙师傅是个身材魁梧的光头，坐在角落一张桌上，面前摆着一盘花生米和半瓶白酒。
            坐下后，他开门见山：“你爸当年替孟建国顶罪，但你知道吗，孟建国其实不是肇事者。”
            愣住了：“那肇事者是谁？”
            “是孟建国的弟弟，孟建军。那晚孟建军喝了酒，开了哥哥的车出去，撞了人。孟建国为了保他弟弟，才找你爸顶罪。”
            孙师傅喝了一口酒，“你爸其实知道内情，但他没说出来。”
            这个消息像一记重锤。
        """.trimIndent(),
        options = listOf(
            opt("❓ 那孟建军现在在哪？", "where_meng_jun"),
            opt("📞 打电话给魏律师核实", "contact_lawyer"),
            opt("🔙 先消化一下，回家想想", "go_home_think"),
            opt("📝 记下孙师傅说的话", "confirm_meeting") 
        )
    ))

    scenes.add(Scene(
        id = "where_meng_jun",
        text = """
            “孟建军后来因为别的事进了监狱，去年刚放出来。”孙师傅夹了一颗花生米，“他现在就在省城，开一家小面馆。”
            写下了面馆的地址。
            谢过孙师傅，走出大排档。夜风吹来，带着烧烤的烟火气。
            这趟浑水，比想象中更深。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去省城找孟建军", "find_meng_jun"),
            opt("📅 先赴茶馆之约", "confirm_meeting"),
            opt("📞 告诉母亲这件事", "call_mother_after_sun") 
        )
    ))

    scenes.add(Scene(
        id = "find_meng_jun",
        text = """
            第二天一早，坐车去了省城，找到那家“孟记面馆”。
            店面不大，一个中年男人正在揉面，见到有人来，抬起头：“吃面？”
            仔细打量他——和孟建国长得有七分相似。
            “您是孟建军？我是林建国的儿子。”
            他手里的面团掉在案板上，发出沉闷的一声响。
        """.trimIndent(),
        options = listOf(
            opt("🤝 坐下谈谈", "meng_jun_talk"),
            opt("🔍 先观察一下面馆", "meng_jun_shop"),
            opt("📸 拍下店面照片", "meng_jun_photo") 
        )
    ))

    scenes.add(Scene(
        id = "meng_jun_shop",
        text = """
            面馆墙上贴着一张旧海报，是七年前的省城风景。柜台后面摆着一台老式收音机，正放着评书。
            角落有一张桌子，上面放着一张合影——孟建国、父亲，还有孟建军，三人勾肩搭背，笑得灿烂。
            目光在照片上停留片刻，然后在孟建军对面坐下。
        """.trimIndent(),
        options = listOf(
            opt("🤝 开诚布公地谈", "meng_jun_talk")
        )
    ))

    scenes.add(Scene(
        id = "meng_jun_talk",
        text = """
            孟建军沉默了很久，最后叹了口气：“你爸是个真汉子。当年我闯了祸，我哥找你爸帮忙，他二话没说就扛了。后来我哥出了车祸，你爸也没把这事说出去。”
            “我这些年一直想道歉，但找不到机会。”他低下头，“那笔钱，是我哥给你的补偿，其实是我的责任。”
            面馆里很安静，只有煮面的锅咕嘟作响。
        """.trimIndent(),
        options = listOf(
            opt("🤝 接受道歉，并把钱捐出去", "donate_together"),
            opt("❓ 你愿意和我一起去见刘阿姨吗？", "go_west_together"),
            opt("🔙 先离开，让我想想", "go_home_think"),
            opt("💰 我想把钱还给你们", "ending_debt"),
            opt("📖 问关于恐吓信的事", "ask_about_threat") 
        )
    ))

    scenes.add(Scene(
        id = "contact_lawyer",
        text = """
            电话拨出去，响了三声就被接起。一个中年男人的声音，带着一丝疲惫：“喂，哪位？”
            “魏律师您好，我是林建国的儿子。赵叔让我联系您。”
            那边沉默了片刻，然后说：“我知道你会打电话来。周六的见面，其实是我安排的。你父亲的事，电话里说不方便，我们见面谈吧。”
            “梧桐树”茶馆。周六下午三点。
            他挂了电话。
        """.trimIndent(),
        options = listOf(
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("🚬 去阳台抽根烟", "balcony_smoke"),
            opt("📞 再打过去问一些问题", "call_lawyer_again") 
        )
    ))

    scenes.add(Scene(
        id = "confirm_meeting",
        text = """
            把信和照片收进抽屉。周六下午三点……记在手机的备忘录里。
            站起来，走到窗边。太阳已经偏西，楼下的小孩在追逐一只皮球，笑声很远。
            茶几上，父亲唯一留下的那只搪瓷缸里，泡着隔夜的浓茶，茶垢已经发黑。
            那场事故，那张借条，那个匿名用户……这一切像一张网，而自己，正站在网的中央。
        """.trimIndent(),
        options = listOf(
            opt("📖 翻翻父亲留下的旧物", "old_stuff"),
            opt("🚶 出去走走", "walk_outside"),
            opt("💤 睡一觉，养足精神", "sleep_rest"),
            opt("📞 给母亲再打个电话", "call_mother_final_early"),
            opt("🚶 提前去茶馆附近踩点", "visit_teahouse_area"),
            opt("📝 写下已知的线索", "write_clues") 
        )
    ))

    scenes.add(Scene(
        id = "sleep_rest",
        text = """
            拉上窗帘，屋子里暗了下来。
            躺在床上，听着风扇转动的声音，慢慢闭上了眼睛。
            这一觉睡得很沉，恍惚间，看到父亲坐在床边，手里捧着那本旧日记。
            “儿子，”他的声音像从很远的地方传来，“阿珍的孩子，我帮不了太多了，但你可以替我去看看他们。”
            “还有，那笔钱，不要留给孟家，也千万别留给自己。捐出去，让那些真正需要的人用。”
            他伸出手，摸了摸额头，凉凉的。
            睁开眼，天已经亮了。阳光从窗帘缝隙里漏进来，在地板上画出一道金色的线。
        """.trimIndent(),
        options = listOf(
            opt("📖 记下父亲的话，去捐钱", "handle_money"),
            opt("🚶 先去阿珍家，再去捐钱", "go_to_azhen"),
            opt("📅 先去茶馆赴约", "confirm_meeting"),
            opt("📞 给母亲打电话", "call_mother_after_dream") 
        )
    ))

    scenes.add(Scene(
        id = "newspaper_office",
        text = """
            报社位于一栋老旧的办公楼里，前台坐着一位戴眼镜的姑娘。
            “我想查一下七年前的寻人启事，信息可以付费。”她推了推眼镜：“七年前的数据已经归档了，不过我可以帮你查电子版。”
            她敲了几分钟键盘，抬起头：“奇怪……那条寻人启事在数据库里被标记为‘加密’，需要权限才能查看。”
            她低声补充了一句：“这种加密通常不像是普通寻人启事，倒像是警方介入过的记录。”
        """.trimIndent(),
        options = listOf(
            opt("📞 联系魏律师", "contact_lawyer"),
            opt("🔙 先回去，周六再说", "confirm_meeting"),
            opt("📝 记下这个线索", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "noodle_ask",
        text = """
            你招手叫住老板娘：“大姐，刚才那两位大爷说的‘梧桐树’，您了解吗？”
            老板娘擦桌子的手顿了一下，压低声音：“那茶馆不一般，老板姓魏，以前是律师。他家的事，少打听为好。”
            “魏律师？”你心跳加速。
            “对，魏国栋。他开了茶馆后就不接案子了，但他那茶馆里，常常有人谈些……不方便在外面说的事。”
            她说完就转身去招呼别的客人了。
        """.trimIndent(),
        options = listOf(
            opt("📞 给魏律师打电话", "contact_lawyer"),
            opt("📅 决定周六去茶馆", "confirm_meeting"),
            opt("🚶 现在就去茶馆看看", "visit_teahouse_area")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_again",
        text = """
            再次拨通母亲的电话，她接起来：“怎么又打来了？”
            “妈，你说的‘别太当真’是什么意思？你是不是知道一些事？”
            沉默了很久，她终于开口：“你爸……他曾经告诉我，如果有一天有人提起‘故人旧事’，让我劝你别深究。但我知道，如果你像我一样倔，拦不住你。”
            “你爸有个朋友，姓孟。那件事之后，你爸一直活在愧疚里。我恨他，也恨自己。但你要答应我，无论查到什么，都要活着回来。”
            电话挂了，留下嘟嘟的忙音。
        """.trimIndent(),
        options = listOf(
            opt("📅 决定周六赴约", "confirm_meeting"),
            opt("📖 翻旧物", "old_stuff"),
            opt("🚬 去阳台", "balcony_smoke")
        )
    ))

    scenes.add(Scene(
        id = "balcony_walk",
        text = """
            你下了楼，漫无目的地沿着街走。空气里混着晚餐的油烟味和花草的气息。
            路过一家五金店，橱窗里摆着各种工具，其中一把锤子很旧，但手柄被磨得光滑。
            你站在橱窗前，想起父亲也有一把类似的锤子，是他修理家具时常用的。
            一个念头闪过：父亲的那些工具，会不会还藏着什么？
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家翻旧工具", "old_stuff"),
            opt("📱 给阿坤打电话", "call_akun"),
            opt("🔍 进五金店看看", "hardware_store")
        )
    ))

    scenes.add(Scene(
        id = "hardware_store",
        text = """
            推门进去，店里光线昏暗，老板正在柜台后修理一台收音机。
            “随便看。”他没抬头。
            你在角落里看到一个旧工具箱，里面有一些陈旧的电工工具，和父亲的那套很像。
            你拿起一把螺丝刀，手柄上刻着一个字：“林”。
            这是父亲的？你问老板，他说这是前些天一个人送来的，说不要了。问那人长什么样，老板想了想：“瘦高个，戴眼镜，像文化人。”
            又是那个穿风衣的男人。
        """.trimIndent(),
        options = listOf(
            opt("🔍 买下螺丝刀", "confirm_meeting"),
            opt("🔙 回家", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "game_frustration",
        text = """
            你打开游戏，却怎么都打不赢那一关。屏幕上的角色死了又复活，复活又死，像是某种隐喻。
            你扔下鼠标，靠在椅背上，盯着那封信。
            那个信封的边角微微翘起，仿佛在催促。
            你终于伸出手，拿起了它。
        """.trimIndent(),
        options = listOf(
            opt("✉️ 拆开信", "read_letter"),
            opt("📱 给朋友打电话求助", "call_akun")
        )
    ))

    scenes.add(Scene(
        id = "write_reply",
        text = """
            你铺开信纸，犹豫了很久，只写了一句话：“我是林建国的儿子，周六我会去。请告诉我更多。”
            你寄出了信，但不知道地址是否正确。
            三天后，你收到了一封回信，邮戳来自省城。信上只有一行字：“你会知道的。带上那张照片。”
            你翻出照片，发现背面除了车牌，还有一行几乎看不见的铅笔字：“城西老宅，曾住过孟家的人。”
        """.trimIndent(),
        options = listOf(
            opt("🚶 去城西老宅", "west_old_house"),
            opt("📅 先赴周六之约", "confirm_meeting"),
            opt("📞 告诉母亲", "call_mother")
        )
    ))

    scenes.add(Scene(
        id = "west_old_house",
        text = """
            城西的老宅早已人去楼空，门锁锈迹斑斑。你绕到后院，发现一扇窗户没关严。
            爬进去，屋里布满灰尘，家具被白布罩着。在客厅的桌上，放着一部老式电话机，旁边有一张纸条：“如果你来了，拨这个号码。”纸条下压着一个手机号。
            你拨过去，响了两声就接了，是一个男人的声音：“你终于来了。我是孟建国的哥哥，你父亲的朋友。周六茶馆见。”
            他挂了电话。原来，这条线索通往周六的约定。
        """.trimIndent(),
        options = listOf(
            opt("📅 去茶馆", "confirm_meeting"),
            opt("🔙 回家准备", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "call_akun_late",
        text = """
            你拨通了阿坤的电话，他声音迷糊：“林哥？这么晚了。”
            “阿坤，你听说过‘魏国栋’这个人吗？”
            “魏国栋……好像是我爸提过，说是你爸以前的律师。你等一下，我帮你翻翻我爸的老通讯录。”
            几分钟后，他发来一张照片，上面是一个手写的号码，正是魏律师的旧号码。
        """.trimIndent(),
        options = listOf(
            opt("📞 拨打魏律师的旧号码", "contact_lawyer"),
            opt("📅 周六再去", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "post_office_bookshop",
        text = """
            你推开一家旧书店的门，店里堆满了书，几乎无处落脚。
            老板是个白发老人，正在修补一本旧书。你随口问起七年前的事，他抬起头：“你找那场车祸的事？”
            “您知道？”
            “那晚我正好收摊回家，看到一辆黑色轿车飞驰而过，车牌我记得很清楚——江A·T3421。后来有人来打听过，但我不肯说。”
            他顿了顿：“因为来打听的人，就是开车的那个人。”
            你震惊：“您看清了他的脸？”
            “戴着鸭舌帽，但我记得他眉心有一颗痣。”
            孟建军眉心确实有一颗痣。
        """.trimIndent(),
        options = listOf(
            opt("📞 告诉魏律师", "contact_lawyer"),
            opt("📅 去茶馆", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "buy_black_umbrella",
        text = """
            你走进一家文具店，买了一把黑伞。撑开伞，在阳光下走着，试图引起注意。
            果然，不久后一个穿风衣的男人靠近你，低声说：“你在找我？”
            “你是谁？”
            “我是你父亲的朋友，也是那个发信人。周六茶馆见，我会告诉你全部。”
            他转身就走，消失在人群中。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🔍 跟踪他", "follow_figure")
        )
    ))

    scenes.add(Scene(
        id = "follow_figure",
        text = """
            你悄悄跟在他身后，他七拐八拐，走进一条小巷。你加快脚步，却在一拐角跟丢了。
            你正懊恼，发现脚边有一张名片，上面印着：“魏国栋 律师”。背面写着一句话：“别跟了，周六见。”
            他早已知道你在跟踪。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🔙 回家", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "search_lane_again",
        text = """
            你在巷子里来回搜了几遍，没有新发现。但在一堆废纸箱下，你找到一本旧笔记本，上面是陌生的字迹：“林建国，你欠我一条命。”
            你合上本子，心里发冷。这可能是孟建国写的，也可能是别人的恐吓。
            你决定把它带回去研究。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家研究", "go_home_think"),
            opt("📞 告诉母亲", "call_mother")
        )
    ))

    scenes.add(Scene(
        id = "library_other_dates",
        text = """
            你翻看了那几天所有的报纸，发现事故前一天，有一则短讯：“省城昨夜发生一起交通事故，一辆桑塔纳撞毁路灯后逃逸，警方正在追查。”
            这起事故与那起肇事事故有关联吗？你复印了下来。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("📞 告诉魏律师", "contact_lawyer")
        )
    ))

    scenes.add(Scene(
        id = "check_iron_box",
        text = """
            你翻过铁盒，发现底部有一层纸垫，揭开后，露出一张全家福照片——父亲、母亲、你，还有一个陌生女人。照片背面写着：“阿珍，1999年秋。”
            原来阿珍曾与你们家有过交集，但母亲从未提起。
            你心里有了新的疑问。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去找阿珍", "go_to_azhen"),
            opt("📞 问母亲", "call_mother_azhen")
        )
    ))

    scenes.add(Scene(
        id = "check_plate_owner",
        text = """
            你通过关系查到那个车牌号曾属于孟建国，但在事故前三个月就过户给了孟建军。过户记录上附有一个地址，正是孙师傅提到的那家面馆。
            一切指向孟建军。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去找孟建军", "find_meng_jun"),
            opt("📅 周六赴约", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "contact_webmaster",
        text = """
            你联系了新闻网站的管理员，对方表示评论区的匿名用户无法追踪，但他在后台看到该用户的注册IP地址来自省城中心区，并且注册时间正是事故发生后的第三天。
            那个IP地址，后来被用于登录一个名为“梧桐叶”的论坛。你搜索那个论坛，发现它早已关闭，但网上还有一些残存帖子的截图，其中一张截图里有一个帖子标题：“七年前那晚，我看到了真相。”
            你点击进去，正文被遮挡，只能看到发帖人的头像——一棵梧桐树。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六去茶馆", "confirm_meeting"),
            opt("📞 联系论坛管理员", "contact_forum_admin")
        )
    ))

    scenes.add(Scene(
        id = "contact_forum_admin",
        text = """
            你辗转找到了当年论坛的管理员，他姓陈，现在在一家咖啡店工作。见到你，他沉默了许久：“那个帖子是我发的。但发帖人不是我，是我帮一个朋友发的。他叫孟建国。”
            “孟建国？”
            “他那时候已经病重，想留下一些话，但又不敢用自己的名字。他让我发的。”
            “他在帖子里写了什么？”
            “他写：‘那晚开车的是我弟弟，但我哥哥替我顶了罪。我后悔了，可是太迟了。’”
            你拿着这份证词，心里有了数。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约，告诉魏律师", "confirm_meeting"),
            opt("🚶 去找孟建军对质", "find_meng_jun")
        )
    ))

    scenes.add(Scene(
        id = "check_scratch",
        text = """
            你搬来梯子，仔细查看那道划痕。发现划痕末端有一行极小的字，像是用刀尖刻的：“老孟，对不住。”
            是父亲的字迹。他那天喝醉，是因为孟建国出事了。
            你默默把梯子搬回原处。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("📞 告诉母亲", "call_mother")
        )
    ))

    scenes.add(Scene(
        id = "copy_letter",
        text = """
            你将父亲的信逐字抄下，纸上的每一个字都仿佛带着重量。你决定把这封信交给魏律师，作为佐证。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🔙 收好", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "check_badge_date",
        text = """
            工牌上的日期是“2005年6月”，你推算那时父亲已经在省城工作。他在第三建筑公司干了多久？为什么要隐瞒这段经历？
            你决定去那家公司查查旧档案。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去省城第三建筑公司", "construction_company"),
            opt("📅 先赴茶馆", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "construction_company",
        text = """
            你找到那家公司，现在的负责人是个中年女人。她听说你是林建国的儿子，眼神变得复杂：“你爸在这里工作过三年，后来他辞职了，再也没回来过。但我记得，他辞职前一周，和一个姓孟的男人发生过争吵。”
            “他们吵什么？”
            “我路过时听到一句：‘你不能再替他了，否则你会毁了自己。’”
            你想起父亲替孟建国顶罪的事，时间线吻合。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("📞 告诉魏律师", "contact_lawyer")
        )
    ))

    scenes.add(Scene(
        id = "copy_diary",
        text = """
            你抄下所有关于阿珍的片段，笔尖在纸上沙沙作响。看着父亲的字迹，仿佛能听见他当年的叹息。
            你决定把这些内容带给阿珍看，或许她能补充更多细节。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去找阿珍", "go_to_azhen"),
            opt("📅 先去茶馆", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_azhen_more",
        text = """
            “妈，你再想想，阿珍有没有提起过什么特别的事？”
            母亲沉默了一会儿：“有一次她来家里吃饭，你爸喝多了，说了一句‘我这辈子对得起所有人，唯独对不起阿珍’。后来我问她，她什么也没说。”
            你挂断电话，决定当面去问阿珍。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去找阿珍", "go_to_azhen"),
            opt("📅 先去茶馆", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "azhen_photo",
        text = """
            你拍下门牌号，正想转身，阿珍的声音从门内传来：“外面是谁？”
            你只好敲门，当面解释。
        """.trimIndent(),
        options = listOf(
            opt("🚶 进屋", "azhen_home")
        )
    ))

    scenes.add(Scene(
        id = "azhen_take_photo",
        text = """
            你拍下那张结婚照，阿珍没有阻止你，只是低声说：“你爸要是能看到，该多好。”
            你握着手机，心里涌上一阵酸楚。
        """.trimIndent(),
        options = listOf(
            opt("📱 发给母亲看", "call_mother_after_azhen"),
            opt("🔙 继续问话", "azhen_last_meeting")
        )
    ))

    scenes.add(Scene(
        id = "azhen_keepsakes",
        text = """
            阿珍从柜子里拿出一个铁盒，里面是一些旧信，其中一封是你父亲写的：“阿珍，若你安好，我便放心。若有难处，记得找我。”
            你读着信，发现父亲在信里提到一个地方——“城西废品站”。那是何叔的地盘。
            也许何叔还知道更多。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去废品站找何叔", "go_he_direct"),
            opt("📞 先打电话", "contact_lawyer")
        )
    ))

    scenes.add(Scene(
        id = "azhen_envelope",
        text = """
            阿珍翻出那个信封，里面除了存单，还有一张纸条：“万一我不在了，把这封信交给何叔。”
            那封信封口还完好。你打开信，里面是父亲给何叔的嘱托：“何叔，我若出事，请将这笔钱用于补偿刘家。拜托。”
            原来父亲早就料到自己的结局。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去废品站", "go_he_direct"),
            opt("📅 先去茶馆", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_after_azhen",
        text = """
            你打电话给母亲：“妈，我见到阿珍了。”
            那头沉默了很久：“她……还好吗？”
            “她很好，志强也结婚了。”
            “那就好。”母亲的声音带着一丝释然，“你爸要是还在，看到你这样，会很高兴的。”
            你挂断电话，心里某块石头似乎轻了一些。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回到正题", "confirm_meeting"),
            opt("📝 写下感悟", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "bookstore_explore",
        text = """
            你在书店里又翻到一本旧书，扉页上写着：“献给林建国，我最好的兄弟。——孟建国。”
            原来父亲和孟建国曾经是挚友。你买下这本书，决定带回去细读。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_early"),
            opt("📖 当场翻阅", "book_notes")
        )
    ))

    scenes.add(Scene(
        id = "book_notes",
        text = """
            你翻开那本书，发现中间夹着一张照片，是父亲和孟建国站在一辆卡车前的合影。背面写着：“2005年，最后一次出车。”
            那是父亲辞职前不久。他们曾经一起跑长途，一起喝酒，一起扛过风雨。
            你突然理解了父亲的义气。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🔙 回家", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "restaurant_evening",
        text = """
            你走进饭馆，老板娘热情地招呼你坐下。你点了一碗面，吃到一半，邻桌一个中年人搭话：“你是林建国的儿子吧？”
            你警惕地看着他。
            “别紧张，我是你爸的老朋友，姓赵。你爸以前常来这儿吃面。他每次来都点同样的面，然后看着窗外发呆。”
            他叹了口气：“你爸心里有事，但他从来不提。那段时间，他总念叨‘老孟’这个名字。”
            你决定多问几句。
        """.trimIndent(),
        options = listOf(
            opt("❓ 您知道‘老孟’是谁吗？", "ask_zhao"),
            opt("📝 记下他的话", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "ask_zhao",
        text = """
            “老孟就是他最好的朋友，叫孟建国。他们一起跑过车，关系铁得很。后来出了事，你爸就变了。”
            “出了什么事？”
            “孟建国的弟弟开车撞了人，孟建国求你爸顶罪。你爸答应了，但心里一直过不去。他跟我说过，他这辈子最怕的就是对不起别人。”
            你默默吃完面，结账离开。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🔙 回家想想", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "call_phone_booth_number",
        text = """
            你拨通了那个号码，接电话的竟是一个熟悉的声音——何叔。
            “何叔？是你？”
            “你终于打过来了。那是我留的电话，怕你找不到我。你爸那笔钱我一直保管着，你什么时候来拿？”
            你决定明天就去。
        """.trimIndent(),
        options = listOf(
            opt("🚶 明天去废品站", "go_he_direct"),
            opt("📞 先问清楚", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "call_akun_again",
        text = """
            你给阿坤回消息：“谢谢你，阿坤，改天请你吃饭。”
            他回复：“林哥，有需要再找我。”
            你放下手机，感到一丝温暖。
        """.trimIndent(),
        options = listOf(
            opt("🔙 继续查线索", "confirm_meeting"),
            opt("📖 翻旧物", "old_stuff")
        )
    ))

    scenes.add(Scene(
        id = "sun_phone_again",
        text = """
            你拨通孙师傅的电话：“孙师傅，您能不能在电话里多说一点？我怕到时候忘了问。”
            他叹了口气：“你爸当年替孟家扛事，但孟建国一直想还他这个人情。他死前留下一封信，让你爸交给一个叫魏国栋的人。但后来那封信被你爸烧了。因为你爸说，有些债，不该让后人背。”
            你怔住了。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🔙 好好想想", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_after_sun",
        text = """
            你打电话给母亲：“妈，孟建国的弟弟还活着，您知道吗？”
            母亲沉默了很久：“我知道。他曾经找过我，想当面道歉。我没见他。”
            “为什么？”
            “因为见了，我就不得不原谅。可我还没准备好。”
            你听着母亲的叹息，觉得自己也开始理解那份复杂的情感。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("🚶 去找孟建军", "find_meng_jun")
        )
    ))

    scenes.add(Scene(
        id = "meng_jun_photo",
        text = """
            你拍下面馆的门面，照片里，孟建军正好抬头看你，眼神里带着一丝慌张。
            你放下手机，走进店里。
        """.trimIndent(),
        options = listOf(
            opt("🤝 坐下谈", "meng_jun_talk")
        )
    ))

    scenes.add(Scene(
        id = "ask_about_threat",
        text = """
            “那封恐吓信是你写的吗？”
            孟建军低下头：“是。我那时候怕你爸供出我哥，一时冲动。后来我后悔了，但你爸已经走了。”
            “你愿意当面道歉吗？”
            “我愿意。”他直视着你，“只要有机会。”
        """.trimIndent(),
        options = listOf(
            opt("🤝 接受道歉", "donate_together"),
            opt("📅 先办正事", "confirm_meeting")
        )
    ))

    scenes.add(Scene(
        id = "call_lawyer_again",
        text = """
            你再次拨通魏律师的电话：“魏律师，我还有一些问题……”
            “周六再说。你只需要知道，你父亲所做的一切，都是为了保护你。有些答案，需要面对面才能给你。”
            他挂了电话，但你心里已经有了一些猜测。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("📖 查旧物", "old_stuff")
        )
    ))

    scenes.add(Scene(
        id = "write_clues",
        text = """
            你在纸上写下所有线索，试图连成一条线。信、借条、照片、车牌、阿珍、孟家、何叔、魏律师……这些碎片渐渐拼成一个模糊的轮廓。
            你感觉真相就在眼前，只差最后一块拼图。
        """.trimIndent(),
        options = listOf(
            opt("📅 周六赴约", "confirm_meeting"),
            opt("📞 给母亲打电话", "call_mother_final_early")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_after_dream",
        text = """
            你给母亲打电话，告诉她梦里的情景。母亲沉默了一会儿，说：“你爸或许真的在看着你。去做你该做的事吧。”
            你感到一种从未有过的笃定。
        """.trimIndent(),
        options = listOf(
            opt("📅 去茶馆", "confirm_meeting"),
            opt("🚶 去阿珍家", "go_to_azhen")
        )
    ))

    scenes.add(Scene(
        id = "sat_morning",
        text = """
            周六一早醒来。窗外的天灰蒙蒙的，像是要下雨。
            换了一件干净的衬衫，镜子里的自己眼下一片乌青。
            出门时楼道很安静。三楼的橘猫蹲在楼梯拐角，舔着爪子，见了人也不躲。
            走过它身边，尾巴尖轻轻动了一下。
            公交车上人不多，选了个靠窗的位置，看着街道两旁的店铺一帧帧后退。
        """.trimIndent(),
        options = listOf(
            opt("☕ 先去喝杯咖啡提神", "coffee_shop"),
            opt("🚶 直接去‘梧桐树’茶馆", "go_teahouse"),
            opt("📱 给魏律师发条消息", "text_lawyer")
        )
    ))

    scenes.add(Scene(
        id = "text_lawyer",
        text = """
            掏出手机，给魏律师发了一条短信：“我已出发，三点准时到。”
            几秒后收到回复：“好的，注意安全。”
            简短得有些敷衍。
            把手机放回口袋，继续赶路。
        """.trimIndent(),
        options = listOf(
            opt("☕ 先喝杯咖啡", "coffee_shop"),
            opt("🚶 直接去茶馆", "go_teahouse")
        )
    ))

    scenes.add(Scene(
        id = "coffee_shop",
        text = """
            走进一家连锁咖啡店，点了杯美式。店员是个戴眼镜的年轻女孩，手法很麻利。
            坐在靠窗的位置，看着外面的人行道上，一个老人推着轮椅，轮椅上坐着另一个老人，慢慢地、慢慢地往前走。
            咖啡的苦涩在舌尖化开，让人清醒了一些。
            还有四十分钟，放下杯子，起身离开。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去‘梧桐树’茶馆", "go_teahouse")
        )
    ))

    scenes.add(Scene(
        id = "go_teahouse",
        text = """
            “梧桐树”茶馆开在一条老巷子里，门脸不大，招牌是木头的，字迹已经有些斑驳。
            门口真的种着一棵梧桐树，枝叶茂盛，遮出一大片阴凉。
            推开门，一股陈年木料和茶叶混合的气味扑面而来。里面很暗，只有几个卡座。
            一个穿灰色中山装的中年男人站起来，朝这边点了点头：“小林？”
            正是照片里站在父亲旁边的那个男人。
        """.trimIndent(),
        options = listOf(
            opt("🤝 走过去坐下", "meeting_talk"),
            opt("🕵️ 先观察一下周围", "observe_teahouse")
        )
    ))

    scenes.add(Scene(
        id = "observe_teahouse",
        text = """
            站在门口，快速扫了一眼室内。
            墙角放着一台老式立式空调，嗡嗡作响。柜台后面的博古架上摆着些紫砂壶，积了灰。
            除了那个中山装男人，角落里还坐着一个戴鸭舌帽的年轻人，低着头看手机，桌上放着一杯没怎么动过的绿茶。
            气氛有些微妙，但还是走了过去。
        """.trimIndent(),
        options = listOf(
            opt("🤝 在中山装男人对面坐下", "meeting_talk")
        )
    ))

    scenes.add(Scene(
        id = "meeting_talk",
        text = """
            在对面坐下。他给倒了一杯茶，动作很稳。
            “你父亲的事，长话短说。”他目光平静，“七年前，你父亲替人顶了一起肇事逃逸的案子。那个人给了他三十万，让他认罪。”
            “但他没有去坐牢。”接话。
            “对。”魏律师喝了一口茶，“因为真正的肇事者，在开庭前三天死了。车祸，意外。”
            茶馆里很安静。角落里的鸭舌帽年轻人，似乎动了一下。
        """.trimIndent(),
        options = listOf(
            opt("❓ 真正的肇事者是谁？", "ask_driver"),
            opt("❓ 那三十万呢？", "ask_money"),
            opt("❓ 你为什么告诉我这些？", "ask_reason"),
            opt("🔍 我想看看那个鸭舌帽的人", "notice_hat_man")
        )
    ))

    scenes.add(Scene(
        id = "notice_hat_man",
        text = """
            侧过头，看向那个戴鸭舌帽的年轻人。
            他似乎察觉到目光，立刻低下头，把手机屏幕凑近。
            魏律师也注意到了，但没有转头，只是轻声说：“别管他。他可能是孟家的人。”
            “孟家？”压低声音。
            “孟建国的儿子。他一直不相信你父亲是无辜的。”魏律师的眼神变得复杂。
        """.trimIndent(),
        options = listOf(
            opt("🔍 继续和魏律师谈话", "ask_driver"),
            opt("🚶 起身去和那个年轻人聊聊", "talk_hat_man")
        )
    ))

    scenes.add(Scene(
        id = "talk_hat_man",
        text = """
            站起身，走到那个年轻人桌前：“你好，我是林建国的儿子。我们能聊聊吗？”
            他抬起头，露出一张年轻却带着敌意的脸：“有什么好聊的？你爸害死了我爸。”
            “我想弄清楚真相。”
            他冷笑一声：“真相？我找了你爸七年，他却死了。现在你来告诉我真相？”
            他站起来，撞开肩膀，头也不回地走出了茶馆。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回到魏律师桌前", "ask_driver"),
            opt("🚶 追出去", "chase_hat_man")
        )
    ))

    scenes.add(Scene(
        id = "chase_hat_man",
        text = """
            追出茶馆，看到那个年轻人快步走进小巷深处。
            加快脚步，在拐角处追上了他：“等一下！我不是来吵架的。”
            他停住脚步，转过身：“你到底想怎样？”
    “我想知道全部。包括你爸和你爸之间的恩怨。”
            他盯着看了很久，然后说：“好，既然你想知道，跟我来。”
            他带进一家小面馆，点了两碗面，开始讲起另一段往事。
        """.trimIndent(),
        options = listOf(
            opt("🍜 坐下听他讲", "meng_son_story"),
            opt("🔙 回茶馆找魏律师", "back_teahouse")
        )
    ))

    scenes.add(Scene(
        id = "meng_son_story",
        text = """
            面端上来了，热气腾腾。他叫孟小军，那年他还在上高中。
            “你爸和我爸以前是过命的交情。但后来，我爸欠了一大笔赌债，你爸帮他还过一次。可我爸又去赌了，最后那次出事，就是因为他输了钱，酒驾撞了人。”
            “你爸替我爸顶罪，我爸觉得愧疚，就给了他三十万。可你爸收了钱，却一分没花，全存了起来，说是要还给孟家。”
            孟小军吃着面，声音有些哽咽：“我爸死后，我恨你爸，觉得是他逼死了我爸。可现在……我好像没那么恨了。”
        """.trimIndent(),
        options = listOf(
            opt("🤝 拍拍他的肩膀", "meng_reconcile"),
            opt("❓ 那你现在想怎么办？", "meng_future")
        )
    ))

    scenes.add(Scene(
        id = "meng_reconcile",
        text = """
            伸手拍了拍他的肩膀：“过去的事，我们都无法改变。但我们可以选择怎么面对未来。”
            他抬起头，眼眶有些红：“那笔钱……你打算怎么处理？”
    “打算捐给事故里受伤的家庭。那是我爸的意思。”
            他沉默了一会儿，然后点点头：“好。这样也好。”
            吃完面，各自离开。巷子里的风，似乎温柔了一些。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回茶馆找魏律师", "back_teahouse"),
            opt("🚶 直接去城西找那家人", "go_west")
        )
    ))

    scenes.add(Scene(
        id = "meng_future",
        text = """
    “我……”孟小军放下筷子，“我想把那笔钱捐出去，替我爸爸赎罪。”
            看着这个年轻人，突然觉得他并不像表现出来的那么冷漠。
    “那笔钱在我一个长辈那里保管，何叔。我们可以一起去找他。”
            他点了点头：“好。”
            约好明天一起去何叔那里。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回茶馆找魏律师", "back_teahouse"),
            opt("🔙 先回家", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "back_teahouse",
        text = """
            回到茶馆，魏律师还在原位，看到进来，微微点头：“谈完了？”
    “嗯，谈完了。”
    “那好，我们继续刚才的话题。”他喝了口茶，“那三十万，你父亲委托给一个叫何叔的人保管。他是个靠谱的人，废品站老板。”
            他写了一个地址递过来。
        """.trimIndent(),
        options = listOf(
            opt("🚶 现在就去废品站", "go_he_direct"),
            opt("📅 明天再去，今天先消化一下", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "ask_driver",
        text = """
    “是谁？”盯着他。
            魏律师沉默了一会儿，从公文包里拿出一张照片，推过来。
            照片上是一个中年男人，国字脸，眉头紧锁。
    “他叫孟建国。你父亲……是在替他顶罪。他们曾经是最好的朋友。”
            看着那张脸，陌生的脸。
            父亲从来没有提起过这个名字。
        """.trimIndent(),
        options = listOf(
            opt("❓ 他现在在哪？", "ask_whereabouts"),
            opt("❓ 那三十万呢？", "ask_money"),
            opt("❓ 你为什么告诉我这些？", "ask_reason"),
            opt("📞 我想见见孟建国的家人", "meet_meng_family")
        )
    ))

    scenes.add(Scene(
        id = "ask_whereabouts",
        text = """
    “孟建国在车祸里受了重伤，之后没多久就去世了。”魏律师说。
    “他死前，把名下一套房产留给了你父亲，作为补偿。你父亲没有要，后来那套房子被法院处理了。”
            所以，父亲什么都没有得到。替人顶了罪，朋友死了，钱也没了，只留下一张借条。
            端起茶杯，发现手在微微发抖。
        """.trimIndent(),
        options = listOf(
            opt("❓ 那三十万呢？", "ask_money"),
            opt("❓ 你为什么告诉我这些？", "ask_reason"),
            opt("📞 我想见见孟建国的家人", "meet_meng_family")
        )
    ))

    scenes.add(Scene(
        id = "ask_money",
        text = """
    “那三十万，你父亲拿了一部分出来，剩下的，一直想退给孟建国的家属。”魏律师说，“但孟建国的家属拒绝接受。他们觉得，是你爸害死了他。”
    “所以这笔钱就一直被保管在一个地方。”
    “在哪？”
    “在你父亲一个老朋友手里。他叫何叔，开废品站的。”
            脑中闪过楼下收废品的三轮车铃声。
        """.trimIndent(),
        options = listOf(
            opt("❓ 为什么孟家觉得是我爸害死了他？", "ask_blame"),
            opt("❓ 那个何叔在哪？", "ask_he"),
            opt("❓ 你为什么告诉我这些？", "ask_reason"),
            opt("🚶 现在就去废品站", "go_he_direct")
        )
    ))

    scenes.add(Scene(
        id = "ask_blame",
        text = """
    “因为那晚，是你父亲约孟建国出去的。”魏律师说，“你父亲说，是他建议孟建国酒后不要开车，但孟建国没听。他觉得自己有责任。”
    “但孟家不这么认为。他们觉得，如果你父亲不约他出去，他就不会出车祸。”
            明白了。这是一笔糊涂账，每个人都有自己的理由，每个人都觉得自己是对的。
            而父亲，选择了用一辈子的沉默来承担这一切。
        """.trimIndent(),
        options = listOf(
            opt("❓ 那个何叔在哪？", "ask_he"),
            opt("❓ 你为什么告诉我这些？", "ask_reason"),
            opt("🚶 去废品站", "go_he_direct")
        )
    ))

    scenes.add(Scene(
        id = "ask_he",
        text = """
    “何叔在城东开废品站，叫‘何记回收’。你去找他，就说是老林的儿子。”
            魏律师在纸上写了个地址，推过来。
    “那笔钱，你父亲的意思是，如果孟家不收，就捐给当年事故里受伤的那个家庭。但一直没找到合适的机会。”
            收好纸条。窗外，梧桐树的影子在风里摇晃。
        """.trimIndent(),
        options = listOf(
            opt("❓ 你为什么告诉我这些？", "ask_reason"),
            opt("🚶 去城东找何叔", "go_he_direct")
        )
    ))

    scenes.add(Scene(
        id = "ask_reason",
        text = """
    “因为时间到了。”魏律师说，“你父亲去世前，委托我，在适当的时候，把这件事告诉你。他说，你有权利知道。”
    “他觉得，你比他更勇敢，更能承受真相。”
            低下头，看着杯里浅绿色的茶水。茶叶一片片沉在杯底，像某种静默的答案。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去城东找何叔", "go_he_direct"),
            opt("🔙 回家再想想", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "meet_meng_family",
        text = """
    “我想见见孟建国的家人。”
            魏律师皱了皱眉：“他儿子可能不太想见你。他叫孟小军，就住在这附近。”
            他给了一个地址。
            决定去试试。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去找孟小军", "meng_son_house"),
            opt("🚶 先去废品站", "go_he_direct")
        )
    ))

    scenes.add(Scene(
        id = "meng_son_house",
        text = """
            找到孟小军的住处，是一间老旧的出租屋。门没关紧，轻轻敲了敲。
            他打开门，看到来人，愣了一下：“你怎么找到这的？”
    “魏律师告诉我的。我想和你谈谈。”
            他犹豫了一下，侧身让进去。
            屋里很乱，桌上摆着几张他父亲的照片。他给倒了杯水，然后坐在床上，等着开口。
        """.trimIndent(),
        options = listOf(
            opt("🤝 我先道歉", "meng_apology"),
            opt("📖 直接说明来意", "meng_purpose")
        )
    ))

    scenes.add(Scene(
        id = "meng_apology",
        text = """
    “对不起。”说，“我知道你恨我爸，但很多事，我们都不清楚全貌。”
            他抬起头，眼眶微红：“我知道。我也查过，你爸确实帮了我爸很多。我只是……不知道怎么放下。”
    “我们可以一起放下。”
            沉默地坐了一会儿，然后他问：“那笔钱，你打算怎么办？”
        """.trimIndent(),
        options = listOf(
            opt("💰 我想捐给受害者家属", "meng_agree_donate"),
            opt("💰 我想还给你", "meng_refund")
        )
    ))

    scenes.add(Scene(
        id = "meng_purpose",
        text = """
    “我来，是想告诉你，那笔钱我会捐给当年事故的受害者家庭。这是我爸的遗愿。”
            孟小军沉默了很久，然后说：“那笔钱，本来就不该是你们家的。捐出去也好，算是替我爸赎罪。”
            他站起来，主动伸出了手：“我们扯平了。”
            握住了他的手。
        """.trimIndent(),
        options = listOf(
            opt("🤝 那我们一起去做这件事", "go_he_together"),
            opt("🚶 我先去处理，回头联系你", "go_he_direct")
        )
    ))

    scenes.add(Scene(
        id = "meng_agree_donate",
        text = """
    “捐出去？你爸的意思？”他问。
    “对。他一直没有放弃这个想法。”
            孟小军叹了口气：“好。我也愿意出一部分，是我自己打工攒的，不多。”
            感到一丝欣慰：“谢谢。”
            约好明天一起去何叔那里。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家准备", "go_home_early")
        )
    ))

    scenes.add(Scene(
        id = "meng_refund",
        text = """
    “这笔钱应该还给你，毕竟是你爸的钱。”说。
            孟小军摇了摇头：“不，这钱是你爸的。他替我爸还了债，这钱就是他的。怎么用，你说了算。”
            他把决定权交了过来。
            感到沉甸甸的责任。
        """.trimIndent(),
        options = listOf(
            opt("💰 还是捐出去吧", "meng_agree_donate"),
            opt("📦 先保管起来，以后再说", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "go_he_together",
        text = """
            第二天，和孟小军一起去了废品站。
            何叔看到两人一起出现，先是一愣，然后笑了：“好啊，你们能走到一起，老林和老孟泉下有知，也该瞑目了。”
            他拿出那个存折，当着面，把三十万取了出来。
            三个人，一起去城西找到了那户人家。
        """.trimIndent(),
        options = listOf(
            opt("🚶 走进那户人家", "enter_west_house")
        )
    ))

    // ============================================================
    // 废品站与何叔
    // ============================================================
    scenes.add(Scene(
        id = "go_he_direct",
        text = """
            直接打车去了城东的废品站。铁皮围成的院子里，堆满了各种废铁和旧纸板。
            一个精瘦的中年男人正蹲在地上，拆一台报废的洗衣机。
            他抬起头，脸上有一道旧疤，眼神很亮：“找谁？”
    “何叔？我是林建国的儿子。”
            他手上的动作停住了，打量了好一会儿，然后站起身，拍拍手上的灰：“进来坐吧。”
            他带进一间用铁皮搭成的屋子，里面很乱，但有一张干净的桌子和两把椅子。
        """.trimIndent(),
        options = listOf(
            opt("🤝 说明来意", "he_talk"),
            opt("🔍 先看看屋里的情况", "observe_he_room")
        )
    ))

    scenes.add(Scene(
        id = "observe_he_room",
        text = """
            走进屋子，快速扫视了一圈。
            墙上挂着一幅旧挂历，是七年前的。桌上放着一部老式收音机，还有半瓶二锅头。
            角落里堆着一些书籍，大多是旧小说和机械维修手册。
            墙上钉着一块木板，上面用图钉按着几张照片，其中一张是父亲和何叔的合影，背景就是这个废品站。
            照片里，父亲笑得很开心。
        """.trimIndent(),
        options = listOf(
            opt("🤝 在何叔对面坐下", "he_talk")
        )
    ))

    scenes.add(Scene(
        id = "he_talk",
        text = """
            坐下来，何叔给倒了杯白开水。
    “魏律师让我来找您。”说，“关于那笔钱。”
            何叔点了点头：“三十万，我一分没动。存了死期，利息也都在。”他从一个铁皮柜里拿出一个存折，推过来。
            看着那上面的数字，有些恍惚。
    “你爸是个犟种，”何叔说，“他认准的事，九头牛都拉不回来。他觉得自己欠孟家的，就一直背着这个包袱。”
        """.trimIndent(),
        options = listOf(
            opt("❓ 何叔，您觉得我爸做错了吗？", "he_opinion"),
            opt("📖 我想看看我爸留给我的东西", "he_keepsake"),
            opt("🤝 这钱，我想按我爸的意思处理", "handle_money"),
            opt("📦 何叔，您还有什么关于我爸的事没告诉我？", "he_more_stories")
        )
    ))

    scenes.add(Scene(
        id = "he_opinion",
        text = """
            何叔沉默了很久，给自己倒了杯二锅头，一仰脖喝了下去。
    “你爸……他唯一做错的，就是太把别人当回事，不把自己当回事。”他抹了把嘴，“他这辈子，就想着怎么让所有人满意，结果自己把自己给累死了。”
            他看着：“你比你爸活得明白。”
            笑了笑，没有回答。不知道自己活得到底明不明白。
        """.trimIndent(),
        options = listOf(
            opt("📖 我想看看我爸留给我的东西", "he_keepsake"),
            opt("🤝 这钱，我想按我爸的意思处理", "handle_money"),
            opt("📦 何叔，还有别的吗？", "he_more_stories")
        )
    ))

    scenes.add(Scene(
        id = "he_keepsake",
        text = """
            何叔从柜子深处取出一个布包，打开，里面是一只旧手表。
            表盘已经有些磨损，但指针还在走。
    “这是你爸临终前让我交给你的。他说，这是他第一次领工资买的，想留给你。”
            接过那只表，沉甸甸的。手腕上，自己的表显示现在是下午四点半。
            戴在手上，扣好表带。
            金属贴在皮肤上，有一种微凉的温度。
        """.trimIndent(),
        options = listOf(
            opt("🤝 这钱，我想按我爸的意思处理", "handle_money"),
            opt("🚶 拿着钱，先离开这里", "leave_with_money"),
            opt("📦 何叔，再给我讲讲我爸以前的事吧", "he_stories_detail")
        )
    ))

    scenes.add(Scene(
        id = "he_stories_detail",
        text = """
            何叔点了一支烟，深深吸了一口：“你爸啊，年轻时是个好司机，技术好，人也仗义。有一次在盘山路上，他为了避让一辆失控的货车，自己把车开到沟里，差点没了命。”
    “从那以后，他就特别惜命，也特别珍惜朋友。孟建国就是那次事故里第一个赶到现场救他的人。”
    “所以你爸一直觉得，这条命是孟建国给的。”
            烟雾缭绕中，何叔的眼神变得很远。
        """.trimIndent(),
        options = listOf(
            opt("🤝 那这钱，我捐出去", "handle_money"),
            opt("📖 明白了，谢谢何叔", "leave_with_money")
        )
    ))

    scenes.add(Scene(
        id = "he_more_stories",
        text = """
    “还有一件事。”何叔压低声音，“你爸曾经收到过一封恐吓信，说如果再替孟建国扛着，就对你不利。他当时很害怕，所以后来才把大部分钱交给我保管，自己带着你妈搬到了现在的城市。”
            心头一紧：“那封信还在吗？”
    “我留着呢。”何叔从铁皮柜的最底层翻出一个信封，递过来。
            信上只有打印的一行字：“管好自己的嘴，否则你儿子会有麻烦。”
            没有落款。
        """.trimIndent(),
        options = listOf(
            opt("📞 报警", "police_report"),
            opt("🔍 我自己查查是谁写的", "investigate_threat"),
            opt("📅 先和魏律师商量", "contact_lawyer_threat")
        )
    ))

    scenes.add(Scene(
        id = "police_report",
        text = """
            拿着恐吓信，去了附近的派出所。
            值班警察看了看信，摇摇头：“七年了，很难查。而且没有实质性伤害，我们只能备案。”
            虽然早有预料，但心里还是凉了半截。
            走出派出所，太阳已经西斜，把影子拉得很长。
        """.trimIndent(),
        options = listOf(
            opt("🔍 自己查查看", "investigate_threat"),
            opt("📅 先和魏律师商量", "contact_lawyer_threat")
        )
    ))

    scenes.add(Scene(
        id = "investigate_threat",
        text = """
            仔细看那封信，打印纸很普通，墨迹也看不出特征。
            但注意到信封上的邮戳，是省城某个区域的邮政所。
            决定去那个邮政所问问，看看能不能调出当年的监控。
            虽然希望渺茫，但还是想试试。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去省城的邮政所", "post_office"),
            opt("📅 先和魏律师商量", "contact_lawyer_threat")
        )
    ))

    scenes.add(Scene(
        id = "post_office",
        text = """
            坐车去了省城，找到那个邮政所。
            七年前的监控早就覆盖了，工作人员也很无奈。
            走出邮政所，站在街边，感到一阵无力。
            这时手机响了，是一个陌生号码。
            接起来，是个男人的声音：“你是林建国的儿子？那封信是我寄的。”
            愣住了。
        """.trimIndent(),
        options = listOf(
            opt("📞 你是谁？", "who_are_you"),
            opt("❓ 你为什么威胁我爸？", "why_threat")
        )
    ))

    scenes.add(Scene(
        id = "who_are_you",
        text = """
    “我是孟建国的弟弟，孟建军。”那个声音带着一丝愧疚，“当年我怕你爸把孟建国供出来，所以写了那封信。但我后来知道，你爸从来没有出卖过我们孟家。”
    “对不起。”
            握着手机，半天说不出话。
            这个道歉，迟到了七年。
        """.trimIndent(),
        options = listOf(
            opt("📞 你现在在哪？我们见个面", "meet_meng_jun"),
            opt("🤝 我接受你的道歉", "accept_apology")
        )
    ))

    scenes.add(Scene(
        id = "meet_meng_jun",
        text = """
            和孟建军约在了一家小饭馆。他看起来比实际年龄老，脸上带着风霜。
            他给倒了杯酒：“我哥出事那年，我才三十岁，什么都不懂。我只想保住我哥的名声，结果做了错事。”
    “你爸从来没有怪过我。”他喝了一大口酒，“现在我告诉你这些，是希望你能原谅我。”
            看着他的眼睛，点了点头：“我原谅你了。”
            那一刻，觉得压在心里的一块石头，终于碎了。
        """.trimIndent(),
        options = listOf(
            opt("🤝 那这钱，我们一起捐出去", "donate_together"),
            opt("🔙 回家，我需要平静一下", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "accept_apology",
        text = """
    “我原谅你了。”说。
            电话那头沉默了很久，然后他轻声说：“谢谢。那笔钱，如果你要捐，告诉我一声，我也想尽一份力。”
            挂了电话，站在邮政所门口，看着天空，第一次觉得云那么轻。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去废品站，把钱取出来", "go_he_direct"),
            opt("🔙 回家", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "contact_lawyer_threat",
        text = """
            打电话给魏律师，告诉他恐吓信的事。
            魏律师沉默了一会儿：“这件事我也有耳闻，但没证据。你父亲当年不想追究，怕连累你。现在既然知道了，小心一点为好。”
            他建议先不要轻举妄动，等把捐款的事处理完再说。
            采纳了他的建议。
        """.trimIndent(),
        options = listOf(
            opt("🚶 先去处理捐款", "handle_money"),
            opt("🔙 回家，想想下一步", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "handle_money",
        text = """
    “何叔，这笔钱，我想捐给城西那户受害者家庭。”
            何叔看着，笑了：“好。你爸要是还在，一定会很高兴。”
            他告诉，那个家庭住在城西的安居小区，女主人姓刘，当年事故中失去了丈夫，自己也受了重伤。
            拿上存折，告别何叔，前往城西。
        """.trimIndent(),
        options = listOf(
            opt("🚶 去城西小区", "go_west")
        )
    ))

    scenes.add(Scene(
        id = "leave_with_money",
        text = """
            拿着存折，走出了废品站。天色已经有些暗了。
            这笔钱，沉甸甸的，像一块石头压在口袋里。
            站在路边，一时不知道该怎么办。
            路的对面，有一棵老槐树，一个老人正坐在树下乘凉，手里的蒲扇缓缓地摇着。
            深吸一口气，决定先回家再说。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家，再想想", "go_home_think"),
            opt("💸 去银行把钱取出来", "withdraw_money"),
            opt("🔒 独自面对，不想再管", "ending_alone")
        )
    ))

    // ============================================================
    // 城西小区与受害者家庭
    // ============================================================
    scenes.add(Scene(
        id = "go_west",
        text = """
            城西的小区很旧，墙壁上爬满了干枯的藤蔓。
            按着何叔给的地址，找到了一栋楼的顶层。
            开门的是一个头发花白的中年女人，她的左臂袖管是空荡荡的。
    “您找谁？”她的声音很客气。
    “阿姨您好，我……是关于七年前那起交通事故的。”
            她的表情瞬间变了，眼神里闪过一丝复杂的情绪，然后她侧过身：“进来吧。”
        """.trimIndent(),
        options = listOf(
            opt("🚶 走进屋", "enter_west_house"),
            opt("🔍 站在门口再说几句", "doorstep_talk")
        )
    ))

    scenes.add(Scene(
        id = "doorstep_talk",
        text = """
    “是这样的，我父亲是林建国，当年……他也和那件事有关。”尽可能让自己的声音平稳，“他留下一笔钱，想补偿给受害者家庭。”
            女人的眼眶一下子红了，她别过头去，用手背擦了擦眼睛。
    “不用了。”她说，“这么多年了，我已经不恨了。你父亲也走了，都过去了。”
    “钱不是我给的，是我父亲的心意。”说。
            她沉默了很久，然后终于侧过身：“……进来说吧。”
        """.trimIndent(),
        options = listOf(
            opt("🚶 走进屋", "enter_west_house")
        )
    ))

    scenes.add(Scene(
        id = "enter_west_house",
        text = """
            屋里很简朴，但收拾得很干净。墙上挂着一幅十字绣，绣的是“平安是福”。
            她给倒了杯水，然后坐在对面。
    “那场车祸，撞断了我的胳膊，也撞没了我的丈夫。”她说，“但肇事的人后来也死了。老天爷算是公平了。”
            把存折放在桌上：“阿姨，这钱您收下。不是为了赔偿，只是为了……让我父亲安心。”
            她看着那本存折，眼泪终于掉了下来。
        """.trimIndent(),
        options = listOf(
            opt("🤝 默默陪她坐一会儿", "west_silence"),
            opt("📝 给她留下联系方式", "west_contact"),
            opt("❓ 阿姨，您当时在现场，看到了什么？", "west_ask_details")
        )
    ))

    scenes.add(Scene(
        id = "west_ask_details",
        text = """
            她擦了擦眼泪，缓缓开口：“那天晚上，我和我丈夫散步回家，走到那条国道边。突然一辆车从后面冲过来，我丈夫把我推开，自己却被撞了。”
    “那辆车停都没停，直接开走了。但我看清了驾驶座上的人，是个戴帽子的男人。”
    “后来警察给我看照片，我认出了其中一个人，就是你父亲。但我不确定是不是他开的车，因为太暗了。”
            心跳加速：“那您记得那个男人的特征吗？”
    “他……戴着一顶灰色的鸭舌帽。”她说。
            立刻想起了茶馆里那个年轻人，孟小军，也戴着鸭舌帽。但这可能是巧合。
        """.trimIndent(),
        options = listOf(
            opt("🔍 继续追问其他细节", "west_more_details"),
            opt("🤝 先放下钱，告辞", "west_silence")
        )
    ))

    scenes.add(Scene(
        id = "west_more_details",
        text = """
    “那顶帽子，您记得有什么特别的标志吗？”
            她想了想：“帽檐上好像有一个小标签，像是某个品牌的标志。但我记不清了。”
            记下这个信息，然后郑重地把存折放在她手里：“阿姨，这钱您收好。”
            她握着存折，手在颤抖：“孩子，谢谢你。”
        """.trimIndent(),
        options = listOf(
            opt("📝 留下联系方式", "west_contact"),
            opt("🔙 离开这里", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "west_silence",
        text = """
            谁都没有说话。窗外，几只麻雀在晾衣绳上跳来跳去。
            她把存折推了回来：“孩子，这钱我不能要。你父亲能记得我们，这份心意就已经够了。这钱，你留着，好好过日子。”
            看着她的眼睛，那双眼睛里没有怨恨，只有一种深深的疲惫。
            最终收回了存折。临走时，向她鞠了一躬。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_final"),
            opt("💸 去银行，把钱捐给其他机构", "donate_money")
        )
    ))

    scenes.add(Scene(
        id = "west_contact",
        text = """
            在一张纸上写下手机号和住址，放在桌上。
    “阿姨，以后有什么事，可以找我。”
            她看着那张纸，点了点头。
            从她家出来时，天色已经完全黑了。楼道里的声控灯坏了，摸索着走下楼，脚步声在狭窄的楼梯间里回荡。
            外面，路灯昏黄，一个卖烤红薯的小贩正推着车经过，香气四溢。
        """.trimIndent(),
        options = listOf(
            opt("🍠 买一个烤红薯", "buy_sweet_potato"),
            opt("🔙 回家", "go_home_final")
        )
    ))

    scenes.add(Scene(
        id = "buy_sweet_potato",
        text = """
            买了两个烤红薯，热乎乎的，捧在手心里。
            用勺子挖了一口，很甜，烫得舌尖发麻。
            那个小贩笑了笑，推着车慢慢消失在街道的尽头。
            一边走一边吃，路灯把影子拉得很长很长。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_final")
        )
    ))

    scenes.add(Scene(
        id = "go_west_together",
        text = """
            和孟小军、孟建军一起，去了城西刘阿姨家。
            刘阿姨看到三人，先是一愣，然后看到孟小军，似乎认出了什么。
    “你是……老孟的儿子？”她问。
            孟小军点了点头：“阿姨，对不起。”
            她深深叹了口气：“都过去了。你们能来，我就很高兴了。”
            当着她的面，把三十万存折交给她，她终于收下了。
            她说，会用这笔钱资助几个贫困学生。
            走出小区时，天已经放晴了。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_final"),
            opt("🔄 去茶馆聚会，与大家再叙", "ending_reconcile")
        )
    ))

    scenes.add(Scene(
        id = "donate_together",
        text = """
            孟建军、孟小军和主角一起，将三十万存折郑重地交给刘阿姨。
            刘阿姨握着存折，手在颤抖：“你们……这是何苦呢？”
            “这是我们应该做的。”孟建军说。
            走出小区，阳光暖洋洋的。孟小军提议：“不如去茶馆坐坐？”
            点了点头。
        """.trimIndent(),
        options = listOf(
            opt("🔄 去茶馆聚会", "ending_reconcile"),
            opt("🔙 各自回家", "go_home_final")
        )
    ))

    // ============================================================
    // 回家与内心抉择
    // ============================================================
    scenes.add(Scene(
        id = "go_home_think",
        text = """
            回到出租屋，关上门，背靠着门板站了一会儿。
            屋里很安静，只有冰箱压缩机运行的低沉嗡鸣。
            走到窗边，看了看外面。对面的楼里，家家户户都亮着灯，像一个个温暖的盒子。
            把那封信、那张照片、那块工牌和那只旧手表，一一摆在桌上。
            它们拼凑出一个从未真正了解过的父亲。
        """.trimIndent(),
        options = listOf(
            opt("🛏️ 躺下，看着天花板发呆", "lie_down"),
            opt("📖 写点什么", "write_something"),
            opt("📱 给母亲打个电话", "call_mother_final"),
            opt("🚶 去父亲墓地看看", "go_cemetery"),
            opt("📖 整理父亲留下的所有东西", "organize_stuff"),
            opt("❓ 放弃追查，让往事随风", "ending_unknown")
        )
    ))

    scenes.add(Scene(
        id = "organize_stuff",
        text = """
            把所有东西摊开在床上：旧工牌、铁盒、日记、毛衣、手表、那本书、那张名片。
            一件一件地抚摸过去。父亲的生活痕迹在这些物件上交织，像一张密密麻麻的网。
            最后拿起那只手表，贴在耳边，听见秒针清脆的走动声。
            窗外，月亮升起来了。
        """.trimIndent(),
        options = listOf(
            opt("🛏️ 躺下休息", "lie_down"),
            opt("📖 写下今天的感悟", "write_something"),
            opt("📱 给母亲打电话", "call_mother_final")
        )
    ))

    scenes.add(Scene(
        id = "go_cemetery",
        text = """
            买了一束白菊，去了父亲的墓地。
            墓碑上的照片里，父亲微笑着，眼神温和。蹲下来，把花放在碑前，用手擦了擦照片上的灰尘。
    “爸，我把事情都弄清楚了。你的钱，捐给了刘阿姨。孟家的人也跟我和解了。”
            风轻轻吹过，墓碑旁的柏树沙沙作响。
            坐在那里，陪父亲说了一个下午的话。
            临走时，摸了摸那只旧手表，时间刚好指向五点。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_final"),
            opt("📞 给母亲打个电话", "call_mother_final")
        )
    ))

    scenes.add(Scene(
        id = "call_mother_final",
        text = """
            电话接通了，母亲的声音有些沙哑：“喂？”
    “妈，我把爸的事处理好了。”
            沉默了很久，然后听到她轻轻叹了口气：“那就好。你爸要是知道了，一定会很高兴。儿子，早点睡吧。”
    “嗯，妈，你也早点睡。”
            挂了电话，觉得心里的一块石头，终于落了地。
        """.trimIndent(),
        options = listOf(
            opt("💤 睡觉", "ending_sleep")
        )
    ))

    scenes.add(Scene(
        id = "go_home_final",
        text = """
            回到家，把存折放进抽屉里。
            那只旧手表在手腕上，发出细微的、稳定的嘀嗒声。
            在桌前坐下，看着窗外的城市灯火。车灯汇成河流，在天际线下缓缓流动。
            一切都结束了，一切又好像刚刚开始。
            关上台灯，黑暗中，只有手表表盘的夜光指针，散发着淡淡的、绿色的光。
        """.trimIndent(),
        options = listOf(
            opt("💤 睡觉", "ending_sleep"),
            opt("💡 重新开灯，翻看父亲的相册", "ending_photo"),
            opt("🚶 出门走走，散散步", "ending_walk")
        )
    ))

    scenes.add(Scene(
        id = "lie_down",
        text = """
            躺在床上，天花板上的裂缝像一张地图。
            想起父亲在信里写的那句话：“人这一辈子，最难的不是选择，而是承受选择之后的结果。”
            承受了吗？不知道。
            但至少，做出了选择。
            闭上眼睛，听到楼下收废品的三轮车铃声，由远及近，又渐渐远去。
        """.trimIndent(),
        options = listOf(
            opt("💤 睡觉", "ending_sleep")
        )
    ))

    scenes.add(Scene(
        id = "write_something",
        text = """
            打开一本空白的笔记本，在扉页上写下今天的日期。
            然后写道：“七月十六日，晴。我找到了父亲的过去，也找到了我自己的路。”
            笔尖在纸上沙沙作响，像一个安静的独白。
            窗外的月亮升起来了，又圆又亮，像一只温柔的眼睛。
        """.trimIndent(),
        options = listOf(
            opt("💤 睡觉", "ending_sleep")
        )
    ))

    scenes.add(Scene(
        id = "withdraw_money",
        text = """
            在银行柜员机前，看着那串数字，手心有些出汗。
            但最终，没有取。拿着存折，走出了银行。
            阳光很烈，眯起眼睛。
            这三十万，是一个沉重的句号，也是父亲留下的一个问号。
            决定，用这笔钱，去做一些父亲想做但没做成的事。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家，好好规划一下", "go_home_think")
        )
    ))

    scenes.add(Scene(
        id = "donate_money",
        text = """
            联系了本地一家正规的慈善机构，说明了来意。
            工作人员是个年轻的姑娘，她认真地记录下信息，然后道谢。
            走出慈善机构的大门，感觉整个人都轻了。
            这笔钱，终于去了它该去的地方。
            抬头看了看天，天空很高，很蓝，像父亲年轻时穿的那件工装的颜色。
        """.trimIndent(),
        options = listOf(
            opt("🔙 回家", "go_home_final")
        )
    ))

    scenes.add(Scene(
        id = "go_home_early",
        text = """
            回到家里，关上门，靠在门板上长长地呼出一口气。
            今天的信息量太大了，需要时间消化。
            脱下外套，挂在门边的钩子上，走进厨房给自己倒了杯水。
            看着窗外的暮色，决定先什么都不做，好好休息一晚。
        """.trimIndent(),
        options = listOf(
            opt("🛏️ 早点休息", "sleep_rest"),
            opt("📖 翻翻书", "go_home_think")
        )
    ))

    // ============================================================
    // 结局
    // ============================================================
    scenes.add(Scene(
        id = "ending_sleep",
        text = """
            躺在床上，枕头上残留着阳光晒过的味道。
            意识逐渐模糊，像沉入一片温暖的水域。
            恍惚间，仿佛看到父亲坐在客厅里，手里端着一杯茶，电视正放着新闻。
            他没有回头，只是说了一句：“回来啦。”
            想回答，却发现自己已经发不出声音。
            但那已经不重要了。
            ─── 结局：沉静的归途 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_photo",
        text = """
            打开那本旧相册，一页一页翻过去。
            有父亲年轻时的照片，他穿着一件白衬衫，站在一座大桥上，风吹起他的衣角。
            还有一张是他的毕业照，一群年轻人站在操场上，每个人都笑得无忧无虑。
            翻到最后一页，夹着一张百日照，胖乎乎的，正在流口水。
            照片背后，是父亲的笔迹：“这小子，将来会比我强。”
            一滴水落在照片上，才发现自己流泪了。
            ─── 结局：被遗忘的时光 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_walk",
        text = """
            走出门，沿着街道一直向前走。
            不知道要去哪里，但脚步却异常坚定。
            路过一家书店，买了一本新书。路过一家花店，买了一束白色的雏菊。
            走进公园，在长椅上坐下，看着湖面上的鸭子。
            手机响了，是孟小军发来的消息：“谢谢你。”
            笑了笑，把手机放回口袋。
            ─── 结局：向前的路 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_reconcile",
        text = """
            孟小军、孟建军，还有魏律师，在“梧桐树”茶馆里聚了一次。
            何叔后来也来了，他带来了一瓶老酒。
            围坐一桌，像多年的老朋友。
            碰杯，为了过去，也为了未来。
            窗外，梧桐树的叶子在风中轻轻摇摆，发出沙沙的声音。
            ─── 结局：和解的茶香 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_alone",
        text = """
            最终没有捐出那笔钱，也没有去找孟家的人。
            把存折锁进了抽屉深处，那只手表也放回了盒子里。
            继续过单调的生活，上班、下班、吃饭、睡觉。
            只是偶尔，在某个深夜里，会想起父亲信里的那句话。
            然后，会把台灯调亮，继续读那本泛黄的旧书。
            ─── 结局：沉默的守夜人 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_azhen",
        text = """
            回到阿珍家，把毛衣还给了她。
            她接过毛衣，眼圈红了：“你爸走那天，我本来想还给他的。”
            “阿姨，您留着吧。我爸会希望您留着。”
            她和志强一起，做了一桌菜，留吃了晚饭。
            临走时，志强送出门：“林哥，以后常来。”
            走在路灯下，影子被拉得很长。心里某个角落，好像被填满了。
            ─── 结局：毛衣的温度 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_unknown",
        text = """
            把所有的线索都整理了一遍，却依然不确定真相到底是什么。
            也许真相并不重要，重要的是，在这段追寻里，重新认识了一个叫“父亲”的人。
            把一切都收进铁盒里，放在书架最高处。
            以后，也许不会再打开了。
            但有些东西，已经留在心里了。
            ─── 结局：未解的谜 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    scenes.add(Scene(
        id = "ending_debt",
        text = """
            把那三十万还给了孟家，虽然他们一开始拒绝。
            但最终，孟小军收下了，说会用来做点小生意。
            看着他真诚的眼睛，觉得这或许是最好的归宿。
            走出面馆，阳光正好，斑驳的树影洒在肩上。
            ─── 结局：偿还 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重开", "start", onSelect = { it -> })),
        isEnding = true
    ))

    // 超时结局
    scenes.add(Scene(
        id = "timeout",
        text = """
            时间一分一秒地流逝，你始终没有做出最后的决定。
            或许，有些问题本来就没有标准答案。
            生活还在继续，而你仍然站在原地。
            ─── 结局：时间耗尽 ───
        """.trimIndent(),
        options = listOf(opt("🔄 重新开始", "start", onSelect = { it -> })),
        isEnding = true
    ))

    // ============================================================
    // 填充场景
    // ============================================================

    return scenes.toList()
}

// ============================================================
// 元数据
// ============================================================
data class StoryInfo(
    val title: String,
    val author: String,
    val genre: String,
    val description: String,
    val version: String = "1.0"
)

fun getStoryInfo(): StoryInfo {
    return StoryInfo(
        title = "梧桐树下的回声",
        author = "YuMoo / 含AI内容",
        genre = "生活 / 推理 / 伦理",
        description = "一封匿名信，引出父亲生前最深的秘密。在追问与抉择中，重新理解‘义气’、‘赎罪’与‘和解’的重量。\n\n长度：75389字",
        version = "2.2"
    )
}