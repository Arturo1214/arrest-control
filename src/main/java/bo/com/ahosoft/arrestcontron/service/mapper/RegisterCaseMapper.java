package bo.com.ahosoft.arrestcontron.service.mapper;


import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.service.dto.RegisterCaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RegisterCase} and its DTO {@link RegisterCaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UnitMapper.class, TypeCaseMapper.class})
public interface RegisterCaseMapper extends EntityMapper<RegisterCaseDTO, RegisterCase> {

    @Mapping(source = "receptionist.id", target = "receptionistId")
    @Mapping(source = "receptionist.login", target = "receptionistLogin")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "dispatcher.id", target = "dispatcherId")
    @Mapping(source = "typeCase.id", target = "typeCaseId")
    RegisterCaseDTO toDto(RegisterCase registerCase);

    @Mapping(source = "receptionistId", target = "receptionist")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "dispatcherId", target = "dispatcher")
    @Mapping(source = "typeCaseId", target = "typeCase")
    @Mapping(target = "supportPatrol", ignore = true)
    @Mapping(target = "checkDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    RegisterCase toEntity(RegisterCaseDTO registerCaseDTO);

    default RegisterCase fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegisterCase registerCase = new RegisterCase();
        registerCase.setId(id);
        return registerCase;
    }
}
