package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import java.time.Instant;

public record PostRequestDto(
        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        @Size(max = 500)
        String content,

        @NotNull
        Instant postedOn,

        @NotNull
        Integer authorId
) {

    public PostEntity parse(AuthorEntity authorEntity) {
        PostEntity postEntity = new PostEntity();
        merge(postEntity, authorEntity);
        return postEntity;
    }

    public void merge(PostEntity to, AuthorEntity authorEntity) {
        to.setTitle(title());
        to.setContent(content());
        to.setPostedOn(postedOn());
        to.setAuthorEntity(authorEntity);
    }

}