package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.annotation.LikeHandler;
import com.changmaidman.scarlet.model.Sentiment;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class SentimentRouter {
    private static final Logger log = LoggerFactory.getLogger(SentimentRouter.class);

    Map<Class<? extends Sentiment>, Method> sentimentRegistry;

//    void processAnnotations() {
//        String pkg = "com.changmaidman.scarlet.annotation";
//        String routeAnnotation = pkg + ".LikeHandler";
//        try (ScanResult scanResult =
//                     new ClassGraph()
//                             .enableAllInfo()
//                             .scan()) {
//            for (ClassInfo classInfo : scanResult.getClassesWithMethodAnnotation(routeAnnotation)) {
//                System.out.println("Class info: " + classInfo);
//            }
//        }
//    }

    void processAnnotations(Class<? extends Annotation> annotation) {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forJavaClassPath())
                    .setScanners(new MethodAnnotationsScanner()));
            Set<Method> methods = reflections.getMethodsAnnotatedWith(annotation);

            for (Method m : methods) {
                try {
                    m.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Error: ", e);
                }
            }
        }

    public static void main(String[] args) {
        SentimentRouter sentimentRouter = new SentimentRouter();
        sentimentRouter.processAnnotations(LikeHandler.class);
    }
}
