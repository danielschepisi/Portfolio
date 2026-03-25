import express from "express";
const app = express();
const port = 3000;

app.get("/", (req, res) => {
res.send("<h1>This is a h1 header!<h1>");
// console.log(req);
});

app.get("/about", (req, res) => {
  res.send("this is about");
});

app.get("/contact", (req, res) => {
  res.send("contact details");
});

app.listen(port, () => {
console.log(`Server running on port ${port}`);
});