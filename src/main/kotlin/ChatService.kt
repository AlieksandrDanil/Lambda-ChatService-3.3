class ChatService {
    private val chats = mutableListOf<Chat>()
    private var currentUserId = ""

    class ChatOrMessageNotFoundException(message: String) : RuntimeException(message)

    fun logIn(userId: String) {
        currentUserId = userId
    }

    fun add(sender: String, receiver: String, directMessage: String): Chat {
        val isSameChat = chats.filter {
            (it.sender == sender && it.receiver == receiver ||
                    (it.sender == receiver && it.receiver == sender))
        }
        if (isSameChat.isNotEmpty())
            throw ChatOrMessageNotFoundException("already exist such chat with sender = $sender and receiver = $receiver")
        val chatId = if (chats.isNotEmpty() && chats.last().chatId > 0) {
            chats.last().chatId + 1
        } else {
            1
        }
        val chat = Chat(
            chatId, sender, receiver, mutableListOf()
        )
        chats += chat
        if (!directMessage.equals(null) && directMessage != "") {
            addMessage(chatId, receiver, directMessage)
        }
        return chat
    }

    fun addMessage(chatId: Int, addressee: String, text: String): Message {
        val chat = getChatById(chatId)
        val messageId = if (chat.messages.isNotEmpty() && chat.messages.last().messageId > 0) {
            chat.messages.last().messageId + 1
        } else {
            1
        }
        val message = Message(messageId, chatId, addressee, text, false)
        chat.messages += message
        return message
    }

    fun delete(chatId: Int): Boolean {
        val chat = chats.find {
            it.chatId == chatId
        }
            ?: throw ChatOrMessageNotFoundException("Chat with id = $chatId not found")
        chat.apply {
            //chats.remove(getChatById(chatId))
            chats.removeIf {
                it.chatId == chatId
            }
            return true
        }
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chats.find {
            it.chatId == chatId
        }
            ?: throw ChatOrMessageNotFoundException("Chat with id = $chatId not found")
        chat.messages.find {
            it.messageId == messageId
        }
            ?: throw ChatOrMessageNotFoundException("Message with id = $messageId not found")
        chat.apply {
            this.messages.removeIf {
                it.messageId == messageId
            }
            if (chat.messages.isEmpty())
                chats.remove(chat)
            return true
        }
    }

    fun getChatInfo(chat: Chat): String {
        return "Chat id: ${chat.chatId}; with ${
            if (chat.receiver.equals(currentUserId, true)) chat.receiver else chat.sender
        }; last message: <${chat.messages.lastOrNull() ?: "сообщений нет"}>"
    }

    fun getMessages(chatId: Int, lastMessageId: Int, count: Int): List<Message> {
        val chat = chats.find {
            it.chatId == chatId
        }
            ?: throw ChatOrMessageNotFoundException("Chat with id = $chatId not found")
        chat.messages.find {
            it.messageId == lastMessageId
        }
            ?: throw ChatOrMessageNotFoundException("Message with id = $lastMessageId not found")
        val latestMessages = chat.messages.takeLastWhile {
            it.messageId > lastMessageId
        }.take(count)
        for (message in latestMessages) {
            if (message.addressee.equals(currentUserId, true)) {
                message.isRead = true
            }
        }
        return latestMessages
    }

    fun getUnreadChatsCount(): List<Chat> {
        return chats.filter {
            it.messages.isNotEmpty()
        }.filter { it.messages.any() }.filter { it ->
            it.messages.any {
                !it.isRead
            }
        }
    }

    private fun getChatById(chatId: Int): Chat {
        val chat = chats.find {
            it.chatId == chatId
        }
        return chat
            ?: throw ChatOrMessageNotFoundException("Chat with id = $chatId not found")
    }

    private fun getMessageByMessageId(chatId: Int, messageId: Int): Message? =
        chats.find {
            it.chatId == chatId
        }?.messages?.find {
            it.messageId == messageId
        }
}