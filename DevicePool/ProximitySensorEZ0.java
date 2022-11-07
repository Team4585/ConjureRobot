package frc.robot.huskylib.devices;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.huskylib.src.RoboDevice;

public class ProximitySensorEZ0 extends RoboDevice{

    private static final double CENTIMETERS_PER_VOLT = 102.4;
    private static final double INCHES_PER_VOLT = 40.315;
    private AnalogInput m_sensor;
    private double m_curReadingRaw = 0.0;
    private double m_currentValueCM = 0.0;
    private double m_currentValueInches = 0.0;

    public ProximitySensorEZ0(String sensorName, int inputChannel){
        super(sensorName);

        m_sensor = new AnalogInput(inputChannel);
    }

    @Override
    public void doGatherInfo() {
        m_curReadingRaw = m_sensor.getVoltage();
        m_currentValueCM = m_curReadingRaw * CENTIMETERS_PER_VOLT;
        m_currentValueInches = m_curReadingRaw * INCHES_PER_VOLT;
    }

    public double getCurrentDistanceCM(){
        return m_currentValueCM;
    }

    public double getCurrentDistanceInches(){
        return m_currentValueInches;
    }

}
