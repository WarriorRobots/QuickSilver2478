/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.DashboardContainer;
import frc.robot.RobotMap;
import frc.robot.Vars;

public class FeedSubsystem extends SubsystemBase {

  private WPI_TalonSRX m_feed;
  //private DigitalInput m_infraredSensor = new DigitalInput(RobotMap.ID_FEED_INFRARED) ;

  /**
   * Creates a new Intake.
   */
  public FeedSubsystem() {
    m_feed = new WPI_TalonSRX(RobotMap.ID_FEED);
    m_feed.setInverted(Vars.FEED_REVERSED);

    //m_infraredSensor = new DigitalInput(RobotMap.ID_FEED_INFRARED);
  }

  // Spins the motor at some low value and other for a specific percent, 

  public void feedAtPercent(double percent)
  {
    m_feed.set(ControlMode.PercentOutput, percent);
  }

  /**
   * Detects if a note is inside of the feed.
   * @return True if a note is present, false if otherwise.
   */
  public boolean containsNote() {
    return false;
    //return !m_infraredSensor.get(); // infrared reads false when it sees a note
  }

  public void stop() {
    m_feed.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Feed/Has note", containsNote()); 
  }

}
