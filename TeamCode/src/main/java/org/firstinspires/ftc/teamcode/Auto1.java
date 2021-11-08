/*/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="A1", group="Autonomous")
//@Disabled
public class Auto1 extends LinearOpMode {
    /* Declare OpMode members. */
    static Hardware robot = new Hardware();
    static ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.setAutoClear(false);
        telemetry.addLine("Status: Booting");
        telemetry.update();

        robot.init(hardwareMap);
        telemetry.addData("Robot initialized: ", true);
        telemetry.update();

        robot.rightFront.setPower(0);
        robot.leftFront .setPower(0);
        robot.rightRear .setPower(0);
        robot.leftRear  .setPower(0);
        robot.arm       .setPower(0);
        robot.feederLeft.setPower(0);
        robot.feederRight.setPower(0);
        robot.arm       .setPower(0);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm       .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.feederLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.feederRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRear .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRear  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.arm       .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.feederLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.feederRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftFront .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightRear .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftRear  .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.arm       .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.feederLeft.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.feederRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Send telemetry message to signify robot waiting;
        telemetry.addLine("Waiting for start");
        telemetry.update();
        telemetry.setAutoClear(true);



        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        robot.arm.setPower(1);
        editHere();
    }


    public void editHere() {
        drive(1000,1000,1000,1000,1, 5000);
        sleep(1000);
        //robot.arm.setTargetPosition(0);

        //robot.feederLeft.setPower(0);
        //robot.feederLeft.setPower(0);
    }

    public void drive (int rightFront, int leftFront, int rightRear, int leftRear, double power, int timeOut) {
        robot.rightFront.setPower(0);
        robot.leftFront .setPower(0);
        robot.rightRear .setPower(0);
        robot.leftRear  .setPower(0);

        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.rightFront.setTargetPosition(-rightFront);
        robot.leftFront .setTargetPosition(-leftFront);
        robot.rightRear .setTargetPosition(-rightRear);
        robot.leftRear  .setTargetPosition(-leftRear);

        robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftFront .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightRear .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftRear  .setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.rightFront.setPower(power);
        robot.leftFront .setPower(power);
        robot.rightRear .setPower(power);
        robot.leftRear  .setPower(power);
        runtime.reset();
        while(timeOut > runtime.milliseconds()) {
            displayTelemetry();
            sleep(25);
        }
    }

    public void displayTelemetry() {
        telemetry.addLine("Drive Encoder ticks")
                .addData("Front Left", robot.rightFront.getCurrentPosition())
                .addData("Front Right", robot.leftFront.getCurrentPosition())
                .addData("Back Left", robot.rightRear.getCurrentPosition())
                .addData("Back Right", robot.leftRear.getCurrentPosition());
        telemetry.update();
    }
}