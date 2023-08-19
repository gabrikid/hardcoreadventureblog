package pt.sardoalware.gabrikid.hardcoreadventureblog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;

public interface PostRepository extends PagingAndSortingRepository<PostEntity, Integer> {



}