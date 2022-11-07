package frc.robot.huskylib.devices;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import frc.robot.huskylib.src.HeadingSensor;
import frc.robot.huskylib.src.LocationSensor;
import frc.robot.huskylib.src.RoboDevice;
import frc.robot.huskylib.src.HuskyVector2D;

public class NavX extends RoboDevice implements HeadingSensor, LocationSensor{
    
    private AHRS m_ahrs;
    private double m_currentHeading = 0.0;
    private HuskyVector2D m_currentLocation = new HuskyVector2D();

    public NavX(){
        super("NavX");

        m_ahrs = new AHRS(SPI.Port.kMXP);
        m_ahrs.reset();
        m_ahrs.calibrate();
    }

    @Override
    public void Initialize(){

    }

    public void DoReset(){
        m_ahrs.reset();
        m_ahrs.resetDisplacement();
        m_ahrs.calibrate();
    }

    @Override
    public double getCurrentHeading(){
        return m_currentHeading;
    }

    @Override
	public HuskyVector2D getCurrentLocation(){
        return m_currentLocation;
    }

    @Override
	public double getCurrentX(){
        return m_currentLocation.getX();
    }

    @Override
	public double getCurrentY(){
        return m_currentLocation.getY();
    }

    @Override
    public void doGatherInfo() {
        super.doGatherInfo();

        m_currentHeading = m_ahrs.getYaw();
        m_currentLocation.setX(m_ahrs.getDisplacementX());
        m_currentLocation.setY(m_ahrs.getDisplacementY());
    }

}
