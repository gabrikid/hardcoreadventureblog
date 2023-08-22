package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDeleteResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.EmailAlreadyExistsException;
import java.util.List;

public interface AuthorService {

    List<AuthorResponseDto> findAll();

    AuthorResponseDto find(Integer id)
        throws AuthorNotFoundException;

    AuthorResponseDto create(AuthorRequestDto authorRequestDto)
            throws EmailAlreadyExistsException;

    AuthorResponseDto update(Integer id, AuthorRequestDto authorRequestDto)
            throws AuthorNotFoundException, EmailAlreadyExistsException;

    AuthorDeleteResponseDto delete(Integer id)
            throws AuthorNotFoundException;

}