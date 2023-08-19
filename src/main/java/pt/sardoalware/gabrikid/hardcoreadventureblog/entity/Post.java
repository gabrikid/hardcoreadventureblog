package pt.sardoalware.gabrikid.hardcoreadventureblog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Instant postedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

}