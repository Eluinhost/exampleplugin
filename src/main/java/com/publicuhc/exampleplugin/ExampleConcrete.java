package com.publicuhc.exampleplugin;

/**
 * We don't use this class within the program itself and instead refer to the interface ExampleInterface.
 * Refering to the interface on injects allows for easy swapping of implementations just by switching the bindings
 * to point to a new implementation
 */
public class ExampleConcrete implements ExampleInterface {

    @Override
    public boolean isExample() {
        return true;
    }
}
