package bo.com.ahosoft.arrestcontron.service.mapper;


import bo.com.ahosoft.arrestcontron.domain.*;
import bo.com.ahosoft.arrestcontron.service.dto.ArrestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Arrest} and its DTO {@link ArrestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArrestMapper extends EntityMapper<ArrestDTO, Arrest> {

    @Mapping(target = "office", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stateDescription", ignore = true)
    @Mapping(target = "depositNumber", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    Arrest toEntity(ArrestDTO dto);

    default Arrest fromId(Long id) {
        if (id == null) {
            return null;
        }
        Arrest arrest = new Arrest();
        arrest.setId(id);
        return arrest;
    }
}
