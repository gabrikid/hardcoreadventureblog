package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.service.AuthorService;

import java.util.List;

public class AuthorControllerTest {

    private AuthorService authorServiceMock;
    private AuthorController authorController;
    private AuthorRequestDto requestFirst, requestSecond;
    private AuthorResponseDto responseFirst, responseSecond;

    @BeforeEach
    public void setupTest() {
        authorServiceMock = mock(AuthorService.class);

        authorController = new AuthorController(authorServiceMock);

        requestFirst = new AuthorRequestDto(NAME_TEST_1, EMAIL_TEST_1);
        requestSecond = new AuthorRequestDto(NAME_TEST_2, EMAIL_TEST_2);

        responseFirst = new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
        responseSecond = new AuthorResponseDto(ID_TEST_2, NAME_TEST_2, EMAIL_TEST_2);
    }

    @Test
    public void findAll() {
        // don't need to validate AuthorResponseDto against a copy, as records are immutable!
        when(authorServiceMock.findAll()).thenReturn(List.of(responseFirst, responseSecond));

        ResponseEntity<List<AuthorResponseDto>> responseEntity = authorController.findAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(responseFirst, responseSecond));
        verify(authorServiceMock).findAll();
    }

    @Test
    public void findAllEmpty() {
        when(authorServiceMock.findAll()).thenReturn(List.of());

        ResponseEntity<List<AuthorResponseDto>> responseEntity = authorController.findAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(List.of());
        verify(authorServiceMock).findAll();
    }

}