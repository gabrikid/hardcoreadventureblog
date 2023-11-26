package pt.sardoalware.gabrikid.hardcoreadventureblog.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.PostUpdateRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.PostEntity;
import java.util.List;

// -----------------------------------------------------------------
// to prevent PITest mutation, we need an annotation with, at least,
// CLASS level retention, and javax.annotation.processing.Generated
// has SOURCE level retention.
// -----------------------------------------------------------------
@AnnotateWith(value = lombok.Generated.class)
// -----------------------------------------------------------------
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorEntity", source = "authorEntity")
    PostEntity dtoToEntity(PostRequestDto postRequestDto, AuthorEntity authorEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postedOn", ignore = true)
    @Mapping(target = "authorEntity", ignore = true)
    void mergeDtoToEntity(PostUpdateRequestDto postUpdateRequestDto, @MappingTarget PostEntity postEntity);

    @Mapping(target = "author", source = "authorEntity")
    PostResponseDto entityToDto(PostEntity postEntity);

    List<PostResponseDto> entityIterableToDtoList(Iterable<PostEntity> postEntityIterable);

}