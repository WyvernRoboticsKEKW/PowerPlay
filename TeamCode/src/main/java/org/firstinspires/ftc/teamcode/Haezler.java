package org.firstinspires.ftc.teamcode;





import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class Haezler {
    public DcMotor topLeft;
    public DcMotor topRight;
    public DcMotor bottomLeft;
    public DcMotor bottomRight;
    public DcMotor cascadeMotor1;
    public DcMotor cascadeMotor2;
    public OpenCvCamera camera;

    public BNO055IMU IMU;


    public Haezler(HardwareMap hwmap){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        topLeft = hwmap.get(DcMotor.class, "topLeft");
        topRight = hwmap.get(DcMotor.class, "topRight");
        bottomLeft = hwmap.get(DcMotor.class, "bottomLeft");
        bottomRight = hwmap.get(DcMotor.class, "bottomRight");
        cascadeMotor1 = hwmap.get(DcMotor.class, "cascadeMotor1");
        cascadeMotor2 = hwmap.get(DcMotor.class, "cascadeMotor2");

        IMU = hwmap.get(BNO055IMU.class, "gyroscope");

        WebcamName webcamName = hwmap.get(WebcamName.class,"Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);

        topLeft.setZeroPowerBehavior(BRAKE);
        topRight.setZeroPowerBehavior(BRAKE);
        bottomLeft.setZeroPowerBehavior(BRAKE);
        bottomRight.setZeroPowerBehavior(BRAKE);

        cascadeMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cascadeMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cascadeMotor1.setTargetPosition(0);
        cascadeMotor2.setTargetPosition(0);
        cascadeMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cascadeMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        IMU.initialize(parameters);
    }

    public double getHeading(){
        Orientation angles = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        return(angles.thirdAngle);
    }
    // doesn't work need fix
    public double resetIMU(){
        Orientation reset = IMU.getAngularOrientation();

        return(reset.thirdAngle);
    }


}