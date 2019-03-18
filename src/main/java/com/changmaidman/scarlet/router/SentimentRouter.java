package com.changmaidman.scarlet.router;

import com.changmaidman.scarlet.model.Dislike;
import com.changmaidman.scarlet.model.Like;
import com.changmaidman.scarlet.model.Sentiment;
import com.changmaidman.scarlet.service.DislikeService;
import com.changmaidman.scarlet.service.LikeService;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class SentimentRouter {
    private static final Logger log = LoggerFactory.getLogger(SentimentRouter.class);

    Map<Class<? extends Sentiment>, List<Method>> sentimentRegistry;
    Map<Optional<? extends Class<?>>, List<Method>> registry;
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
                    registry.put(classOfMethod, methodList);
                    sentimentRegistry.put(sentimentClass, methodList);
                    SentimentService service = (SentimentService) classOfMethod.get().newInstance();
                    method.invoke(service);
                }
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                log.error("Error: ", e);
            }
        }
    }

    public Optional<SentimentPairHandler> getRegistryHandler(SentimentService service) {
        return registry.keySet()
                .stream()
                .filter(classType -> classType.get().isInstance(service))
                .map(classType -> new SentimentPairHandler((Class<? extends SentimentService>) classType.get(), registry.get(classType)))
                .findFirst();
    }

    public Optional<SentimentHandler> getSentimentHandler(Sentiment sentiment) {
        return sentimentRegistry.keySet()
                .stream()
                .filter(classType -> classType.isInstance(sentiment))
                .map(classType -> new SentimentHandler(classType, sentimentRegistry.get(classType)))
                .findFirst();
    }

    public static void main(String[] args) {
//        Optional<SentimentHandler> likeHandler = LikeRouter.INSTANCE.getSentimentHandler(new Like());
        Optional<SentimentPairHandler> likePairHandler = LikeRouter.INSTANCE.getRegistryHandler(new LikeService());
        Optional<SentimentPairHandler> disLikePairHandler = DislikeRouter.INSTANCE.getRegistryHandler(new DislikeService());
//        Optional<SentimentHandler> dislikeHandler = DislikeRouter.INSTANCE.getSentimentHandler(new Dislike());
//        likeHandler.ifPresent(handler -> System.out.println(handler.getSentiment()));
//        likeHandler.ifPresent(handler -> System.out.println(handler.getMethod()));
        likePairHandler.ifPresent(likeHandler2 -> System.out.println(likeHandler2.getSentimentService()));
        likePairHandler.ifPresent(likeHandler2 -> System.out.println(likeHandler2.getMethod()));
        disLikePairHandler.ifPresent(likeHandler2 -> System.out.println(likeHandler2.getSentimentService()));
        disLikePairHandler.ifPresent(likeHandler2 -> System.out.println(likeHandler2.getMethod()));
//        dislikeHandler.ifPresent(handler -> System.out.println(handler.getSentiment()));
//        dislikeHandler.ifPresent(handler -> System.out.println(handler.getMethod()));
    }
}
