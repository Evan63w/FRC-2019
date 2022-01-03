/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.robot.commands.DriveTrainCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import 

/**
 * Tank Drive-Train-Subsystem for the robot. Uses PWMTalonSRX motors
 */
public class DriveTrainSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // Declare our motors
    private PWMTalonSRX r1;
    private PWMTalonSRX r2;
    private PWMTalonSRX l1;
    private PWMTalonSRX l2;
    private final double TURNING_CONSTANT = .5;
    private MecanumDrive drive;

    /**
     * Constructor for DriveTrainSubsystem. Handles our drive train
     * 
     * @param r1 Dependency injection for future mocking in JUnit tests
     * @param r2 Dependency injection for future mocking in JUnit tests
     * @param l1 Dependency injection for future mocking in JUnit tests
     * @param l2 Dependency injection for future mocking in JUnit tests
     */
    public DriveTrainSubsystem(PWMTalonSRX r1, PWMTalonSRX r2, PWMTalonSRX l1, PWMTalonSRX l2) {
        this.r1 = r1;
        this.r2 = r2;
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Drive robot based on controller sticks
     * 
     * @param speed Controller Left Stick - Y value
     * @param turn  Controller Right Stick - X value
     */
    public void drive(double LSX, double LSY, double RSX) {

        if (LSX != 0)
        {
            drive.mecanumDrive_Polar(Math.sqrt(LSX * LSX + LSY + LSY), Math.atan(LSY / LSX), m_driveStick.getZ());
        }
        else
        {
            drive.mecanumDrive_Polar(Math.sqrt(LSX * LSX + LSY + LSY), 90, m_driveStick.getZ());
        }
        // if (turn == 0) // We are not turning and can act based on just speed
        //     forback(speed);
        // else if (turn < 0) { // We are turning right
        //     // positive X is right, negative X is left
        //     r1.setSpeed(speed);
        //     r2.setSpeed(speed);
        //     l1.setSpeed(speed + TURNING_CONSTANT * turn);
        //     l2.setSpeed(speed + TURNING_CONSTANT * turn);
        // } else if (turn > 0) { // We are turning left
        //     l1.setSpeed(speed);
        //     l2.setSpeed(speed);
        //     r1.setSpeed(speed - TURNING_CONSTANT * turn);
        //     r2.setSpeed(speed - TURNING_CONSTANT * turn);
        // } else // Don't know what just happened and we shouldn't ever stop
        //     stop();
    }

    /**
     * Stop the robot
     */
    public void stop() {
        r1.setSpeed(0);
        r2.setSpeed(0);
        l1.setSpeed(0);
        l2.setSpeed(0);
    }

    /**
     * Move the robot based on speed (Controller Left stick Y - value)
     */
    public void forback(double speed) {
        r1.setSpeed(speed);
        r2.setSpeed(speed);
        l1.setSpeed(speed);
        l2.setSpeed(speed);
    }

    /**
     * Used for testing the JUnit test cases (broken because Mockito isn't working)
     */
    public double getR1Speed() {
        return this.r1.getSpeed();
    }

}
