package com.techskillplanet.phonics.data

data class WordExample(
    val text: String,
    val tip: String,
)

data class Phoneme(
    val id: String,
    val symbol: String,
    val groupTitle: String,
    val mouthTip: String,
    val words: List<WordExample>,
)

data class PhonemeGroup(
    val title: String,
    val items: List<Phoneme>,
)

object PhonemeRepository {
    val groups: List<PhonemeGroup> = listOf(
        PhonemeGroup(
            title = "短元音",
            items = listOf(
                Phoneme(
                    id = "i_short",
                    symbol = "/ɪ/",
                    groupTitle = "短元音",
                    mouthTip = "嘴角轻轻向两边，声音短，不要拖长。",
                    words = listOf(
                        WordExample("sit", "sit 里练目标音"),
                        WordExample("pig", "pig 里再听一次"),
                        WordExample("fish", "fish 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "e",
                    symbol = "/e/",
                    groupTitle = "短元音",
                    mouthTip = "嘴巴自然咧开，像 pen 中间的短音。",
                    words = listOf(
                        WordExample("pen", "pen 里练目标音"),
                        WordExample("red", "red 里再听一次"),
                        WordExample("bed", "bed 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ae",
                    symbol = "/æ/",
                    groupTitle = "短元音",
                    mouthTip = "嘴巴张大一点，像“啊”和“诶”之间，声音短。",
                    words = listOf(
                        WordExample("cat", "cat 里练目标音"),
                        WordExample("bag", "bag 里再听一次"),
                        WordExample("apple", "apple 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "v_short",
                    symbol = "/ʌ/",
                    groupTitle = "短元音",
                    mouthTip = "嘴巴自然打开，声音短而有力。",
                    words = listOf(
                        WordExample("cup", "cup 里练目标音"),
                        WordExample("sun", "sun 里再听一次"),
                        WordExample("bus", "bus 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "o_short",
                    symbol = "/ɒ/",
                    groupTitle = "短元音",
                    mouthTip = "嘴巴圆一点，短促发出。",
                    words = listOf(
                        WordExample("dog", "dog 里练目标音"),
                        WordExample("box", "box 里再听一次"),
                        WordExample("hot", "hot 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "u_short",
                    symbol = "/ʊ/",
                    groupTitle = "短元音",
                    mouthTip = "嘴唇微圆，声音短，不要读成长音。",
                    words = listOf(
                        WordExample("book", "book 里练目标音"),
                        WordExample("good", "good 里再听一次"),
                        WordExample("look", "look 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "schwa",
                    symbol = "/ə/",
                    groupTitle = "短元音",
                    mouthTip = "最轻的弱读音，嘴巴放松。",
                    words = listOf(
                        WordExample("about", "about 里练目标音"),
                        WordExample("teacher", "teacher 里再听一次"),
                        WordExample("sofa", "sofa 放进单词读"),
                    ),
                ),
            ),
        ),
        PhonemeGroup(
            title = "长元音",
            items = listOf(
                Phoneme(
                    id = "i_long",
                    symbol = "/iː/",
                    groupTitle = "长元音",
                    mouthTip = "嘴角向两边，声音拉长。",
                    words = listOf(
                        WordExample("see", "see 里练目标音"),
                        WordExample("tree", "tree 里再听一次"),
                        WordExample("green", "green 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "a_long",
                    symbol = "/ɑː/",
                    groupTitle = "长元音",
                    mouthTip = "嘴巴打开，声音拉长。",
                    words = listOf(
                        WordExample("car", "car 里练目标音"),
                        WordExample("star", "star 里再听一次"),
                        WordExample("park", "park 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "o_long",
                    symbol = "/ɔː/",
                    groupTitle = "长元音",
                    mouthTip = "嘴唇圆，声音拉长。",
                    words = listOf(
                        WordExample("ball", "ball 里练目标音"),
                        WordExample("door", "door 里再听一次"),
                        WordExample("four", "four 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "u_long",
                    symbol = "/uː/",
                    groupTitle = "长元音",
                    mouthTip = "嘴唇收圆，声音拉长。",
                    words = listOf(
                        WordExample("blue", "blue 里练目标音"),
                        WordExample("food", "food 里再听一次"),
                        WordExample("moon", "moon 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "er_long",
                    symbol = "/ɜː/",
                    groupTitle = "长元音",
                    mouthTip = "舌头放松，发长一点。",
                    words = listOf(
                        WordExample("bird", "bird 里练目标音"),
                        WordExample("girl", "girl 里再听一次"),
                        WordExample("nurse", "nurse 放进单词读"),
                    ),
                ),
            ),
        ),
        PhonemeGroup(
            title = "双元音",
            items = listOf(
                Phoneme(
                    id = "ei",
                    symbol = "/eɪ/",
                    groupTitle = "双元音",
                    mouthTip = "从 /e/ 滑到 /ɪ/，声音会移动。",
                    words = listOf(
                        WordExample("cake", "cake 里练目标音"),
                        WordExample("name", "name 里再听一次"),
                        WordExample("rain", "rain 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ai",
                    symbol = "/aɪ/",
                    groupTitle = "双元音",
                    mouthTip = "从大口音滑到 /ɪ/。",
                    words = listOf(
                        WordExample("bike", "bike 里练目标音"),
                        WordExample("time", "time 里再听一次"),
                        WordExample("kite", "kite 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "oi",
                    symbol = "/ɔɪ/",
                    groupTitle = "双元音",
                    mouthTip = "先圆嘴，再滑到 /ɪ/。",
                    words = listOf(
                        WordExample("boy", "boy 里练目标音"),
                        WordExample("toy", "toy 里再听一次"),
                        WordExample("coin", "coin 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ou",
                    symbol = "/əʊ/",
                    groupTitle = "双元音",
                    mouthTip = "从放松音滑到圆嘴。",
                    words = listOf(
                        WordExample("go", "go 里练目标音"),
                        WordExample("home", "home 里再听一次"),
                        WordExample("nose", "nose 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "au",
                    symbol = "/aʊ/",
                    groupTitle = "双元音",
                    mouthTip = "从大口音滑到圆嘴。",
                    words = listOf(
                        WordExample("cow", "cow 里练目标音"),
                        WordExample("house", "house 里再听一次"),
                        WordExample("mouth", "mouth 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ia",
                    symbol = "/ɪə/",
                    groupTitle = "双元音",
                    mouthTip = "先短 /ɪ/，再滑到弱音。",
                    words = listOf(
                        WordExample("ear", "ear 里练目标音"),
                        WordExample("near", "near 里再听一次"),
                        WordExample("dear", "dear 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ea",
                    symbol = "/eə/",
                    groupTitle = "双元音",
                    mouthTip = "先 /e/，再放松收尾。",
                    words = listOf(
                        WordExample("air", "air 里练目标音"),
                        WordExample("chair", "chair 里再听一次"),
                        WordExample("bear", "bear 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ua",
                    symbol = "/ʊə/",
                    groupTitle = "双元音",
                    mouthTip = "先短 /ʊ/，再滑到弱音。",
                    words = listOf(
                        WordExample("tour", "tour 里练目标音"),
                        WordExample("poor", "poor 里再听一次"),
                        WordExample("sure", "sure 放进单词读"),
                    ),
                ),
            ),
        ),
        PhonemeGroup(
            title = "清辅音",
            items = listOf(
                Phoneme(
                    id = "p",
                    symbol = "/p/",
                    groupTitle = "清辅音",
                    mouthTip = "双唇闭合后轻轻爆破，不震动声带。",
                    words = listOf(
                        WordExample("pen", "pen 里练目标音"),
                        WordExample("pig", "pig 里再听一次"),
                        WordExample("map", "map 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "t",
                    symbol = "/t/",
                    groupTitle = "清辅音",
                    mouthTip = "舌尖顶住上齿龈，轻轻弹开。",
                    words = listOf(
                        WordExample("top", "top 里练目标音"),
                        WordExample("tea", "tea 里再听一次"),
                        WordExample("cat", "cat 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "k",
                    symbol = "/k/",
                    groupTitle = "清辅音",
                    mouthTip = "舌后部抬起，轻轻送气。",
                    words = listOf(
                        WordExample("key", "key 里练目标音"),
                        WordExample("kite", "kite 里再听一次"),
                        WordExample("book", "book 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "f",
                    symbol = "/f/",
                    groupTitle = "清辅音",
                    mouthTip = "上牙轻碰下唇，送气。",
                    words = listOf(
                        WordExample("fish", "fish 里练目标音"),
                        WordExample("face", "face 里再听一次"),
                        WordExample("leaf", "leaf 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "th_clear",
                    symbol = "/θ/",
                    groupTitle = "清辅音",
                    mouthTip = "舌尖轻放牙齿之间，送气。",
                    words = listOf(
                        WordExample("three", "three 里练目标音"),
                        WordExample("think", "think 里再听一次"),
                        WordExample("mouth", "mouth 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "s",
                    symbol = "/s/",
                    groupTitle = "清辅音",
                    mouthTip = "舌尖靠近上齿龈，像小蛇吐气。",
                    words = listOf(
                        WordExample("sun", "sun 里练目标音"),
                        WordExample("sit", "sit 里再听一次"),
                        WordExample("bus", "bus 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "sh",
                    symbol = "/ʃ/",
                    groupTitle = "清辅音",
                    mouthTip = "嘴唇稍圆，发 sh 的气音。",
                    words = listOf(
                        WordExample("ship", "ship 里练目标音"),
                        WordExample("she", "she 里再听一次"),
                        WordExample("fish", "fish 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "tsh",
                    symbol = "/tʃ/",
                    groupTitle = "清辅音",
                    mouthTip = "像 ch，短促发出。",
                    words = listOf(
                        WordExample("chair", "chair 里练目标音"),
                        WordExample("chicken", "chicken 里再听一次"),
                        WordExample("watch", "watch 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "h",
                    symbol = "/h/",
                    groupTitle = "清辅音",
                    mouthTip = "轻轻哈气，不要太重。",
                    words = listOf(
                        WordExample("hat", "hat 里练目标音"),
                        WordExample("home", "home 里再听一次"),
                        WordExample("hello", "hello 放进单词读"),
                    ),
                ),
            ),
        ),
        PhonemeGroup(
            title = "浊辅音",
            items = listOf(
                Phoneme(
                    id = "b",
                    symbol = "/b/",
                    groupTitle = "浊辅音",
                    mouthTip = "双唇闭合后爆破，声带震动。",
                    words = listOf(
                        WordExample("bag", "bag 里练目标音"),
                        WordExample("boy", "boy 里再听一次"),
                        WordExample("cab", "cab 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "d",
                    symbol = "/d/",
                    groupTitle = "浊辅音",
                    mouthTip = "舌尖弹开，声带震动。",
                    words = listOf(
                        WordExample("dog", "dog 里练目标音"),
                        WordExample("desk", "desk 里再听一次"),
                        WordExample("red", "red 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "g",
                    symbol = "/g/",
                    groupTitle = "浊辅音",
                    mouthTip = "舌后部抬起，声带震动。",
                    words = listOf(
                        WordExample("girl", "girl 里练目标音"),
                        WordExample("go", "go 里再听一次"),
                        WordExample("bag", "bag 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "v",
                    symbol = "/v/",
                    groupTitle = "浊辅音",
                    mouthTip = "上牙轻碰下唇，声带震动。",
                    words = listOf(
                        WordExample("van", "van 里练目标音"),
                        WordExample("very", "very 里再听一次"),
                        WordExample("five", "five 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "th_voice",
                    symbol = "/ð/",
                    groupTitle = "浊辅音",
                    mouthTip = "舌尖轻放牙齿之间，声带震动。",
                    words = listOf(
                        WordExample("this", "this 里练目标音"),
                        WordExample("that", "that 里再听一次"),
                        WordExample("mother", "mother 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "z",
                    symbol = "/z/",
                    groupTitle = "浊辅音",
                    mouthTip = "像 /s/，但声带震动。",
                    words = listOf(
                        WordExample("zoo", "zoo 里练目标音"),
                        WordExample("zero", "zero 里再听一次"),
                        WordExample("nose", "nose 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "zh",
                    symbol = "/ʒ/",
                    groupTitle = "浊辅音",
                    mouthTip = "像 /ʃ/，但声带震动。",
                    words = listOf(
                        WordExample("measure", "measure 里练目标音"),
                        WordExample("vision", "vision 里再听一次"),
                        WordExample("usual", "usual 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "dzh",
                    symbol = "/dʒ/",
                    groupTitle = "浊辅音",
                    mouthTip = "像 j，短促发出。",
                    words = listOf(
                        WordExample("jam", "jam 里练目标音"),
                        WordExample("job", "job 里再听一次"),
                        WordExample("orange", "orange 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "r",
                    symbol = "/r/",
                    groupTitle = "浊辅音",
                    mouthTip = "舌头卷起但不要碰到上颚。",
                    words = listOf(
                        WordExample("red", "red 里练目标音"),
                        WordExample("rain", "rain 里再听一次"),
                        WordExample("rabbit", "rabbit 放进单词读"),
                    ),
                ),
            ),
        ),
        PhonemeGroup(
            title = "其他辅音",
            items = listOf(
                Phoneme(
                    id = "m",
                    symbol = "/m/",
                    groupTitle = "其他辅音",
                    mouthTip = "双唇闭合，鼻音出来。",
                    words = listOf(
                        WordExample("map", "map 里练目标音"),
                        WordExample("milk", "milk 里再听一次"),
                        WordExample("home", "home 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "n",
                    symbol = "/n/",
                    groupTitle = "其他辅音",
                    mouthTip = "舌尖顶住上齿龈，鼻音出来。",
                    words = listOf(
                        WordExample("name", "name 里练目标音"),
                        WordExample("nine", "nine 里再听一次"),
                        WordExample("sun", "sun 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ng",
                    symbol = "/ŋ/",
                    groupTitle = "其他辅音",
                    mouthTip = "舌后部抬起，鼻音出来。",
                    words = listOf(
                        WordExample("sing", "sing 里练目标音"),
                        WordExample("king", "king 里再听一次"),
                        WordExample("long", "long 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "l",
                    symbol = "/l/",
                    groupTitle = "其他辅音",
                    mouthTip = "舌尖顶住上齿龈，声音从两侧出来。",
                    words = listOf(
                        WordExample("leg", "leg 里练目标音"),
                        WordExample("like", "like 里再听一次"),
                        WordExample("ball", "ball 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "j",
                    symbol = "/j/",
                    groupTitle = "其他辅音",
                    mouthTip = "像 yes 开头的轻音。",
                    words = listOf(
                        WordExample("yes", "yes 里练目标音"),
                        WordExample("yellow", "yellow 里再听一次"),
                        WordExample("you", "you 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "w",
                    symbol = "/w/",
                    groupTitle = "其他辅音",
                    mouthTip = "嘴唇先圆，再放开。",
                    words = listOf(
                        WordExample("we", "we 里练目标音"),
                        WordExample("water", "water 里再听一次"),
                        WordExample("window", "window 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "tr",
                    symbol = "/tr/",
                    groupTitle = "其他辅音",
                    mouthTip = "先 /t/ 再快速滑到 /r/。",
                    words = listOf(
                        WordExample("tree", "tree 里练目标音"),
                        WordExample("train", "train 里再听一次"),
                        WordExample("try", "try 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "dr",
                    symbol = "/dr/",
                    groupTitle = "其他辅音",
                    mouthTip = "先 /d/ 再快速滑到 /r/。",
                    words = listOf(
                        WordExample("dream", "dream 里练目标音"),
                        WordExample("drive", "drive 里再听一次"),
                        WordExample("dress", "dress 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "ts",
                    symbol = "/ts/",
                    groupTitle = "其他辅音",
                    mouthTip = "先 /t/ 再接 /s/，短促。",
                    words = listOf(
                        WordExample("cats", "cats 里练目标音"),
                        WordExample("hats", "hats 里再听一次"),
                        WordExample("boats", "boats 放进单词读"),
                    ),
                ),
                Phoneme(
                    id = "dz",
                    symbol = "/dz/",
                    groupTitle = "其他辅音",
                    mouthTip = "先 /d/ 再接 /z/，声带震动。",
                    words = listOf(
                        WordExample("beds", "beds 里练目标音"),
                        WordExample("birds", "birds 里再听一次"),
                        WordExample("cards", "cards 放进单词读"),
                    ),
                ),
            ),
        ),
    )

    val phonemes: List<Phoneme> = groups.flatMap { it.items }

    fun findById(id: String): Phoneme = phonemes.firstOrNull { it.id == id } ?: phonemes.first()

    fun firstUnfinished(completedIds: List<String>): Phoneme {
        val done = completedIds.toSet()
        return phonemes.firstOrNull { it.id !in done } ?: phonemes.first()
    }
}
