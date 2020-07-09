package bo.com.ahosoft.arrestcontron.service.mapper;


import bo.com.ahosoft.arrestcontron.domain.TypeCase;
import bo.com.ahosoft.arrestcontron.service.dto.TypeCaseDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link TypeCase} and its DTO {@link TypeCaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeCaseMapper extends EntityMapper<TypeCaseDTO, TypeCase> {


    default TypeCase fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeCase typeCase = new TypeCase();
        typeCase.setId(id);
        return typeCase;
    }
}
