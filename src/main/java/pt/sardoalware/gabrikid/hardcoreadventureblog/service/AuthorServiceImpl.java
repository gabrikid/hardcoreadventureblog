package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.AuthorRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> getAll() {
        Iterable<AuthorEntity> authorEntityIterable = authorRepository.findAll();

        return StreamSupport
                .stream(authorEntityIterable.spliterator(), false)
                .map(AuthorDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto insert(AuthorDto authorDto) {
        AuthorEntity authorEntity = authorRepository.save(
                AuthorDto.parse(authorDto)
        );

        authorDto.setId(authorEntity.getId());

        return authorDto;
    }

}