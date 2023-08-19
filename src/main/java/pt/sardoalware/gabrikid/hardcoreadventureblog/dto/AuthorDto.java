package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    public static AuthorDto of(AuthorEntity authorEntity) {
        return new AuthorDto(
                authorEntity.getId(),
                authorEntity.getName(),
                authorEntity.getEmail()
        );
    }

    public static AuthorEntity parse(AuthorDto authorDto) {
        return new AuthorEntity(
                null,
                authorDto.getName(),
                authorDto.getEmail()
        );
    }

}