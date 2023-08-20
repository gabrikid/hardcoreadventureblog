package pt.sardoalware.gabrikid.hardcoreadventureblog.repository;

import org.springframework.data.repository.CrudRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {

    Optional<AuthorEntity> findByEmailIgnoreCase(String email);

}