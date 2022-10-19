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

        /* TODO: Initialize Motors
        *
        * You should initialize your motors at 0 power.
        *
        * robot.MotorName.setPower(0);
        */

        /* TODO: Reset Motors Encoders
         *
         * Reset your encoders so you work with fresh numbers
         * every single time you restart your robot
         *
         * EXAMPLE
         *
         * robot.MotorName.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         *
         */

        waitForStart();
        if(opModeIsActive()) {
            while (opModeIsActive()) {
                editHere();
                displayTelemetry();
                // Pace this loop so jaw action is reasonable speed.
                sleep(25);
            }
        }
    }

    public void editHere() {
        // TODO: Program your TeleOp!


    }

    public void displayTelemetry() {
        Log.i("Encoder Ticks", String.format("%d, %d, %d, %d"));
        telemetry.addLine("Drive Encoder ticks");
        telemetry.update();
    }
}
