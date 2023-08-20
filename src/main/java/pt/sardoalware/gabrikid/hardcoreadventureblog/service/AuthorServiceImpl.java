package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.EmailAlreadyExistsException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> findAll() {
        Iterable<AuthorEntity> authorEntityIterable = authorRepository.findAll();

        return StreamSupport
                .stream(authorEntityIterable.spliterator(), false)
                .map(AuthorDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto create(AuthorDto authorDto)
            throws EmailAlreadyExistsException {
        validateEmailNotExists(authorDto.getEmail());

        AuthorEntity authorEntity = authorRepository.save(
                AuthorDto.parse(authorDto)
        );

        authorDto.setId(authorEntity.getId());

        return authorDto;
    }

    @Override
    public AuthorDto update(Integer id, AuthorDto authorDto)
            throws AuthorNotFoundException, EmailAlreadyExistsException {
        AuthorEntity authorEntity = validateAuthorExists(id);

        if (!authorDto.getEmail().equals(authorEntity.getEmail())) {
            validateEmailNotExists(authorDto.getEmail());
        }

        AuthorDto.merge(authorDto, authorEntity);

        authorEntity = authorRepository.save(authorEntity);

        authorDto.setId(authorEntity.getId());

        return authorDto;
    }

    @Override
    public AuthorDto delete(Integer id)
            throws AuthorNotFoundException {
        AuthorEntity authorEntity = validateAuthorExists(id);

        authorRepository.delete(authorEntity);

        return AuthorDto.of(authorEntity);
    }

    private AuthorEntity validateAuthorExists(Integer id) throws AuthorNotFoundException {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findById(id);

        if (authorEntityOptional.isPresent()) {
            return authorEntityOptional.get();
        }
        else {
            throw new AuthorNotFoundException("Author not found");
        }
    }

    private void validateEmailNotExists(String email) throws EmailAlreadyExistsException {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findByEmailIgnoreCase(email);

        if (authorEntityOptional.isPresent()) {
            throw new EmailAlreadyExistsException("The email already exists");
        }
    }

}