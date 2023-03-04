package frc.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.Misc;

public class AutoSelecter {
    private static AutoSelecter instance;
    private static SendableChooser<String> autoChooser;

    public static AutoSelecter getInstance() {
        if (instance == null) {
            instance = new AutoSelecter();
        }
        return instance;
    }

    private AutoSelecter() {
        autoChooser = new SendableChooser<String>();
        autoChooser.setDefaultOption("DRIVE_BACK", "DRIVE_BACK_V1");
        autoChooser.addOption("DRIVE_BALANCE", "DRIVE_BALANCE_V3");
        autoChooser.addOption("SCORE BALANCE", "SCORE_BALANCE_V1");
        autoChooser.addOption("SCORE HIGH", "SCORE_HIGH_V1");
        autoChooser.addOption("LEAD_BY_NOSE_PLAYBACK", Misc.TEACH_MODE_FILE_NAME);

        SmartDashboard.putData("Autonomous Route", autoChooser);
    }

    public String getFileName() {
        return autoChooser.getSelected();
    }
}
