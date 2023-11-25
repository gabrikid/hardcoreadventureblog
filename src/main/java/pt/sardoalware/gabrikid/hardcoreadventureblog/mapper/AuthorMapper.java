package pt.sardoalware.gabrikid.hardcoreadventureblog.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorDeleteResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorRequestDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.AuthorResponseDto;
import pt.sardoalware.gabrikid.hardcoreadventureblog.entity.AuthorEntity;
import java.util.List;

// -----------------------------------------------------------------
// to prevent PITest mutation, we need an annotation with, at least,
// CLASS level retention, and javax.annotation.processing.Generated
// has SOURCE level retention.
// -----------------------------------------------------------------
@AnnotateWith(value = lombok.Generated.class)
// -----------------------------------------------------------------
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthorMapper {

    AuthorEntity dtoToEntity(AuthorRequestDto authorRequestDto);

    void mergeDtoToEntity(AuthorRequestDto authorRequestDto, @MappingTarget AuthorEntity authorEntity);

    AuthorResponseDto entityToDto(AuthorEntity authorEntity);

    List<AuthorResponseDto> entityIterableToDtoList(Iterable<AuthorEntity> authorEntityList);

    AuthorDeleteResponseDto entityToDeleteDto(AuthorEntity authorEntity, Integer postsDeleted);

}