package bo.com.ahosoft.arrestcontron.web.rest.vm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter @Setter @ToString
public class ArrestNoFineVM {

    @NotNull
    private Long id;

    @NotNull
    private String stateDescription;
}
