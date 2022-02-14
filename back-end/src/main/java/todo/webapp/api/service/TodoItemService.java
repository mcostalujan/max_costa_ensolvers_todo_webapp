package todo.webapp.api.service;

import todo.webapp.api.dto.TodoItemDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.exception.domain.TodoItemException;
import todo.webapp.api.model.TodoItem;

import java.util.List;

public interface TodoItemService {
    List<TodoItem> getAll();

    List<TodoItem> getAllByItemGroup(String idItemGroup) throws ItemGroupException;

    TodoItem findById(String id) throws TodoItemException;

    TodoItem saveByJson(TodoItemDto todoItemDto) throws ItemGroupException, TodoItemException;

    TodoItem saveByForm(String idTodoItem, String descTodoItem, String isDone, String idItemGroup) throws ItemGroupException, TodoItemException;

    void deleteById(String idTodoItem) throws TodoItemException;
}
