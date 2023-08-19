package pt.sardoalware.gabrikid.hardcoreadventureblog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.service.AuthorService;
import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> insert(@RequestBody @Valid AuthorDto authorDto) {
        return ResponseEntity.ok(authorService.insert(authorDto));
    }

}