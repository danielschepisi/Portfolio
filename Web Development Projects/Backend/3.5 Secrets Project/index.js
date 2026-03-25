//To see how the final website should work, run "node solution.js".
//Make sure you have installed all the dependencies with "npm i".
//The password is ILoveProgramming


import express from "express";
// import bodyParser from "body-parser"; // now in express
import { dirname } from "path";
import { fileURLToPath } from "url";
const __dirname = dirname(fileURLToPath(import.meta.url));

const app = express();
const port = 3000;

app.use(express.urlencoded({ extended: "true" }));

app.get("/", (req, res) => {
  res.sendFile(__dirname + "/public/index.html");
});

app.post("/check", (req, res) => {
console.log(req.body);
if (req.body.password === "XXX") {
  res.sendFile(__dirname + "/public/secret.html");
} else {
  // res.sendFile(__dirname + "/public/index.html"); 
  res.redirect("/");
}
});

app.listen(port); //could have anon func to log to tell if working