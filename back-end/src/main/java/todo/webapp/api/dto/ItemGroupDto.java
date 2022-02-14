package todo.webapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import todo.webapp.api.constant.ItemGroupConstant;
import todo.webapp.api.exception.domain.ItemGroupException;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemGroupDto {
    private String idItemGroup;
    private String descItemGroup;

    public boolean validateIdItemGroup() throws ItemGroupException {
        if (this.idItemGroup==null || this.idItemGroup.isEmpty() || this.idItemGroup.trim().isEmpty())
            throw new ItemGroupException(ItemGroupConstant.EMPTY_ITEM_GROUP_ID);
        try {
            Long.parseLong(this.idItemGroup);
        } catch (NumberFormatException nfe) {
            throw new ItemGroupException(ItemGroupConstant.INVALID_ITEM_GROUP_ID);
        }
        return true;
    }

    public boolean validateDescItemGroup() throws ItemGroupException {
        if (this.descItemGroup==null || this.descItemGroup.isEmpty() || this.descItemGroup.trim().isEmpty())
            throw new ItemGroupException(ItemGroupConstant.EMPTY_ITEM_GROUP_DESC);
        return true;
    }

}
