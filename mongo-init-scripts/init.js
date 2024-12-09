// Read JSON file
const fs = require("fs");

// Read JSON file synchronously
const rawData = fs.readFileSync("/docker-entrypoint-initdb.d/stores.json");
const jsonData = JSON.parse(rawData);

// MongoDB database
db = db.getSiblingDB("jumbo");

// Create user for the database
db.createUser({
  user: "jumbo",
  pwd: "jumbo",
  roles: [{ role: "readWrite", db: "jumbo" }],
});

// Create stores collection
db.createCollection("stores");

jsonData.stores.forEach((store) => {
  store.location = {
    type: "Point",
    coordinates: [
      parseFloat(store.longitude || 0),
      parseFloat(store.latitude || 0),
    ],
  };
});

// Add data from JSON file
db.stores.insertMany(jsonData.stores);

// db.stores.createIndex({ location: "2dsphere" });

print("Data has been successfully imported from stores.json to MongoDB!");
