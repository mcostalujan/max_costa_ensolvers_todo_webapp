package todo.webapp.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo.webapp.api.dao.TodoItemDao;
import todo.webapp.api.dto.TodoItemDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.exception.domain.TodoItemException;
import todo.webapp.api.model.ItemGroup;
import todo.webapp.api.model.TodoItem;
import todo.webapp.api.service.TodoItemService;
import todo.webapp.api.service.UtilityService;

import java.util.Collections;
import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    private TodoItem todoItem;
    private TodoItemDto todoItemDto;
    private ItemGroup itemGroup;

    private final TodoItemDao todoItemDao;
    private final UtilityService utilityService;

    @Autowired
    public TodoItemServiceImpl(TodoItemDao todoItemDao, UtilityService utilityService){
        this.todoItemDao = todoItemDao;
        this.utilityService = utilityService;
    }

    @Override
    public List<TodoItem> getAll() {
        return todoItemDao.findAll();
    }

    @Override
    public List<TodoItem> getAllByItemGroup(String idItemGroup) throws ItemGroupException {
        this.itemGroup = this.utilityService.getItemGroup(idItemGroup);
        if(this.itemGroup!=null)
            return todoItemDao.findAllByItemGroup(this.itemGroup);
        return Collections.emptyList();
    }

    @Override
    public TodoItem findById(String id) throws TodoItemException {
        return this.utilityService.getTodoItem(id);
    }

    @Override
    public TodoItem saveByJson(TodoItemDto todoItemDto) throws ItemGroupException, TodoItemException {
        this.todoItemDto = todoItemDto;
        return setAttributesAndSaveToDB();
    }

    @Override
    public TodoItem saveByForm(String idTodoItem, String descTodoItem, String isDone, String idItemGroup) throws ItemGroupException, TodoItemException {
        this.todoItemDto = new TodoItemDto();
        this.todoItemDto.setIdTodoItem(idTodoItem);
        this.todoItemDto.setDescTodoItem(descTodoItem);
        this.todoItemDto.setIdItemGroup(idItemGroup);
        this.todoItemDto.setIsDone(isDone);
        return setAttributesAndSaveToDB();
    }

    @Override
    public void deleteById(String idTodoItem) throws TodoItemException {
        this.todoItem = this.utilityService.getTodoItem(idTodoItem);
        if(this.todoItem!=null)
            this.todoItemDao.deleteById(Long.parseLong(idTodoItem));
    }

    private TodoItem setAttributesAndSaveToDB() throws ItemGroupException, TodoItemException {
        if(validateNewTodoItem()){
            if(this.todoItemDto.getIdTodoItem()!=null && this.todoItemDto.validateStatus()){
                this.todoItem = this.utilityService.getTodoItem(todoItemDto.getIdTodoItem());
                this.todoItem.setIsDone(Boolean.parseBoolean(this.todoItemDto.getIsDone()));
            }
            else{
                this.todoItem = new TodoItem();
                this.todoItem.setIsDone(false);
            }
            this.todoItem.setDescTodoItem(todoItemDto.getDescTodoItem());
            this.todoItem.setItemGroup(this.itemGroup);
            this.todoItemDao.save(this.todoItem);
            return this.todoItem;
        }
        return null;
    }

    private boolean validateNewTodoItem() throws ItemGroupException, TodoItemException {
        this.itemGroup = this.utilityService.getItemGroup(this.todoItemDto.getIdItemGroup());
        return this.itemGroup!=null && this.todoItemDto.validateDescItem();
    }

}
