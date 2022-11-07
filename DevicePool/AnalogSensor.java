package frc.robot.huskylib.devices;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.huskylib.src.RoboDevice;

public class AnalogSensor extends RoboDevice{

    private static final int MAX_READING = 100;

    private AnalogPotentiometer m_pot;
    private double m_curReadingRaw = 0.0;
    private double m_conversionScale = 100.0;
    private int m_currentValue = 0;

    public AnalogSensor(String sensorName, int analogPort){
        super(sensorName);

        m_pot = new AnalogPotentiometer(analogPort);
    }
    
    public void setConversionScale(double newScale){
        m_conversionScale = newScale;
    }


    @Override
    public void doGatherInfo() {
        m_curReadingRaw = m_pot.get();
        m_currentValue = (int)(m_curReadingRaw * m_conversionScale);

        if(m_currentValue > MAX_READING){
            m_currentValue = MAX_READING;
        }
    }

    public int getCurrentReading(){
        return m_currentValue;
    }
}
