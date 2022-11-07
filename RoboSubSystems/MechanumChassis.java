package frc.robot;

import frc.robot.huskylib.src.*;
import frc.robot.huskylib.src.MechanumDriveTrain;
import frc.robot.huskylib.src.RoboDevice;

public class XXRoboXXChassis extends RoboDevice{

  private MechanumDriveTrain m_driveTrain = 
    new MechanumDriveTrain(  WiringConnections.LEFT_FRONT_CONTROLLER_ID,
                             WiringConnections.LEFT_REAR_CONTROLLER_ID,
                             WiringConnections.RIGHT_FRONT_CONTROLLER_ID,
                             WiringConnections.RIGHT_REAR_CONTROLLER_ID
                          );

  private HeadingSensor m_HeadingSensor = null;
  private LocationSensor m_LocationSensor = null;
  
  private HuskyVector2D m_currentLocation = new HuskyVector2D();
  private double m_currentHeading = 0.0;
  private HuskyVector2D m_currentVelocity = new HuskyVector2D();;

  public XXRoboXXChassis(){
    super("XXRoboXXChassis");

    AddChildDevice(m_driveTrain);
  }
    
  public void Initialize(){
    m_driveTrain.Initialize();
  }

  public void setTargForwardBack(double targFB){
    m_driveTrain.setTargForwardBackSpeed(targFB);
  }

  public void setTargSideToSide(double targSS){
    m_driveTrain.setTargSideToSideSpeed(targSS);
  }

  public void setTargRotation(double targRot){
    m_driveTrain.setTargRotationSpeed(targRot);
  }

  @Override
  public void doGatherInfo() {
    super.doGatherInfo();

    if(m_HeadingSensor != null){
      m_currentHeading = m_HeadingSensor.getCurrentHeading();
    }

    if(m_LocationSensor != null){
      m_currentLocation.setVals(m_LocationSensor.getCurrentLocation());
    }

  }

  public HuskyVector2D getCurrentLocation(){
    return m_currentLocation;
  }

  public HuskyVector2D getCurrentVelocity(){
    return m_currentVelocity;
  }

  public double getCurrentHeading(){
    return m_currentHeading;
  }


  @Override
  public void doActions() {
    super.doActions();
  }

}
