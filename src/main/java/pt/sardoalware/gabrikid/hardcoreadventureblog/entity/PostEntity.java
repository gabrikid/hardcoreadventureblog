package pt.sardoalware.gabrikid.hardcoreadventureblog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Instant postedOn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private AuthorEntity authorEntity;

}