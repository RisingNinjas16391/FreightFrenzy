package org.firstinspires.ftc.teamcode;
/* Copyright (c) 2017 FIRST. All rights reserved.
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
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

/**
 * This is NOT an opmode.
 * <p> i code lol
 * This class can be used to define all the specific hardware for a single robot.
 */
public class Hardware {
    private final ElapsedTime period = new ElapsedTime();
    /* Public OpMode members. */
    public DcMotor rightFront;
    public DcMotor leftFront;
    public DcMotor rightRear;
    public DcMotor leftRear;
    public DcMotor arm;
    //public DcMotor armLeft;
    public DcMotor feederLeft;
    public DcMotor feederRight;
    public DcMotor carousel;

    WebcamName webcamName;

    //public DcMotor carouselSpinner;
//    public DcMotorEx flywheel;
//    public DcMotor intake;
//    public Servo gate;
//    public Servo gate1;
    /* local OpMode members. */
    HardwareMap hwMap = null;

    /* Constructor */
    public Hardware() {

    }
    public static class armPositions {
        final public static int FEED            = -7;
        final public static int BOTTOM          = -250;
        final public static int MIDDLE          = -450;
        final public static int TOP             = -710;
        final public static int INVERSETOP      = -2520;
        final public static int INVERSEMIDDLE   = -2810;
        final public static int INVERSEBOTTOM   = -2980;
        final public static int CAPUP           = -2200;
        final public static int CAPDOWN         = -2450;
        final public static int INVERSEFEED     = -3350;

        public armPositions() {

        }

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors


        //map motors
        rightFront  = hwMap.get(DcMotor.class, "right front");
        leftFront   = hwMap.get(DcMotor.class, "left front");
        rightRear   = hwMap.get(DcMotor.class, "right rear");
        leftRear    = hwMap.get(DcMotor.class, "left rear");
        arm = hwMap.get(DcMotor.class, "arm");
        feederLeft  = hwMap.get(DcMotor.class, "feeder left");
        feederRight  = hwMap.get(DcMotor.class, "feeder right");
        carousel = hwMap.get(DcMotor.class, "carousel");

        //forward or backward settings
        rightFront  .setDirection(DcMotor.Direction.REVERSE);
        leftFront   .setDirection(DcMotor.Direction.FORWARD);
        rightRear   .setDirection(DcMotor.Direction.REVERSE);
        leftRear    .setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.REVERSE);
        feederLeft  .setDirection(DcMotor.Direction.FORWARD);
        feederRight  .setDirection(DcMotor.Direction.REVERSE);
        carousel.setDirection(DcMotor.Direction.FORWARD);

        //encoder settings
        rightFront  .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront   .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear   .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear    .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        feederLeft  .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        feederRight .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        carousel.setMode(DcMotor.RunMode. RUN_WITHOUT_ENCODER);

        //brake settings
        rightFront  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront   .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear   .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear    .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        feederRight .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        feederLeft  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carousel  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
}