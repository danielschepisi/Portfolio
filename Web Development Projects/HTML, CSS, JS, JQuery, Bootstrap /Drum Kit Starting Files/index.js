
// document.querySelector("button").addEventListener("click", handleClike);
document.querySelectorAll(".drum").forEach( (element) => {
  element.addEventListener("click", function () { 
    playSound(element.innerHTML);
})
});


document.addEventListener("keydown", function(e) {
playSound(e.key);
})


function playSound(drumLetter) {
  var soundURL = "./sounds/";

if (drumLetter === "w" ) {
  soundURL += "tom-1.mp3";
  } else if (drumLetter === "a"){
    soundURL += "tom-2.mp3"
  } else if (drumLetter === "s"){
    soundURL += "tom-3.mp3"
  }else if (drumLetter === "d"){
    soundURL += "tom-4.mp3"
  }else if (drumLetter === "j"){
    soundURL += "snare.mp3"
  }else if (drumLetter === "k"){
    soundURL += "crash.mp3"
  }else if (drumLetter === "l"){
    soundURL += "kick-bass.mp3"
  } else {
    return;
  }

  var audio = new Audio(soundURL);
  audio.play();

  var activeButton = document.querySelector("." + drumLetter);
  activeButton.classList.add("pressed");

  setTimeout(function () {
    activeButton.classList.remove("pressed");
  }, 100);

}


// {
// $0.addEventListener("click", function () { alert("From anon")})
// };



function handleClike() {
  alert("click got handled");
}