package todo.webapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import todo.webapp.api.constant.TodoItemConstant;
import todo.webapp.api.exception.domain.TodoItemException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemDto {
    private String idTodoItem;
    private String descTodoItem;
    private String isDone;
    private String idItemGroup;

    public boolean validateIdTodoItem() throws TodoItemException {
        if (this.idTodoItem==null || this.idTodoItem.isEmpty() || this.idTodoItem.trim().isEmpty())
            throw new TodoItemException(TodoItemConstant.EMPTY_TODO_ITEM_ID);
        try {
            Long.parseLong(this.idTodoItem);
        } catch (NumberFormatException nfe) {
            throw new TodoItemException(TodoItemConstant.INVALID_TODO_ITEM_ID);
        }
        return true;
    }

    public boolean validateDescItem() throws TodoItemException {
        if (this.descTodoItem==null || this.descTodoItem.isEmpty() || this.descTodoItem.trim().isEmpty())
            throw new TodoItemException(TodoItemConstant.EMPTY_TODO_ITEM_DESC);
        return true;
    }

    public boolean validateStatus() throws TodoItemException {
        if (this.isDone==null || this.isDone.isEmpty() || this.isDone.trim().isEmpty())
            throw new TodoItemException(TodoItemConstant.EMPTY_TODO_ITEM_STATUS);
        if (!this.isDone.equals("true") && !this.isDone.equals("false")
                && !this.isDone.equals("TRUE") && !this.isDone.equals("FALSE"))
            throw new TodoItemException(TodoItemConstant.INVALID_TODO_ITEM_STATUS);
        return true;
    }

}
