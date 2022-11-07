package frc.robot.huskylib.src;


// Dummy version of Mechanum drive train

public class MechanumDriveTrain extends RoboDevice{

  private double m_targForwardBackwardSpeed = 0.0;  // 0.0 = stopped    1.0 = full speed forward    -1.0 = full speed backward
  private double m_targSideToSideSpeed = 0.0;       // 0.0 = stopped    1.0 = full speed right      -1.0 = full speed left
  private double m_targRotationSpeed = 0.0;         // 0.0 - stopped    1.0 = full counterclockwise -1.0 = full speed clockwise

  private double m_curForwardBackwardSpeed = 0.0;   // 0.0 = stopped    1.0 = full speed forward    -1.0 = full speed backward
  private double m_curSideToSideSpeed = 0.0;        // 0.0 = stopped    1.0 = full speed right      -1.0 = full speed left
  private double m_curRotationSpeed = 0.0;          // 0.0 - stopped    1.0 = full counterclockwise -1.0 = full speed clockwise


  private double m_currentSpeed = 0.0;
  private double m_currentRotationSpeed = 0.0;


  public MechanumDriveTrain(int leftFrontID, int leftRearID, int rightFrontID, int rightRearID){
    super("MechanumDummyDriveTrain");
  }

  public void Initialize(){
  }

  public void setTargForwardBackSpeed(double targFBSpeed){
    m_targForwardBackwardSpeed = targFBSpeed;
  }

  public void setTargSideToSideSpeed(double targSSSpeed){
    m_targSideToSideSpeed = targSSSpeed;
  }

  public void setTargRotationSpeed(double targRotSpeed){
    m_targRotationSpeed = targRotSpeed;
  }


  @Override
  public void doGatherInfo() {
    super.doGatherInfo();
  }

  @Override
  public void doActions() {
    super.doActions();

    // make this more sophisticated when we are use PID speeds
    double useForwardBackwardSpeed = m_targForwardBackwardSpeed;
    double useSideToSideSpeed = m_targSideToSideSpeed;
    double useRotationSpeed = m_targRotationSpeed;

    // scale all values back if they would go greater than 1.0 or less than -1.0
    double ratioVal = Math.max(Math.abs(useForwardBackwardSpeed) + Math.abs(useSideToSideSpeed) + Math.abs(useRotationSpeed), 1.0);

    m_curForwardBackwardSpeed = useForwardBackwardSpeed / ratioVal;
    m_curSideToSideSpeed = useSideToSideSpeed / ratioVal;
    m_curRotationSpeed = useRotationSpeed /ratioVal;

    if(debugModeOn()){
      System.out.println(getDeviceName() + "-- F/B: " + m_curForwardBackwardSpeed + "  S/S" + m_curSideToSideSpeed + "  Rot: " + useRotationSpeed);
    }

  }

}
