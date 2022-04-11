data class Message(
    var messageId: Int = 0,
    val chatId: Int,
    val addressee: String = "",
    val text: String,
    var isRead: Boolean = false
)