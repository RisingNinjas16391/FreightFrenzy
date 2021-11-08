package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name = "Diagnostic", group = "Teleop")
public class Diagnostic extends LinearOpMode {

    Hardware robot = new Hardware();   //Uses heavily modified untested hardware
    static ElapsedTime runtime = new ElapsedTime();
    boolean inverted = false;
    boolean pressed = false;
    int selection = 0;

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        robot.rightFront.setPower(0);
        robot.leftFront .setPower(0);
        robot.rightRear .setPower(0);
        robot.leftRear  .setPower(0);
        robot.arm.setPower(0);
        robot.feederLeft.setPower(0);
        robot.feederRight.setPower(0);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);      //When finished remove
        robot.leftFront .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.feederLeft  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.feederRight  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRear .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRear  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.feederLeft  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.feederRight  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Press Start to Begin");    //
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            editHere();
            displayTelemetry();
            // Pace this loop so jaw action is reasonable speed.
            sleep(25);
        }
    }

    public void editHere() {
        // drive train control
        double control = gamepad1.left_stick_y;

        robot.rightFront    .setPower(((selection == 1) ? 1:0) * control);
        robot.leftFront     .setPower(((selection == 2) ? 1:0) * control);
        robot.rightRear     .setPower(((selection == 3) ? 1:0) * control);
        robot.leftRear      .setPower(((selection == 4) ? 1:0) * control);
        robot.feederRight   .setPower(((selection == 5) ? 1:0) * control);
        robot.feederLeft    .setPower(((selection == 6) ? 1:0) * control);
        robot.arm           .setPower(((selection == 7) ? 1:0) * control);


        if (gamepad1.left_bumper) {
            if (!pressed) {
                if (selection > 0) {
                    selection--;
                }
                pressed = true;
            }
        } else if (gamepad1.right_bumper) {
            if (!pressed) {
                if (selection < 7) {
                    selection ++;
                }
                pressed = true;
            }
        } else {
            pressed = false;
        }
    }

    public void displayTelemetry() {
        telemetry.addLine("Drive Encoder ticks")
                .addData("Front Left", -robot.rightFront.getCurrentPosition())
                .addData("Front Right", -robot.leftFront.getCurrentPosition())
                .addData("Back Left", -robot.rightRear.getCurrentPosition())
                .addData("Back Right", -robot.leftRear.getCurrentPosition());
        // telemetry.addLine("Launcher")
        //         .addData("Speed", robot.flywheel.getVelocity())
        // .addData("intake Location", robot.intake.getCurrentPosition());
        telemetry.addLine("Position Of Feeder")
                .addData("", robot.arm.getCurrentPosition());
                //.addData("", robot.armLeft.getCurrentPosition());
        telemetry.addLine("Inverted boolean")
                .addData("Boolean value", inverted);
        telemetry.update();
    }
}
