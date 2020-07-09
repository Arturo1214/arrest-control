package bo.com.ahosoft.arrestcontron.web.rest.vm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter @Setter @ToString
public class ArrestPendingVM {
    @NotNull
    private Long id;

}
