package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDeleteResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.EmailAlreadyExistsException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.AuthorRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.PostRepository;
import java.util.List;
import java.util.Optional;

public class AuthorServiceImplTest {

    private AuthorRepository authorRepositoryMock;
    private PostRepository postRepositoryMock;
    private AuthorServiceImpl authorServiceImpl;
    private AuthorRequestDto requestFirst, requestSecond;
    private AuthorEntity entityFirst, entitySecond;
    private AuthorResponseDto responseFirst, responseSecond;

    @BeforeEach
    public void setupTest() {
        authorRepositoryMock = mock(AuthorRepository.class);
        postRepositoryMock = mock(PostRepository.class);

        authorServiceImpl = new AuthorServiceImpl(authorRepositoryMock, postRepositoryMock);

        requestFirst = new AuthorRequestDto(NAME_TEST_1, EMAIL_TEST_1);
        requestSecond = new AuthorRequestDto(NAME_TEST_2, EMAIL_TEST_2);

        entityFirst = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        entitySecond = new AuthorEntity(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);

        responseFirst = new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        responseSecond = new AuthorResponseDto(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
    }

    @Test
    public void findAll() {
        when(authorRepositoryMock.findAll()).thenReturn(List.of(entityFirst, entitySecond));

        List<AuthorResponseDto> authorResponseDtoList = authorServiceImpl.findAll();

        assertThat(authorResponseDtoList).isEqualTo(List.of(responseFirst, responseSecond));
        verify(authorRepositoryMock).findAll();
    }

    @Test
    public void findAllEmpty() {
        when(authorRepositoryMock.findAll()).thenReturn(List.of());

        List<AuthorResponseDto> authorResponseDtoList = authorServiceImpl.findAll();

        assertThat(authorResponseDtoList).isEqualTo(List.of());
        verify(authorRepositoryMock).findAll();
    }

    @Test
    public void find() throws AuthorNotFoundException {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));

        AuthorResponseDto authorResponseDto = authorServiceImpl.find(ID_TEST_1);

        assertThat(authorResponseDto).isEqualTo(responseFirst);
        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void findThrowsAuthorNotFound() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authorServiceImpl.find(ID_TEST_1)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void create() throws EmailAlreadyExistsException {
        AuthorEntity entityFirstCreate = requestFirst.parse();

        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_1)).thenReturn(Optional.empty());
        when(authorRepositoryMock.save(entityFirstCreate)).thenReturn(entityFirst);

        AuthorResponseDto authorResponseDto = authorServiceImpl.create(requestFirst);

        assertThat(authorResponseDto).isEqualTo(responseFirst);
        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_1);
        verify(authorRepositoryMock).save(entityFirstCreate);
    }

    @Test
    public void createThrowsEmailAlreadyExists() {
        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_1)).thenReturn(Optional.of(entityFirst));

        assertThatThrownBy(() ->
                authorServiceImpl.create(requestFirst)
        ).isInstanceOf(
                EmailAlreadyExistsException.class
        );

        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_1);
    }

    @Test
    public void update() throws AuthorNotFoundException, EmailAlreadyExistsException {
        AuthorEntity entityFirstUpdate = new AuthorEntity(ID_TEST_1, NAME_TEST_2, EMAIL_TEST_2);
        AuthorResponseDto responseFirstUpdate = new AuthorResponseDto(entityFirstUpdate);

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_2)).thenReturn(Optional.empty());
        when(authorRepositoryMock.save(entityFirstUpdate)).thenReturn(entityFirstUpdate);

        AuthorResponseDto authorResponseDto = authorServiceImpl.update(ID_TEST_1, requestSecond);

        assertThat(authorResponseDto).isEqualTo(responseFirstUpdate);
        verify(authorRepositoryMock).findById(ID_TEST_1);
        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_2);
        verify(authorRepositoryMock).save(entityFirstUpdate);
    }

    @Test
    public void updateName() throws AuthorNotFoundException, EmailAlreadyExistsException {
        AuthorRequestDto requestFirstUpdate = new AuthorRequestDto(NAME_TEST_2, EMAIL_TEST_1);
        AuthorEntity entityFirstUpdate = new AuthorEntity(ID_TEST_1, NAME_TEST_2, EMAIL_TEST_1);
        AuthorResponseDto responseFirstUpdate = new AuthorResponseDto(entityFirstUpdate);

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(authorRepositoryMock.save(entityFirstUpdate)).thenReturn(entityFirstUpdate);

        AuthorResponseDto authorResponseDto = authorServiceImpl.update(ID_TEST_1, requestFirstUpdate);

        assertThat(authorResponseDto).isEqualTo(responseFirstUpdate);
        verify(authorRepositoryMock).findById(ID_TEST_1);
        verify(authorRepositoryMock).save(entityFirstUpdate);
        verify(authorRepositoryMock, never()).findByEmailIgnoreCase(EMAIL_TEST_1);
    }

    @Test
    public void updateThrowsAuthorNotFound() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authorServiceImpl.update(ID_TEST_1, requestSecond)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void updateThrowsEmailAlreadyExists() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_2)).thenReturn(Optional.of(entitySecond));

        assertThatThrownBy(() ->
                authorServiceImpl.update(ID_TEST_1, requestSecond)
        ).isInstanceOf(
                EmailAlreadyExistsException.class
        );

        verify(authorRepositoryMock).findById(ID_TEST_1);
        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_2);
    }

    @Test
    public void delete() throws AuthorNotFoundException {
        AuthorDeleteResponseDto responseFirstDelete = new AuthorDeleteResponseDto(
                ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1, TWO
        );

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(postRepositoryMock.deleteByAuthorEntity(entityFirst)).thenReturn(TWO);

        AuthorDeleteResponseDto authorDeleteResponseDto = authorServiceImpl.delete(ID_TEST_1);

        assertThat(authorDeleteResponseDto).isEqualTo(responseFirstDelete);
        verify(postRepositoryMock).deleteByAuthorEntity(entityFirst);
        verify(authorRepositoryMock).delete(entityFirst);
    }

    @Test
    public void deleteThrowsAuthorNotFound() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authorServiceImpl.delete(ID_TEST_1)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

}