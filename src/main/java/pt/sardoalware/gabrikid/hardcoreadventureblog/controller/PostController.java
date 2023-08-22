package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.PostNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.service.PostService;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDto> find(
            @PathVariable Integer id
    ) throws PostNotFoundException {
        return ResponseEntity.ok(postService.find(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDto> create(
            @RequestBody @Valid PostRequestDto postRequestDto
    ) throws AuthorNotFoundException {
        return ResponseEntity.ok(postService.create(postRequestDto));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Integer id, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto
    ) throws PostNotFoundException {
        return ResponseEntity.ok(postService.update(id, postUpdateRequestDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDto> delete(
            @PathVariable Integer id
    ) throws PostNotFoundException {
        return ResponseEntity.ok(postService.delete(id));
    }

}