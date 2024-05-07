package ynsrc.simpleapiserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    private int statusCode = 200;
    private String statusMessage = "OK";
    private String text = "";
    private Map<String, String> headers = new LinkedHashMap<>();

    public Response() { }
    public Response(Object serializableObject) {
        if (serializableObject instanceof JSONObject) {
            this.text = ((JSONObject) serializableObject).toString(4);
        } else if (serializableObject instanceof JSONArray) {
            this.text = ((JSONArray) serializableObject).toString(4);
        } else  {
            this.text = new JSONObject(serializableObject).toString(4);
        }
    }

    public static Response error(int statusCode, String errorMessage) {
        JSONObject jsError = new JSONObject();
        jsError.put("error", errorMessage);
        Response response = new Response(jsError);
        response.setStatus(statusCode, errorMessage);
        return response;
    }

    public static Response error(String errorMessage) {
        return error(500, errorMessage);
    }

    public void setStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public void setText(String text) { this.text = text; }
    public void setJson(JSONObject jsonObject) { this.text = jsonObject.toString(4); }
    public void setJson(JSONArray jsonArray) { this.text = jsonArray.toString(4); }

    public boolean hasHeader(String name) { return this.headers.containsKey(name); }
    public void setHeader(String name, String value) { this.headers.put(name, value); }
    public void getHeader(String name, String defaultValue) { this.headers.getOrDefault(name, defaultValue); }

    public void send(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        StringBuilder responseText = new StringBuilder("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");
        responseText.append("Content-Type: application/json\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseText.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }

        responseText.append("\r\n");

        responseText.append(text);

        outputStream.write(responseText.toString().getBytes(StandardCharsets.UTF_8));
    }
}
