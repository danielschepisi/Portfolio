let buttonColors = ["red", "blue", "green", "yellow"];
var gamePattern = [];
var currentSequencePosition = 0;
var gameActive = false;

$(document).on("keydown", resetGame)

function resetGame() {
  gamePattern = [];
  currentSequencePosition = 0;
  nextSequence();
  gameActive = true;
  $("h1").text("Good Luck")
}

function nextSequence() {
let randomNumber  = Math.floor(Math.random()*4);
let chosenColor = buttonColors[randomNumber];
gamePattern.push(chosenColor);
currentSequencePosition = 0;

//show next color
setTimeout(function() {
  $("." + chosenColor).addClass("pressed");
var audio = new Audio('./sounds/' + chosenColor + '.mp3');
  audio.play();
}, 400)

setTimeout(function() {
  $("." + chosenColor).removeClass("pressed");
}, 550)
}

// $(".btn").on("click", handleButtonClick);
$(".btn").click(handleButtonClick);

function handleButtonClick(event) {
  if (!gameActive) { return };
  let btnPressed = event.target;
  btnPressed.classList.add("pressed");

if (currentSequencePosition <= gamePattern.length - 1) {
  //if correct
  if (gamePattern[currentSequencePosition] === String(btnPressed.id)) {
    var audio = new Audio('./sounds/' + String(btnPressed.id) + '.mp3');
    audio.play();
    currentSequencePosition++;
  } else { //if wrong
    var audio = new Audio('./sounds/wrong.mp3');
    audio.play();
    gameActive = false;
    $('body').addClass("game-over");
    setTimeout(function() {
      $('body').removeClass("game-over");
      }, 150)
    $("h1").text("Press A key to start");
  }
} 

if (currentSequencePosition == gamePattern.length) {
  nextSequence();
}

//remove class in a tick
setTimeout(function() {
btnPressed.classList.remove("pressed");
}, 150)
}

function showButtonPressedUI() {

}