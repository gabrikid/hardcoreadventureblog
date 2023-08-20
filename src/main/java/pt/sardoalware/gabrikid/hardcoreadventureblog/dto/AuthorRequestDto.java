package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;

public record AuthorRequestDto(
        @NotBlank
        @Size(max = 50)
        String name,

        @NotBlank
        @Size(max = 50)
        @Email
        String email
) {
    public AuthorEntity parse() {
        AuthorEntity authorEntity = new AuthorEntity();
        merge(authorEntity);
        return authorEntity;
    }

    public void merge(AuthorEntity to) {
        to.setName(name());
        to.setEmail(email());
    }
}