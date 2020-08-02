import reflection.api.Investigator;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class InvestigatorImpl implements Investigator {
    private Class<?> theClass;
    private Object theInstance;

    @Override
    public void load(Object anInstanceOfSomething) {
            this.theInstance = anInstanceOfSomething;
            this.theClass = anInstanceOfSomething.getClass();
    }

    @Override
    public int getTotalNumberOfMethods() {
        Method[] allMethods = theClass.getDeclaredMethods();
        return allMethods.length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        Constructor<?>[] allConstructors = theClass.getDeclaredConstructors();
        return allConstructors.length;
    }

    @Override
    public int getTotalNumberOfFields() {
        Field[] allFields = theClass.getDeclaredFields();
        return allFields.length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Set<String> allInterfacesNames = new HashSet<>();
        Class<?>[] allInterfaces = theClass.getInterfaces();
        for(Class<?> clazz: allInterfaces){
            allInterfacesNames.add(clazz.getSimpleName());
        }
        return allInterfacesNames;
    }

    @Override
    public int getCountOfConstantFields() {
        int counter = 0;
        Field[] allFields = theClass.getDeclaredFields();
        for(Field field:allFields){
            if(Modifier.isFinal(field.getModifiers())){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public int getCountOfStaticMethods() {
        int counter = 0;
        Method[] allMethods = theClass.getDeclaredMethods();
        for(Method method:allMethods){
            if(Modifier.isStatic(method.getModifiers())){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public boolean isExtending() {
        Class<?> superClass = theClass.getSuperclass();
        return (superClass != null && superClass != Objects.class);
    }

    @Override
    public String getParentClassSimpleName() {
        Class<?> superClass = theClass.getSuperclass();
        if(superClass == null){
            return null;
        }
        return superClass.getSimpleName();
    }

    @Override
    public boolean isParentClassAbstract() {
        Class<?> superClass = theClass.getSuperclass();
        if(superClass == null){
            return false;
        }else {
            return Modifier.isAbstract(superClass.getModifiers());
        }
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        Set<String> allFieldsNames = new HashSet<>();
        return getNamesOfAllInheritanceChainFieldsRec(allFieldsNames, this.theClass);
    }

    private Set<String> getNamesOfAllInheritanceChainFieldsRec(Set<String> fieldsNames, Class<?> superClass){
        Field[] allFields = superClass.getDeclaredFields();
        for(Field field:allFields){
            fieldsNames.add(field.getName());
        }

        if(superClass.getSuperclass() != null){
            getNamesOfAllInheritanceChainFieldsRec(fieldsNames, superClass.getSuperclass());
        }
        return fieldsNames;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args){
        int res = -1;
        Method[] allMethods = theClass.getDeclaredMethods();
        for(Method method:allMethods){
            if(Objects.equals(method.getName(), methodName)){
                try {
                    res = (int) method.invoke(this.theInstance, args);
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return -1;
                }
            }
        }
        return res;
    }
    

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        for (Constructor<?> constructor : this.theClass.getDeclaredConstructors()){
            if(constructor.getParameterCount() == numberOfArgs){
                try {
                    return constructor.newInstance(args);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        Method methodToInvoke;
        try {
            methodToInvoke = this.theClass.getDeclaredMethod(name, parametersTypes);
            methodToInvoke.setAccessible(true);
            return methodToInvoke.invoke(this.theInstance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        StringBuilder chain = new StringBuilder();
        chain.append(theClass.getSimpleName());
        Class<?> currentClass = theClass;
        while(currentClass.getSuperclass() != null){
            currentClass = currentClass.getSuperclass();
            chain.insert(0, delimiter);
            chain.insert(0, currentClass.getSimpleName());
        }
        return chain.toString();
    }
}
