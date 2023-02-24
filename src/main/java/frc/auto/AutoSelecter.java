package frc.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.Constants.Misc;

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
        autoChooser.setDefaultOption("1_CONE_LOW", "AUTO_PATH_1");
        autoChooser.addOption("1_CUBE_LOW", "AUTO_PATH_2");
        autoChooser.addOption("2_CONE_MID", "AUTO_PATH_3");
        autoChooser.addOption("2_CUBE_MID", "AUTO_PATH_4");
        autoChooser.addOption("LIVE_RECORD", Misc.TEACH_MODE_FILE_NAME);

        SmartDashboard.putData("Autonomous Route", autoChooser);
    }

    public String getFileName() {
        return autoChooser.getSelected();
    }
}
