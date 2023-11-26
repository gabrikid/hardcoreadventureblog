package pt.sardoalware.gabrikid.hardcoreadventureblog.mapper;

import static org.assertj.core.api.Assertions.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import java.time.Instant;
import java.util.List;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    public void dtoToEntity() {
        // given
        Instant givenInstant = Instant.now();
        PostRequestDto givenDto = new PostRequestDto(TITLE_TEST_1, CONTENT_TEST_1, givenInstant, ID_TEST_1);
        AuthorEntity givenAuthorEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        // when
        PostEntity actualEntity = postMapper.dtoToEntity(givenDto, givenAuthorEntity);

        // then
        PostEntity expectedEntity = new PostEntity(
                null, TITLE_TEST_1, CONTENT_TEST_1, givenInstant, givenAuthorEntity
        );
        assertThat(actualEntity).isEqualTo(expectedEntity);
    }

    @Test
    public void mergeDtoToEntity() {
        // given
        PostUpdateRequestDto givenDto = new PostUpdateRequestDto(TITLE_TEST_2, CONTENT_TEST_2);
        Instant givenInstant = Instant.now();
        AuthorEntity givenAuthorEntity = new AuthorEntity(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
        PostEntity givenEntity = new PostEntity(
                ID_TEST_1, TITLE_TEST_1, CONTENT_TEST_1, givenInstant, givenAuthorEntity
        );

        // when
        postMapper.mergeDtoToEntity(givenDto, givenEntity);

        // then
        PostEntity expectedEntity = new PostEntity(
                ID_TEST_1, TITLE_TEST_2, CONTENT_TEST_2, givenInstant, givenAuthorEntity
        );
        assertThat(givenEntity).isEqualTo(expectedEntity);
    }

    @Test
    public void entityToDto() {
        // given
        Instant givenInstant = Instant.now();
        AuthorEntity givenAuthorEntity = new AuthorEntity(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
        PostEntity givenEntity = new PostEntity(
                ID_TEST_1, TITLE_TEST_1, CONTENT_TEST_1, givenInstant, givenAuthorEntity
        );

        // when
        PostResponseDto actualDto = postMapper.entityToDto(givenEntity);

        // then
        AuthorResponseDto expectedAuthorDto = new AuthorResponseDto(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
        PostResponseDto expectedDto = new PostResponseDto(
                ID_TEST_1,
                TITLE_TEST_1,
                CONTENT_TEST_1,
                givenInstant,
                expectedAuthorDto
        );
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void entityIterableToDtoList() {
        // given
        Instant instantFirst = Instant.now();
        Instant instantSecond = instantFirst.plusSeconds(TWO);
        AuthorEntity givenAuthorEntityFirst = new AuthorEntity(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
        AuthorEntity givenAuthorEntitySecond = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        Iterable<PostEntity> givenIterable = List.of(
                new PostEntity(ID_TEST_1, TITLE_TEST_1, CONTENT_TEST_1, instantFirst, givenAuthorEntityFirst),
                new PostEntity(ID_TEST_2, TITLE_TEST_2, CONTENT_TEST_2, instantSecond, givenAuthorEntitySecond)
        );

        // when
        List<PostResponseDto> actualList = postMapper.entityIterableToDtoList(givenIterable);

        // then
        AuthorResponseDto expectedAuthorDtoFirst = new AuthorResponseDto(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
        AuthorResponseDto expectedAuthorDtoSecond = new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        List<PostResponseDto> expectedList = List.of(
                new PostResponseDto(ID_TEST_1, TITLE_TEST_1, CONTENT_TEST_1, instantFirst, expectedAuthorDtoFirst),
                new PostResponseDto(ID_TEST_2, TITLE_TEST_2, CONTENT_TEST_2, instantSecond, expectedAuthorDtoSecond)
        );
        assertThat(actualList).isEqualTo(expectedList);
    }

}