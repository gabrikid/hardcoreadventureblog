package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.exception.AuthorNotFoundException;
import java.util.List;

public interface PostService {

    List<PostResponseDto> findAll();

    PostResponseDto create(PostRequestDto postRequestDto)
            throws AuthorNotFoundException;

}