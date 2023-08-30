package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import static org.assertj.core.api.Assertions.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;

public class AuthorRequestDtoTest {

    private AuthorRequestDto requestFirst;
    private AuthorEntity entityFirst;

    @BeforeEach
    public void setupTest() {
        requestFirst = new AuthorRequestDto(NAME_TEST_1, EMAIL_TEST_1);

        entityFirst = new AuthorEntity(null, NAME_TEST_1, EMAIL_TEST_1);
    }

    @Test
    public void isRecord() {
        // There are assumptions made on some unit tests, commented with "*#Record assumption#*".
        // Records are immutable, which facilitates unit testing in the sense we can assert that
        // a returned object is the same as the one we provided in mock, without the need to
        // double-check its data, as there is no way to change it.
        // Given this, if AuthorRequestDto changes to normal class, the aforementioned tests
        // should be reviewed.
        assertThat(AuthorRequestDto.class.isRecord()).isTrue();
    }

    @Test
    public void parse() {
        assertThat(requestFirst.parse()).isEqualTo(entityFirst);
    }

    @Test
    public void merge() {
        AuthorEntity entityFirstMerge = new AuthorEntity(null, NAME_TEST_2, EMAIL_TEST_2);

        requestFirst.merge(entityFirstMerge);

        assertThat(entityFirstMerge).isEqualTo(entityFirst);
    }

}