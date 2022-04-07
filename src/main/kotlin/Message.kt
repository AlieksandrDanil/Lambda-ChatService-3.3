data class Message(
    var messageId: Int,
    val chatId: Int,
//    val senderUserId: Int,
//    val receiverUserId: Int,
    val text: String,
    val unreadMessage: Boolean = true
)