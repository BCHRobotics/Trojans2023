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
        autoChooser.setDefaultOption("CONE_HIGH_BALANCE", "AUTO_PATH_1");
        autoChooser.addOption("CUBE_HIGH_BALANCE", "AUTO_PATH_2");
        autoChooser.addOption("2_CONE_LOW", "AUTO_PATH_3");
        autoChooser.addOption("2_CUBE_LOW", "AUTO_PATH_4");
        autoChooser.addOption("DRIVE_BACK", "AUTO_PATH_5");
        autoChooser.addOption("LEAD_BY_NOSE_PLAYBACK", Misc.TEACH_MODE_FILE_NAME);

        SmartDashboard.putData("Autonomous Route", autoChooser);
    }

    public String getFileName() {
        return autoChooser.getSelected();
    }
}
