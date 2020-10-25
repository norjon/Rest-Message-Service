package com.rest.messages.Rest.Messages;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageInventoryRepository
  extends CrudRepository<MessageEntity, Long> {
}