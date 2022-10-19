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
    // TODO: Create motors! See Example Below:
    // public DcMotor <motor name>;


    /* local OpMode members. */
    HardwareMap hwMap = null;

    /* Constructor */
    public Hardware() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // TODO: Define and Initialize Motors! See Example Below:

        /* Mapping Motors!

        * You must map the motors to the name you create
        * in the control hub configuration file on the
        * driver station phone!
        *
        * EXAMPLE
        *
        * MotorName = ahwMap.get(DcMotor.class, "Motor Name");

        */

        // TODO: Set Motor Direction!

        /* Setting Motor Directions!
        *
        * You must set the motors to turn a specific direction
        * so that they turn in the desired direction!
        *
        * EXAMPLE
        *
        * MotorName.setDirection(DcMotor.Direction.FORWARD);
        * OR
        * MotorName.setDirection(DcMotor.Direction.REVERSE);
        *
        */

        // TODO: Set Motor Encoder Settings!

        /* Setting Motor Encoder Settings!
         *
         * An ENCODER is hardware that keeps track of the amount of
         * rotations a motor makes. Theoretically it allows your
         * motors to move in a consistent manner.
         *
         * EXAMPLE
         *
         * MotorName.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         * OR
         * MotorName.setMode(DcMotor.Direction.RUN_WITHOUT_ENCODER);
         *
         */

        // TODO: Set Motor Brake Mode!

        /* Setting Motor Brakes!
         *
         * Motors have a brake mode that, when they are not supplied power,
         * would either drift (coast mode) or actively fight to not move
         * (brake mode)
         *
         * EXAMPLE
         *
         * MotorName.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         * OR
         * MotorName.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.COAST);
         *
         */
    }
}