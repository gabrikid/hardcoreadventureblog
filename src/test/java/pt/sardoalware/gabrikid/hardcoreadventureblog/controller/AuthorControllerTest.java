package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDeleteResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.EmailAlreadyExistsException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.service.AuthorService;
import java.util.List;

public class AuthorControllerTest {

    private AuthorService authorServiceMock;
    private AuthorController authorController;
    private AuthorRequestDto requestFirst;
    private AuthorResponseDto responseFirst;

    @BeforeEach
    public void setupTest() {
        authorServiceMock = mock(AuthorService.class);

        authorController = new AuthorController(authorServiceMock);

        requestFirst = new AuthorRequestDto(NAME_TEST_1, EMAIL_TEST_1);

        responseFirst = new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1);
    }

    @Test
    public void findAll() {
        when(authorServiceMock.findAll()).thenReturn(List.of(responseFirst));

        ResponseEntity<List<AuthorResponseDto>> responseEntity = authorController.findAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(responseFirst)); // *#Record assumption#*
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

    @Test
    public void find() throws AuthorNotFoundException {
        when(authorServiceMock.find(ID_TEST_1)).thenReturn(responseFirst);

        ResponseEntity<AuthorResponseDto> responseEntity = authorController.find(ID_TEST_1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(authorServiceMock).find(ID_TEST_1);
    }

    @Test
    public void findThrowsAuthorNotFoundException() throws AuthorNotFoundException {
        when(authorServiceMock.find(ID_TEST_1)).thenThrow(new AuthorNotFoundException());

        assertThatThrownBy(() ->
                authorController.find(ID_TEST_1)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorServiceMock).find(ID_TEST_1);
    }

    @Test
    public void create() throws EmailAlreadyExistsException {
        when(authorServiceMock.create(requestFirst)).thenReturn(responseFirst);

        ResponseEntity<AuthorResponseDto> responseEntity = authorController.create(requestFirst);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(authorServiceMock).create(requestFirst);
    }

    @Test
    public void createThrowsEmailAlreadyExists() throws EmailAlreadyExistsException {
        when(authorServiceMock.create(requestFirst)).thenThrow(new EmailAlreadyExistsException());

        assertThatThrownBy(() ->
                authorController.create(requestFirst)
        ).isInstanceOf(
                EmailAlreadyExistsException.class
        );

        verify(authorServiceMock).create(requestFirst);
    }

    @Test
    public void update() throws AuthorNotFoundException, EmailAlreadyExistsException {
        when(authorServiceMock.update(ID_TEST_1, requestFirst)).thenReturn(responseFirst);

        ResponseEntity<AuthorResponseDto> responseEntity = authorController.update(ID_TEST_1, requestFirst);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(authorServiceMock).update(ID_TEST_1, requestFirst);
    }

    @Test
    public void updateThrowsAuthorNotFound() throws AuthorNotFoundException, EmailAlreadyExistsException {
        when(authorServiceMock.update(ID_TEST_1, requestFirst)).thenThrow(new AuthorNotFoundException());

        assertThatThrownBy(() ->
                authorController.update(ID_TEST_1, requestFirst)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorServiceMock).update(ID_TEST_1, requestFirst);
    }

    @Test
    public void updateThrowsEmailAlreadyExists() throws AuthorNotFoundException, EmailAlreadyExistsException {
        when(authorServiceMock.update(ID_TEST_1, requestFirst)).thenThrow(new EmailAlreadyExistsException());

        assertThatThrownBy(() ->
                authorController.update(ID_TEST_1, requestFirst)
        ).isInstanceOf(
                EmailAlreadyExistsException.class
        );

        verify(authorServiceMock).update(ID_TEST_1, requestFirst);
    }

    @Test
    public void delete() throws AuthorNotFoundException {
        AuthorDeleteResponseDto responseFirstDelete = new AuthorDeleteResponseDto(
                ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1, TWO
        );

        when(authorServiceMock.delete(ID_TEST_1)).thenReturn(responseFirstDelete);

        ResponseEntity<AuthorDeleteResponseDto> responseEntity = authorController.delete(ID_TEST_1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirstDelete); // *#Record assumption#*
        verify(authorServiceMock).delete(ID_TEST_1);
    }

    @Test
    public void deleteThrowsAuthorNotFound() throws AuthorNotFoundException {
        when(authorServiceMock.delete(ID_TEST_1)).thenThrow(new AuthorNotFoundException());

        assertThatThrownBy(() ->
                authorController.delete(ID_TEST_1)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(authorServiceMock).delete(ID_TEST_1);
    }

}