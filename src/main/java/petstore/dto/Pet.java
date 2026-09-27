package petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private long id;
    private String name;
    private String status;
    private Category category;
    private List<Tag> tags;
    private List<String> photoUrls;
}