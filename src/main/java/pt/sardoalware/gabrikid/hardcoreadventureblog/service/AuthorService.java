package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDto;
import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAll();

    AuthorDto insert(AuthorDto authorDto);

}