package todo.webapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Table(name = "item_group")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idItemGroup;
    private String descItemGroup;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "itemGroup")
    private List<TodoItem> todoItemList;


}
