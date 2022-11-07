package frc.robot.huskylib.src;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;


public class WestCoastDriveTrain extends RoboDevice{

  private double m_targSpeed = 0.0;
  private double m_targRotationSpeed = 0.0;
  private double m_currentSpeed = 0.0;
  private double m_currentRotationSpeed = 0.0;

  private TalonSRX m_leftMasterController;
  private VictorSPX m_leftSlaveController;
  private TalonSRX m_rightMasterController;
  private VictorSPX m_rightSlaveController;


  public WestCoastDriveTrain(int leftMasterID, int leftSlaveID, int rightMasterID, int rightSlaveID){
    super("WestCoastDriveTrain");

    m_leftMasterController = new TalonSRX(leftMasterID);
    m_leftSlaveController = new VictorSPX(leftSlaveID);
    m_rightMasterController = new TalonSRX(rightMasterID);
    m_rightSlaveController = new VictorSPX(rightSlaveID);

    m_leftSlaveController.follow(m_leftMasterController);
    m_rightSlaveController.follow(m_rightMasterController);
  }

  public void Initialize(){
  }

  public void setTargSpeed(double targSpeed){
    m_targSpeed = targSpeed;
  }

  public void setTargRotationSpeed(double targRotationSpeed){
    m_targRotationSpeed = targRotationSpeed;
  }

  public double getTargSpeed(){
    return m_targSpeed;
  }

  public double getTargRotationSpeed(){
    return m_targRotationSpeed;
  }

  public double getCurrentSpeed(){
    return m_currentSpeed;
  }

  public double getCurrentRotationSpeed(){
    return m_currentRotationSpeed;
  }

  @Override
  public void doGatherInfo() {
    super.doGatherInfo();
  }

  @Override
  public void doActions() {
    super.doActions();

    m_leftMasterController.set(ControlMode.PercentOutput, m_targSpeed - m_targRotationSpeed);
    m_rightMasterController.set(ControlMode.PercentOutput, m_targRotationSpeed - m_targSpeed);

    // if(m_currentSpeed < m_targSpeed){
    //   m_currentSpeed += 0.01;
    // }
    // else if (m_currentSpeed > m_targSpeed) {
    //   m_currentSpeed -= 0.01;
    // }

    // if(m_currentRotationSpeed < m_targRotationSpeed){
    //   m_currentRotationSpeed += 0.01;
    // }
    // else if (m_currentRotationSpeed > m_targRotationSpeed) {
    //   m_currentRotationSpeed -= 0.01;
    // }

    if(debugModeOn()){
      System.out.println(getDeviceName() + "-- speed: " + m_currentSpeed + "  rotation: " + m_currentRotationSpeed);
    }

  }

}
