public class MobileDisplay implements DisplayInterface {

    private final WeatherStation weatherStation;

    private double temperature;

    public MobileDisplay(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
    }

    @Override
    public void update() {
        this.temperature = weatherStation.getTemperature();
    }
}
