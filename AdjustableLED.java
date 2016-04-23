import com.pi4j.wiringpi.*;
/**
 * This class represents a PWM LED connected to the Raspberry pi on a given pin (default pin 6)
 * 
 * Connect the physical LED to a GND pin and to a GPIO pin.
 * 
 * 
 * @author Fabio Hedayioglu 
 * @author Ian Utting
 * @version 1.0
 * 
 */
public class AdjustableLED
{
    //The LED brightness level
    private int value;
    
    private final int PinNumber;
    
    //Constants
    private final int MAX_BRIGHTNESS = 100;
    private final int MIN_BRIGHTNESS = 0;
    

    /**
     * Creates the Adjustable LED.
     * 
     
    public AdjustableLED()
    {
        this(PinNumber);
    }*/
    
    /**
     * Creates the Adjustable LED in a given pin number.
     * 
     */
    public AdjustableLED(int pin)
    {
        Gpio.wiringPiSetup();
        //Sets the PinNumber pin to be a PWM pin, with values changing from 0 to 100
        SoftPwm.softPwmCreate(pin,MIN_BRIGHTNESS,MAX_BRIGHTNESS);
        value = MIN_BRIGHTNESS;
        //Sets the LED to 0 brightness
        SoftPwm.softPwmWrite(pin, value);
        
        PinNumber = pin;
    }
    
    
    
    /**
     * Set the LED value, valid values are integers from 0 to 100, inclusive.
     * 
     */
    public void setValue(int desiredValue)
    {
        //checks if the desired value is valid. if so, proceed.
        if (desiredValue >= MIN_BRIGHTNESS && desiredValue <= MAX_BRIGHTNESS){
            //update the local value.
            value = desiredValue;
            //write white the desired value to the pin.
            SoftPwm.softPwmWrite(PinNumber, value);
        }
    }
    
    /**
     * Returns the current value of the LED.
     * @return integer from 0 to 100 with the LED current value.
     * 
     */
    public int getValue()
    {
        return value;
    }
    
    public void on() {
        value = MAX_BRIGHTNESS;
        //write white the desired value to the pin.
        SoftPwm.softPwmWrite(PinNumber, value);
    }

    public void off() {
        value = MIN_BRIGHTNESS;
        //write white the desired value to the pin.
        SoftPwm.softPwmWrite(PinNumber, value);
    }
    
    public boolean atLowestBrightness() {
        if(value == MIN_BRIGHTNESS) {
            return true;
        }
        else {
            return false;
        }
    }

    public void increaseBrightness() {
        int increment = MAX_BRIGHTNESS / 10;
        
        if(value < MAX_BRIGHTNESS) {
            value += increment;
            //write white the desired value to the pin.
            SoftPwm.softPwmWrite(PinNumber, value);
        }
    }
    
    public void decreaseBrightness() {
        int increment = MAX_BRIGHTNESS / 10;
        
        if(value > MIN_BRIGHTNESS) {
            value -= increment;
            //write white the desired value to the pin.
            SoftPwm.softPwmWrite(PinNumber, value);
        }
    }
    
    public void dimToZero() {
        
        while(value > MIN_BRIGHTNESS) {
            value --;
            //write white the desired value to the pin.
            SoftPwm.softPwmWrite(PinNumber, value);
            try{
                Thread.sleep(18);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
    
    
    public void dimToMax() {
        
        while(value < MAX_BRIGHTNESS) {
            value ++;
            //write white the desired value to the pin.
            SoftPwm.softPwmWrite(PinNumber, value);
            try{
                Thread.sleep(18);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}

