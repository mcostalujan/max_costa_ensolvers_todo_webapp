package todo.webapp.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.webapp.api.model.ItemGroup;
import todo.webapp.api.model.TodoItem;

import java.util.List;

@Repository
public interface TodoItemDao extends JpaRepository<TodoItem, Long> {

    TodoItem findByIdTodoItem (Long idTodoItem);

    List<TodoItem> findAllByItemGroup(ItemGroup itemGroup);

}
