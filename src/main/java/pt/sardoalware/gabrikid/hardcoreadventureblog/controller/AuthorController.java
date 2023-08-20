package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDto;
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
    public ResponseEntity<List<AuthorDto>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> create(@RequestBody @Valid AuthorDto authorDto)
            throws EmailAlreadyExistsException {
        return ResponseEntity.ok(authorService.create(authorDto));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> update(@PathVariable Integer id, @RequestBody @Valid AuthorDto authorDto)
            throws AuthorNotFoundException, EmailAlreadyExistsException {
        return ResponseEntity.ok(authorService.update(id, authorDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> delete(@PathVariable Integer id)
            throws AuthorNotFoundException {
        return ResponseEntity.ok(authorService.delete(id));
    }

}