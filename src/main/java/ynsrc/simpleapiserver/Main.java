package ynsrc.simpleapiserver;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    DummyDatabase db = new DummyDatabase();

    @API.Get("/")
    public Response index(Request request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "A Simple REST API Example with Java");
        String[] actions = { "add", "list", "delete" };
        jsonObject.put("actions", new JSONArray(actions));
        jsonObject.put("version", "1.0.0");
        return new Response(jsonObject);
    }

    @API.Post("/add")
    public Response addItem(Request request) {
        String name = request.getJsonString("name", "");
        String description = request.getJsonString("description", "");

        if (name.isEmpty() || description.isEmpty()) {
            return Response.error("name and description parameters are required");
        } else  {
            var item = new DummyDatabase.DummyItem(name, description);
            db.items.add(item);

            return new Response(item);
        }
    }

    @API.Get("/list")
    public Response listItems(Request request) {
        var jsonObject = new JSONObject();
        jsonObject.put("count", db.items.size());
        jsonObject.put("list", db.items);
        return new Response(jsonObject);
    }

    @API.Post("/delete")
    public Response delete(Request request) {
        int id = request.getJsonInt("id", -1);

        if (id == -1) {
            return Response.error("id parameter is required");
        } else  {
            db.items.remove(id);

            var jsonObject = new JSONObject();
            jsonObject.put("deleted", id);
            return new Response(jsonObject);
        }
    }

    public static void main(String[] args) {
        APIServer apiServer = new APIServer();
        apiServer.run(Main.class);
    }
}