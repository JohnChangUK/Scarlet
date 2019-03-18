package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.model.Dislike;
import com.changmaidman.scarlet.model.Like;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class SentimentRouter {
    private static final Logger log = LoggerFactory.getLogger(SentimentRouter.class);

    Map<Class<? extends Sentiment>, List<Method>> sentimentRegistry;
    List<Method> methodList;

    void process(Class<? extends Annotation> annotation, Class<? extends Sentiment> sentimentClass) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.changmaidman.scarlet"))
                .setScanners(new MethodAnnotationsScanner(),
                        new TypeAnnotationsScanner(),
                        new SubTypesScanner())
                .filterInputsBy(new FilterBuilder()
                        .excludePackage("com.changmaidman.scarlet.test")));

        Set<Method> handlerMethods = reflections.getMethodsAnnotatedWith(annotation);
        Optional<? extends Class<?>> classOfMethod = reflections
                .getMethodsAnnotatedWith(annotation)
                .stream()
                .map(Method::getDeclaringClass)
                .findFirst();

        for (Method method : handlerMethods) {
            try {
                if (classOfMethod.isPresent()) {
                    methodList.add(method);
                    sentimentRegistry.put(sentimentClass, methodList);
                    Object newClass = classOfMethod.get().newInstance();
                    method.invoke(newClass);
                }
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                log.error("Error: ", e);
            }
        }
    }

    Optional<SentimentHandler> getSentimentHandler(Class<? extends Sentiment> sentiment) {
        return sentimentRegistry.keySet()
                .stream()
                .filter(classType -> classType.getCanonicalName().equals(sentiment.getCanonicalName()))
                .map(classType -> new SentimentHandler(classType, sentimentRegistry.get(classType)))
                .findFirst();
    }

    public static void main(String[] args) {
        Optional<SentimentHandler> likeHandler = LikeRouter.INSTANCE.getSentimentHandler(Like.class);
        Optional<SentimentHandler> dislikeHandler = DislikeRouter.INSTANCE.getSentimentHandler(Dislike.class);
        likeHandler.ifPresent(handler -> System.out.println(handler.getSentiment()));
        likeHandler.ifPresent(handler -> System.out.println(handler.getMethod()));
        dislikeHandler.ifPresent(handler -> System.out.println(handler.getSentiment()));
        dislikeHandler.ifPresent(handler -> System.out.println(handler.getMethod()));
    }
}
