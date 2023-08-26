package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.AuthorRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.PostRepository;
import java.util.List;
import java.util.Optional;

public class AuthorServiceImplTest {

    private AuthorRepository authorRepositoryMock;
    private AuthorServiceImpl authorServiceImpl;
    private AuthorEntity entityFirst, entitySecond;
    private AuthorResponseDto responseFirst, responseSecond;

    @BeforeEach
    public void setupTest() {
        authorRepositoryMock = mock(AuthorRepository.class);

        authorServiceImpl = new AuthorServiceImpl(authorRepositoryMock, mock(PostRepository.class));

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
        assertThat(authorResponseDtoList.size()).isEqualTo(2);
        assertThat(authorResponseDtoList.get(0)).isEqualTo(responseFirst);
        assertThat(authorResponseDtoList.get(1)).isEqualTo(responseSecond);
    }

    @Test
    public void find() throws AuthorNotFoundException {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));

        AuthorResponseDto authorResponseDto = authorServiceImpl.find(ID_TEST_1);

        assertThat(authorResponseDto).isNotNull();
        assertThat(authorResponseDto).isEqualTo(responseFirst);
    }

    @Test
    public void findThrowing() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                authorServiceImpl.find(ID_TEST_1)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );
    }

}