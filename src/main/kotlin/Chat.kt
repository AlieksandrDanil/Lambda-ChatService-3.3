data class Chat(
    val chatId: Int,
    val userId: Int,
    val receiverUserId: Int,
    var lastMessageId: Int,
    //var directMessages: MutableList<Message>,
    var messagesCount: Int
)
