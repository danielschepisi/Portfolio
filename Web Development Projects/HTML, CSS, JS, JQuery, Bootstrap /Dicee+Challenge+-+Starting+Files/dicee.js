var randomNumber1 = Math.ceil(Math.random() * 6);
var randomNumber2 = Math.ceil(Math.random() * 6);

document.querySelector(".img1").src = "./images/dice" + randomNumber1 + ".png";
document.querySelector(".img2").src = "./images/dice" + randomNumber2 + ".png";

let headerElement = document.querySelector("h1");



if (randomNumber1 === randomNumber2) {
  headerElement.innerText = "Draw!";
} else if (randomNumber1 < randomNumber2) {
  headerElement.innerText = "Player 2 Wins!";
} else {
  headerElement.innerText = "Player 1 Wins!";
}