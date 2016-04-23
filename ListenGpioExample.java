import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * This example code demonstrates how to setup a listener
 * for GPIO pin state changes on the Raspberry Pi. This example uses 4 buttons and 2 LED's  
 * 
 * @author Lars Birkmose
 * @version 21/4/2016
 */
public class ListenGpioExample {
    
    public static void main(String args[]) throws InterruptedException {
        
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");
        
        //Create LED's
        final AdjustableLED myLED;
        final AdjustableLED programRunningLED;
        
        myLED = new AdjustableLED(6);
        programRunningLED = new AdjustableLED(29);
        
        //Flash LED 2 times to show that connection is OK
        
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #07 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myUpButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myDimButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myOnButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myOffButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
        
        // create and register gpio pin listener for Up Button
        myUpButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                if(myUpButton.isHigh()) {
                    myLED.increaseBrightness();
                    System.out.println("Skruer op (10%) til " + myLED.getValue() + "%");
                }
                
            }
            
        });

        // create and register gpio pin listener for Dim Button
        myDimButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                if(myDimButton.isHigh()) {
                    System.out.println("Skruer stille ned ...");
                    myLED.dimToZero();
                }
            }
            
        });

        // create and register gpio pin listener for Off Button
        myOffButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                if(myOffButton.isHigh()) {
                    System.out.println("Slukker ...");
                    myLED.off();
                }
            }
            
        });
        
        // create and register gpio pin listener for Off Button
        myOnButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                
                if(myOnButton.isHigh()) {
                    System.out.println("Tænder ...");
                    myLED.on();
                }
            }
            
        });
        
        //System.out.println(" ... complete the GPIO #07 circuit and see the listener feedback here in the console.");
        
        // keep program running until user aborts (CTRL-C)
        for (;;) {
            if(programRunningLED.atLowestBrightness()) {
                programRunningLED.dimToMax();
            }
            else {
                programRunningLED.dimToZero();
            }
            Thread.sleep(500);
        }
        
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
    }
}

