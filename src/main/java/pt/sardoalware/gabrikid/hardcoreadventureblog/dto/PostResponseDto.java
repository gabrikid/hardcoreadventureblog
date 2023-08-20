package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import java.time.Instant;

public record PostResponseDto(
        Integer id,
        String title,
        String content,
        Instant postedOn,
        AuthorResponseDto author
) {

    public PostResponseDto(PostEntity from) {
        this(
                from.getId(),
                from.getTitle(),
                from.getContent(),
                from.getPostedOn(),
                new AuthorResponseDto(from.getAuthorEntity())
        );
    }

}