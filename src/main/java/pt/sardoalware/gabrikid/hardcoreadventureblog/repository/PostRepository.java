package pt.sardoalware.gabrikid.hardcoreadventureblog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {



}