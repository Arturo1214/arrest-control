package bo.com.ahosoft.arrestcontron.web.rest.vm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter @Setter @ToString
public class ArrestPaidOutVM {

    @NotNull
    private Long id;

    @NotNull
    private String depositNumber;

    private String stateDescription;
}
