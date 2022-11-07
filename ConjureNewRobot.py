"""
    File is: ConjureNewRobot.py
    FRC Team 4585   Husky Robotics

"""
import argparse
import sys
import os
import datetime
import shutil

class ConjureRobot():
    def __init__(self, args):
        self.__RobotName = args.name
        self.__TargSrcDir = "..\\" + self.__RobotName + "\\src\\main\\java\\frc\\robot"
        self.__TeleopDecisionsStr = ""

        self.__SubsystemDeclarationsStr = ""
        self.__SubsystemInitializationsStr = ""
        self.__TeleopDeclarationsStr = ""
        self.__TeleopSetVarsStr = ""
        self.__AutoDeclarationsStr = ""
        self.__AutoSetVarsStr = ""

    def DoRobotCreation(self):
        RetVal = 0      # assume success
    
        self.CopyRawTemplateDir()
        self.SelectChassis()
        self.DesignateSubsystems()
        self.AdjustFileContents()
      
        return(RetVal)


    def SelectChassis(self):
        ChassisType = int(input((
            "Enter Chassis type:\n"
            "  1 -> West Coast\n"
            "  2 -> Mechanum\n"
            "  3 -> West Coast Dummy\n"
            "  4 -> Mechanum Dummy\n"
            "  5 -> None\n")))

        if(ChassisType == 1):
            self.AddWestCoastChassis()
        
        elif(ChassisType == 2):
            self.AddMechanumChassis()
        
        elif(ChassisType == 3):
            self.AddWestCoastDummyChassis()
        
        elif(ChassisType == 4):
            self.AddMechanumDummyChassis()
        
        elif(ChassisType == 5):
            self.AddNoChassis()

        else:
            print("Unknown Chassis specified: " + ChassisType)
   

    def DesignateSubsystems(self):
        CheckForAnotherSubsystem = True
        NewSubsystemName = ""
        
        while CheckForAnotherSubsystem:
            NewSubsystemName = input("Enter new subsystem name -> ")
            if NewSubsystemName != "":
                # print("   New subsystem entered: " + NewSubsystemName)
                TargFileName = os.path.join(self.__TargSrcDir, NewSubsystemName + ".java")
                shutil.copyfile(os.path.join("RoboSubSystems", "SubSystemTemplate.java"), TargFileName)
                self.ReplaceStringsInFile(TargFileName, "XXRoboSubsystemNameXX", NewSubsystemName)

                self.__SubsystemDeclarationsStr += (
                    "  private {} m_{} = new {}();\n"
                    ).format(NewSubsystemName, NewSubsystemName, NewSubsystemName)
                self.__SubsystemInitializationsStr += (
                    "    m_TeleopDecider.set{}SubSystem(m_{});\n"
                    "    m_AutoDecider.set{}SubSystem(m_{});\n"
                    ).format(NewSubsystemName, NewSubsystemName, NewSubsystemName, NewSubsystemName)
                self.__TeleopDeclarationsStr += (
                    "  private {} m_{};\n"
                    ).format(NewSubsystemName, NewSubsystemName)
                self.__TeleopSetVarsStr += (
                    "  public void set{}SubSystem({} {}Sys){{\n"
                    "    m_{} = {}Sys;\n"
                    "  }}\n"
                    "\n"
                    ).format(NewSubsystemName, NewSubsystemName, NewSubsystemName, NewSubsystemName, NewSubsystemName)
                self.__AutoDeclarationsStr += (
                    "  private {} m_{};\n"
                    ).format(NewSubsystemName, NewSubsystemName)
                self.__AutoSetVarsStr += (
                    "  public void set{}SubSystem({} {}Sys){{\n"
                    "    m_{} = {}Sys;\n"
                    "  }}\n"
                    "\n"
                    ).format(NewSubsystemName, NewSubsystemName, NewSubsystemName, NewSubsystemName, NewSubsystemName)

            else:
                CheckForAnotherSubsystem = False



    def CustomizeChassisWiring(self, WireStr):
        self.ReplaceStringsInFile(os.path.join(self.__TargSrcDir, "WiringConnections.java"), "XXChassisConstantsXX", WireStr)

    def CustomizeTeleop(self, DecisionStr):
        self.ReplaceStringsInFile(os.path.join(self.__TargSrcDir, "XXRoboXXTeleopDecisionMaker.java"), "XXRoboTeleopDecisionXX", DecisionStr)

    def AllChassisCommon(self):
        self.__SubsystemDeclarationsStr += (
            "  private {}Chassis m_TheChassis = new {}Chassis();\n"
            ).format(self.__RobotName, self.__RobotName)

        self.__TeleopDeclarationsStr += (
            "  private {}Chassis m_Chassis;\n"
            ).format(self.__RobotName)
            
        self.__SubsystemInitializationsStr += (
            "    m_TeleopDecider.setChassis(m_TheChassis);\n"
            "    m_AutoDecider.setChassis(m_TheChassis);\n"
            )

        self.__TeleopSetVarsStr += (
            "  public void setChassis({}Chassis TheChassis){{\n"
            "    m_Chassis = TheChassis;\n"
            "  }}\n"
            "\n"
            ).format(self.__RobotName)

        self.__AutoDeclarationsStr += (
            "  private {}Chassis m_Chassis;\n"
            ).format(self.__RobotName)
            
        self.__AutoSetVarsStr += (
            "  public void setChassis({}Chassis TheChassis){{\n"
            "    m_Chassis = TheChassis;\n"
            "  }}\n"
            "\n"
            ).format(self.__RobotName)


    def WestCoastChassisCommon(self):
        WCWiringDefaultStr = (
            "  public static final int LEFT_MASTER_CONTROLLER_ID = CAN_CHANNEL_0;\n"
            "  public static final int LEFT_SLAVE_CONTROLLER_ID = CAN_CHANNEL_1;\n"
            "  public static final int RIGHT_MASTER_CONTROLLER_ID = CAN_CHANNEL_2;\n"
            "  public static final int RIGHT_SLAVE_CONTROLLER_ID = CAN_CHANNEL_3;\n"
            )

        self.AllChassisCommon()

        shutil.copyfile(os.path.join("RoboSubSystems", "WestCoastChassis.java"), os.path.join(self.__TargSrcDir, "XXRoboXXChassis.java"))
        self.CustomizeChassisWiring(WCWiringDefaultStr)
        
        WCTeleopStr = (
            "      m_Chassis.setTargForwardBack(m_TheJoystick.getForwardBackwardValue());\n"
            "      m_Chassis.setTargRotation(m_TheJoystick.getTwistValue());\n"
        )
        self.CustomizeTeleop(WCTeleopStr)

    def AddWestCoastChassis(self):
        print("Add West Coast Chassis...")
        shutil.copyfile(os.path.join("RoboSubSystems", "WestCoastDriveTrain.java"), os.path.join(self.__TargSrcDir, "huskylib\\src", "WestCoastDriveTrain.java"))
        self.WestCoastChassisCommon()
        
        
    def AddWestCoastDummyChassis(self):
        print("Add West Coast Dummy Chassis...")
        shutil.copyfile(os.path.join("RoboSubSystems", "WestCoastDummyDriveTrain.java"), os.path.join(self.__TargSrcDir, "huskylib\\src", "WestCoastDriveTrain.java"))
        self.WestCoastChassisCommon()
        
    def MechanumChassisCommon(self):
        MecWiringDefaultStr = (
            "  public static final int LEFT_FRONT_CONTROLLER_ID = CAN_CHANNEL_1;\n"
            "  public static final int LEFT_REAR_CONTROLLER_ID = CAN_CHANNEL_3;\n"
            "  public static final int RIGHT_FRONT_CONTROLLER_ID = CAN_CHANNEL_2;\n"
            "  public static final int RIGHT_REAR_CONTROLLER_ID = CAN_CHANNEL_4;\n"
            )

        self.AllChassisCommon()

        shutil.copyfile(os.path.join("RoboSubSystems", "MechanumChassis.java"), os.path.join(self.__TargSrcDir, "XXRoboXXChassis.java"))
        self.CustomizeChassisWiring(MecWiringDefaultStr)

        MecTeleopStr = (
            "    m_Chassis.setTargForwardBack(m_TheJoystick.getForwardBackwardValue());\n"
            "    m_Chassis.setTargSideToSide(m_TheJoystick.getSideToSideValue());\n"
            "    m_Chassis.setTargRotation(m_TheJoystick.getTwistValue());\n"
        )
        self.CustomizeTeleop(MecTeleopStr)


    def AddMechanumChassis(self):
        print("Add Mechanum Chassis...")
        shutil.copyfile(os.path.join("RoboSubSystems", "MechanumDriveTrain.java"), os.path.join(self.__TargSrcDir, "huskylib\\src", "MechanumDriveTrain.java"))
        self.MechanumChassisCommon()

        
    def AddMechanumDummyChassis(self):
        print("Add Mechanum Dummy Chassis...")
        shutil.copyfile(os.path.join("RoboSubSystems", "MechanumDummyDriveTrain.java"), os.path.join(self.__TargSrcDir, "huskylib\\src", "MechanumDriveTrain.java"))
        self.MechanumChassisCommon()
        
    def AddNoChassis(self):
        print("No chassis...")
        self.CustomizeChassisWiring("")
        self.CustomizeTeleop("")


    def ReplaceStringsInFile(self, FileName, OrigStr, TargStr):
        with open(FileName, "r+") as f:
            AllFileString = f.read()
            AllFileString = AllFileString.replace(OrigStr, TargStr)
            f.seek(0)
            f.write(AllFileString)
            f.truncate()
            f.close()

    def AdjustFileContents(self):
        TeleopFile = os.path.join(self.__TargSrcDir, "XXRoboXXTeleopDecisionMaker.java")
        self.ReplaceStringsInFile(TeleopFile, "XXRoboTeleopVariablesXX", self.__TeleopDeclarationsStr)
        self.ReplaceStringsInFile(TeleopFile, "XXRoboTeleopSetVarsXX", self.__TeleopSetVarsStr)

        AutoFile = os.path.join(self.__TargSrcDir, "XXRoboXXAutonomousDecisionMaker.java")
        self.ReplaceStringsInFile(AutoFile, "XXRoboAutonomousVariablesXX", self.__AutoDeclarationsStr)
        self.ReplaceStringsInFile(AutoFile, "XXRoboAutonomousSetVarsXX", self.__AutoSetVarsStr)

        RobotFile = os.path.join(self.__TargSrcDir, "Robot.java")
        self.ReplaceStringsInFile(RobotFile, "XXRoboSubsystemDeclarationsXX", self.__SubsystemDeclarationsStr)
        self.ReplaceStringsInFile(RobotFile, "XXRoboSubsystemInitializeXX", self.__SubsystemInitializationsStr)

        for filename in os.listdir(self.__TargSrcDir):
            if "XXRoboXX" in filename:
                NewFileName = filename.replace("XXRoboXX", self.__RobotName)
                os.rename(os.path.join(self.__TargSrcDir, filename), os.path.join(self.__TargSrcDir, NewFileName))
                
        for filename in os.listdir(self.__TargSrcDir):
            if ".java" in filename:
                self.ReplaceStringsInFile(os.path.join(self.__TargSrcDir, filename), "XXRoboXX", self.__RobotName)


    def CopyRawTemplateDir(self):
        shutil.copytree("XXRoboXX", os.path.join("..", self.__RobotName))


        # CustomizeFile(".project")
        # CustomizeFile("build.gradle")
        # CustomizeFile("robot\\Main.java")
        # CustomizeFile(os.path.join("robot", self.__RobotName + ".java"))

#=================================================================

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("name",
                        help = "name of new robot.",
                        nargs = '?')

    args = parser.parse_args()
    return ConjureRobot(args)



if __name__ == '__main__':
    RobotConjure = main()
    sys.exit(RobotConjure.DoRobotCreation())

