package pt.sardoalware.gabrikid.hardcoreadventureblog.entity;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorEntityTest {

    private static final Integer
            ID_TEST_1 = 1,
            ID_TEST_2 = 2;
    private static final String
            NAME_TEST_1 = "Test",
            EMAIL_TEST_1 = "test@test.com",
            NAME_TEST_2 = "Test2",
            EMAIL_TEST_2 = "test2@test.com",
            A_STRING = "aString";

    private AuthorEntity
            first,
            firstCopy,
            second,
            third,
            fourth;

    @BeforeEach
    public void setupTest() {
        first = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        firstCopy = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        second = new AuthorEntity(ID_TEST_2, NAME_TEST_1, EMAIL_TEST_1);
        third = new AuthorEntity(ID_TEST_1, NAME_TEST_2, EMAIL_TEST_1);
        fourth = new AuthorEntity(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_2);
    }

    @Test
    public void noArgsConstructor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(ID_TEST_1);
        authorEntity.setName(NAME_TEST_1);
        authorEntity.setEmail(EMAIL_TEST_1);

        assertThat(authorEntity.getId()).isEqualTo(ID_TEST_1);
        assertThat(authorEntity.getName()).isEqualTo(NAME_TEST_1);
        assertThat(authorEntity.getEmail()).isEqualTo(EMAIL_TEST_1);
    }

    @Test
    public void allArgsConstructor() {
        assertThat(first.getId()).isEqualTo(ID_TEST_1);
        assertThat(first.getName()).isEqualTo(NAME_TEST_1);
        assertThat(first.getEmail()).isEqualTo(EMAIL_TEST_1);
    }

    @Test
    public void toStringContains() {
        assertThat(first.toString()).contains(String.valueOf(first.getId()), first.getName(), first.getEmail());
    }

    @Test
    public void equalsReturnsTrue() {
        assertThat(first.equals(firstCopy)).isTrue();
    }

    @Test
    public void equalsReturnsFalse() {
        assertThat(first.equals(second)).isFalse();
        assertThat(first.equals(third)).isFalse();
        assertThat(first.equals(fourth)).isFalse();
        assertThat(first.equals(A_STRING)).isFalse();
    }

    @Test
    public void hashCodeEquals() {
        assertThat(first.hashCode()).isEqualTo(firstCopy.hashCode());
    }

    @Test
    public void hashCodeNotEquals() {
        assertThat(first.hashCode()).isNotEqualTo(second.hashCode());
        assertThat(first.hashCode()).isNotEqualTo(third.hashCode());
        assertThat(first.hashCode()).isNotEqualTo(fourth.hashCode());
        assertThat(first.hashCode()).isNotEqualTo(A_STRING.hashCode());
    }

}