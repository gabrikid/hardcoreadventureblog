package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;

public record PostUpdateRequestDto(
        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        @Size(max = 500)
        String content
) {

    public void merge(PostEntity to) {
        to.setTitle(title());
        to.setContent(content());
    }

}