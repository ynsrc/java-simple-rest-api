package ynsrc.simpleapiserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private HttpMethod method = HttpMethod.GET;
    private String path = "";
    private String remoteAddress = "";
    private final Map<String, String> headers = new LinkedHashMap<>();
    private JSONObject json = new JSONObject();

    public JSONObject getPostedJson() { return this.json; }
    public JSONObject getJsonObject(String key) { return this.json.getJSONObject(key); }
    public JSONArray getJsonArray(String key) { return this.json.getJSONArray(key); }

    public String getJsonString(String key, String defaultValue) { return json.optString(key, defaultValue); }
    public int getJsonInt(String key, int defaultValue) { return json.optInt(key, defaultValue);}
    public boolean getJsonBoolean(String key, boolean defaultValue) { return json.optBoolean(key, defaultValue); }
    public double getJsonDouble(String key, double defaultValue) { return json.optDouble(key, defaultValue); }
    public double getJsonLong(String key, long defaultValue) { return json.optLong(key, defaultValue); }

    public String getRemoteAddress() { return remoteAddress; }

    public HttpMethod getMethod() { return method; }
    public String getPath() {
        return path.contains("?") ? path.split("\\?")[0] : path;
    }

    public boolean hasHeader(String name) { return headers.containsKey(name.toLowerCase()); }
    public String getHeader(String name, String defaultValue) { return headers.getOrDefault(name.toLowerCase(), defaultValue);}

    public Request(Socket socket) throws IOException {
        remoteAddress = socket.getRemoteSocketAddress().toString().replaceFirst("/", "");

        InputStream inputStream = socket.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) break;

            if (line.startsWith("GET") || line.startsWith("POST")) {
                String[] pathData = line.split(" ");
                String method = pathData[0];
                String path = pathData[1];
                this.method = (method.equals("POST")) ? HttpMethod.POST : HttpMethod.GET;
                this.path = path;
            } else if (line.contains(": ")) {
                String[] headerData = line.split(": ");
                String headerName = headerData[0];
                String headerValue = headerData[1];
                headers.put(headerName.toLowerCase(), headerValue);
            }
        }

        int contentLength = Integer.parseInt(getHeader("content-length", "0"));

        if (contentLength > 0) {
            int c;
            StringBuilder postedString = new StringBuilder();
            while ((c = reader.read()) != -1) {
                postedString.append((char) c);
                if (postedString.length() == contentLength) break;
            }
            json = new JSONObject(postedString.toString());
        }
    }
}
