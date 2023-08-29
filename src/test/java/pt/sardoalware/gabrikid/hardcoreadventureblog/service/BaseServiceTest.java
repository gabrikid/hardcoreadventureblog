package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.AuthorRepository;
import java.util.Optional;

public class BaseServiceTest {

    private AuthorRepository authorRepositoryMock;
    private BaseService baseService;

    @BeforeEach
    public void setupTest() {
        authorRepositoryMock = mock(AuthorRepository.class);
        baseService = mock(BaseService.class, CALLS_REAL_METHODS);
    }

    @Test
    public void validateRecordExistence() throws AuthorNotFoundException {
        AuthorEntity
                first = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1),
                firstCopy = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);

        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(first));

        AuthorEntity authorEntity = baseService.validateRecordExistence(
                authorRepositoryMock, ID_TEST_1, AuthorNotFoundException::new
        );

        assertThat(authorEntity).isSameAs(first);
        assertThat(authorEntity).isEqualTo(firstCopy);
        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

    @Test
    public void validateRecordExistenceThrowing() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                baseService.validateRecordExistence(authorRepositoryMock, ID_TEST_1, AuthorNotFoundException::new)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );
        verify(authorRepositoryMock).findById(ID_TEST_1);
    }

}