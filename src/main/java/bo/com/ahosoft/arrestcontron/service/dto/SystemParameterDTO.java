package bo.com.ahosoft.arrestcontron.service.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A SystemParameter.
 */
@Getter @Setter @ToString
public class SystemParameterDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String id;

    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemParameterDTO)) {
            return false;
        }
        return id != null && id.equals(((SystemParameterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
