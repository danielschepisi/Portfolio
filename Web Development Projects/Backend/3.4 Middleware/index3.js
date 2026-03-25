import express from "express";

const app = express();
const port = 3000;

app.use(dansMiddleWare);

app.get("/", (req, res) => {
  res.send("Hello");
});

app.listen(port, () => {
  console.log(`Listening on port ${port}`);
});


function dansMiddleWare(req, res, next) {
  console.log(req.url);
  console.log(req.method);
  next();
};