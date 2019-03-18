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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class SentimentRouter {

    private static final Logger log = LoggerFactory.getLogger(SentimentRouter.class);

    Map<Optional<? extends Class<?>>, List<Method>> sentimentRegistry;
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

        Optional<? extends Class<?>> classOfMethod = reflections
                .getMethodsAnnotatedWith(annotation)
                .stream()
                .map(Method::getDeclaringClass)
                .findFirst();

        for (Method method : handlerMethods) {
            if (classOfMethod.isPresent()) {
                methodList.add(method);
                sentimentRegistry.put(classOfMethod, methodList);
            }
        }
    }

    public Optional<SentimentPairHandler> getRegistryHandler(SentimentService service) {
        return sentimentRegistry.keySet()
                .stream()
                .filter(classType -> classType.get().isInstance(service))
                .map(classType -> new SentimentPairHandler(
                        classType.get(), sentimentRegistry.get(classType)))
                .findFirst();
    }
}
