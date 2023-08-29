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

        assertThat(authorResponseDtoList).isNotNull();
        assertThat(authorResponseDtoList.size()).isEqualTo(TWO);
        assertThat(authorResponseDtoList.get(ZERO)).isEqualTo(responseFirst);
        assertThat(authorResponseDtoList.get(ONE)).isEqualTo(responseSecond);

        when(authorRepositoryMock.findAll()).thenReturn(List.of());

        authorResponseDtoList = authorServiceImpl.findAll();

        assertThat(authorResponseDtoList).isNotNull();
        assertThat(authorResponseDtoList.size()).isEqualTo(ZERO);

        verify(authorRepositoryMock, times(2)).findAll();
    }

    @Test
    public void find() throws AuthorNotFoundException {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));

        AuthorResponseDto authorResponseDto = authorServiceImpl.find(ID_TEST_1);

        assertThat(authorResponseDto).isNotNull();
        assertThat(authorResponseDto).isEqualTo(responseFirst);
        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void findThrowing() {
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
        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_1)).thenReturn(Optional.empty());
        when(authorRepositoryMock.save(requestFirst.parse())).thenReturn(entityFirst);

        AuthorResponseDto authorResponseDto = authorServiceImpl.create(requestFirst);

        assertThat(authorResponseDto).isNotNull();
        assertThat(authorResponseDto).isEqualTo(responseFirst);
        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_1);
        verify(authorRepositoryMock).save(requestFirst.parse());
    }

    @Test
    public void createThrowing() {
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
        AuthorEntity updatedEntityFirst = new AuthorEntity(ID_TEST_1, NAME_TEST_2, EMAIL_TEST_2);
        AuthorResponseDto updatedResponseFirst = new AuthorResponseDto(updatedEntityFirst);

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_2)).thenReturn(Optional.empty());
        when(authorRepositoryMock.save(updatedEntityFirst)).thenReturn(updatedEntityFirst);

        AuthorResponseDto authorResponseDto = authorServiceImpl.update(ID_TEST_1, requestSecond);

        assertThat(authorResponseDto).isNotNull();
        assertThat(authorResponseDto).isEqualTo(updatedResponseFirst);
        verify(authorRepositoryMock).findById(ID_TEST_1);
        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_2);
        verify(authorRepositoryMock).save(updatedEntityFirst);
    }

    @Test
    public void updateThrowing() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authorServiceImpl.update(ID_TEST_1, requestSecond)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(authorRepositoryMock.findByEmailIgnoreCase(EMAIL_TEST_2)).thenReturn(Optional.of(entitySecond));

        assertThatThrownBy(() ->
                authorServiceImpl.update(ID_TEST_1, requestSecond)
        ).isInstanceOf(
                EmailAlreadyExistsException.class
        );

        verify(authorRepositoryMock, times(2)).findById(ID_TEST_1);
        verify(authorRepositoryMock).findByEmailIgnoreCase(EMAIL_TEST_2);
    }

    @Test
    public void delete() throws AuthorNotFoundException {
        AuthorDeleteResponseDto deletedResponseFirst = new AuthorDeleteResponseDto(
                ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1, TWO
        );

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));
        when(postRepositoryMock.deleteByAuthorEntity(entityFirst)).thenReturn(TWO);

        AuthorDeleteResponseDto authorDeleteResponseDto = authorServiceImpl.delete(ID_TEST_1);

        assertThat(authorDeleteResponseDto).isNotNull();
        assertThat(authorDeleteResponseDto).isEqualTo(deletedResponseFirst);
        verify(authorRepositoryMock).delete(entityFirst);
        verify(postRepositoryMock).deleteByAuthorEntity(entityFirst);
    }

    @Test
    public void deleteThrowing() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authorServiceImpl.delete(ID_TEST_1)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );
        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

}