package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AuthorServiceImpl authorService;

    @Override
    public List<PostResponseDto> findAll() {
        Iterable<PostEntity> postEntityIterable = postRepository.findAll();

        return StreamSupport
                .stream(postEntityIterable.spliterator(), false)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto create(PostRequestDto postRequestDto)
            throws AuthorNotFoundException {
        AuthorEntity authorEntity = authorService.validateAuthorExists(postRequestDto.authorId());

        PostEntity postEntity = postRepository.save(
                postRequestDto.parse(authorEntity)
        );

        return new PostResponseDto(postEntity);
    }

}