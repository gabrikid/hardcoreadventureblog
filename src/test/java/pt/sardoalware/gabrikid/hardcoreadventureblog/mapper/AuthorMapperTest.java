package pt.sardoalware.gabrikid.hardcoreadventureblog.mapper;

import static org.assertj.core.api.Assertions.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDeleteResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import java.util.List;

@SpringBootTest
public class AuthorMapperTest {

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    public void dtoToEntity() {
        AuthorEntity authorEntity = authorMapper.dtoToEntity(
                new AuthorRequestDto(NAME_TEST_1, EMAIL_TEST_1)
        );

        assertThat(authorEntity).isEqualTo(
                new AuthorEntity(null, NAME_TEST_1, EMAIL_TEST_1)
        );
    }

    @Test
    public void mergeDtoToEntity() {
        AuthorRequestDto authorRequestDto = new AuthorRequestDto(NAME_TEST_2, EMAIL_TEST_2);
        AuthorEntity authorEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        authorMapper.mergeDtoToEntity(authorRequestDto, authorEntity);

        assertThat(authorEntity).isEqualTo(
                new AuthorEntity(ID_TEST_1, NAME_TEST_2, EMAIL_TEST_2)
        );
    }

    @Test
    public void entityToDto() {
        AuthorResponseDto authorResponseDto = authorMapper.entityToDto(
                new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1)
        );

        assertThat(authorResponseDto).isEqualTo(
                new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1)
        );
    }

    @Test
    public void entityIterableToDtoList() {
        Iterable<AuthorEntity> authorEntityIterable = List.of(
                new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1),
                new AuthorEntity(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2)
        );

        List<AuthorResponseDto> authorResponseDtoList = authorMapper.entityIterableToDtoList(authorEntityIterable);

        assertThat(authorResponseDtoList).isEqualTo(
                List.of(
                        new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1),
                        new AuthorResponseDto(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2)
                )
        );
    }

    @Test
    public void entityToDeleteDto() {
        AuthorDeleteResponseDto authorDeleteResponseDto = authorMapper.entityToDeleteDto(
                new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1),
                TWO
        );

        assertThat(authorDeleteResponseDto).isEqualTo(
                new AuthorDeleteResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1, TWO)
        );
    }

}

/*
    @Test
    public void dtoToEntityReturnsNull() {
        AuthorEntity authorEntity = authorMapper.dtoToEntity(null);

        assertThat(authorEntity).isNull();
    }

    @Test
    public void mergeDtoToEntityNullRequest() {
        AuthorEntity authorEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        assertDoesNotThrow(() -> authorMapper.mergeDtoToEntity(null, authorEntity));

        assertThat(authorEntity.getId()).isEqualTo(ID_TEST_1);
        assertThat(authorEntity.getName()).isEqualTo(NAME_TEST_1);
        assertThat(authorEntity.getEmail()).isEqualTo(EMAIL_TEST_1);
    }

    @Test
    public void mergeDtoToEntityNullEntity() {
        AuthorRequestDto authorRequestDto = new AuthorRequestDto(NAME_TEST_2, EMAIL_TEST_2);

        assertDoesNotThrow(() -> authorMapper.mergeDtoToEntity(authorRequestDto, null));
    }

    @Test
    public void mergeDtoToEntityNullArguments() {
        assertDoesNotThrow(() -> authorMapper.mergeDtoToEntity(null, null));
    }

    @Test
    public void entityToDtoReturnsNull() {
        AuthorResponseDto authorResponseDto = authorMapper.entityToDto(null);

        assertThat(authorResponseDto).isNull();
    }

    @Test
    public void entityIterableToDtoListReturnsNull() {
        List<AuthorResponseDto> authorResponseDtoList = authorMapper.entityIterableToDtoList(null);

        assertThat(authorResponseDtoList).isNull();
    }
*/