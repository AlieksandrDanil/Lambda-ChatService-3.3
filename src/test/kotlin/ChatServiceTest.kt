import org.junit.Test
import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun add() {
        val service = ChatService()
        service.clear()
        val chat1 = service.add(1,5,"Initial first msg")
        val chat2 = service.add(6,1,"Initial second msg")

        val result = listOf(chat1, chat2)
        val expected = mutableListOf(
            Chat(chatId=1, userId=1, receiverUserId=5, lastMessageId=1, messagesCount=0),
            Chat(chatId=2, userId=6, receiverUserId=1, lastMessageId=2, messagesCount=1)
        )
        assertEquals(expected,result)
    }


    @Test
    fun addMessage() {
        val service = ChatService()
        service.clear()
        service.add(1,5,"Initial first msg")
        service.add(6,1,"Initial second msg")
        service.addMessage(1, "First Msg")
        service.addMessage(1, "Second Msg")
        service.addMessage(2, "Third Msg")
        service.addMessage(2, "Fourth Msg")
        val result = service.addMessage(2, "Fifth Msg")

        val expected =
//            Message(messageId=1, chatId=1, text="Initial first msg", unreadMessage=true),
//            Message(messageId=2, chatId=2, text="Initial second msg", unreadMessage=true),
//            Message(messageId=3, chatId=1, text="First Msg", unreadMessage=true),
//            Message(messageId=4, chatId=1, text="Second Msg", unreadMessage=true),
//            Message(messageId=5, chatId=2, text="Third Msg", unreadMessage=true),
//            Message(messageId=6, chatId=2, text="Fourth Msg", unreadMessage=true),
            Message(messageId=7, chatId=2, text="Fifth Msg", unreadMessage=true)
        assertEquals(expected, result)
    }

    @Test//(expected = ChatService.ChatOrMsgException::class)
    fun getChats() {
        val service = ChatService()
        service.clear()
        service.add(1,5,"Initial first msg")
        service.add(6,1,"Initial second msg")
        service.addMessage(1, "First Msg")
        service.addMessage(1, "Second Msg")
        service.addMessage(2, "Third Msg")
        service.addMessage(2, "Fourth Msg")
        service.addMessage(2, "Fifth Msg")

        getChats()
        //        val result = getChats()
//        val expected = mutableListOf()
//        assertEquals(expected,result)
    }

    @Test
    fun getChatById() {
    }

}