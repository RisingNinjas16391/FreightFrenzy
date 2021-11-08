/* Copyright (c) 2019 FIRST. All rights reserved.
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
package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Freight Frenzy game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Autonomous", group = "Red")
//@Disabled
public class Auto extends LinearOpMode {
    /* Note: This sample uses the all-objects Tensor Flow model (FreightFrenzy_BCDM.tflite), which contains
     * the following 4 detectable objects
     *  0: Ball,
     *  1: Cube,
     *  2: Duck,
     *  3: Marker (duck location tape marker)
     *
     *  Two additional model assets are available which only contain a subset of the objects:
     *  FreightFrenzy_BC.tflite  0: Ball,  1: Cube
     *  FreightFrenzy_DM.tflite  0: Duck,  1: Marker
     */
    Hardware robot = new Hardware();   //Uses heavily modified untested hardware
    static ElapsedTime runtime = new ElapsedTime();
    List<Recognition> updatedRecognitions;
    //int level;

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AYNvGMT/////AAABmbqx+cB66Uiuq1x4OtVVYaYqPxwETuoHASIFChwOE0FE5KyJCGATrLe9r1HPlycJfKg4" +
                    "LyYDwGfvzkJ5Afan80ksPhJFg+93fhn8xNTgV09R7cY6VtUrO61/myrjehuHoRU6UH8hAZdlV0E" +
                    "6/Q1y36TSsp0iaOWX008UCFI/jJo/UoWG7y6uZsPH5MJxGucu6jWBjERv+bS9zHvsGFDlGmIFdJi" +
                    "c2YbYP+SpUM+KK437815Iz/PxAAAK+1SUObQVGiVj/FuqB5yhSvBrkX1H1NQ2jzZDfNfzEQr5cHM" +
                    "zU68IOGhxd+yjicwx7ppxaAcFlrPE8hILKAQ90k5i6gwY1vzHwapOgLA5PSI0jsX1z/Dg";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.25, 30.0/9.0);
        }
        robot.init(hardwareMap);
        robot.rightFront.setPower(0);
        robot.leftFront .setPower(0);
        robot.rightRear .setPower(0);
        robot.leftRear  .setPower(0);
        robot.arm.setPower(0);
        //robot.armLeft     .setPower(0);
        robot.feederLeft.setPower(0);
        robot.feederRight.setPower(0);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightRear .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftRear  .setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        //robot.carouselSpinner .setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRear .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRear  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.armLeft   .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.feederLeft  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.feederRight  .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        robot.arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.arm.setPower(0.10);
        Log.i("Robot", "standby");
        waitForStart();
        if (opModeIsActive()) {
            int path = 3;
            runtime.reset();
            robot.arm.setPower(0);
            robot.arm.setTargetPosition(0);
            robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.arm.setTargetPosition(0);
            robot.arm.setPower(1);
            while (opModeIsActive() && runtime.milliseconds() < 3000) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals("Duck")) {
                                if (recognition.getLeft() <= 400) {
                                    path = 1;
                                } else {
                                    path = 0;
                                }
                            }
                            Log.i("Robot", String.format("Duck found at %f", recognition.getLeft()));
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            i++;
                        }
                        telemetry.update();
                    }
                }
            }
            if (path == 1) {
                //Third barcode
                pathOne();
            } else if (path == 2) {
                //second path
                pathTwo();
            } else if (path == 3) {
                //first path
                pathThree();
            } else {
                telemetry.addLine("Error: no barcode detected");
                telemetry.update();
                while (opModeIsActive()) {
                    sleep(25);
                }
                return;
            }
            drive(250, 250, 0, 250, 0.5, 10000);
            drive(500, 500, 500, 500, 0.5, 2000);
            robot.feederRight.setPower(-4);
            robot.feederLeft.setPower(-4);
            sleep(1000);
        }
    }

    public void pathOne() {
        telemetry.addLine("Level 1");
        telemetry.update();
        robot.arm.setTargetPosition(Hardware.armPositions.BOTTOM);


    }

    public void pathTwo() {
        telemetry.addLine("Level 2");
        telemetry.update();
        robot.arm.setTargetPosition(Hardware.armPositions.MIDDLE);

    }

    public void pathThree() {
        telemetry.addLine("Level 3");
        telemetry.update();
        robot.arm.setTargetPosition(Hardware.armPositions.TOP);

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

        robot.rightFront.setTargetPosition(rightFront);
        robot.leftFront .setTargetPosition(leftFront);
        robot.rightRear .setTargetPosition(rightRear);
        robot.leftRear  .setTargetPosition(leftRear);

        robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftFront .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightRear .setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftRear  .setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.rightFront.setPower(power);
        robot.leftFront .setPower(power);
        robot.rightRear .setPower(power);
        robot.leftRear  .setPower(power);
        runtime.reset();
        while(timeOut > runtime.milliseconds() && opModeIsActive()) {
            displayTelemetry();
            sleep(25);
        }
    }

    public void displayTelemetry() {
        Log.i("Robot", String.format("%d, %d, %d, %d", robot.rightFront.getCurrentPosition(), robot.leftFront.getCurrentPosition(),
                robot.rightRear.getCurrentPosition(), robot.leftRear.getCurrentPosition()));
        telemetry.addLine("Drive Encoder ticks")
                .addData("Front Left", robot.rightFront.getCurrentPosition())
                .addData("Front Right", robot.leftFront.getCurrentPosition())
                .addData("Back Left", robot.rightRear.getCurrentPosition())
                .addData("Back Right", robot.leftRear.getCurrentPosition());
        telemetry.update();
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.67f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
