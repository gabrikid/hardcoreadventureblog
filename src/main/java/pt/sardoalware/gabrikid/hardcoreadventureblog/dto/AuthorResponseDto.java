package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;

public record AuthorResponseDto(Integer id, String name, String email) {

    public AuthorResponseDto(AuthorEntity from) {
        this(from.getId(), from.getName(), from.getEmail());
    }

}