package todo.webapp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import todo.webapp.api.constant.ItemGroupConstant;
import todo.webapp.api.constant.TodoItemConstant;
import todo.webapp.api.dto.TodoItemDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.exception.domain.TodoItemException;
import todo.webapp.api.model.HttpResponse;
import todo.webapp.api.model.TodoItem;
import todo.webapp.api.service.TodoItemService;
import static org.springframework.http.HttpStatus.*;
import java.util.List;

@Controller
@RequestMapping("/todoItem")
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/listAll")
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        List<TodoItem> allTodoItems = this.todoItemService.getAll();
        if(allTodoItems.isEmpty()) return new ResponseEntity<>(NO_CONTENT);
        return new ResponseEntity<>(allTodoItems, OK);
    }

    @GetMapping("/listAllByItemGroup/{idItemGroup}")
    public ResponseEntity<List<TodoItem>> obtenerTodosLosPagosPorIntervalo(@PathVariable("idItemGroup") String idItemGroup)
            throws ItemGroupException {
        List<TodoItem> allTodoItemsByItemGroup = this.todoItemService.getAllByItemGroup(idItemGroup);
        if(allTodoItemsByItemGroup.isEmpty()) return new ResponseEntity<>(NO_CONTENT);
        return new ResponseEntity<>(allTodoItemsByItemGroup, OK);
    }


    @GetMapping("/findById/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable("id") String id) throws TodoItemException {
        TodoItem foundTodoItem = this.todoItemService.findById(id);
        if(foundTodoItem==null) return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(foundTodoItem, OK);
    }

    @PostMapping("/saveByJson")
    public ResponseEntity<TodoItem> saveTodoItemByJson(@RequestBody TodoItemDto todoItemDto)
            throws ItemGroupException, TodoItemException {
        TodoItem savedTodoItemByJson = this.todoItemService.saveByJson(todoItemDto);
        return new ResponseEntity<>(savedTodoItemByJson, CREATED);
    }

    @PostMapping("/saveByForm")
    public ResponseEntity<TodoItem> saveTodoItemByForm(
            @RequestParam(value = "idTodoItem", required = false) String idTodoItem,
            @RequestParam(value = "descTodoItem", required = false) String descTodoItem,
            @RequestParam(value = "isDone", required = false) String isDone,
            @RequestParam(value = "idItemGroup", required = false) String idItemGroup) throws ItemGroupException, TodoItemException {
        TodoItem savedTodoItemByForm = this.todoItemService.saveByForm(idTodoItem,descTodoItem,isDone,idItemGroup);
        return new ResponseEntity<>(savedTodoItemByForm, CREATED);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpResponse> deleteTodoItem(@PathVariable("id") String idTodoItem) throws TodoItemException {
        this.todoItemService.deleteById(idTodoItem);
        return response(OK, TodoItemConstant.TODO_ITEM_DELETED);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message),
                httpStatus);
    }

}
