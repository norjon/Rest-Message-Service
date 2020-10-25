package com.rest.messages.Rest.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
public class MessagingController {

	@Autowired
	private MessageInventoryRepository messageInventoryRepository;

	/**
	 * Read messages for a given phone number.
	 * @param phoneNumber
	 * @return
	 */
	@GetMapping("/readmessages")
	public List<String> readmessages(@RequestParam(value = "phonenumber") String phoneNumber) {

		Stream<MessageEntity> stream = StreamSupport.stream(messageInventoryRepository.findAll().spliterator(), false);

		List<MessageEntity> messageEntities = stream.filter(messageEntity -> !messageEntity.isHasBeenRead()).filter(messageEntity -> messageEntity.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());

		messageEntities.forEach(messageEntity -> {
			messageEntity.setHasBeenRead(true);
			messageInventoryRepository.save(messageEntity);});

		return messageEntities.stream().map(messageEntity -> messageEntity.getMessage()).collect(Collectors.toList());
	}

	/**
	 * Add som initial test data. This simplifies testing the client.
	 */
	@GetMapping("/inittestdata")
	public void initTestData() {

		MessageEntity messageEntity1 = new MessageEntity("0701234567", "Message 1", false);
		messageInventoryRepository.save(messageEntity1);
		MessageEntity messageEntity2 = new MessageEntity("0701234567", "Message 2", true);
		messageInventoryRepository.save(messageEntity2);
		MessageEntity messageEntity3 = new MessageEntity("0701234567", "Message 3", false);
		messageInventoryRepository.save(messageEntity3);
		MessageEntity messageEntity4 = new MessageEntity("0701234567", "Message 4", false);
		messageInventoryRepository.save(messageEntity4);

	}

	/**
	 * Read messages sorted by timestamp and select from the list given start and stop indices.
	 * @param phoneNumber
	 * @param start
	 * @param stop
	 * @return
	 */
	@GetMapping("/readsomemessages")
	public List<MessageEntity> readSomeMessages(@RequestParam(value = "phonenumber") String phoneNumber,
										 @RequestParam(value = "start") String start,
										 @RequestParam(value = "stop") String stop
										 ) {
		Comparator<MessageEntity> messageEntityComparator
				= Comparator.comparing(MessageEntity::getTimestamp);
		Stream<MessageEntity> stream = StreamSupport.stream(messageInventoryRepository.findAll().spliterator(), false);

		List<MessageEntity> messageEntities = stream.filter(messageEntity -> messageEntity.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
		messageEntities.sort(messageEntityComparator);

		if(Integer.parseInt(start) < messageEntities.size() && Integer.parseInt(stop) < messageEntities.size()) {
			List<MessageEntity> messageEntities1 = messageEntities.subList(Integer.parseInt(start), Integer.parseInt(stop));

			return messageEntities1;
		}

		return messageEntities;
	}

	/**
	 * Read all messages for all phone numbers
	 * @return
	 */
	@GetMapping("/readallmessages")
	public List<MessageEntity> readAllMessages() {

		Stream<MessageEntity> stream = StreamSupport.stream(messageInventoryRepository.findAll().spliterator(), false);

		List<MessageEntity> messageEntities = stream.collect(Collectors.toList());

		return messageEntities;
	}

	/**
	 * Send a message containing phonenumber, message and status indicating if it has been read by receiver.
	 * @param message
	 * @return
	 */
	@PostMapping("/sendmessage")
	Message message(@RequestBody Message message) {
		messageInventoryRepository.save(new MessageEntity(message.getPhoneNumber(),message.getMessage(),false));
		return message;
	}

	/**
	 * Delete message according to criterias set on the DeleteMessage object. It will delete matching messages.
	 * @param deleteMessages
	 * @return
	 */
	@PostMapping("/deletemessages")
	DeleteMessage deleteMessages(@RequestBody DeleteMessage deleteMessages) {
		Stream<MessageEntity> stream = StreamSupport.stream(messageInventoryRepository.findAll().spliterator(), false);

		List<MessageEntity> messageEntities = stream
				.filter(messageEntity -> deleteMessages.getMessage() == null || messageEntity.getMessage().equals(deleteMessages.getMessage()))
				.filter(messageEntity -> deleteMessages.getPhoneNumber() == null || messageEntity.getPhoneNumber().equals(deleteMessages.getPhoneNumber()))
				.filter(messageEntity -> deleteMessages.getTimestamp() == null || messageEntity.getTimestamp().equals(deleteMessages.getTimestamp()))
				.collect(Collectors.toList());
		messageEntities.forEach(messageEntity -> messageInventoryRepository.delete(messageEntity));

		return deleteMessages;
	}

}