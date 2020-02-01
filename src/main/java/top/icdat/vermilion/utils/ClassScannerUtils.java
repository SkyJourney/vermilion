package top.icdat.vermilion.utils;

import top.icdat.vermilion.utils.scan.ScanExecutor;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassScannerUtils {

    public static Set<Class<?>> searchClasses(String...packageNames){
        return Stream.of(packageNames)
                .flatMap(packageName -> searchClasses(packageName).stream())
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> searchClasses(Predicate<Class<?>> predicate,String...packageNames){
        return Stream.of(packageNames)
                .flatMap(packageName -> searchClasses(predicate, packageName).stream())
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> searchClasses(String packageName){
        return searchClasses(null,packageName);
    }

    public static Set<Class<?>> searchClasses(Predicate<Class<?>> predicate, String packageName){
        return ScanExecutor.getInstance().search(packageName,predicate);
    }


}
