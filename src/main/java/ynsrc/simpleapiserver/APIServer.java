package ynsrc.simpleapiserver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class APIServer {

    public <T> void run(Class<T> clazz, int port) {
        try {
            T serverInstance = clazz.getDeclaredConstructor().newInstance();

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Service started at http://127.0.0.1:" + port);

            Map<HttpMethod, Map<String, Method>> apiMap = new LinkedHashMap<>();

            for (HttpMethod httpMethod : HttpMethod.values()) {
                Map<String, Method> methodMap = new LinkedHashMap<>();
                apiMap.put(httpMethod, methodMap);
            }

            for (Method method : clazz.getMethods()) {
                for (Annotation methodAnnotation : method.getDeclaredAnnotations()) {
                    if (methodAnnotation.annotationType().isAnnotationPresent(HttpMethodType.class)) {
                        HttpMethodType httpMethodType = methodAnnotation.annotationType()
                                .getAnnotation(HttpMethodType.class);

                        String path = methodAnnotation.annotationType()
                                .getMethod("value").invoke(methodAnnotation).toString();

                        apiMap.get(httpMethodType.value()).put(path, method);
                    }
                }
            }

            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();

                Request request = new Request(client);
                Response response = new Response();

                System.out.println(request.getMethod().name() + " " + request.getPath() + " from " + request.getRemoteAddress());

                Method method = apiMap.get(request.getMethod()).getOrDefault(request.getPath(), null);

                if (method == null) {
                    response = Response.error(404, "NOT FOUND");
                } else {
                    try {
                        response = (Response) method.invoke(serverInstance, request);
                    } catch (Exception ex) {
                        response = Response.error(500, "An error occured in server");
                        ex.printStackTrace();
                    }
                }

                response.send(client);
                client.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public <T> void run(Class<T> clazz) {
        run(clazz, 5000);
    }
}
