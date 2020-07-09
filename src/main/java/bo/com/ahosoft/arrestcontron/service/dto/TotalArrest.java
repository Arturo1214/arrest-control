package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.Office;
import bo.com.ahosoft.arrestcontron.domain.Unit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TotalArrest {
    private Unit unit;
    private Long totalArrested = 0L;
    private Long totalPedestrian = 0L;
    private Long totalPassenger = 0L;
    private Long totalDriver = 0L;
    private Long totalMotorized = 0L;
    private Long totalVehicle = 0L;
    private Long totalMotorcycle = 0L;
    private List<DetailTotal> detailTotals = new ArrayList<>();

    @Getter @Setter
    public static class DetailTotal {
        private Office office;
        private Long totalArrested = 0L;
        private Long totalPedestrian = 0L;
        private Long totalPassenger = 0L;
        private Long totalDriver = 0L;
        private Long totalMotorized = 0L;
        private Long totalVehicle = 0L;
        private Long totalMotorcycle = 0L;
    }
}
