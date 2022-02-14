package todo.webapp.api.service;

import todo.webapp.api.dto.ItemGroupDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.exception.domain.TodoItemException;
import todo.webapp.api.model.ItemGroup;

import java.util.List;

public interface ItemGroupService {


    List<ItemGroup> getAll();

    ItemGroup findById(String id) throws ItemGroupException;

    ItemGroup saveByJson(ItemGroupDto itemGroupDto) throws ItemGroupException, TodoItemException;

    ItemGroup saveByForm(String idItemGroup, String descItemGroup) throws ItemGroupException, TodoItemException;

    void deleteById(String idItemGroup) throws ItemGroupException;
}
