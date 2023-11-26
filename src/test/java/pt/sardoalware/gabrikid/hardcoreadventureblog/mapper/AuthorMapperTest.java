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
        //given
        AuthorRequestDto givenDto = new AuthorRequestDto(NAME_TEST_1, EMAIL_TEST_1);

        // when
        AuthorEntity actualEntity = authorMapper.dtoToEntity(givenDto);

        // then
        AuthorEntity expectedEntity = new AuthorEntity(null, NAME_TEST_1, EMAIL_TEST_1);
        assertThat(actualEntity).isEqualTo(expectedEntity);
    }

    @Test
    public void mergeDtoToEntity() {
        // given
        AuthorRequestDto givenDto = new AuthorRequestDto(NAME_TEST_2, EMAIL_TEST_2);
        AuthorEntity givenEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        // when
        authorMapper.mergeDtoToEntity(givenDto, givenEntity);

        // then
        AuthorEntity expectedEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_2, EMAIL_TEST_2);
        assertThat(givenEntity).isEqualTo(expectedEntity);
    }

    @Test
    public void entityToDto() {
        // given
        AuthorEntity givenEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        // when
        AuthorResponseDto actualDto = authorMapper.entityToDto(givenEntity);

        // then
        AuthorResponseDto expectedDto = new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void entityIterableToDtoList() {
        // given
        Iterable<AuthorEntity> givenIterable = List.of(
                new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1),
                new AuthorEntity(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2)
        );

        // when
        List<AuthorResponseDto> actualList = authorMapper.entityIterableToDtoList(givenIterable);

        // then
        List<AuthorResponseDto> expectedList = List.of(
                new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1),
                new AuthorResponseDto(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2)
        );
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    public void entityToDeleteDto() {
        // given
        AuthorEntity givenEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        // when
        AuthorDeleteResponseDto actualDto = authorMapper.entityToDeleteDto(givenEntity, TWO);

        // then
        AuthorDeleteResponseDto expectedDto = new AuthorDeleteResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1, TWO);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

}