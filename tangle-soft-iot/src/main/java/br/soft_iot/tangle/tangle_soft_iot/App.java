package br.soft_iot.tangle.tangle_soft_iot;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        ControllerImpl ctr = new ControllerImpl();
        ctr.start();
    }
}
