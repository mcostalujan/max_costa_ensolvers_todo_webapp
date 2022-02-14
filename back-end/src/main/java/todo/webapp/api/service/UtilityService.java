package todo.webapp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo.webapp.api.constant.ItemGroupConstant;
import todo.webapp.api.constant.TodoItemConstant;
import todo.webapp.api.dao.ItemGroupDao;
import todo.webapp.api.dao.TodoItemDao;
import todo.webapp.api.dto.ItemGroupDto;
import todo.webapp.api.dto.TodoItemDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.exception.domain.TodoItemException;
import todo.webapp.api.model.ItemGroup;
import todo.webapp.api.model.TodoItem;

@Service
public final class UtilityService {

    private ItemGroup itemGroup;
    private TodoItem todoItem;

    private final ItemGroupDao itemGroupDao;
    private final TodoItemDao todoItemDao;

    @Autowired
    private UtilityService(ItemGroupDao itemGroupDao, TodoItemDao todoItemDao) {
        this.itemGroupDao = itemGroupDao;
        this.todoItemDao = todoItemDao;
    }

    public ItemGroup getItemGroup(String idItemGroup) throws ItemGroupException {
        ItemGroupDto itemGroupDto = new ItemGroupDto();
        itemGroupDto.setIdItemGroup(idItemGroup);
        if(itemGroupDto.validateIdItemGroup()) {
            this.itemGroup = this.itemGroupDao.findByIdItemGroup(Long.parseLong(idItemGroup));
            if (this.itemGroup == null) throw new ItemGroupException(ItemGroupConstant.ITEM_GROUP_NOT_FOUND);
            return this.itemGroup;
        }
        return null;
    }

    public TodoItem getTodoItem(String idTodoItem) throws TodoItemException {
        TodoItemDto todoItemDto = new TodoItemDto();
        todoItemDto.setIdTodoItem(idTodoItem);
        if(todoItemDto.validateIdTodoItem()){
            this.todoItem = this.todoItemDao.findByIdTodoItem(Long.parseLong(idTodoItem));
            if (this.todoItem == null) throw new TodoItemException(TodoItemConstant.TODO_ITEM_NOT_FOUND);
            return this.todoItem;
        }
        return null;
    }
}
