/* 
1. Use the inquirer npm package to get user input.
2. Use the qr-image npm package to turn the user entered URL into a QR code image.
3. Create a txt file to save the user input using the native fs node module.
*/


import inquirer from 'inquirer';
import fs from "fs";
import qr from "qr-image";

inquirer  
  .prompt([{
    message: "Gimme URL",
    name: "URL"
  }
  ])
  .then((answers) => {
    const rul = answers.URL
    fs.writeFile("./dansURL", rul, (err) => {
      if (err) throw err;
      console.log('The file has been saved!');
    })

    var qr_svg = qr.image(rul);
    qr_svg.pipe(fs.createWriteStream('myqrcode3.png'));

  })
  .catch((error) => {
    if (error.isTtyError) {
      // Prompt couldn't be rendered in the current environment
    } else {
      // Something else went wrong
    }


  });