package frc.robot.huskylib.devices;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.huskylib.src.RoboDevice;

public class DIOSensor extends RoboDevice{
    
    private int m_channel;
    private DigitalInput m_input;
    private boolean m_isClosed = true;
    private boolean m_justChanged = false;

    public DIOSensor(String sensorName, int channelID){
        super(sensorName);

        m_channel = channelID;
        m_input = new DigitalInput(m_channel);

    }

	@Override
    public void doGatherInfo() {
        super.doGatherInfo();

        boolean rawReading = m_input.get();
        m_justChanged = (rawReading != m_isClosed);
        m_isClosed = rawReading;
    }

    public boolean IsClosed(){
        return m_isClosed;
    }

    public boolean IsOpen(){
        return !m_isClosed;
    }

    public boolean CloseEvent(){
        return (m_justChanged && m_isClosed);
    }

    public boolean OpenEvent(){
        return (m_justChanged && !m_isClosed);
    }

}
