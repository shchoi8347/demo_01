package com.example.demo.service;

import com.example.demo.Repository.TodoRepository;
import com.example.demo.model.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;
    public String testService() {
        TodoEntity entity = TodoEntity.builder().title("My first Todo item").build();

        repository.save(entity);

        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        if( entity == null ) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if( entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

        repository.save(entity);

        log.info("Entity Id: {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }


}
