package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.FrameworkJavaPlugin;
import com.publicuhc.pluginframework.routing.exception.CommandParseException;
import com.publicuhc.pluginframework.shaded.inject.AbstractModule;
import com.publicuhc.pluginframework.shaded.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class ExamplePlugin extends FrameworkJavaPlugin {

    @Override
    public void onFrameworkEnable() {
        try {
            getRouter().registerCommands(ExampleCommands.class);
        } catch(CommandParseException e) {
            e.printStackTrace();
        }
    }

    @Inject
    public void setExampleObject(ExampleObject object) {
        //before onFrameworkEnable GUICE will call this method with an ExampleObject which we can use here
    }

    @Inject
    public void setExampleInterface(ExampleInterface example) {
        //the exampleinterface we get here is actually an exampleconcrete due to the bindings provided
    }

    @Override
    public List<AbstractModule> initialModules() {
        List<AbstractModule> modules = new ArrayList<AbstractModule>();
        modules.add(new AbstractModule() {
            @Override
            protected void configure() {
                //whenever we request an object of type ExampleInterface we will send an ExampleConcrete implementation
                bind(ExampleInterface.class).to(ExampleConcrete.class);
            }
        });
        return modules;
    }
}
