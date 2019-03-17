package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.Sentiment;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SentimentRouter {
    private static final Logger log = LoggerFactory.getLogger(SentimentRouter.class);

    Map<Class<? extends Sentiment>, Method> sentimentRegistry;

    void processAnnotations(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.changmaidman.scarlet"))
                .setScanners(new MethodAnnotationsScanner(), new TypeAnnotationsScanner(),
                        new SubTypesScanner())
                .filterInputsBy(new FilterBuilder().excludePackage("com.changmaidman.scarlet.test")));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);
        Optional<? extends Class<?>> annotated = reflections.getMethodsAnnotatedWith(annotation)
                .stream()
                .map(Method::getDeclaringClass)
                .findFirst();

        for (Method m : methods) {
            try {
                if (annotated.isPresent()) {
                    Object newClass = annotated.get().newInstance();
                    m.invoke(newClass);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("Error: ", e);
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SentimentRouter sentimentRouter = new SentimentRouter();
        sentimentRouter.processAnnotations(LikeHandler.class);
    }
}
