# Simple REST API with pure Java

This is an example REST API project.

Default port is 5000, you can change it in `Main.java` or `APIServer.java`.

## Index
```
$ curl "http://127.0.0.1:5000/"
{
    "name": "A Simple REST API Example with Java",
    "actions": [
        "add",
        "list",
        "delete"
    ],
    "version": "1.0.0"
}
```

## List Items
```
$ curl "http://127.0.0.1:5000/list"
{
    "count": 3,
    "list": [
        {
            "name": "cat",
            "description": "cat is meowing",
            "id": 0
        },
        {
            "name": "dog",
            "description": "dog is barking",
            "id": 1
        },
        {
            "name": "bird",
            "description": "bird is singing",
            "id": 2
        }
    ]
}
```

## Delete Item
```
$ curl "http://127.0.0.1:5000/delete" -H "content-type: application/json" -d '{"id": 1}'
{"deleted": 1}
```

## Add Item
```
$ curl "http://127.0.0.1:5000/add" -H "content-type: application/json" -d '{"name":"fish", "description":"fish is swimming"}'
{
    "name": "fish",
    "description": "fish is swimming",
    "id": 3
}
```

## List Again
```
$ curl "http://127.0.0.1:5000/list"
{
    "count": 3,
    "list": [
        {
            "name": "cat",
            "description": "cat is meowing",
            "id": 0
        },
        {
            "name": "bird",
            "description": "bird is singing",
            "id": 2
        },
        {
            "name": "fish",
            "description": "fish is swimming",
            "id": 3
        }
    ]
}
```

# License
The Unlicense. Feel free to use or change it how you need.