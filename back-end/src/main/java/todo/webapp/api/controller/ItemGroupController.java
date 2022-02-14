package todo.webapp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import todo.webapp.api.constant.ItemGroupConstant;
import todo.webapp.api.dto.ItemGroupDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.exception.domain.TodoItemException;
import todo.webapp.api.model.HttpResponse;
import todo.webapp.api.model.ItemGroup;
import todo.webapp.api.service.ItemGroupService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/itemGroup")
public class ItemGroupController {

    @Autowired
    private ItemGroupService itemGroupService;

    @GetMapping("/listAll")
    public ResponseEntity<List<ItemGroup>> getAllItemGroups() {
        List<ItemGroup> allItemGroups = this.itemGroupService.getAll();
        if(allItemGroups.isEmpty()) return new ResponseEntity<>(NO_CONTENT);
        return new ResponseEntity<>(allItemGroups, OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ItemGroup> getItemGroupById(@PathVariable("id") String id) throws ItemGroupException {
        ItemGroup foundItemGroup = this.itemGroupService.findById(id);
        if(foundItemGroup==null) return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(foundItemGroup, OK);
    }

    @PostMapping("/saveByJson")
    public ResponseEntity<ItemGroup> saveItemGroupByJson(@RequestBody ItemGroupDto itemGroupDto) throws ItemGroupException, TodoItemException {
        ItemGroup savedItemGroupByJson = this.itemGroupService.saveByJson(itemGroupDto);
        return new ResponseEntity<>(savedItemGroupByJson, CREATED);
    }

    @PostMapping("/saveByForm")
    public ResponseEntity<ItemGroup> saveItemGroupByForm(
            @RequestParam(value = "idItemGroup", required = false) String idItemGroup,
            @RequestParam("descItemGroup") String descItemGroup) throws ItemGroupException, TodoItemException {
        ItemGroup savedItemGroupByForm = this.itemGroupService.saveByForm(idItemGroup,descItemGroup);
        return new ResponseEntity<>(savedItemGroupByForm, CREATED);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpResponse> deleteItemGroup(@PathVariable("id") String idItemGroup) throws ItemGroupException {
        this.itemGroupService.deleteById(idItemGroup);
        return response(OK, ItemGroupConstant.ITEM_GROUP_DELETED);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message),
                httpStatus);
    }
    
}
