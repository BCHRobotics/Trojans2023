package frc.util.control;

public class Output {

    public FeedForwardControl feedForward;
    public PIDControl pid;

    public Output(FeedForwardConstants aFF, PIDConstants pid) {
        this.feedForward = new FeedForwardControl(aFF);
        this.pid = new PIDControl(pid);
    }

    public double calculate(double setPoint, double feedback, double velFF) {
        return this.feedForward.calculate(setPoint, velFF) + this.pid.calculate(feedback, setPoint);
    }

}