import express from "express";
// import bodyParser from "body-parser"; // now in express
// import { dirname } from "path";
// import { fileURLToPath } from "url";
// const __dirname = dirname(fileURLToPath(import.meta.url));

const app = express();
const port = 3000;

const day = new Date().getDay(); // sun 0 - sat 6
// console.log("sdjf", day);

var dayType = "weekday";
var action = "work hard"

if (day === 0 || day === 6 ) {
dayType = "weekend";
action = "have fun";
}

app.get("/", (req, res) => {
res.render("index.ejs", 
{ dayType: dayType,
action: action
}
);
});

app.listen(port, () => {
  console.log(`Listening on port ${port}.`);
});