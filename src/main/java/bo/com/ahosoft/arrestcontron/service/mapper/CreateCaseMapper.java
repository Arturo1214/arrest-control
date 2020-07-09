package bo.com.ahosoft.arrestcontron.service.mapper;


import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.service.dto.CreateCaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity {@link RegisterCase} and its DTO {@link CreateCaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class, TypeCaseMapper.class})
public interface CreateCaseMapper extends EntityMapper<CreateCaseDTO, RegisterCase> {

    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "typeCase.id", target = "typeCaseId")
    CreateCaseDTO toDto(RegisterCase registerCase);

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "stateCase", ignore = true)
    @Mapping(target = "descriptionCompletion", ignore = true)
    @Mapping(target = "receptionist", ignore = true)
    @Mapping(target = "dispatcher", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "checkDate", ignore = true)
    @Mapping(source = "typeCaseId", target = "typeCase")
    @Mapping(source = "unitId", target = "unit")
    RegisterCase toEntity(CreateCaseDTO dto);


    default RegisterCase fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegisterCase registerCase = new RegisterCase();
        registerCase.setId(id);
        return registerCase;
    }
}
