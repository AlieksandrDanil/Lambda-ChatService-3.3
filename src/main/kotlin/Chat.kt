data class Chat(
    val chatId: Int = 0,
    val sender: String = "",
    val receiver: String = "",
    var messages: MutableList<Message>
)
