package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.FrameworkJavaPlugin;
import com.publicuhc.pluginframework.routing.Router;
import com.publicuhc.pluginframework.routing.exception.CommandParseException;
import com.publicuhc.pluginframework.shaded.inject.AbstractModule;
import com.publicuhc.pluginframework.shaded.inject.Inject;
import com.publicuhc.pluginframework.shaded.inject.Module;

import java.util.List;

public class ExamplePlugin extends FrameworkJavaPlugin {

    @Inject private Router router;

    @Override
    public void onFrameworkEnable() {
        try {
            router.registerCommands(ExampleCommands.class);
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
    public void initialModules(List<Module> modules) {
        modules.addAll(getDefaultModules());
        modules.add(new AbstractModule() {
            @Override
            protected void configure() {
                //whenever we request an object of type ExampleInterface we will send an ExampleConcrete implementation
                bind(ExampleInterface.class).to(ExampleConcrete.class);
            }
        });
    }
}
