// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
//import com.revrobotics.SparkRelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.Vars;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */
  // Motor Declaration
  private CANSparkMax arm_left, arm_right;
  private RelativeEncoder m_armEncoder;
  public SparkPIDController m_armController;


  public ArmSubsystem() {
    // Configuration
    arm_left = new CANSparkMax(RobotMap.ID_ARM_LEFT, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    arm_left.restoreFactoryDefaults();
    m_armEncoder = arm_left.getEncoder();
    // m_armController = arm_left.getPIDController();

    // turn motor config
    arm_left.setInverted(Vars.LEFT_ARM_REVERSED);
    arm_left.setCANTimeout(Constants.MS_TIMEOUT);

    arm_right = new CANSparkMax(RobotMap.ID_ARM_RIGHT, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    arm_right.restoreFactoryDefaults();
    arm_right.follow(arm_left);
    arm_right.setInverted(Vars.RIGHT_ARM_REVERSED);
    arm_right.setCANTimeout(Constants.MS_TIMEOUT);


    // m_armController.setP(Vars.angleKP);
    // m_armController.setI(Vars.angleKI);
    // m_armController.setD(Vars.angleKD);
    // m_armController.setFF(Vars.angleKFF);


    // encoder offsets
    resetEncoders();

  }

  /**
   * Runs the Arms at a percent from -1 to 1
   * 
   * @param percent percent from -1 to 1
   */
  public void setPercent(double percent) {
    arm_left.set(percent);
  }

  // Sets the arm to the given unbounded angle (NO SAFETY)
  public void setArmAngleUnbounded(double degrees) {
    if(getArmAngle() < degrees && Math.abs(degrees-getArmAngle()) > Vars.ARM_TOLERANCE){
      arm_left.set(Vars.ARM_FORWARD);
      //arm_right.set(.5);
    }
    else if(getArmAngle() > degrees && Math.abs(degrees-getArmAngle()) > Vars.ARM_TOLERANCE){
      arm_left.set(Vars.ARM_BACKWARD);
      //arm_right.set(-.5);
    } else {
    arm_left.set(0);
    //arm_right.set(0);
    }
  }

  public void setAngleUnbounded(double degrees) {
    setArmAngleUnbounded(degrees);
  }

  // Sets the arm to the given bounded angle
  public void setAngleBounded(double degrees) {
    setArmAngleBounded(degrees);
  }

  /**
   * Sets the angle of the bounded arm (in degrees)
   * 
   * @param degrees
   */
  public void setArmAngleBounded(double degrees) {
    if (degrees < Vars.ARM_MIN_ANGLE) {
      setArmAngleUnbounded(Vars.ARM_MIN_ANGLE);
    } else if (degrees > Vars.ARM_MAX_ANGLE) {
      setArmAngleUnbounded(Vars.ARM_MAX_ANGLE);
    } else {
      setArmAngleUnbounded(degrees);
    }
  }

  public double getArmEnc() {
    return m_armEncoder.getPosition();
  }

  public double getArmAngle() {
    return toDegrees(getArmEnc());
  }


  public double toNative(double degrees) {
    return Math.round(degrees / Vars.ARM_GEARING * Constants.CLICKS_PER_REV_INTEGRATED / (360.0*12));
  }

  public double toDegrees(double nativeUnits) {
    return nativeUnits * Vars.ARM_GEARING / Constants.CLICKS_PER_REV_INTEGRATED * (360.0*12);
  }

  public void resetEncoders() {
    m_armEncoder.setPosition(0);
  }

  public void stop() {
    arm_left.stopMotor();
    //arm_right.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Encoder value", getArmEnc());
    SmartDashboard.putNumber("Arm Angle", getArmAngle());
  }

}
