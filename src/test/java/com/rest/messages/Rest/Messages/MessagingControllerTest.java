package com.rest.messages.Rest.Messages;

import org.aspectj.bridge.IMessageContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessagingControllerTest {

    @InjectMocks
    MessagingController messagingController;

    @Mock
    MessageInventoryRepository messageInventoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void verify_message_saved_when_sending_message(){
        messagingController.message(mock(Message.class));

        verify(messageInventoryRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    void verify_that_messages_are_flagged_when_read(){
        MessageEntity messageEntity1 = new MessageEntity("0701234567", "Message 1", false);
        MessageEntity messageEntity2 = new MessageEntity("0701234567", "Message 2", true);
        MessageEntity messageEntity3 = new MessageEntity("0701234567", "Message 3", false);
        MessageEntity messageEntity4 = new MessageEntity("0701234567", "Message 4", false);
        Iterable<MessageEntity> messageEntities = Arrays.asList(messageEntity1, messageEntity2, messageEntity3, messageEntity4);

        when(messageInventoryRepository.findAll()).thenReturn(messageEntities);

        messagingController.readmessages("0701234567");

        verify(messageInventoryRepository, times(3)).save(any());
    }

    @Test
    void verify_that_the_expected_messages_are_returned(){
        MessageEntity messageEntity1 = new MessageEntity("0701234567", "Message 1", false);
        messageInventoryRepository.save(messageEntity1);
        MessageEntity messageEntity2 = new MessageEntity("0701234567", "Message 2", true);
        messageInventoryRepository.save(messageEntity2);
        MessageEntity messageEntity3 = new MessageEntity("0701234567", "Message 3", false);
        messageInventoryRepository.save(messageEntity3);
        MessageEntity messageEntity4 = new MessageEntity("0701234567", "Message 4", false);
        List<MessageEntity> messageEntities = Arrays.asList(messageEntity1, messageEntity2, messageEntity3, messageEntity4);

        when(messageInventoryRepository.findAll()).thenReturn(messageEntities);

        List<MessageEntity> messageEntities1 = messagingController.readSomeMessages("0701234567", "1", "3");

        assertEquals(2, messageEntities1.size());
    }

}