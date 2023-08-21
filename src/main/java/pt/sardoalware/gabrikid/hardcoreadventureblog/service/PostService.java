package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.PostNotFoundException;
import java.util.List;

public interface PostService {

    List<PostResponseDto> findAll();

    PostResponseDto create(PostRequestDto postRequestDto)
            throws AuthorNotFoundException;

    PostResponseDto update(Integer id, PostUpdateRequestDto postUpdateRequestDto)
            throws PostNotFoundException;

    PostResponseDto delete(Integer id)
            throws PostNotFoundException;

}