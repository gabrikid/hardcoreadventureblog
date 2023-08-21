package pt.sardoalware.gabrikid.hardcoreadventureblog.repository;

import org.springframework.data.repository.CrudRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

    Integer deleteByAuthorEntity(AuthorEntity authorEntity);

}