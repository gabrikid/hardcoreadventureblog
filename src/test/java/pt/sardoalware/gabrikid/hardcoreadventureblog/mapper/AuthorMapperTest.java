package pt.sardoalware.gabrikid.hardcoreadventureblog.mapper;

import static org.assertj.core.api.Assertions.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

}