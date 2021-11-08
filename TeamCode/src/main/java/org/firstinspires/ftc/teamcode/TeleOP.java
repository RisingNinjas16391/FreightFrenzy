package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp", group = "Teleop")
public class TeleOP extends LinearOpMode {

    Hardware robot = new Hardware();   //Uses heavily modified untested hardware
    static ElapsedTime runtime = new ElapsedTime();
    boolean inverted = false;

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
        //robot.armLeft     .setPower(0);
        robot.feederLeft.setPower(0);
        robot.feederRight.setPower(0);
        robot.carousel.setPower(0);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.armLeft  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //robot.carouselSpinner  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);      //When finished remove
        robot.leftFront .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //robot.armLeft  .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.feederLeft  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.feederRight  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.carousel  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //robot.carouselSpinner .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRear .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRear  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.armLeft   .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.feederLeft  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.feederRight  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.carousel  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.arm.setTargetPosition(0);
        //robot.armLeft.setPower(1);
        //robot.armRight.setPower(1);
        //robot.carouselSpinner  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Press Start to Begin");    //
        telemetry.update();
        robot.arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.arm.setPower(0.20);
        waitForStart();
        if(opModeIsActive()) {
            robot.arm.setPower(0);
            robot.arm.setTargetPosition(0);
            robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.arm.setTargetPosition(0);
            robot.arm.setPower(1);
            while (opModeIsActive()) {
                editHere();
                displayTelemetry();
                // Pace this loop so jaw action is reasonable speed.
                sleep(25);
            }
        }
    }

    public void editHere() {
        // drive train control
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        robot.arm.setPower(1);

        double[] driveValues = {
                forward + strafe + turn,
                forward - strafe - turn,
                forward - strafe + turn,
                forward + strafe - turn
        };

        robot.rightFront.setPower(driveValues[0]);
        robot.leftFront.setPower(driveValues[1]);
        robot.rightRear.setPower(driveValues[2]);
        robot.leftRear.setPower(driveValues[3]);


        if (gamepad2.left_trigger > 0) {
            robot.feederLeft.setPower(-0.4);
            robot.feederRight.setPower(-0.4);

        } else if (gamepad2.right_trigger > 0) {
            robot.feederLeft.setPower(1);
            robot.feederRight.setPower(1);
        } else {
            robot.feederLeft.setPower(0.4 * (gamepad2.right_trigger - gamepad2.left_trigger));
            robot.feederRight.setPower(-0.4 * (gamepad2.right_trigger - gamepad2.left_trigger));
        }

        if (gamepad2.a) {
            // inverse feed
            robot.arm.setTargetPosition(Hardware.armPositions.INVERSEFEED);
        }
        if (gamepad2.x) {
            // inverse stage 3
            robot.arm.setTargetPosition(Hardware.armPositions.INVERSETOP);
        }
        if (gamepad2.b) {
            // inverse stage 1
            robot.arm.setTargetPosition(Hardware.armPositions.INVERSEBOTTOM);
        }
        if (gamepad2.y) {
            // inverse stage 2
            robot.arm.setTargetPosition(Hardware.armPositions.INVERSEMIDDLE);
        }
        if (gamepad2.dpad_left) {
            // first stage
            robot.arm.setTargetPosition(Hardware.armPositions.BOTTOM);;

        }
        if (gamepad2.dpad_up) {
            // second stage
            robot.arm.setTargetPosition(Hardware.armPositions.MIDDLE);;

        }
        if (gamepad2.dpad_right) {
            // third stage
            robot.arm.setTargetPosition(Hardware.armPositions.TOP);;

        }
        if (gamepad2.dpad_down) {
            // feed position
            robot.arm.setTargetPosition(Hardware.armPositions.FEED);
        }
        if(gamepad2.left_bumper) {
            // capstone up
            robot.arm.setTargetPosition(Hardware.armPositions.CAPUP);
        }
        if (gamepad2.right_bumper) {
            // capstone down
            robot.arm.setTargetPosition(Hardware.armPositions.CAPDOWN);
        }
        else {
            robot.carousel.setPower(gamepad2.right_stick_x);

        }

    }

    public void displayTelemetry() {
        Log.i("Encoder Ticks", String.format("%d, %d, %d, %d", robot.rightFront.getCurrentPosition(), robot.leftFront.getCurrentPosition(),
                robot.rightRear.getCurrentPosition(), robot.leftRear.getCurrentPosition()));
        telemetry.addLine("Drive Encoder ticks")
                .addData("Front Left", -robot.rightFront.getCurrentPosition())
                .addData("Front Right", -robot.leftFront.getCurrentPosition())
                .addData("Back Left", -robot.rightRear.getCurrentPosition())
                .addData("Back Right", -robot.leftRear.getCurrentPosition());
        telemetry.addLine("Position Of Arm")
                .addData("", robot.arm.getCurrentPosition());
        telemetry.update();
    }
}
