package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import edu.wpi.first.wpilibj.*;

import org.junit.*;

public class DriveTrainSubsystemTest {

    static { // Needed to fake out FRC into thinking we're legit
        HLUsageReporting.SetImplementation(new HLUsageReporting.Null());
    }

    @Test
    public void DriveTrainTest() { // Basic test currently passes

        PWMTalonSRX r1Mock = mock(PWMTalonSRX.class);
        PWMTalonSRX r2Mock = mock(PWMTalonSRX.class);
        PWMTalonSRX l1Mock = mock(PWMTalonSRX.class);
        PWMTalonSRX l2Mock = mock(PWMTalonSRX.class);

        DriveTrainSubsystem driveTrain = new DriveTrainSubsystem(r1Mock, r2Mock, l1Mock, l2Mock);
        driveTrain.drive(1, 0);
        System.out.println(driveTrain.getR1Speed()); // Should be 1, returns 0
        assertEquals(driveTrain.getR1Speed(), 1); // Fails because R1 speed is not 1, it is 0.0 (Bug)
    }
}