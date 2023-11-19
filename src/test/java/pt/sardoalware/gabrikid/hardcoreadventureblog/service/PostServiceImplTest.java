package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.PostNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.AuthorRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.PostRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class PostServiceImplTest {

    private AuthorRepository authorRepositoryMock;
    private PostRepository postRepositoryMock;
    private PostServiceImpl postServiceImpl;
    private PostRequestDto requestFirst;
    private PostUpdateRequestDto requestFirstUpdate;
    private AuthorEntity authorEntity;
    private PostEntity entityFirst, entityFirstUpdate, entitySecond;
    private PostResponseDto responseFirst, responseFirstUpdate, responseSecond;

    @BeforeEach
    public void setupTest() {
        authorRepositoryMock = mock(AuthorRepository.class);
        postRepositoryMock = mock(PostRepository.class);

        postServiceImpl = new PostServiceImpl(authorRepositoryMock, postRepositoryMock);

        requestFirst = new PostRequestDto(TITLE_TEST_1, CONTENT_TEST_1, Instant.now(), ID_TEST_1);

        requestFirstUpdate = new PostUpdateRequestDto(TITLE_TEST_2, CONTENT_TEST_2);

        authorEntity = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        entityFirst = new PostEntity(ID_TEST_1, TITLE_TEST_1, CONTENT_TEST_1, requestFirst.postedOn(), authorEntity);
        entityFirstUpdate = new PostEntity(ID_TEST_1, TITLE_TEST_2, CONTENT_TEST_2, requestFirst.postedOn(), authorEntity);
        entitySecond = new PostEntity(ID_TEST_2, TITLE_TEST_2, CONTENT_TEST_2, requestFirst.postedOn(), authorEntity);

        responseFirst = new PostResponseDto(
                ID_TEST_1,
                TITLE_TEST_1,
                CONTENT_TEST_1,
                requestFirst.postedOn(),
                new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1)
        );
        responseFirstUpdate = new PostResponseDto(
                ID_TEST_1,
                TITLE_TEST_2,
                CONTENT_TEST_2,
                requestFirst.postedOn(),
                responseFirst.author()
        );
        responseSecond = new PostResponseDto(
                ID_TEST_2,
                TITLE_TEST_2,
                CONTENT_TEST_2,
                requestFirst.postedOn(),
                responseFirst.author()
        );
    }

    @Test
    public void findAll() {
        when(postRepositoryMock.findAll()).thenReturn(List.of(entityFirst, entitySecond));

        List<PostResponseDto> postResponseDtoList = postServiceImpl.findAll();

        assertThat(postResponseDtoList).isEqualTo(List.of(responseFirst, responseSecond));
        verify(postRepositoryMock).findAll();
    }

    @Test
    public void findAllEmpty() {
        when(postRepositoryMock.findAll()).thenReturn(List.of());

        List<PostResponseDto> postResponseDtoList = postServiceImpl.findAll();

        assertThat(postResponseDtoList).isEqualTo(List.of());
        verify(postRepositoryMock).findAll();
    }

    @Test
    public void find() throws PostNotFoundException {
        when(postRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));

        PostResponseDto postResponseDto = postServiceImpl.find(ID_TEST_1);

        assertThat(postResponseDto).isEqualTo(responseFirst);
        verify(postRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void findThrowsPostNotFoundException() {
        when(postRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                postServiceImpl.find(ID_TEST_1)
        ).isInstanceOf(
                PostNotFoundException.class
        );

        verify(postRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void create() throws AuthorNotFoundException {
        PostEntity entityFirstCreate = requestFirst.parse(authorEntity);

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(authorEntity));
        when(postRepositoryMock.save(entityFirstCreate)).thenReturn(entityFirst);

        PostResponseDto postResponseDto = postServiceImpl.create(requestFirst);

        assertThat(postResponseDto).isEqualTo(responseFirst);
        verify(authorRepositoryMock).findById(ID_TEST_1);
        verify(postRepositoryMock).save(entityFirstCreate);
    }

    @Test
    public void createThrowsAuthorNotFoundException() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                postServiceImpl.create(requestFirst)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void update() throws PostNotFoundException {
        when(postRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(postRepositoryMock.save(entityFirstUpdate)).thenReturn(entityFirstUpdate);

        PostResponseDto postResponseDto = postServiceImpl.update(ID_TEST_1, requestFirstUpdate);

        assertThat(postResponseDto).isEqualTo(responseFirstUpdate);
        verify(postRepositoryMock).findById(ID_TEST_1);
        verify(postRepositoryMock).save(entityFirstUpdate);
    }

    @Test
    public void updateThrowsPostNotFoundException() {
        when(postRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                postServiceImpl.update(ID_TEST_1, requestFirstUpdate)
        ).isInstanceOf(
                PostNotFoundException.class
        );

        verify(postRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void delete() throws PostNotFoundException {
        when(postRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));

        PostResponseDto postResponseDto = postServiceImpl.delete(ID_TEST_1);

        assertThat(postResponseDto).isEqualTo(responseFirst);
        verify(postRepositoryMock).findById(ID_TEST_1);
        verify(postRepositoryMock).delete(entityFirst);
    }

    @Test
    public void deleteThrowsPostNotFoundException() {
        when(postRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                postServiceImpl.delete(ID_TEST_1)
        ).isInstanceOf(
                PostNotFoundException.class
        );

        verify(postRepositoryMock).findById(ID_TEST_1);
    }

}