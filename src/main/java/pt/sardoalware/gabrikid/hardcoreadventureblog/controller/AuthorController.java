package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDeleteResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.EmailAlreadyExistsException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.service.AuthorService;
import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorResponseDto>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseDto> create(
            @RequestBody @Valid AuthorRequestDto authorRequestDto
    ) throws EmailAlreadyExistsException {
        return ResponseEntity.ok(authorService.create(authorRequestDto));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseDto> update(
            @PathVariable Integer id, @RequestBody @Valid AuthorRequestDto authorRequestDto
    ) throws AuthorNotFoundException, EmailAlreadyExistsException {
        return ResponseEntity.ok(authorService.update(id, authorRequestDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDeleteResponseDto> delete(
            @PathVariable Integer id
    ) throws AuthorNotFoundException {
        return ResponseEntity.ok(authorService.delete(id));
    }

}