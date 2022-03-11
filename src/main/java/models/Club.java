package models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String clubName;
    @Lob
    private String description;
    private String chairman;
    private String manager;
    @OneToOne(cascade = CascadeType.ALL)
    private Ground ground;
    private String winners;
}
