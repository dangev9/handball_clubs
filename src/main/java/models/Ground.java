package models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String description;
    private String renovated;
    private String opened;
    private String capacity;
    private String location;

    public Ground(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
