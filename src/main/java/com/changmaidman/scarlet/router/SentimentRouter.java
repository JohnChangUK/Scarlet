package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.service.SentimentService;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SentimentRouter {

    private static final Logger log = LoggerFactory.getLogger(SentimentRouter.class);

    Map<Class<?>, List<Method>> sentimentRegistry;
    List<Method> methodList;

    void process(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.changmaidman.scarlet"))
                        .setScanners(new MethodAnnotationsScanner(),
                                new TypeAnnotationsScanner(),
                                new SubTypesScanner())
                        .filterInputsBy(
                                new FilterBuilder()
                                        .excludePackage("com.changmaidman.scarlet.test")));

        Set<Method> handlerMethods = reflections.getMethodsAnnotatedWith(annotation);

        List<Class<?>> classOfMethod = reflections
                .getMethodsAnnotatedWith(annotation)
                .stream()
                .map(Method::getDeclaringClass)
                .collect(Collectors.toList());

//        methodList.addAll(handlerMethods);

        for (Method method : methodList) {
            for (Class clazz : classOfMethod) {
                if (method.getDeclaringClass().getCanonicalName().equals(clazz.getCanonicalName())) {
                    methodList = new ArrayList<>();
                    methodList.add(method);
                    sentimentRegistry.put(clazz, methodList);
                }

            }
            classOfMethod.forEach(clazz ->
                    sentimentRegistry.put(clazz, ));
        }

        classOfMethod.forEach(clazz ->
                sentimentRegistry.put(clazz,
                        methodList.stream()
                                .filter(method ->
                                        method.getDeclaringClass()
                                                .getCanonicalName()
                                                .equals(clazz.getCanonicalName())).findFirst()));
    }

    public Optional<SentimentPairHandler> getRegistryHandler(SentimentService service) {
        return sentimentRegistry.keySet()
                .stream()
                .filter(classType -> classType.isInstance(service))
                .map(classType -> new SentimentPairHandler(
                        classType, sentimentRegistry.get(classType)))
                .findFirst();
    }
}
