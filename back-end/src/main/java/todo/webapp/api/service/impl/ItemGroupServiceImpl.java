package todo.webapp.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todo.webapp.api.dao.ItemGroupDao;
import todo.webapp.api.dto.ItemGroupDto;
import todo.webapp.api.exception.domain.ItemGroupException;
import todo.webapp.api.model.ItemGroup;
import todo.webapp.api.service.ItemGroupService;
import todo.webapp.api.service.UtilityService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemGroupServiceImpl implements ItemGroupService {

    private ItemGroupDto itemGroupDto;
    private ItemGroup itemGroup;

    private final ItemGroupDao itemGroupDao;
    private final UtilityService utilityService;

    @Autowired
    public ItemGroupServiceImpl(ItemGroupDao itemGroupDao, UtilityService utilityService){
        this.itemGroupDao = itemGroupDao;
        this.utilityService = utilityService;
    }

    @Override
    public List<ItemGroup> getAll() {
        return this.itemGroupDao.findAll();
    }

    @Override
    public ItemGroup findById(String id) throws ItemGroupException {
        return this.utilityService.getItemGroup(id);
    }

    @Override
    public ItemGroup saveByJson(ItemGroupDto itemGroupDto) throws ItemGroupException {
        this.itemGroupDto = itemGroupDto;
        return setAttributesAndSaveToDB();
    }

    @Override
    public ItemGroup saveByForm(String idItemGroup, String descItemGroup) throws ItemGroupException {
        this.itemGroupDto = new ItemGroupDto();
        this.itemGroupDto.setIdItemGroup(idItemGroup);
        this.itemGroupDto.setDescItemGroup(descItemGroup);
        return setAttributesAndSaveToDB();
    }

    @Override
    public void deleteById(String idItemGroup) throws ItemGroupException {
        this.itemGroup = this.utilityService.getItemGroup(idItemGroup);
        if(this.itemGroup!=null)
            this.itemGroupDao.deleteById(Long.parseLong(idItemGroup));
    }

    private ItemGroup setAttributesAndSaveToDB() throws ItemGroupException {
        if(validateNewItemGroup()){
            if(this.itemGroupDto.getIdItemGroup()!=null)
                itemGroup = this.utilityService.getItemGroup(this.itemGroupDto.getIdItemGroup());
            else {
                itemGroup = new ItemGroup();
                itemGroup.setTodoItemList(new ArrayList<>());
            }
            itemGroup.setDescItemGroup(itemGroupDto.getDescItemGroup());
            this.itemGroupDao.save(itemGroup);
            return itemGroup;
        }
        return null;
    }

    private boolean validateNewItemGroup() throws ItemGroupException {
        return this.itemGroupDto.validateDescItemGroup();
    }


}
