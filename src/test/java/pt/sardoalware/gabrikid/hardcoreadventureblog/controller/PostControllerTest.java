package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pt.sardoalware.gabrikid.hardcoreadventureblog.util.Constants.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.PostNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.service.PostService;
import java.time.Instant;
import java.util.List;

public class PostControllerTest {

    private PostService postServiceMock;
    private PostController postController;
    private PostRequestDto requestFirst;
    private PostUpdateRequestDto requestFirstUpdate;
    private PostResponseDto responseFirst;

    @BeforeEach
    public void setupTest() {
        postServiceMock = mock(PostService.class);

        postController = new PostController(postServiceMock);

        requestFirst = new PostRequestDto(TITLE_TEST_1, CONTENT_TEST_1, Instant.now(), ID_TEST_1);

        requestFirstUpdate = new PostUpdateRequestDto(TITLE_TEST_1, CONTENT_TEST_1);

        responseFirst = new PostResponseDto(
                ID_TEST_1,
                TITLE_TEST_1,
                CONTENT_TEST_1,
                requestFirst.postedOn(),
                new AuthorResponseDto(ID_TEST_1, NAME_TEST_1, EMAIL_TEST_1)
        );
    }

    @Test
    public void findAll() {
        when(postServiceMock.findAll()).thenReturn(List.of(responseFirst));

        ResponseEntity<List<PostResponseDto>> responseEntity = postController.findAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(List.of(responseFirst)); // *#Record assumption#*
        verify(postServiceMock).findAll();
    }

    @Test
    public void findAllEmpty() {
        when(postServiceMock.findAll()).thenReturn(List.of());

        ResponseEntity<List<PostResponseDto>> responseEntity = postController.findAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(List.of());
        verify(postServiceMock).findAll();
    }

    @Test
    public void find() throws PostNotFoundException {
        when(postServiceMock.find(ID_TEST_1)).thenReturn(responseFirst);

        ResponseEntity<PostResponseDto> responseEntity = postController.find(ID_TEST_1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(postServiceMock).find(ID_TEST_1);
    }

    @Test
    public void findThrowsPostNotFoundException() throws PostNotFoundException {
        when(postServiceMock.find(ID_TEST_1)).thenThrow(new PostNotFoundException());

        assertThatThrownBy(() ->
                postController.find(ID_TEST_1)
        ).isInstanceOf(
                PostNotFoundException.class
        );

        verify(postServiceMock).find(ID_TEST_1);
    }

    @Test
    public void create() throws AuthorNotFoundException {
        when(postServiceMock.create(requestFirst)).thenReturn(responseFirst);

        ResponseEntity<PostResponseDto> responseEntity = postController.create(requestFirst);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(postServiceMock).create(requestFirst);
    }

    @Test
    public void createThrowsAuthorNotFoundException() throws AuthorNotFoundException {
        when(postServiceMock.create(requestFirst)).thenThrow(new AuthorNotFoundException());

        assertThatThrownBy(() ->
                postController.create(requestFirst)
        ).isInstanceOf(
                AuthorNotFoundException.class
        );

        verify(postServiceMock).create(requestFirst);
    }

    @Test
    public void update() throws PostNotFoundException {
        when(postServiceMock.update(ID_TEST_1, requestFirstUpdate)).thenReturn(responseFirst);

        ResponseEntity<PostResponseDto> responseEntity = postController.update(ID_TEST_1, requestFirstUpdate);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(postServiceMock).update(ID_TEST_1, requestFirstUpdate);
    }

    @Test
    public void updateThrowsPostNotFoundException() throws PostNotFoundException {
        when(postServiceMock.update(ID_TEST_1, requestFirstUpdate)).thenThrow(new PostNotFoundException());

        assertThatThrownBy(() ->
                postController.update(ID_TEST_1, requestFirstUpdate)
        ).isInstanceOf(
                PostNotFoundException.class
        );

        verify(postServiceMock).update(ID_TEST_1, requestFirstUpdate);
    }

    @Test
    public void delete() throws PostNotFoundException {
        when(postServiceMock.delete(ID_TEST_1)).thenReturn(responseFirst);

        ResponseEntity<PostResponseDto> responseEntity = postController.delete(ID_TEST_1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(responseFirst); // *#Record assumption#*
        verify(postServiceMock).delete(ID_TEST_1);
    }

    @Test
    public void deleteThrowsPostNotFoundException() throws PostNotFoundException {
        when(postServiceMock.delete(ID_TEST_1)).thenThrow(new PostNotFoundException());

        assertThatThrownBy(() ->
                postController.delete(ID_TEST_1)
        ).isInstanceOf(
                PostNotFoundException.class
        );

        verify(postServiceMock).delete(ID_TEST_1);
    }

}