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

    private BaseService baseService;
    private AuthorRepository authorRepositoryMock;
    private AuthorEntity entityFirst;

    @BeforeEach
    public void setupTest() {
        baseService = mock(BaseService.class, CALLS_REAL_METHODS);
        authorRepositoryMock = mock(AuthorRepository.class);
        entityFirst = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
    }

    @Test
    public void validateRecordExistence() throws AuthorNotFoundException {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.of(entityFirst));

        AuthorEntity authorEntity = baseService.validateRecordExistence(
                authorRepositoryMock, ID_TEST_1, AuthorNotFoundException::new
        );

        assertThat(authorEntity).isSameAs(entityFirst);
    }

    @Test
    public void validateRecordExistenceThrowing() {
        when(authorRepositoryMock.findById(ID_TEST_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                baseService.validateRecordExistence(authorRepositoryMock, ID_TEST_1, AuthorNotFoundException::new)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );
    }

}