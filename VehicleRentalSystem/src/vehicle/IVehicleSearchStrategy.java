package vehicle;

import java.util.List;

public interface IVehicleSearchStrategy {
    List<Vehicle> search(List<Vehicle> allVehicles, String criteria);
}