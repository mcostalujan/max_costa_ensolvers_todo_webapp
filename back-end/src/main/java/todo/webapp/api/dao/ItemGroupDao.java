package todo.webapp.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.webapp.api.model.ItemGroup;

@Repository
public interface ItemGroupDao extends JpaRepository<ItemGroup, Long> {

    ItemGroup findByIdItemGroup(Long idItemGroup);

}
