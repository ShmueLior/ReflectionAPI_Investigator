import reflection.api.Investigator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

public class InvestigatorImpl implements Investigator {
    private Class theClass;

    @Override
    public void load(Object anInstanceOfSomething) {
        try {
            this.theClass = anInstanceOfSomething.getClass();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public int getTotalNumberOfMethods() {
        Method[] allMethods;
        try {
            allMethods = theClass.getDeclaredMethods();
        } catch (Exception e){
            throw e;
        }
        return allMethods.length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        Constructor[] allConstructors;
        try {
             allConstructors = theClass.getDeclaredConstructors();
        }catch (Exception e) {
            throw e;
        }
        return allConstructors.length;
    }

    @Override
    public int getTotalNumberOfFields() {
        Field[] allFields;
        try {
            allFields = theClass.getDeclaredFields();
        }catch (Exception e){
            throw e;
        }
        return allFields.length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Set<String> allInterfacesNames = Collections.emptySet();
        Class[] allInterfaces;
        allInterfaces = theClass.getInterfaces();
        for(int i = 0; i< allInterfaces.length; i++) {
            allInterfacesNames.add(allInterfaces[i].getName());
        }
        return allInterfacesNames;
    }

    @Override
    public int getCountOfConstantFields() {
        return 0;
    }

    @Override
    public int getCountOfStaticMethods() {
        return 0;
    }

    @Override
    public boolean isExtending() {
        return false;
    }

    @Override
    public String getParentClassSimpleName() {
        return null;
    }

    @Override
    public boolean isParentClassAbstract() {
        return false;
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        return null;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        return 0;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        return null;
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        return null;
    }
}
