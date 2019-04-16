package Robot.components.digital.devices;

import Robot.components.digital.ports.DIOPort;

public abstract class AbstractDigitalDevice {

    private final DIOPort portForInput;

    private final double voltageRequired;

    private final double currentRequired;

    public AbstractDigitalDevice(DIOPort portForInput,double voltageRequired, double currentRequired) {
        this.portForInput = portForInput;
        this.voltageRequired = voltageRequired;
        this.currentRequired = currentRequired;
    }


    public abstract void actionOnInputVoltage();


}
