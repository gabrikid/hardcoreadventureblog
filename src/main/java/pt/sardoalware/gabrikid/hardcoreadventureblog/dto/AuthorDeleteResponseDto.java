package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;

public record AuthorDeleteResponseDto(Integer id, String name, String email, Integer postsDeleted) {

    public AuthorDeleteResponseDto(AuthorEntity from, Integer postsDeleted) {
        this(from.getId(), from.getName(), from.getEmail(), postsDeleted);
    }

}