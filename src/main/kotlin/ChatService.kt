class ChatService {
    private val chats = mutableListOf<Chat>()
    private val messages = mutableListOf<Message>()

    class ChatOrMsgException(message: String) : RuntimeException(message)

    open class Pair<S,R>(var sender: S, var receiver: R)
    class Address(sender: Int, receiver: Int): Pair<Int, Int>(sender, receiver)

//    val <T> List<T>.indexOf: Int
//        get() = this.indexOf

//    data class ToGetGhat(val list: List<Chat>, val chatId: Int)
//    private infix fun List<Chat>.to(chatId: Int) = ToGetGhat(this, chatId)

    fun add(userId: Int, receiverUserId: Int, directMessage: String): Chat {
        chats.find {
            it.userId == userId &&
                    it.receiverUserId == receiverUserId
        } ?: let {
            val chatId = if (chats.isNotEmpty() && chats.last().chatId > 0) {
                chats.last().chatId + 1
            } else {
                1
            }
            if (!directMessage.equals(null) && directMessage != "") {
                addMessage(chatId, directMessage)
            }
            val chat = Chat(
                chatId, userId, receiverUserId, messages.last().messageId, chats.size
            )
            chats += chat
            return chat
        }
        //val predicate = fun(chat: Chat) = chat.userId == 1
        throw ChatOrMsgException("already exist such chat with userId = $userId and receiverId $receiverUserId")
    }

    fun delete() {
    }

    fun addMessage(chatId: Int, text: String): Message {
//        print("Введите сообщение: ")
//        val myDirectMessage = readLine()?.toString()
        val messageId = if (messages.isNotEmpty() && messages.last().messageId > 0) {
            messages.last().messageId + 1
        } else {
            1
        }
        val message = Message(messageId, chatId, text, true)
        messages += message
        return message
    }

    fun deleteMessage(): Boolean {
        return false
    }

    fun getChats() {
        println("Check")
//        try {
//        return chats.filter {
//                it.userId == 1
//            }
//        } catch (e: RuntimeException) {
//            throw ChatOrMsgException("no found any chats with such userId=$userId")
//        }
    }

    fun getChatById(chatId: Int): Chat {
        val chat = chats.find {
            it.chatId == chatId
        }
        return chat ?: throw ChatOrMsgException("no found chat with id $chatId")
    }

    fun getUnreadChatsCount(): List<Chat> {
        val chatIdsWhereUnreadMsg = messages.filter {
            it.unreadMessage
        }
        println(chatIdsWhereUnreadMsg)
        val msg = chatIdsWhereUnreadMsg//.indexOf

        return chats.filter {
            it.chatId == msg.size
            //it.chatId == chatIdsWhereUnreadMsg.find(it.chatId == 1).chatId
        }
    }

    fun clear() {
        chats.clear()
        messages.clear()
    }

    private inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
        return filterTo(ArrayList<T>(), predicate)
    }

//    private fun <T> Iterable<T>.find(list: List<T>, predicate: (T) -> Boolean): T {
//        val it = this.iterator()
//        while (it.hasNext()) {
//            if (it.next() == predicate)
//                it.next()
//        }
//        return it.next()
//    }




    //    private inline fun <T> Iterable<T>.find(predicate: (T) -> Boolean): T {
//        for (element in this) if (predicate(element)) return element
//        throw NoSuchElementException("Collection contains no element matching the predicate.")
//    }

    //val predicate = fun(chat: Chat) = chat.userId == 1
//    private inline fun <T> Iterable<T>.reduce(predicate: (T) -> Boolean): List<T> {
//        return filterTo(ArrayList<T>(), predicate)
//    }

}