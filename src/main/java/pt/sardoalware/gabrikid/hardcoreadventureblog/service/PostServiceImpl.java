package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.PostNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.repository.PostRepository;
import java.util.List;
import java.util.Optional;
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

    @Override
    public PostResponseDto update(Integer id, PostUpdateRequestDto postUpdateRequestDto)
            throws PostNotFoundException {
        PostEntity postEntity = validatePostExists(id);

        postUpdateRequestDto.merge(postEntity);

        postEntity = postRepository.save(postEntity);

        return new PostResponseDto(postEntity);
    }

    @Override
    public PostResponseDto delete(Integer id)
            throws PostNotFoundException {
        PostEntity postEntity = validatePostExists(id);

        postRepository.delete(postEntity);

        return new PostResponseDto(postEntity);
    }

    protected PostEntity validatePostExists(Integer id) throws PostNotFoundException {
        Optional<PostEntity> postEntityOptional = postRepository.findById(id);

        if (postEntityOptional.isPresent()) {
            return postEntityOptional.get();
        }
        else {
            throw new PostNotFoundException();
        }
    }

}