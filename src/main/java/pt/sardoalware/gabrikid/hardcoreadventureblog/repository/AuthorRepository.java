package pt.sardoalware.gabrikid.hardcoreadventureblog.repository;

import org.springframework.data.repository.CrudRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {

}