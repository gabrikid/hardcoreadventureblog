package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.EmailAlreadyExistsException;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAll();

    AuthorDto create(AuthorDto authorDto)
            throws EmailAlreadyExistsException;

    AuthorDto update(Integer id, AuthorDto authorDto)
            throws AuthorNotFoundException, EmailAlreadyExistsException;

    AuthorDto delete(Integer id)
            throws AuthorNotFoundException;

}