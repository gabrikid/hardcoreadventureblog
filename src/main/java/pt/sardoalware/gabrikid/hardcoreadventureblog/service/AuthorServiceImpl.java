package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
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
    public List<AuthorResponseDto> findAll() {
        Iterable<AuthorEntity> authorEntityIterable = authorRepository.findAll();

        return StreamSupport
                .stream(authorEntityIterable.spliterator(), false)
                .map(AuthorResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseDto create(AuthorRequestDto authorRequestDto)
            throws EmailAlreadyExistsException {
        validateEmailNotExists(authorRequestDto.email());

        AuthorEntity authorEntity = authorRepository.save(
                authorRequestDto.parse()
        );

        return new AuthorResponseDto(authorEntity);
    }

    @Override
    public AuthorResponseDto update(Integer id, AuthorRequestDto authorRequestDto)
            throws AuthorNotFoundException, EmailAlreadyExistsException {
        AuthorEntity authorEntity = validateAuthorExists(id);

        // if the author is changing its email, gotta make sure that the
        // email address is not already associated to another author
        if (!authorRequestDto.email().equalsIgnoreCase(authorEntity.getEmail())) {
            validateEmailNotExists(authorRequestDto.email());
        }

        authorRequestDto.merge(authorEntity);

        authorEntity = authorRepository.save(authorEntity);

        return new AuthorResponseDto(authorEntity);
    }

    @Override
    public AuthorResponseDto delete(Integer id)
            throws AuthorNotFoundException {
        AuthorEntity authorEntity = validateAuthorExists(id);

        authorRepository.delete(authorEntity);

        return new AuthorResponseDto(authorEntity);
    }

    protected AuthorEntity validateAuthorExists(Integer id) throws AuthorNotFoundException {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findById(id);

        if (authorEntityOptional.isPresent()) {
            return authorEntityOptional.get();
        }
        else {
            throw new AuthorNotFoundException();
        }
    }

    protected void validateEmailNotExists(String email) throws EmailAlreadyExistsException {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findByEmailIgnoreCase(email);

        if (authorEntityOptional.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
    }

}