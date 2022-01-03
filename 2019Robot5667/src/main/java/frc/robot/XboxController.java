package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.Timer;

public class XboxController extends Joystick {

    Robot robot;
    // Controller values NOTE: Are these neccessary?
    // Other than for copycat I don't see a use for them - Rafael Piloto
    private double inputRSX, inputRSY, rStickX, rStickY; // Right joystick axis values
    private double inputLSX, inputLSY, lStickX, lStickY; // Left joystick axis values
    private double inputRT, inputLT; // Trigger values
    private boolean inputRB, inputLB; // Bumper states
    private boolean inputA, inputB, inputX, inputY, inputMenu, inputStart; // Button states
    private Object[] controls = { inputLSX, inputLSY, inputRSX, inputRSY, inputRT, inputLT, inputRB, inputLB, inputA,
            inputB, inputX, inputY, inputMenu, inputStart };
    private boolean drive;
    private final double kGHOST = .1; // Threshold for blocking ghost signals
    private final double tStep = .01;

    /**
     * Initializes an Xbox controller.
     * 
     * @param port Controller port (default should be 0)
     */

    public XboxController(int port) {
        // NOTE: Is this neccessary? Why not just use the Joystick functions? Is this
        // only for copycat?
        super(port);

        inputRSX = 0;
        rStickX = 0;
        inputLSY = 0;
        lStickY = 0;
        inputLSX = 0;
        inputRT = 0;
        inputLT = 0;
        inputRB = false;
        inputLB = false;
        inputA = false;
        inputB = false;
        inputX = false;
        inputY = false;
        inputMenu = false;
        inputStart = false;
        drive = true;

    }

    void updateController() {
        inputRSX = super.getRawAxis(RobotMap.sRightX_Port);
        inputRSY = -super.getRawAxis(RobotMap.sRightY_Port);
        inputLSX = super.getRawAxis(RobotMap.sLeftX_Port);
        inputLSY = -super.getRawAxis(RobotMap.sLeftY_Port);
        inputRT = super.getRawAxis(RobotMap.TriggerRight_Port);
        inputLT = super.getRawAxis(RobotMap.TriggerLeft_Port);
        inputRB = super.getRawButton(RobotMap.BumperRight_Port);
        inputLB = super.getRawButton(RobotMap.BumperLeft_Port);
        inputA = super.getRawButton(RobotMap.button_A_Port);
        inputB = super.getRawButton(RobotMap.button_B_Port);
        inputX = super.getRawButton(RobotMap.button_X_Port);
        inputY = super.getRawButton(RobotMap.button_Y_Port);
        inputMenu = super.getRawButton(RobotMap.button_menu_Port);
        inputStart = super.getRawButton(RobotMap.button_Start_Port);
    }

    public void recall(String command) {

        System.out.println("Starting recall...");
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/lvuser/" + command + ".txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                int prev = -1;
                for (int i = 0; i < controls.length; i++) {
                    if (i > 5) {
                        controls[i] = Boolean.parseBoolean(line.substring(prev + 1, line.indexOf(",", prev + 1)));
                    } else {
                        controls[i] = Double.parseDouble(line.substring(prev + 1, line.indexOf(",", prev + 1)));
                    }
                }

                enableController();

                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                Timer.delay(tStep);
            }
            String everything = sb.toString();
            System.out.println(everything);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done recalling.");

    }

    public void copycat(String command) {
        System.out.println("Starting Copy Cat... ");
        // double stop = input;

        try {
            File file = new File("./copycat/" + command + ".txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for (double t = 0; t != -1; t += tStep) {
                t = Math.round(t * 100) / 100.0;
                System.out.println(t);
                updateController();
                if (inputRB) {
                    break;
                }
                enableController();

                Timer.delay(tStep);

                for (Object input : controls) {
                    writer.write(input + ",");
                }
                writer.write("\n");

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done copying.");

        // System.out.println("Lower: " + lower.toString());
        // System.out.println("Upper: " + upper.toString());
    }

    /**
     * Executes robot commands based off the values of the controller.
     */
    public void enableController() { // NOTE: What are we doing here?
        // Check the values of the controller
        updateController();
        // Check buttons
        if (inputA) {
            while (inputA)
                updateController();
        } else if (inputB) {
            while (inputB)
                updateController();
        } else if (inputX) {
            while (inputX)
                updateController();
        } else if (inputY) {
            while (inputY)
                updateController();
        } else if (inputMenu) {
            while (inputMenu)
                updateController();
        } else if (inputLB) {
            while (inputLB)
                updateController();
        } else if (inputRB) {
            while (inputRB)
                updateController();
        } else if (inputStart) {
            while (inputStart)
                updateController();
        } else {
        }
    }

    /**
     * 
     * @return Returns stick values - Used in DriveTrain when driving
     */
    public double[][] getSticks() {
        // Returns the [0] Left Stick X & Y - [1] Right Stick X & Y
        return new double[][] { { this.getLeftX(), this.getLeftY() }, { this.getRightX(), this.getRightY() } };
    }

    /**
     * 
     * @return Returns stick value as long as it exceeds the kGhost value -
     *         otherwise we return 0
     */
    public double getRightX() {
        return (super.getRawAxis(RobotMap.sRightX_Port) > kGHOST || super.getRawAxis(RobotMap.sRightX_Port) < -kGHOST)
                ? super.getRawAxis(RobotMap.sRightX_Port)
                : 0;
    }

    /**
     * 
     * @return Returns stick value as long as it exceeds the kGhost value -
     *         otherwise we return 0
     */
    public double getRightY() {
        return (-super.getRawAxis(RobotMap.sRightY_Port) > kGHOST || -super.getRawAxis(RobotMap.sRightY_Port) < -kGHOST)
                ? -super.getRawAxis(RobotMap.sRightY_Port)
                : 0;
    }

    /**
     * 
     * @return Returns stick value as long as it exceeds the kGhost value -
     *         otherwise we return 0
     */
    public double getLeftX() {
        return (super.getRawAxis(RobotMap.sLeftX_Port) > kGHOST || super.getRawAxis(RobotMap.sLeftX_Port) < -kGHOST)
                ? super.getRawAxis(RobotMap.sLeftX_Port)
                : 0;
    }

    /**
     * 
     * @return Returns stick value as long as it exceeds the kGhost value -
     *         otherwise we return 0
     */
    public double getLeftY() {
        return (-super.getRawAxis(RobotMap.sLeftY_Port) > kGHOST || -super.getRawAxis(RobotMap.sLeftY_Port) < -kGHOST)
                ? -super.getRawAxis(RobotMap.sLeftY_Port)
                : 0;
    }

}