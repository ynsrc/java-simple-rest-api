package ynsrc.simpleapiserver;

import java.util.ArrayList;
import java.util.List;

public class DummyDatabase {
    private static int autoIncrementId = 0;

    public static class DummyItem {
        private int id = 0;
        private String name = "";
        private String description = "";

        public DummyItem(String name, String description) {
            this.id = autoIncrementId++;
            this.name = name;
            this.description = description;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    public List<DummyItem> items = new ArrayList<>();

    public DummyDatabase() {
        items.add(new DummyItem("cat", "cat is meowing"));
        items.add(new DummyItem("dog", "dog is barking"));
        items.add(new DummyItem("bird", "bird is singing"));
    }

}
