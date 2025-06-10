public class WeatherStation extends Observable {

    private double temperature;
    private double pressure;
    private double windSpeed;
    private double humidity;

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
        notify();
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        notify();
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
        notify();
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        notify();
    }

    public void setWeatherData(double temperature, double pressure, double windSpeed, double humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        notify();
    }
}
